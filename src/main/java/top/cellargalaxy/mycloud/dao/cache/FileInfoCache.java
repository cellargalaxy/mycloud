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

import java.util.Date;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/13
 */
@Repository
@CacheConfig
public class FileInfoCache implements FileInfoDao {
	@Autowired
	private FileInfoMapper fileInfoMapper;

	@Cacheable(key = "'FileInfoCache-selectOne-'+#p0", cacheNames = "1m")
	public FileInfoBo selectOne(FileInfoPo fileInfoPo) {
		return fileInfoMapper.selectOne(fileInfoPo);
	}

	@Cacheable(key = "'FileInfoCache-selectAll'", cacheNames = "1m")
	public List<FileInfoBo> selectAll() {
		return fileInfoMapper.selectAll();
	}

	@Cacheable(key = "'FileInfoCache-selectAllContentType'", cacheNames = "1m")
	public List<String> selectAllContentType() {
		return fileInfoMapper.selectAllContentType();
	}

	//

	@Caching(evict = {
			@CacheEvict(key = "'FileInfoCache-selectOne-'+#p0", cacheNames = "1m"),
			@CacheEvict(key = "'FileInfoCache-selectAll'", cacheNames = "1m"),
			@CacheEvict(key = "'FileInfoCache-selectAllContentType'", cacheNames = "1m"),
	})
	public int insert(FileInfoPo fileInfoPo) {
		Date date = new Date();
		fileInfoPo.setCreateTime(date);
		return fileInfoMapper.insert(fileInfoPo);
	}


	@Caching(evict = {
			@CacheEvict(key = "'FileInfoCache-selectOne-'+#p0", cacheNames = "1m"),
			@CacheEvict(key = "'FileInfoCache-selectAll'", cacheNames = "1m"),
			@CacheEvict(key = "'FileInfoCache-selectAllContentType'", cacheNames = "1m"),
	})
	public int delete(FileInfoPo fileInfoPo) {
		return fileInfoMapper.delete(fileInfoPo);
	}


	@Caching(evict = {
			@CacheEvict(key = "'FileInfoCache-selectOne-'+#p0", cacheNames = "1m"),
			@CacheEvict(key = "'FileInfoCache-selectAll'", cacheNames = "1m"),
			@CacheEvict(key = "'FileInfoCache-selectAllContentType'", cacheNames = "1m"),
	})
	public int update(FileInfoPo fileInfoPo) {
		fileInfoPo.setCreateTime(null);
		return fileInfoMapper.update(fileInfoPo);
	}

	//

	@Cacheable(key = "'FileInfoCache-selectPageSome-'+#p0", cacheNames = "1m")
	public List<FileInfoBo> selectPageSome(FileInfoQuery fileInfoQuery) {
		return fileInfoMapper.selectPageSome(fileInfoQuery);
	}

	@Cacheable(key = "'FileInfoCache-selectAllSome-'+#p0", cacheNames = "1m")
	public List<FileInfoBo> selectAllSome(FileInfoQuery fileInfoQuery) {
		return fileInfoMapper.selectAllSome(fileInfoQuery);
	}

	@Cacheable(key = "'FileInfoCache-selectCount-'+#p0", cacheNames = "1m")
	public int selectCount(FileInfoQuery fileInfoQuery) {
		return fileInfoMapper.selectCount(fileInfoQuery);
	}
}
