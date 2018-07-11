package top.cellargalaxy.newcloud.model.po;

import java.io.Serializable;

/**
 * Created by cellargalaxy on 18-7-11.
 */
public class AuthorizationPo implements Serializable {
	private static final long serialVersionUID = 1523109653163477354L;
	private int authorizationId;
	private int userId;
	private int permissionId;

	public int getAuthorizationId() {
		return authorizationId;
	}

	public void setAuthorizationId(int authorizationId) {
		this.authorizationId = authorizationId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

	@Override
	public String toString() {
		return "AuthorizationPo{" +
				"authorizationId=" + authorizationId +
				", userId=" + userId +
				", permissionId=" + permissionId +
				'}';
	}
}
