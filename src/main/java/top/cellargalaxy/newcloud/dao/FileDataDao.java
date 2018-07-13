package top.cellargalaxy.newcloud.dao;

import top.cellargalaxy.newcloud.model.po.FileDataPo;
import top.cellargalaxy.newcloud.model.query.FileDataQuery;

/**
 * @author cellargalaxy
 * @time 2018/7/3
 */
public interface FileDataDao {
	int insert(FileDataPo fileDataPo);

	int delete(FileDataQuery fileDataQuery);

	FileDataPo select(FileDataQuery fileDataQuery);

	int update(FileDataPo fileDataPo);

}
