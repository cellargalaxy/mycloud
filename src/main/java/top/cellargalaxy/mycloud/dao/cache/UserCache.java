package top.cellargalaxy.mycloud.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import top.cellargalaxy.mycloud.dao.UserDao;
import top.cellargalaxy.mycloud.dao.mapper.UserMapper;
import top.cellargalaxy.mycloud.model.bo.UserBo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.UserQuery;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/13
 */
@Repository
@CacheConfig(cacheNames = "user")
public class UserCache implements UserDao {
	@Autowired
	private UserMapper userMapper;

	@CacheEvict(key = "#p0.userId")
	public int insert(UserPo userPo) {
		return userMapper.insert(userPo);
	}

	@CacheEvict(key = "#p0.userId")
	public int delete(UserQuery userQuery) {
		return userMapper.delete(userQuery);
	}

	@Cacheable(key = "#p0.userId", condition = "#p0.userId>0")
	public UserBo selectOne(UserQuery userQuery) {
		return userMapper.selectOne(userQuery);
	}

	public List<UserBo> selectSome(UserQuery userQuery) {
		return userMapper.selectSome(userQuery);
	}

	@CacheEvict(key = "#p0.userId")
	public int update(UserPo userPo) {
		return userMapper.update(userPo);
	}
}