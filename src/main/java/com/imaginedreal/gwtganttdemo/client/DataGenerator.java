package com.imaginedreal.gwtganttdemo.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.imaginedreal.gwtgantt.DateUtil;
import com.imaginedreal.gwtgantt.model.DurationFormat;
import com.imaginedreal.gwtgantt.model.Predecessor;
import com.imaginedreal.gwtgantt.model.PredecessorType;
import com.imaginedreal.gwtgantt.model.Task;

public class DataGenerator {

	@SuppressWarnings("deprecation")
    public static List<Task> generateTasks() {
		List<Task> taskList = new ArrayList<Task>();
		
		Task task1 = new Task();
		task1.setUID(1);
		task1.setSummary(true);
		task1.setName("Test Task 1");
		task1.setLevel(0);
		task1.setOrder(1);
		task1.setPercentComplete(50);
		task1.setStart(new Date());
		task1.setFinish(new Date());
		task1.setDuration(DateUtil.differenceInDays(task1.getFinish(), task1.getStart()));
		taskList.add(task1);
		
		Task task2 = new Task();
		task2.setUID(2);
		task2.setName("Test Task 2");
		task2.setLevel(1);
		task2.setOrder(2);
		task2.setStyle(Task.STYLE_ORANGE);
		task2.setPercentComplete(75);
		task2.setStart(new Date());
		task2.setFinish(new Date());
		task2.setDuration(DateUtil.differenceInDays(task2.getFinish(), task2.getStart()));
		taskList.add(task2);

		Task task3 = new Task();
		task3.setUID(3);
		task3.setName("Test Task 3");
		task3.setLevel(1);
		task3.setOrder(3);
		task3.setPercentComplete(25);
		task3.setStart(new Date());
		task3.setFinish(new Date(new Date().getYear(),new Date().getMonth(), new Date().getDate()+1));
		task3.setDuration(DateUtil.differenceInDays(task3.getFinish(), task3.getStart()));
		task3.getPredecessors().add(new Predecessor(2, PredecessorType.SS));
		taskList.add(task3);

		Task task4 = new Task();
		task4.setUID(4);
		task4.setName("Test Task 4");
		task4.setLevel(0);
		task4.setOrder(4);
		task4.setPercentComplete(0);
		task4.setStart(new Date());
		task4.setFinish(new Date());
		task4.setDuration(3);
		task4.setDurationFormat(DurationFormat.HOURS);
		task4.setStyle(Task.STYLE_RED);
		task4.getPredecessors().add(new Predecessor(3, PredecessorType.FS));
		taskList.add(task4);
		
		Task task5 = new Task();
		task5.setUID(5);
		task5.setName("Test Task 5");
		task5.setLevel(0);
		task5.setOrder(5);
		task5.setPercentComplete(25);
		task5.setStart(new Date(new Date().getYear(),new Date().getMonth(), new Date().getDate()+5));
		task5.setFinish(new Date(new Date().getYear(),new Date().getMonth(), new Date().getDate()+7));
		task5.setDuration(DateUtil.differenceInDays(task5.getFinish(), task5.getStart()));
		task5.getPredecessors().add(new Predecessor(4, PredecessorType.FS));
		taskList.add(task5);
		
		Task task6 = new Task();
		task6.setUID(6);
		task6.setName("Test Task 6");
		task6.setLevel(0);
		task6.setOrder(6);
		task6.setPercentComplete(0);
		task6.setStyle(Task.STYLE_PURPLE);
		task6.setStart(new Date(new Date().getYear(),new Date().getMonth(), new Date().getDate()+5));
		task6.setFinish(new Date(new Date().getYear(),new Date().getMonth(), new Date().getDate()+7));
		task6.setDuration(DateUtil.differenceInDays(task6.getFinish(), task6.getStart()));
		task6.getPredecessors().add(new Predecessor(5, PredecessorType.FF));
		taskList.add(task6);
		
		Task task7 = new Task();
		task7.setUID(7);
		task7.setName("Summary Task (#7)");
		task7.setLevel(0);
		task7.setOrder(7);
		task7.setPercentComplete(0);
		task7.setStart(new Date());
		task7.setFinish(DateUtil.addDays(new Date(), 10));
		task7.setDuration(DateUtil.differenceInDays(task7.getFinish(), task7.getStart()));
		task7.setSummary(true);
		taskList.add(task7);
		
		Date date = new Date();
		for(int i=8;i<18;i++) {
			
			Task taskN = new Task();
			taskN.setUID(i);
			taskN.setName("Test Task "+i);
			taskN.setLevel(1);
			taskN.setOrder(i);
			taskN.setPercentComplete(0);
			taskN.setStart(date);
			//increment date
			date = (Date)date.clone();
			date = DateUtil.addDays(date, 1);
			
			taskN.setFinish(date);
			taskN.setDuration(DateUtil.differenceInDays(
					taskN.getFinish(), taskN.getStart()));
			
			if(i>8)
				taskN.getPredecessors().add(new Predecessor(i-1, PredecessorType.FS));
			
			//add task to list
			taskList.add(taskN);
		}
		
		Task task18 = new Task();
		task18.setUID(18);
		task18.setName("Task (#18)");
		task18.setLevel(0);
		task18.setOrder(18);
		task18.setPercentComplete(0);
		task18.setStart(DateUtil.addDays(new Date(), 30));
		task18.setFinish(DateUtil.addDays(new Date(), 60));
		task18.setDuration(DateUtil.differenceInDays(task18.getFinish(), task18.getStart()));
		task18.setSummary(false);
		taskList.add(task18);
		
		Task task19 = new Task();
		task19.setUID(19);
		task19.setName("Task (#19)");
		task19.setLevel(0);
		task19.setOrder(19);
		task19.setPercentComplete(0);
		task19.setStart(DateUtil.reset(new Date()));
		task19.setFinish(DateUtil.reset(new Date()));
		task19.setDuration(4);
		task19.setDurationFormat(DurationFormat.HOURS);
		taskList.add(task19);
		
		return taskList;
	}
}
