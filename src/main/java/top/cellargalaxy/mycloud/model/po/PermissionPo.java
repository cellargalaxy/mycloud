package top.cellargalaxy.mycloud.model.po;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * Created by cellargalaxy on 18-7-11.
 */
public class PermissionPo implements Serializable {
	private static final long serialVersionUID = 5548266485951275822L;
	private int permissionId;
	private String permissionMark;
	private Date createTime;
	private Date updateTime;

	public int getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

	public String getPermissionMark() {
		return permissionMark;
	}

	public void setPermissionMark(String permissionMark) {
		this.permissionMark = permissionMark;
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
		return "PermissionPo{" +
				"permissionId=" + permissionId +
				", permissionMark='" + permissionMark + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PermissionPo that = (PermissionPo) o;
		return permissionId == that.permissionId &&
				Objects.equals(permissionMark, that.permissionMark) &&
				Objects.equals(createTime, that.createTime) &&
				Objects.equals(updateTime, that.updateTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(permissionId, permissionMark, createTime, updateTime);
	}
}
