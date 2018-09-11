package top.cellargalaxy.mycloud.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.dao.TaskDao;
import top.cellargalaxy.mycloud.model.bo.TaskBo;
import top.cellargalaxy.mycloud.model.bo.schedule.Task;
import top.cellargalaxy.mycloud.model.po.TaskPo;
import top.cellargalaxy.mycloud.model.query.TaskQuery;
import top.cellargalaxy.mycloud.service.TaskService;
import top.cellargalaxy.mycloud.service.schedule.TaskSchedule;

import java.util.Collection;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/8/10
 */
@Transactional
@Service
public class TaskServiceImpl implements TaskService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TaskDao taskDao;
	@Autowired
	private TaskSchedule taskSchedule;

	private final int taskDetailLength;

	@Autowired
	public TaskServiceImpl(MycloudConfiguration mycloudConfiguration) {
		taskDetailLength = mycloudConfiguration.getTaskDetailLength();
		logger.info("FileUserController, taskDetailLength:{}", taskDetailLength);
	}

	@Override
	public String addWaitTask(Task task) {
		logger.info("addWaitTask:{}", task);
		return taskSchedule.addWaitTask(task);
	}

	@Override
	public String removeWaitTask(TaskPo taskPo) {
		logger.info("removeWaitTask:{}", taskPo);
		return taskSchedule.removeWaitTask(taskPo.getTaskId()) == null ? "等待任务空删除" : null;
	}

	@Override
	public String executeTask(Task task) throws Exception {
		logger.info("executeTask:{}", task);
		return taskSchedule.executeTask(task);
	}

	@Override
	public Task getCurrentTask() {
		logger.info("getCurrentTask");
		return taskSchedule.getCurrentTask();
	}

	@Override
	public Collection<Task> listWaitTask() {
		logger.info("listWaitTask");
		return taskSchedule.listWaitTask();
	}

	@Override
	public int getWaitTaskCount() {
		logger.info("getWaitTaskCount");
		return taskSchedule.listWaitTask().size();
	}

	@Override
	public String addTask(TaskPo taskPo) {
		logger.info("addTask:{}", taskPo);
		String string = checkAddTask(taskPo);
		if (string != null) {
			return string;
		}
		if (taskPo.getTaskDetail() != null && taskPo.getTaskDetail().length() > taskDetailLength) {
			taskPo.setTaskDetail(taskPo.getTaskDetail().substring(0, taskDetailLength));
		}
		int i = taskDao.insert(taskPo);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "任务空新增";
		}
		return null;
	}

	@Override
	public String removeTask(TaskPo taskPo) {
		logger.info("removeTask:{}", taskPo);
		int i = taskDao.delete(taskPo);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "完成任务空删除";
		}
		return null;
	}

	@Override
	public TaskBo getTask(TaskPo taskPo) {
		logger.info("getTask:{}", taskPo);
		return taskDao.selectOne(taskPo);
	}

	@Override
	public List<TaskBo> listTask(TaskQuery taskQuery) {
		logger.info("listTask:{}", taskQuery);
		return taskDao.selectSome(taskQuery);
	}

	@Override
	public int getTaskCount(TaskQuery taskQuery) {
		logger.info("getTaskCount:{}", taskQuery);
		return taskDao.selectCount(taskQuery);
	}

	@Override
	public String changeTask(TaskPo taskPo) {
		logger.info("changeTask:{}", taskPo);
		String string = checkChangeTask(taskPo);
		if (string != null) {
			return string;
		}
		if (taskPo.getTaskDetail() != null && taskPo.getTaskDetail().length() > taskDetailLength) {
			taskPo.setTaskDetail(taskPo.getTaskDetail().substring(0, taskDetailLength));
		}
		int i = taskDao.update(taskPo);
		if (i == 0) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "任务空更新";
		}
		return null;
	}

	@Override
	public String checkAddTask(TaskPo taskPo) {
		logger.info("checkAddTask:{}", taskPo);
		return TaskDao.checkInsert(taskPo);
	}

	@Override
	public String checkChangeTask(TaskPo taskPo) {
		logger.info("checkChangeTask:{}", taskPo);
		return TaskDao.checkUpdate(taskPo);
	}
}
