package top.cellargalaxy.mycloud.model.bo;

import top.cellargalaxy.mycloud.model.po.AuthorizationPo;

import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		AuthorizationBo that = (AuthorizationBo) o;
		return Objects.equals(username, that.username) &&
				Objects.equals(permissionMark, that.permissionMark);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), username, permissionMark);
	}
}
