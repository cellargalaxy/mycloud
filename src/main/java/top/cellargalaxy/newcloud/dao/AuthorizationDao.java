package top.cellargalaxy.newcloud.dao;

import top.cellargalaxy.newcloud.model.po.AuthorizationPo;
import top.cellargalaxy.newcloud.model.query.AuthorizationQuery;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/12
 */
public interface AuthorizationDao {
	int insert(AuthorizationPo authorizationPo);

	int delete(AuthorizationPo authorizationPo);

	AuthorizationPo selectOne(AuthorizationQuery authorizationQuery);

	List<AuthorizationPo> selectSome(AuthorizationQuery authorizationQuery);

	int update(AuthorizationPo authorizationPo);
}
