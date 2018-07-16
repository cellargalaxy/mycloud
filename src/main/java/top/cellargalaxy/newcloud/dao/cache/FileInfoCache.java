package top.cellargalaxy.newcloud.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;
import top.cellargalaxy.newcloud.dao.FileInfoDao;
import top.cellargalaxy.newcloud.dao.db.FileInfoMapper;
import top.cellargalaxy.newcloud.model.bo.FileInfoBo;
import top.cellargalaxy.newcloud.model.po.FileInfoPo;
import top.cellargalaxy.newcloud.model.query.FileInfoQuery;

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
			@CacheEvict(key = "'selectContentType'")})
	public int insert(FileInfoPo fileInfoPo) {
		return fileInfoMapper.insert(fileInfoPo);
	}

	@Caching(evict = {
			@CacheEvict(key = "#p0.fileId"),
			@CacheEvict(key = "'selectContentType'")})
	public int delete(FileInfoQuery fileInfoQuery) {
		return fileInfoMapper.delete(fileInfoQuery);
	}

	@Cacheable(key = "#p0.fileId", condition = "#p0.fileId>0")
	public FileInfoBo selectOne(FileInfoQuery fileInfoQuery) {
		return fileInfoMapper.selectOne(fileInfoQuery);
	}

	public List<FileInfoBo> selectSome(FileInfoQuery fileInfoQuery) {
		return fileInfoMapper.selectSome(fileInfoQuery);
	}

	@Cacheable(key = "'selectContentType'")
	public List<String> selectContentType() {
		return fileInfoMapper.selectContentType();
	}

	@Caching(evict = {
			@CacheEvict(key = "#p0.fileId"),
			@CacheEvict(key = "'selectContentType'")})
	public int update(FileInfoPo fileInfoPo) {
		return fileInfoMapper.update(fileInfoPo);
	}
}
