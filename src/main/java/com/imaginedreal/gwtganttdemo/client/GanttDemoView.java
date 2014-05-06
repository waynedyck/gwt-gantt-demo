package com.imaginedreal.gwtganttdemo.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.cell.client.EditTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;
import com.imaginedreal.gwtgantt.DateUtil;
import com.imaginedreal.gwtgantt.gantt.GanttChart;
import com.imaginedreal.gwtgantt.gantt.ProvidesTask;
import com.imaginedreal.gwtgantt.gantt.ProvidesTaskImpl;
import com.imaginedreal.gwtgantt.model.Task;
import com.imaginedreal.gwtgantt.table.GridView;
import com.imaginedreal.gwtgantt.table.TaskGridColumn;
import com.imaginedreal.gwtgantt.table.TaskGridNameCell;
import com.imaginedreal.gwtgantt.table.override.CellDateImpl;
import com.imaginedreal.gwtgantt.table.override.CellTextImpl;

public class GanttDemoView extends Composite {

    /**
     * The UiBinder interface.
     */ 
    interface GanttDemoViewUiBinder extends UiBinder<Widget, GanttDemoView> {
    }
    
    /**
     * The UiBinder used to generate the view.
     */
    private static GanttDemoViewUiBinder uiBinder = GWT
            .create(GanttDemoViewUiBinder.class);

    @UiField
    Button zoomInButton;
    
    @UiField
    Button zoomOutButton;
    
    @UiField
    Button refreshButton;
    
    @UiField(provided = true)
    GridView<Task> gridView;
    
    @UiField(provided = true)
    GanttChart<Task> ganttChart;
    
    private SelectionModel<Task> selectionModel = new SingleSelectionModel<Task>();
    private ListDataProvider<Task> taskDataProvider = new ListDataProvider<Task>();
    
    public GanttDemoView() {
        ProvidesTask<Task> provider = new ProvidesTaskImpl();
        gridView = new GridView<Task>();
        ganttChart = new GanttChart<Task>(provider);
        
        List<TaskGridColumn> columns = getColumns();
        gridView.addColumns(columns);
        
        gridView.setSelectionModel(selectionModel);
        ganttChart.setSelectionModel(selectionModel);
        taskDataProvider.addDataDisplay(gridView);
        taskDataProvider.addDataDisplay(ganttChart);
        List<? extends Task> taskList = DataGenerator.generateTasks();
        taskDataProvider.setList((List<Task>) taskList);
        
        initWidget(uiBinder.createAndBindUi(this));
    }
    
    @UiHandler("zoomInButton")
    void onZoomInClick(ClickEvent e) {
        ganttChart.zoomIn();
    }
    
    @UiHandler("zoomOutButton")
    void onZoomOutClick(ClickEvent e) {
        ganttChart.zoomOut();
    }
    
    @UiHandler("refreshButton")
    void onClick(ClickEvent e) {
        taskDataProvider.refresh();
    }
    
    public List<TaskGridColumn> getColumns() {

        String shortFormatPattern = "MM/dd/yy";
        String dayFormatPattern = "EEE";
        DateTimeFormat format = DateTimeFormat.getFormat(
                dayFormatPattern + " " + shortFormatPattern);
        
        List<TaskGridColumn> colums = new ArrayList<TaskGridColumn>();

        // ORDER COLUMN
        Column<Task, String> orderColumn = new Column<Task, String>(
                new TextCell()) {

            @Override
            public String getValue(Task object) {
                return String.valueOf(object.getOrder());
            }
        };

        // NAME COLUMN
        Column<Task, Task> nameColumn = new Column<Task, Task>(
                new TaskGridNameCell(){
                    @Override
                    public void onExpandCollapse(Task task) {
                        taskDataProvider.refresh();
                    }
                }) {

            @Override
            public Task getValue(Task object) {
                return object;
            }
        };
        
        nameColumn.setFieldUpdater(new FieldUpdater<Task, Task>() {
            public void update(int index, Task object, Task value) {
                // Called when the user changes the value.
                // object.setName(value);
            }
        });

        // DURATION COLUMN
        Column<Task, String> durationColumn = new Column<Task, String>(
                new CellTextImpl(CellTextImpl.ALIGN_RIGHT) {
                      public String validate(String newValue, String oldValue) {
                          return newValue; // was oldValue. Why?
                      }
                }) {

            @Override
            public String getValue(Task object) {
                return String.valueOf(object.getDuration());
            }
        };
        durationColumn.setFieldUpdater(new FieldUpdater<Task, String>() {
            public void update(int index, Task object, String value) {
                // Called when the user changes the value.
                // object.setper
                GWT.log("setting duration");
                //object.setDuration(0);
                object.setDuration(Double.parseDouble(value));
                object.setFinish(new Date(new Date().getYear(),new Date().getMonth(), new Date().getDate()+(int)object.getDuration()));
                taskDataProvider.refresh();
            }
        });

        // START COLUMN
        Column<Task, Date> startColumn = new Column<Task, Date>(
                //new DateCell(format)) {
                new CellDateImpl(format)) { // Not sure why this one wasn't used.
            @Override
            public Date getValue(Task object) {
                return object.getStart();
            }
        };
        startColumn.setFieldUpdater(new FieldUpdater<Task, Date>() {
            public void update(int index, Task object, Date value) {
                object.setStart(value);
                object.setDuration(DateUtil.differenceInDays(object.getFinish(), object.getStart()));
                taskDataProvider.refresh();
            }
        });

        // FINISH COLUMN
        Column<Task, Date> finishColumn = new Column<Task, Date>(
                //new DateCell(format)) {
                new CellDateImpl(format)) { // Not sure why this one wasn't used.
            @Override
            public Date getValue(Task object) {
                return object.getFinish();
            }
        };
        finishColumn.setFieldUpdater(new FieldUpdater<Task, Date>() {
            public void update(int index, Task object, Date value) {
                object.setFinish(value);
                object.setDuration(DateUtil.differenceInDays(object.getFinish(), object.getStart()));
                taskDataProvider.refresh();
            }
        });

        // PREDECESSOR COLUMN
        Column<Task, String> predecessorColumn = new Column<Task, String>(
                new EditTextCell()) {
            @Override
            public String getValue(Task object) {
                return "";
            }
        };
        predecessorColumn.setFieldUpdater(new FieldUpdater<Task, String>() {
            public void update(int index, Task object, String value) {

            }
        });

        // RESOURCE COLUMN
        Column<Task, String> resourceColumn = new Column<Task, String>(
                new EditTextCell()) {
            @Override
            public String getValue(Task object) {
                return "";
            }
        };
        resourceColumn.setFieldUpdater(new FieldUpdater<Task, String>() {
            public void update(int index, Task object, String value) {

            }
        });

        colums.add(new TaskGridColumn<Task>(orderColumn,"&nbsp;", 50));
        colums.add(new TaskGridColumn<Task>(nameColumn,"Task Name", 350));
        colums.add(new TaskGridColumn<Task>(durationColumn,"Duration", 80));
        colums.add(new TaskGridColumn<Task>(startColumn,"Start", 110));
        colums.add(new TaskGridColumn<Task>(finishColumn,"Finish", 110));
        colums.add(new TaskGridColumn<Task>(predecessorColumn,"Predecessors", 100));
        colums.add(new TaskGridColumn<Task>(resourceColumn,"Resources", 100));
        return colums;
        
    }

}
