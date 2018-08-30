package top.cellargalaxy.mycloud.model.bo.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import top.cellargalaxy.mycloud.exception.GlobalException;
import top.cellargalaxy.mycloud.model.po.TaskPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.vo.TaskVo;

import java.io.Closeable;
import java.util.Date;
import java.util.Objects;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public abstract class Task extends TaskVo implements Closeable {
	private static final long serialVersionUID = 7062287537522505438L;
	protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	private final UserPo userPo;
	private final boolean persistent;

	public Task(UserPo userPo, String taskSort) {
		this.userPo = userPo;
		persistent = true;
		setUserId(userPo.getUserId());
		setTaskSort(taskSort);
		setCreateTime(new Date());
		setStatus(TaskPo.WAIT_STATUS);
	}

	public Task(String taskSort) {
		userPo = null;
		persistent = false;
		setTaskSort(taskSort);
		setCreateTime(new Date());
		setStatus(TaskPo.WAIT_STATUS);
	}

	public abstract String serializationTaskDetail();

	protected static final String serialization2Json(Object object) {
		try {
			if (object != null) {
				return OBJECT_MAPPER.writeValueAsString(object);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			GlobalException.add(e);
		}
		return "{}";
	}

	public UserPo getUserPo() {
		return userPo;
	}

	public boolean isPersistent() {
		return persistent;
	}

	@Override
	public String toString() {
		return "Task{" +
				"super=" + super.toString() +
				", userPo=" + userPo +
				", persistent=" + persistent +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		Task task = (Task) o;
		return persistent == task.persistent &&
				Objects.equals(userPo, task.userPo);
	}

	@Override
	public int hashCode() {

		return Objects.hash(super.hashCode(), userPo, persistent);
	}

	@Override
	public void close() {
		setFinishTime(new Date());
		setTaskDetail(serializationTaskDetail());
	}
}
