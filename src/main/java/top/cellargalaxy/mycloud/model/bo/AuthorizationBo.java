package top.cellargalaxy.mycloud.model.bo;

import top.cellargalaxy.mycloud.model.po.AuthorizationPo;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
public class AuthorizationBo extends AuthorizationPo {
	private String userName;
	private String permissionMark;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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
				", userName='" + userName + '\'' +
				", permissionMark='" + permissionMark + '\'' +
				'}';
	}
}
