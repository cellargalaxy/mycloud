package top.cellargalaxy.mycloud.model.vo;

import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.bo.UserBo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cellargalaxy on 18-7-19.
 */
public class UserAuthorizationVo implements Serializable {
	private static final long serialVersionUID = 4395852149921551518L;
	private final UserBo user;
	private final List<AuthorizationBo> authorizations;

	public UserAuthorizationVo(UserBo user, List<AuthorizationBo> authorizations) {
		this.user = user;
		this.authorizations = authorizations;
	}

	public UserBo getUser() {
		return user;
	}

	public List<AuthorizationBo> getAuthorizations() {
		return authorizations;
	}

	@Override
	public String toString() {
		return "UserAuthorizationVo{" +
				"user=" + user +
				", authorizations=" + authorizations +
				'}';
	}
}
