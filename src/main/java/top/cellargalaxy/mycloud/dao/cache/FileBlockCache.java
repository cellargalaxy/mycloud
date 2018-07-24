package top.cellargalaxy.mycloud.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
	@Autowired
	private FileBlockMapper fileBlockMapper;

	@CacheEvict(key = "#p0.fileId")
	public int insert(FileBlockPo fileBlockPo) {
		return fileBlockMapper.insert(fileBlockPo);
	}

	@CacheEvict(key = "#p0.fileId")
	public int delete(FileBlockQuery fileBlockQuery) {
		return fileBlockMapper.delete(fileBlockQuery);
	}

	@Cacheable(key = "#p0.fileId", condition = "#p0.fileId>0")
	public FileBlockBo selectOne(FileBlockQuery fileBlockQuery) {
		return fileBlockMapper.selectOne(fileBlockQuery);
	}

	public List<FileBlockBo> selectSome(FileBlockQuery fileBlockQuery) {
		return fileBlockMapper.selectSome(fileBlockQuery);
	}

	@CacheEvict(key = "#p0.fileId")
	public int update(FileBlockPo fileBlockPo) {
		return fileBlockMapper.update(fileBlockPo);
	}
}