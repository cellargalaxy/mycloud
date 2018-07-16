package top.cellargalaxy.newcloud.dao;

import top.cellargalaxy.newcloud.model.po.FileBlockPo;
import top.cellargalaxy.newcloud.model.query.FileBlockQuery;
import top.cellargalaxy.newcloud.model.bo.FileBlockBo;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
public interface FileBlockDao {
	String TABLE_NAME = "file_block";

	int insert(FileBlockPo fileBlockPo);

	int delete(FileBlockQuery fileBlockQuery);

	FileBlockBo selectOne(FileBlockQuery fileBlockQuery);

	List<FileBlockBo> selectSome(FileBlockQuery fileBlockQuery);

	int update(FileBlockPo fileBlockPo);

	static String checkInsert(FileBlockPo fileBlockPo) {
		if (fileBlockPo.getFileId() < 1) {
			return "文件id不得为空";
		}
		if (fileBlockPo.getBlockId() < 1) {
			return "数据块id不得为空";
		}
		return null;
	}

	static String checkUpdate(FileBlockPo fileBlockPo) {
		if (fileBlockPo.getFileId() < 1 && fileBlockPo.getBlockId() < 1) {
			return "文件id与数据块id不得同时为空";
		}
		return null;
	}
}
