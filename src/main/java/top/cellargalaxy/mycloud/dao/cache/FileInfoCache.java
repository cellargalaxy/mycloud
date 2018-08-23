package top.cellargalaxy.mycloud.dao.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import top.cellargalaxy.mycloud.dao.FileInfoDao;
import top.cellargalaxy.mycloud.dao.mapper.FileInfoMapper;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/13
 */
@Repository
@CacheConfig(cacheNames = "fileInfo")
public class FileInfoCache implements FileInfoDao {
	private Logger logger = LoggerFactory.getLogger(FileInfoCache.class);
	@Autowired
	private FileInfoMapper fileInfoMapper;

	@Cacheable(key = "'selectOne'+(#p0.md5!=null?#p0.md5:#p0.fileId)", condition = "true")
	public FileInfoBo selectOne(FileInfoPo fileInfoPo) {
		logger.info("selectOne:{}", fileInfoPo);
		return fileInfoMapper.selectOne(fileInfoPo);
	}

	public List<FileInfoBo> selectSome(FileInfoQuery fileInfoQuery) {
		logger.info("selectSome:{}", fileInfoQuery);
		return fileInfoMapper.selectSome(fileInfoQuery);
	}

	public int selectCount(FileInfoQuery fileInfoQuery) {
		logger.info("selectCount:{}", fileInfoQuery);
		return fileInfoMapper.selectCount(fileInfoQuery);
	}

	public List<FileInfoBo> selectAll() {
		logger.info("selectAll");
		return fileInfoMapper.selectAll();
	}

	@Cacheable(key = "'selectContentType'", condition = "true")
	public List<String> selectContentType() {
		logger.info("selectContentType");
		return fileInfoMapper.selectContentType();
	}

	//
	@Caching(evict = {
			@CacheEvict(key = "'selectContentType'"),
	})
	public int insert(FileInfoPo fileInfoPo) {
		logger.info("insert:{}", fileInfoPo);
		return fileInfoMapper.insert(fileInfoPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'selectOne'+#p0.fileId"),
			@CacheEvict(key = "'selectOne'+#p0.md5"),
			@CacheEvict(key = "'selectContentType'"),
	})
	public int delete(FileInfoPo fileInfoPo) {
		logger.info("delete:{}", fileInfoPo);
		return fileInfoMapper.delete(fileInfoPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'selectOne'+#p0.fileId"),
			@CacheEvict(key = "'selectOne'+#p0.md5"),
			@CacheEvict(key = "'selectContentType'"),
	})
	public int update(FileInfoPo fileInfoPo) {
		logger.info("update:{}", fileInfoPo);
		return fileInfoMapper.update(fileInfoPo);
	}
}
