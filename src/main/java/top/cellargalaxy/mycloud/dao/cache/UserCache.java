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

import java.util.Date;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/13
 */
@Repository
@CacheConfig
public class UserCache implements UserDao {
	@Autowired
	private UserMapper userMapper;

	@Cacheable(key = "'UserCache-selectOne-'+#p0", cacheNames = "1m")
	public UserBo selectOne(UserPo userPo) {
		return userMapper.selectOne(userPo);
	}

	@Cacheable(key = "'UserCache-selectAll'", cacheNames = "1m")
	public List<UserBo> selectAll() {
		return userMapper.selectAll();
	}

	//

	@Caching(evict = {
			@CacheEvict(key = "'UserCache-selectOne-'+#p0", cacheNames = "1m"),
			@CacheEvict(key = "'UserCache-selectAll'", cacheNames = "1m"),
	})
	public int insert(UserPo userPo) {
		Date date = new Date();
		userPo.setCreateTime(date);
		userPo.setUpdateTime(date);
		return userMapper.insert(userPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'UserCache-selectOne-'+#p0", cacheNames = "1m"),
			@CacheEvict(key = "'UserCache-selectAll'", cacheNames = "1m"),
	})
	public int delete(UserPo userPo) {
		return userMapper.delete(userPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'UserCache-selectOne-'+#p0", cacheNames = "1m"),
			@CacheEvict(key = "'UserCache-selectAll'", cacheNames = "1m"),
	})
	public int update(UserPo userPo) {
		userPo.setUpdateTime(new Date());
		return userMapper.update(userPo);
	}

	//

	@Cacheable(key = "'UserCache-selectPageSome-'+#p0", cacheNames = "1m")
	public List<UserBo> selectPageSome(UserQuery userQuery) {
		return userMapper.selectPageSome(userQuery);
	}

	@Cacheable(key = "'UserCache-selectAllSome-'+#p0", cacheNames = "1m")
	public List<UserBo> selectAllSome(UserQuery userQuery) {
		return userMapper.selectAllSome(userQuery);
	}

	@Cacheable(key = "'UserCache-selectCount-'+#p0", cacheNames = "1m")
	public int selectCount(UserQuery userQuery) {
		return userMapper.selectCount(userQuery);
	}
}