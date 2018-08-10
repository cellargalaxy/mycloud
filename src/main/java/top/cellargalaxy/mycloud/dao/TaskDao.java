package top.cellargalaxy.mycloud.dao;

import top.cellargalaxy.mycloud.model.bo.TaskBo;
import top.cellargalaxy.mycloud.model.po.TaskPo;
import top.cellargalaxy.mycloud.model.query.TaskQuery;
import top.cellargalaxy.mycloud.util.StringUtil;

/**
 * @author cellargalaxy
 * @time 2018/8/9
 */
public interface TaskDao extends AbstractDao<TaskPo, TaskBo, TaskQuery> {
	String TABLE_NAME = "task";

	static String checkInsert(TaskPo taskPo) {
		if (taskPo.getUserId() < 1) {
			return "账号id不能为空";
		}
		if (StringUtil.isBlank(taskPo.getTaskSort())) {
			return "任务分类不能为空";
		}
		return null;
	}

	static String checkUpdate(TaskPo taskPo) {
		if (taskPo.getTaskId() < 1) {
			return "任务id不能为空";
		}
		return null;
	}
}
