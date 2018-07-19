package top.cellargalaxy.mycloud.model.bo;

import top.cellargalaxy.mycloud.model.po.AuthorizationPo;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
public class AuthorizationBo extends AuthorizationPo {
	private String username;
	private String permissionMark;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPermissionMark() {
		return permissionMark;
	}

	public void setPermissionMark(String permissionMark) {
		this.permissionMark = permissionMark;
	}

	@Override
	public String toString() {
		return "AuthorizationBo{" +
				"super=" + super.toString() +
				", username='" + username + '\'' +
				", permissionMark='" + permissionMark + '\'' +
				'}';
	}
}
