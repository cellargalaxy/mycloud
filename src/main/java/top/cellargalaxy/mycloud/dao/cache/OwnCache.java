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

import java.util.Date;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
@Repository
@CacheConfig
public class OwnCache implements OwnDao {
	@Autowired
	private OwnMapper ownMapper;

	@Cacheable(key = "'OwnCache-selectOne-'+#p0", cacheNames = "1m")
	public OwnBo selectOne(OwnPo ownPo) {
		return ownMapper.selectOne(ownPo);
	}

	@Cacheable(key = "'OwnCache-selectAll'", cacheNames = "1m")
	public List<OwnBo> selectAll() {
		return ownMapper.selectAll();
	}

	//

	@Caching(evict = {
			@CacheEvict(key = "'OwnCache-selectOne-'+#p0", cacheNames = "1m"),
			@CacheEvict(key = "'OwnCache-selectAll'", cacheNames = "1m"),
	})
	public int insert(OwnPo ownPo) {
		Date date = new Date();
		ownPo.setCreateTime(date);
		ownPo.setUpdateTime(date);
		return ownMapper.insert(ownPo);
	}


	@Caching(evict = {
			@CacheEvict(key = "'OwnCache-selectOne-'+#p0", cacheNames = "1m"),
			@CacheEvict(key = "'OwnCache-selectAll'", cacheNames = "1m"),
	})
	public int delete(OwnPo ownPo) {
		return ownMapper.delete(ownPo);
	}


	@Caching(evict = {
			@CacheEvict(key = "'OwnCache-selectOne-'+#p0", cacheNames = "1m"),
			@CacheEvict(key = "'OwnCache-selectAll'", cacheNames = "1m"),
	})
	public int update(OwnPo ownPo) {
		ownPo.setCreateTime(null);
		ownPo.setUpdateTime(new Date());
		return ownMapper.update(ownPo);
	}

	//

	@Cacheable(key = "'OwnCache-selectPageSome-'+#p0", cacheNames = "1m")
	public List<OwnBo> selectPageSome(OwnQuery ownQuery) {
		return ownMapper.selectPageSome(ownQuery);
	}

	@Cacheable(key = "'OwnCache-selectAllSome-'+#p0", cacheNames = "1m")
	public List<OwnBo> selectAllSome(OwnQuery ownQuery) {
		return ownMapper.selectAllSome(ownQuery);
	}

	@Cacheable(key = "'OwnCache-selectCount-'+#p0", cacheNames = "1m")
	public int selectCount(OwnQuery ownQuery) {
		return ownMapper.selectCount(ownQuery);
	}

	@Cacheable(key = "'OwnCache-selectAllSort-'+#p0", cacheNames = "1m")
	public List<String> selectAllSort(OwnQuery ownQuery) {
		return ownMapper.selectAllSort(ownQuery);
	}
}
