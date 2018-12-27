package top.cellargalaxy.mycloud.model.bo;

import lombok.Data;
import top.cellargalaxy.mycloud.model.po.AuthorizationPo;
import top.cellargalaxy.mycloud.model.po.Permission;

import java.util.Date;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
@Data
public class AuthorizationBo extends AuthorizationPo {
	public static final AuthorizationBo GUEST = new AuthorizationBo();

	static {
		GUEST.setAuthorizationId(0);
		GUEST.setUserId(UserBo.GUEST.getUserId());
		GUEST.setPermission(Permission.GUEST);
		GUEST.setCreateTime(new Date());
		GUEST.setUpdateTime(new Date());
		GUEST.setUsername(UserBo.GUEST.getUsername());
	}

	private String username;

	@Override
	public String toString() {
		return "AuthorizationBo{" +
				"username='" + username + '\'' +
				", super=" + super.toString() +
				'}';
	}
}
