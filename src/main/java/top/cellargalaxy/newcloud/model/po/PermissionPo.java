package top.cellargalaxy.newcloud.model.po;

import java.io.Serializable;
import java.util.Date;

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
}
