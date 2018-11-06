package top.cellargalaxy.mycloud.dao.file;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Repository;
import top.cellargalaxy.mycloud.dao.mapper.FileInfoMapper;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/10/30
 */
@ConditionalOnMissingBean(DataSource.class)
@Repository
public class FileInfoFileDao implements FileInfoMapper {
	@Override
	public int insert(FileInfoPo fileInfoPo) {
		return 0;
	}

	@Override
	public int delete(FileInfoPo fileInfoPo) {
		return 0;
	}

	@Override
	public int update(FileInfoPo fileInfoPo) {
		return 0;
	}

	@Override
	public FileInfoBo selectOne(FileInfoPo fileInfoPo) {
		return null;
	}

	@Override
	public List<FileInfoBo> selectPageSome(FileInfoQuery fileInfoQuery) {
		return Collections.EMPTY_LIST;
	}

	@Override
	public List<FileInfoBo> selectAllSome(FileInfoQuery fileInfoQuery) {
		return Collections.EMPTY_LIST;
	}

	@Override
	public int selectCount(FileInfoQuery fileInfoQuery) {
		return 0;
	}

	@Override
	public List<FileInfoBo> selectAll() {
		return Collections.EMPTY_LIST;
	}

	@Override
	public List<String> selectAllContentType() {
		return Collections.EMPTY_LIST;
	}
}
