package top.cellargalaxy.mycloud.model.bo;

import top.cellargalaxy.mycloud.model.po.UserPo;

import java.util.Date;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
public class UserBo extends UserPo {
	public static final UserBo GUEST = new UserBo();

	static {
		GUEST.setUserId(0);
		GUEST.setUsername("GUEST");
		GUEST.setCreateTime(new Date());
		GUEST.setUpdateTime(new Date());
	}
}
