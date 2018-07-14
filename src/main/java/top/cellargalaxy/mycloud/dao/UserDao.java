package top.cellargalaxy.mycloud.dao;

import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.UserQuery;

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
