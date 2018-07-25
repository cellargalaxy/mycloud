package top.cellargalaxy.mycloud.dao;

import top.cellargalaxy.mycloud.model.po.AuthorizationPo;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;
import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/12
 */
public interface AuthorizationDao {
	String TABLE_NAME = "authorization";

	int insert(AuthorizationPo authorizationPo);

	int delete(AuthorizationQuery authorizationQuery);

	AuthorizationBo selectOne(AuthorizationQuery authorizationQuery);

	List<AuthorizationBo> selectSome(AuthorizationQuery authorizationQuery);

	int selectCount(AuthorizationQuery authorizationQuery);

	int update(AuthorizationPo authorizationPo);

	static String checkInsert(AuthorizationPo authorizationPo) {
		if (authorizationPo.getUserId() < 1) {
			return "用户id不得为空";
		}
		if (authorizationPo.getPermissionId() < 1) {
			return "权限id不得为空";
		}
		return null;
	}

	static String checkUpdate(AuthorizationPo authorizationPo) {
		if (authorizationPo.getUserId() < 0) {
			return "用户id不得为空";
		}
		return null;
	}
}
