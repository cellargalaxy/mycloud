package top.cellargalaxy.mycloud.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import top.cellargalaxy.mycloud.dao.FileBlockDao;
import top.cellargalaxy.mycloud.dao.mapper.FileBlockMapper;
import top.cellargalaxy.mycloud.model.bo.FileBlockBo;
import top.cellargalaxy.mycloud.model.po.FileBlockPo;
import top.cellargalaxy.mycloud.model.query.FileBlockQuery;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
@Repository
@CacheConfig(cacheNames = "fileBlock")
public class FileBlockCache implements FileBlockDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private FileBlockMapper fileBlockMapper;

	@Cacheable(key = "'selectOne'+(#p0.fileId+'-'+#p0.blockId)", condition = "true")
	public FileBlockBo selectOne(FileBlockPo fileBlockPo) {
		logger.debug("selectOne:{}", fileBlockPo);
		return fileBlockMapper.selectOne(fileBlockPo);
	}

	@Cacheable(key = "'selectSome'+#p0", condition = "true")
	public List<FileBlockBo> selectSome(FileBlockQuery fileBlockQuery) {
		logger.debug("selectSome:{}", fileBlockQuery);
		return fileBlockMapper.selectSome(fileBlockQuery);
	}

	@Cacheable(key = "'selectCount'+#p0", condition = "true")
	public int selectCount(FileBlockQuery fileBlockQuery) {
		logger.debug("selectCount:{}", fileBlockQuery);
		return fileBlockMapper.selectCount(fileBlockQuery);
	}

	@Cacheable(key = "'selectAll'", condition = "true")
	public List<FileBlockBo> selectAll() {
		logger.debug("selectAll");
		return fileBlockMapper.selectAll();
	}

	//
	@Caching(evict = {
			@CacheEvict(key = "'selectOne'+(#p0.fileId+'-'+#p0.blockId)"),
			@CacheEvict(key = "'selectAll'"),
	})
	public int insert(FileBlockPo fileBlockPo) {
		logger.debug("insert:{}", fileBlockPo);
		return fileBlockMapper.insert(fileBlockPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'selectOne'+(#p0.fileId+'-'+#p0.blockId)"),
			@CacheEvict(key = "'selectAll'"),
	})
	public int delete(FileBlockPo fileBlockPo) {
		logger.debug("delete:{}", fileBlockPo);
		return fileBlockMapper.delete(fileBlockPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'selectOne'+(#p0.fileId+'-'+#p0.blockId)"),
			@CacheEvict(key = "'selectAll'"),
	})
	public int update(FileBlockPo fileBlockPo) {
		logger.debug("update:{}", fileBlockPo);
		return fileBlockMapper.update(fileBlockPo);
	}
}
