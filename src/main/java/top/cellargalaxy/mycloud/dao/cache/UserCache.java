package top.cellargalaxy.mycloud.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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

	@Cacheable(key = "'selectOne'+#p0", condition = "true")
	public UserBo selectOne(UserPo userPo) {
		return userMapper.selectOne(userPo);
	}

	@Cacheable(key = "'selectSome'+#p0", condition = "true")
	public List<UserBo> selectSome(UserQuery userQuery) {
		return userMapper.selectSome(userQuery);
	}

	@Cacheable(key = "'selectCount'+#p0", condition = "true")
	public int selectCount(UserQuery userQuery) {
		return userMapper.selectCount(userQuery);
	}

	@Cacheable(key = "'selectAll'", condition = "true")
	public List<UserBo> selectAll() {
		return userMapper.selectAll();
	}

	//
	@Caching(evict = {
			@CacheEvict(key = "'selectAll'"),
	})
	public int insert(UserPo userPo) {
		return userMapper.insert(userPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'selectAll'"),
	})
	public int delete(UserPo userPo) {
		return userMapper.delete(userPo);
	}

	public int update(UserPo userPo) {
		return userMapper.update(userPo);
	}
}
