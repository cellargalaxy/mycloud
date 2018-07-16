package top.cellargalaxy.newcloud.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import top.cellargalaxy.newcloud.dao.OwnDao;
import top.cellargalaxy.newcloud.dao.db.OwnMapper;
import top.cellargalaxy.newcloud.model.bo.OwnBo;
import top.cellargalaxy.newcloud.model.po.OwnPo;
import top.cellargalaxy.newcloud.model.query.OwnQuery;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
@Repository
@CacheConfig(cacheNames = "own")
public class OwnCache implements OwnDao {
	@Autowired
	private OwnMapper ownMapper;

	@Caching(evict = {
			@CacheEvict(key = "#p0.ownId"),
			@CacheEvict(key = "'selectSort'")})
	public int insert(OwnPo ownPo) {
		return ownMapper.insert(ownPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "#p0.ownId"),
			@CacheEvict(key = "'selectSort'")})
	public int delete(OwnQuery ownQuery) {
		return ownMapper.delete(ownQuery);
	}

	@Cacheable(key = "#p0.ownId", condition = "#p0.ownId>0")
	public OwnBo selectOne(OwnQuery ownQuery) {
		return ownMapper.selectOne(ownQuery);
	}

	public List<OwnBo> selectSome(OwnQuery ownQuery) {
		return ownMapper.selectSome(ownQuery);
	}

	@Cacheable(key = "'selectSort'")
	public List<String> selectSort(int userId) {
		return ownMapper.selectSort(userId);
	}

	@Caching(evict = {
			@CacheEvict(key = "#p0.ownId"),
			@CacheEvict(key = "'selectSort'")})
	public int update(OwnPo ownPo) {
		return ownMapper.update(ownPo);
	}
}
