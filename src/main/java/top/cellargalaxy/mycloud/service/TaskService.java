package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.bo.TaskBo;
import top.cellargalaxy.mycloud.model.bo.schedule.Task;
import top.cellargalaxy.mycloud.model.po.TaskPo;
import top.cellargalaxy.mycloud.model.query.TaskQuery;

import java.util.Collection;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/8/10
 */
public interface TaskService {
	String addWaitTask(Task task);

	String removeWaitTask(TaskPo taskPo);

	Task getCurrentTask();

	Collection<Task> listWaitTask();

	int getWaitTaskCount();

	String addTask(TaskPo taskPo);

	String removeTask(TaskPo taskPo);

	TaskBo getTask(TaskPo taskPo);

	List<TaskBo> listTask(TaskQuery taskQuery);

	int getTaskCount(TaskQuery taskQuery);

	String changeTask(TaskPo taskPo);

	String checkAddTask(TaskPo taskPo);

	String checkChangeTask(TaskPo taskPo);
}
