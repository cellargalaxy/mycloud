package top.cellargalaxy.mycloud.model.vo;

import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.bo.PermissionBo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Created by cellargalaxy on 18-7-19.
 */
public class PermissionAuthorizationVo implements Serializable {
	private static final long serialVersionUID = 8753004433126440056L;
	private final PermissionBo permission;
	private final List<AuthorizationBo> authorizations;

	public PermissionAuthorizationVo(PermissionBo permission, List<AuthorizationBo> authorizations) {
		this.permission = permission;
		this.authorizations = authorizations;
	}

	public PermissionBo getPermission() {
		return permission;
	}

	public List<AuthorizationBo> getAuthorizations() {
		return authorizations;
	}

	@Override
	public String toString() {
		return "PermissionAuthorizationVo{" +
				"permission=" + permission +
				", authorizations=" + authorizations +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PermissionAuthorizationVo that = (PermissionAuthorizationVo) o;
		return Objects.equals(permission, that.permission) &&
				Objects.equals(authorizations, that.authorizations);
	}

	@Override
	public int hashCode() {
		return Objects.hash(permission, authorizations);
	}
}
