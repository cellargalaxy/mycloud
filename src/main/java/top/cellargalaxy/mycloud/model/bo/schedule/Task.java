package top.cellargalaxy.mycloud.model.bo.schedule;

import top.cellargalaxy.mycloud.model.po.UserPo;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public abstract class Task implements Serializable, Closeable {
	public static final int CANCEL_STATUS = -3;
	public static final int WAIT_STATUS = -2;
	public static final int EXECUTION_STATUS = -1;
	public static final int FAIL_STATUS = 0;
	public static final int SUCCESS_STATUS = 1;
	private static final long serialVersionUID = 7062287537522505438L;
	private final UserPo userPo;
	private final String taskId;
	private final Date createTime;
	private final String taskSort;
	private int status;
	private String massage;
	private Date finishTime;

	public Task(UserPo userPo, String taskSort) {
		this.userPo = userPo.getUserId() > 0 ? userPo : null;
		this.taskSort = taskSort;
		taskId = UUID.randomUUID().toString();
		createTime = new Date();
		status = WAIT_STATUS;
	}

	public abstract String getTaskDetail();

	public UserPo getUserPo() {
		return userPo;
	}

	public String getTaskId() {
		return taskId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getTaskSort() {
		return taskSort;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMassage() {
		return massage;
	}

	public void setMassage(String massage) {
		this.massage = massage;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	@Override
	public String toString() {
		return "Task{" +
				"userPo=" + userPo +
				", taskId='" + taskId + '\'' +
				", createTime=" + createTime +
				", status=" + status +
				", massage='" + massage + '\'' +
				", finishTime=" + finishTime +
				'}';
	}

	@Override
	public void close() throws IOException {
		finishTime = new Date();
	}
}
