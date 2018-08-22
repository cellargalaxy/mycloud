package top.cellargalaxy.mycloud.dao.cache;

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
	@Autowired
	private FileInfoMapper fileInfoMapper;

	@Cacheable(key = "'selectOne'+#p0", condition = "true")
	public FileInfoBo selectOne(FileInfoPo fileInfoPo) {
		return fileInfoMapper.selectOne(fileInfoPo);
	}

	@Cacheable(key = "'selectSome'+#p0", condition = "true")
	public List<FileInfoBo> selectSome(FileInfoQuery fileInfoQuery) {
		return fileInfoMapper.selectSome(fileInfoQuery);
	}

	@Cacheable(key = "'selectCount'+#p0", condition = "true")
	public int selectCount(FileInfoQuery fileInfoQuery) {
		return fileInfoMapper.selectCount(fileInfoQuery);
	}

	@Cacheable(key = "'selectAll'", condition = "true")
	public List<FileInfoBo> selectAll() {
		return fileInfoMapper.selectAll();
	}

	@Cacheable(key = "'selectContentType'", condition = "true")
	public List<String> selectContentType() {
		return fileInfoMapper.selectContentType();
	}

	//
	@Caching(evict = {
			@CacheEvict(key = "'selectAll'"),
			@CacheEvict(key = "'selectContentType'"),
	})
	public int insert(FileInfoPo fileInfoPo) {
		return fileInfoMapper.insert(fileInfoPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'selectAll'"),
			@CacheEvict(key = "'selectContentType'"),
	})
	public int delete(FileInfoPo fileInfoPo) {
		return fileInfoMapper.delete(fileInfoPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "'selectContentType'"),
	})
	public int update(FileInfoPo fileInfoPo) {
		return fileInfoMapper.update(fileInfoPo);
	}
}
