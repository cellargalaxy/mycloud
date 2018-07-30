package top.cellargalaxy.mycloud.service.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.cellargalaxy.mycloud.exception.GlobalException;
import top.cellargalaxy.mycloud.model.bo.schedule.Task;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * @author cellargalaxy
 * @time 2018/7/19
 */
@Component
public class TaskSchedule {
	public static final int MAX_FINISH_TASKS_LIST = 1000;
	private final BlockingQueue<Task> waitTasks = new LinkedTransferQueue<>();
	private Task currentTask;
	private final LinkedList<Task> finishTasks = new LinkedList<>();

	@Autowired
	private TaskExecuteFactory taskExecuteFactory;

	@Scheduled(fixedDelay = 1000 * 5)
	public void uploadFileSchedule() {
		while (true) {
			try (Task task = getWaitTask()) {
				currentTask = task;
				TaskExecute taskExecute = taskExecuteFactory.getTaskExecute(currentTask.getTaskSort());
				taskExecute.executeTask(currentTask);
				addFinishTask(currentTask, Task.SUCCESS_STATUS);
				currentTask = null;
			} catch (Exception e) {
				e.printStackTrace();
				GlobalException.add(e, 0, "未知异常");
				if (currentTask != null) {
					addFinishTask(currentTask, Task.FAIL_STATUS);
				}
			}
		}
	}

	public void addTask(Task task) {
		if (task == null) {
			return;
		}
		task.setStatus(Task.WAIT_STATUS);
		waitTasks.add(task);
	}

	//这样应该会有并发问题
	public Task removeTask(String taskId) {
		if (taskId == null) {
			return null;
		}
		Iterator<Task> iterator = waitTasks.iterator();
		while (iterator.hasNext()) {
			Task task = iterator.next();
			if (task.equals(taskId)) {
				iterator.remove();
				task.setStatus(Task.CANCEL_STATUS);
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
		if (task == null) {
			return;
		}
		if (finishTasks.size() == MAX_FINISH_TASKS_LIST) {
			finishTasks.poll();
		}
		task.setStatus(status);
		finishTasks.add(task);
	}

	//并发问题
	public List<Task> listWaitTask(int off, int len) {
		List<Task> tasks = new LinkedList<>();
		Iterator<Task> iterator = waitTasks.iterator();
		for (int i = 0; iterator.hasNext() && i < off; i++) {
			iterator.next();
		}
		for (int i = 0; iterator.hasNext() && i < len; i++) {
			tasks.add(iterator.next());
		}
		return tasks;
	}

	//并发问题
	public List<Task> listFinishTask(int off, int len) {
		return finishTasks.subList(off, off + len);
	}

	public BlockingQueue<Task> getWaitTasks() {
		return waitTasks;
	}

	public Task getCurrentTask() {
		return currentTask;
	}

	public List<Task> getFinishTasks() {
		return finishTasks;
	}
}
