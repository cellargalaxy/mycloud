package top.cellargalaxy.newcloud.dao;

import top.cellargalaxy.newcloud.model.po.UserPo;
import top.cellargalaxy.newcloud.model.query.UserQuery;

import java.util.List;

/**
 * Created by cellargalaxy on 18-7-12.
 */
public interface UserDao {
	int insert(UserPo userPo);

	int delete(UserQuery userQuery);

	UserPo selectOne(UserQuery userQuery);

	List<UserPo> selectSome(UserQuery userQuery);

	int update(UserPo userPo);
}
