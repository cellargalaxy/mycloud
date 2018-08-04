package top.cellargalaxy.mycloud.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import top.cellargalaxy.mycloud.dao.OwnDao;
import top.cellargalaxy.mycloud.dao.mapper.OwnMapper;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;

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
			@CacheEvict(key = "'selectSort'+#p0.userId"),
	})
	public int insert(OwnPo ownPo) {
		return ownMapper.insert(ownPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "#p0.ownId"),
			@CacheEvict(key = "'selectSort'+#p0.userId"),
	})
	public int delete(OwnPo ownPo) {
		return ownMapper.delete(ownPo);
	}

	@Cacheable(key = "#p0.ownId", condition = "#p0.ownId>0")
	public OwnBo selectOne(OwnPo ownPo) {
		return ownMapper.selectOne(ownPo);
	}

	public List<OwnBo> selectSome(OwnQuery ownQuery) {
		return ownMapper.selectSome(ownQuery);
	}

	public int selectCount(OwnQuery ownQuery) {
		return ownMapper.selectCount(ownQuery);
	}

	@Cacheable(key = "'selectSort'+#p0.userId", condition = "#p0.userId>0")
	public List<String> selectSort(OwnQuery ownQuery) {
		return ownMapper.selectSort(ownQuery);
	}

	@Caching(evict = {
			@CacheEvict(key = "#p0.ownId"),
			@CacheEvict(key = "'selectSort'+#p0.userId"),
	})
	public int update(OwnPo ownPo) {
		return ownMapper.update(ownPo);
	}
}
