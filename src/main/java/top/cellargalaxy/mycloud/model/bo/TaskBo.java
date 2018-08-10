package top.cellargalaxy.mycloud.model.bo;

import top.cellargalaxy.mycloud.model.po.TaskPo;

import java.util.Objects;

/**
 * @author cellargalaxy
 * @time 2018/8/9
 */
public class TaskBo extends TaskPo {
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "TaskBo{" +
				"super=" + super.toString() +
				", username='" + username + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		TaskBo taskBo = (TaskBo) o;
		return Objects.equals(username, taskBo.username);
	}

	@Override
	public int hashCode() {

		return Objects.hash(super.hashCode(), username);
	}
}
