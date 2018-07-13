package top.cellargalaxy.newcloud.dao;

import top.cellargalaxy.newcloud.model.po.FileInfoPo;
import top.cellargalaxy.newcloud.model.query.FileInfoQuery;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/3
 */
public interface FileInfoDao {
	int insert(FileInfoPo fileInfoPo);

	int delete(FileInfoQuery fileInfoQuery);

	FileInfoPo selectOne(FileInfoQuery fileInfoQuery);

	List<FileInfoPo> selectSome(FileInfoQuery fileInfoQuery);

	List<String> selectContentType();

	List<String> selectSort();

	int update(FileInfoPo fileInfoPo);

}
