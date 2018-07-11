package top.cellargalaxy.newcloud.model.po;

import java.io.Serializable;

/**
 * Created by cellargalaxy on 18-7-11.
 */
public class PermissionPo implements Serializable {
	private static final long serialVersionUID = 5548266485951275822L;
	private int permissionId;
	private String permissionMark;

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

	@Override
	public String toString() {
		return "PermissionPo{" +
				"permissionId=" + permissionId +
				", permissionMark='" + permissionMark + '\'' +
				'}';
	}
}
