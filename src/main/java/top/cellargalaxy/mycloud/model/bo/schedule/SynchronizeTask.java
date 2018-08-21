package top.cellargalaxy.mycloud.model.bo.schedule;

import top.cellargalaxy.mycloud.model.po.UserPo;

/**
 * @author cellargalaxy
 * @time 2018/8/21
 */
public class SynchronizeTask extends Task {
	public static final String TASK_SORT = "同步数据";

	public SynchronizeTask(UserPo userPo) {
		super(userPo, TASK_SORT);
		setPersistent(false);
	}

	@Override
	public String serializationTaskDetail() {
		return null;
	}
}
