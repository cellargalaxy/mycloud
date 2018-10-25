package top.cellargalaxy.mycloud.model.po;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author cellargalaxy
 * @time 2018/7/9
 */
public class UserPo implements Serializable {
	private static final long serialVersionUID = -8175567370336139965L;
	private int userId;
	private String username;
	@JsonIgnore
	private String password;
	private Date createTime;
	private Date updateTime;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserPo userPo = (UserPo) o;
		return userId == userPo.userId &&
				Objects.equals(username, userPo.username) &&
				Objects.equals(password, userPo.password) &&
				Objects.equals(createTime, userPo.createTime) &&
				Objects.equals(updateTime, userPo.updateTime);
	}

	@Override
	public int hashCode() {

		return Objects.hash(userId, username, password, createTime, updateTime);
	}

	@Override
	public String toString() {
		return "UserPo{" +
				"userId=" + userId +
				", username='" + username + '\'' +
				'}';
	}
}
