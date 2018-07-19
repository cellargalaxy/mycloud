package top.cellargalaxy.mycloud.service.schedule;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * @author cellargalaxy
 * @time 2018/7/19
 */
public abstract class AbstractSchedule<Task extends top.cellargalaxy.mycloud.model.bo.schedule.Task> {
	public static final int MAX_FINISH_TASKS_LIST = 1000;
	private final BlockingQueue<Task> waitTasks = new LinkedTransferQueue<>();
	private Task currentTask;
	private final LinkedList<Task> finishTasks = new LinkedList<>();

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

	protected Task getWaitTask() throws InterruptedException {
		currentTask = waitTasks.take();
		currentTask.setStatus(Task.EXECUTION_STATUS);
		return currentTask;
	}

	protected void addFinishTask(Task task, int status) {
		if (task == null) {
			return;
		}
		if (finishTasks.size() == MAX_FINISH_TASKS_LIST) {
			finishTasks.poll();
		}
		task.setStatus(status);
		finishTasks.add(task);
		currentTask = null;
	}

	//并发问题
	public List<Task> getWaitTasks(int off, int len) {
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
	public List<Task> getFinishTasks(int off, int len) {
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
