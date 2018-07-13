package top.cellargalaxy.newcloud.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cellargalaxy on 18-7-11.
 */
public class AuthorizationPo implements Serializable {
	private static final long serialVersionUID = 1523109653163477354L;
	public int authorizationId;
	private int userId;
	private int permissionId;
	private Date createTime;
	private Date updateTime;

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
	public String toString() {
		return "AuthorizationPo{" +
				"authorizationId=" + authorizationId +
				", userId=" + userId +
				", permissionId=" + permissionId +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
