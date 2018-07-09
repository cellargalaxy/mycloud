package top.cellargalaxy.newcloud.model.po;

import java.io.Serializable;

/**
 * @author cellargalaxy
 * @time 2018/7/9
 */
public class UserPo implements Serializable {
	private static final long serialVersionUID = -8175567370336139965L;
	private int userId;
	private String userName;
	private String userPassword;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Override
	public String toString() {
		return "UserPo{" +
				"userId=" + userId +
				", userName='" + userName + '\'' +
				", userPassword='" + userPassword + '\'' +
				'}';
	}
}
