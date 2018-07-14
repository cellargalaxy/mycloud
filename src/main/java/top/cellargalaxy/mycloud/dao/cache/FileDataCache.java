package top.cellargalaxy.mycloud.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import top.cellargalaxy.mycloud.dao.FileDataDao;
import top.cellargalaxy.mycloud.dao.db.FileDataMapper;
import top.cellargalaxy.mycloud.model.po.FileDataPo;
import top.cellargalaxy.mycloud.model.query.FileDataQuery;

/**
 * @author cellargalaxy
 * @time 2018/7/13
 */
@Repository
@CacheConfig(cacheNames = "fileData")
public class FileDataCache implements FileDataDao {
	@Autowired
	private FileDataMapper fileDataMapper;

	@CacheEvict(key = "#p0.fileId")
	public int insert(FileDataPo fileDataPo) {
		return fileDataMapper.insert(fileDataPo);
	}

	@CacheEvict(key = "#p0.fileId")
	public int delete(FileDataQuery fileDataQuery) {
		return fileDataMapper.delete(fileDataQuery);
	}

	@Cacheable(key = "#p0.fileId", condition = "#p0.fileId>0")
	public FileDataPo select(FileDataQuery fileDataQuery) {
		return fileDataMapper.select(fileDataQuery);
	}

	@CacheEvict(key = "#p0.fileId")
	public int update(FileDataPo fileDataPo) {
		return fileDataMapper.update(fileDataPo);
	}
}
