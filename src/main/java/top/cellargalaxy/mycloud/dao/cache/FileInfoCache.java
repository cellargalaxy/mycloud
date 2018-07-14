package top.cellargalaxy.mycloud.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import top.cellargalaxy.mycloud.dao.FileInfoDao;
import top.cellargalaxy.mycloud.dao.db.FileInfoMapper;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;

import java.util.Date;
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

	@Caching(evict = {
			@CacheEvict(key = "#p0.fileId"),
			@CacheEvict(key = "'selectCreateTime'"),
			@CacheEvict(key = "'selectContentType'"),
			@CacheEvict(key = "'selectSort'")})
	public int insert(FileInfoPo fileInfoPo) {
		return fileInfoMapper.insert(fileInfoPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "#p0.fileId"),
			@CacheEvict(key = "'selectCreateTime'"),
			@CacheEvict(key = "'selectContentType'"),
			@CacheEvict(key = "'selectSort'")})
	public int delete(FileInfoQuery fileInfoQuery) {
		return fileInfoMapper.delete(fileInfoQuery);
	}

	@Cacheable(key = "#p0.fileId", condition = "#p0.fileId>0")
	public FileInfoPo selectOne(FileInfoQuery fileInfoQuery) {
		return fileInfoMapper.selectOne(fileInfoQuery);
	}

	public List<FileInfoPo> selectSome(FileInfoQuery fileInfoQuery) {
		return fileInfoMapper.selectSome(fileInfoQuery);
	}

	@Cacheable(key = "'selectCreateTime'")
	public List<Date> selectCreateTime() {
		return fileInfoMapper.selectCreateTime();
	}

	@Cacheable(key = "'selectContentType'")
	public List<String> selectContentType() {
		return fileInfoMapper.selectContentType();
	}

	@Cacheable(key = "'selectSort'")
	public List<String> selectSort() {
		return fileInfoMapper.selectSort();
	}

	@Caching(evict = {
			@CacheEvict(key = "#p0.fileId"),
			@CacheEvict(key = "'selectCreateTime'"),
			@CacheEvict(key = "'selectContentType'"),
			@CacheEvict(key = "'selectSort'")})
	public int update(FileInfoPo fileInfoPo) {
		return fileInfoMapper.update(fileInfoPo);
	}
}
