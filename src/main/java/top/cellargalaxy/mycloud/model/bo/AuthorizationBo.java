package top.cellargalaxy.mycloud.model.bo;

import top.cellargalaxy.mycloud.model.po.AuthorizationPo;
import top.cellargalaxy.mycloud.model.po.Permission;

import java.util.Date;
import java.util.Objects;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
public class AuthorizationBo extends AuthorizationPo {
	public static final AuthorizationBo GUEST = new AuthorizationBo();

	static {
		GUEST.setAuthorizationId(0);
		GUEST.setUserId(0);
		GUEST.setPermission(Permission.GUEST);
		GUEST.setCreateTime(new Date());
		GUEST.setUpdateTime(new Date());
		GUEST.setUsername("GUEST");
	}

	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		AuthorizationBo that = (AuthorizationBo) o;
		return Objects.equals(username, that.username);
	}

	@Override
	public int hashCode() {

		return Objects.hash(super.hashCode(), username);
	}

	@Override
	public String toString() {
		return "AuthorizationBo{" +
				"username='" + username + '\'' +
				", super=" + super.toString() +
				'}';
	}
}
