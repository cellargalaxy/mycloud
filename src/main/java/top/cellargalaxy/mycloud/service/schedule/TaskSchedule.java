package top.cellargalaxy.mycloud.service.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.cellargalaxy.mycloud.exception.GlobalException;
import top.cellargalaxy.mycloud.model.bo.schedule.SynchronizeTask;
import top.cellargalaxy.mycloud.model.bo.schedule.Task;
import top.cellargalaxy.mycloud.model.po.TaskPo;
import top.cellargalaxy.mycloud.service.TaskService;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * @author cellargalaxy
 * @time 2018/7/19
 */
@Component
public class TaskSchedule {
	private Logger logger = LoggerFactory.getLogger(TaskSchedule.class);
	private final BlockingQueue<Task> waitTasks = new LinkedTransferQueue<>();
	private Task currentTask;
	@Autowired
	private TaskService taskService;

	@Autowired
	private TaskExecuteFactory taskExecuteFactory;

	@Scheduled(fixedDelay = 1000 * 5)
	public void schedule() {
		try {
			currentTask = getWaitTask();
			logger.info("schedule:{}, result:{}", currentTask, executeTask(currentTask));
		} catch (InterruptedException e) {
			logger.info("schedule:执行任务异常:{}", e.getMessage());
			GlobalException.add(e, 0, "未知异常");
			addFinishTask(currentTask, Task.FAIL_STATUS);
		}
	}

	public String executeTask(Task currentTask) {
		logger.info("executeTask:{}", currentTask);
		String string = null;
		try (Task task = currentTask) {
			TaskExecute taskExecute = taskExecuteFactory.getTaskExecute(currentTask.getTaskSort());
			string = taskExecute.executeTask(currentTask);
		} catch (Exception e) {
			logger.info("executeTask:执行任务异常:{}", e.getMessage());
			GlobalException.add(e, 0, "未知异常");
			addFinishTask(currentTask, Task.FAIL_STATUS);
		}
		if (string != null) {
			currentTask.setMassage(string);
			addFinishTask(currentTask, Task.FAIL_STATUS);
		} else {
			addFinishTask(currentTask, Task.SUCCESS_STATUS);
		}
		return string;
	}

	public String addWaitTask(Task task) {
		logger.info("addWaitTask:{}", task);
		if (task == null) {
			return "任务不得为空";
		}
		task.setStatus(Task.WAIT_STATUS);
		waitTasks.add(task);
		return null;
	}

	//这样应该会有并发问题
	public Task removeWaitTask(int taskId) {
		logger.info("removeWaitTask:{}", taskId);
		Iterator<Task> iterator = waitTasks.iterator();
		while (iterator.hasNext()) {
			Task task = iterator.next();
			if (task.getTaskId() == taskId) {
				iterator.remove();
				addFinishTask(task, TaskPo.CANCEL_STATUS);
				return task;
			}
		}
		return null;
	}

	private Task getWaitTask() throws InterruptedException {
		Task task = waitTasks.take();
		task.setStatus(Task.EXECUTION_STATUS);
		return task;
	}

	private void addFinishTask(Task task, int status) {
		if (task == null || !task.isPersistent()) {
			return;
		}
		task.setStatus(status);
		taskService.addTask(task);
	}

	//并发问题
	public Collection<Task> listWaitTask() {
		logger.info("listWaitTask");
		return waitTasks;
	}

	public BlockingQueue<Task> getWaitTasks() {
		return waitTasks;
	}

	public Task getCurrentTask() {
		return currentTask;
	}
}
