package top.cellargalaxy.mycloud.model.bo.schedule;

import top.cellargalaxy.mycloud.model.po.TaskPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.vo.TaskVo;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public abstract class Task extends TaskVo implements Closeable {
	private static final long serialVersionUID = 7062287537522505438L;
	private final UserPo userPo;

	public Task(UserPo userPo, String taskSort, String taskDetail) {
		super(taskDetail);
		this.userPo = userPo;
		setUserId(userPo.getUserId());
		setTaskSort(taskSort);
		setCreateTime(new Date());
		setStatus(TaskPo.WAIT_STATUS);
	}

	public UserPo getUserPo() {
		return userPo;
	}

	@Override
	public void close() {
		setFinishTime(new Date());
	}
}
