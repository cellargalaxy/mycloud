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
public abstract class Schedule<Task extends top.cellargalaxy.mycloud.model.bo.schedule.Task> {
	public static final int MAX_FINISH_TASKS_LIST = 1000;
	private final BlockingQueue<Task> waitTasks = new LinkedTransferQueue<>();
	private Task currentTask;
	private final LinkedList<Task> finishTasks = new LinkedList<>();

	public void addTask(Task task) {
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
				return task;
			}
		}
		return null;
	}

	protected Task getWaitTask() throws InterruptedException {
		currentTask = waitTasks.take();
		return currentTask;
	}

	protected void addFinishTask(Task task) {
		if (finishTasks.size() == MAX_FINISH_TASKS_LIST) {
			finishTasks.poll();
		}
		finishTasks.add(task);
		currentTask = null;
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
