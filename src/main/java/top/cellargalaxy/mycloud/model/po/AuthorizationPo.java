package top.cellargalaxy.mycloud.model.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Created by cellargalaxy on 18-7-11.
 */
public class AuthorizationPo implements Serializable {
	private static final long serialVersionUID = 1523109653163477354L;
	private int authorizationId;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AuthorizationPo that = (AuthorizationPo) o;
		return authorizationId == that.authorizationId &&
				userId == that.userId &&
				permissionId == that.permissionId &&
				Objects.equals(createTime, that.createTime) &&
				Objects.equals(updateTime, that.updateTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(authorizationId, userId, permissionId, createTime, updateTime);
	}
}
