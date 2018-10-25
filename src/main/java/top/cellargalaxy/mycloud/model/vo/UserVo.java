package top.cellargalaxy.mycloud.model.vo;

import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.bo.UserBo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author cellargalaxy
 * @time 2018/10/25
 */
public class UserVo implements Serializable {
	private static final long serialVersionUID = 4950747715064264519L;
	private UserBo user;
	private List<AuthorizationBo> authorizations;

	public UserBo getUser() {
		return user;
	}

	public void setUser(UserBo user) {
		this.user = user;
	}

	public List<AuthorizationBo> getAuthorizations() {
		return authorizations;
	}

	public void setAuthorizations(List<AuthorizationBo> authorizations) {
		this.authorizations = authorizations;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		UserVo userVo = (UserVo) o;
		return Objects.equals(user, userVo.user) &&
				Objects.equals(authorizations, userVo.authorizations);
	}

	@Override
	public int hashCode() {

		return Objects.hash(user, authorizations);
	}

	@Override
	public String toString() {
		return "UserVo{" +
				"user=" + user +
				", authorizations=" + authorizations +
				'}';
	}
}
