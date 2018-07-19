package top.cellargalaxy.mycloud.model.vo;

import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.bo.UserBo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cellargalaxy on 18-7-19.
 */
public class UserOwnVo implements Serializable {
	private static final long serialVersionUID = 4255254484350454880L;
	private final UserBo user;
	private final List<OwnBo> owns;

	public UserOwnVo(UserBo user, List<OwnBo> owns) {
		this.user = user;
		this.owns = owns;
	}

	public UserBo getUser() {
		return user;
	}

	public List<OwnBo> getOwns() {
		return owns;
	}

	@Override
	public String toString() {
		return "UserOwnVo{" +
				"user=" + user +
				", owns=" + owns +
				'}';
	}
}
