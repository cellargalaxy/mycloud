package top.cellargalaxy.mycloud.model.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author cellargalaxy
 * @time 2018/8/9
 */
public class TaskPo implements Serializable {
	private static final long serialVersionUID = 7753961505674827304L;
	public static final int WAIT_STATUS = 1;
	public static final int EXECUTION_STATUS = 2;
	public static final int CANCEL_STATUS = 3;
	public static final int SUCCESS_STATUS = 4;
	public static final int FAIL_STATUS = 5;

	private int taskId;
	private int userId;
	private Date createTime;
	private String taskSort;
	private int status;
	private String massage;
	private Date finishTime;
	private String taskDetail;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getTaskSort() {
		return taskSort;
	}

	public void setTaskSort(String taskSort) {
		this.taskSort = taskSort;
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

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getTaskDetail() {
		return taskDetail;
	}

	public void setTaskDetail(String taskDetail) {
		this.taskDetail = taskDetail;
	}

	@Override
	public String toString() {
		return "TaskPo{" +
				"taskId=" + taskId +
				", userId=" + userId +
				", createTime=" + createTime +
				", taskSort='" + taskSort + '\'' +
				", status=" + status +
				", massage='" + massage + '\'' +
				", finishTime=" + finishTime +
				", taskDetail='" + taskDetail + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TaskPo taskPo = (TaskPo) o;
		return taskId == taskPo.taskId &&
				userId == taskPo.userId &&
				status == taskPo.status &&
				Objects.equals(createTime, taskPo.createTime) &&
				Objects.equals(taskSort, taskPo.taskSort) &&
				Objects.equals(massage, taskPo.massage) &&
				Objects.equals(finishTime, taskPo.finishTime) &&
				Objects.equals(taskDetail, taskPo.taskDetail);
	}

	@Override
	public int hashCode() {

		return Objects.hash(taskId, userId, createTime, taskSort, status, massage, finishTime, taskDetail);
	}
}
