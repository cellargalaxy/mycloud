package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.bo.FileBlockBo;
import top.cellargalaxy.mycloud.model.po.FileBlockPo;
import top.cellargalaxy.mycloud.model.query.FileBlockQuery;

import java.util.List;

/**
 * Created by cellargalaxy on 18-8-5.
 */
public interface FileBlockService {
	String addFileBlock(FileBlockPo fileBlockPo);

	String removeFileBlock(FileBlockPo fileBlockPo);

	FileBlockBo getFileBlock(FileBlockPo fileBlockPo);

	List<FileBlockBo> listFileBlock(FileBlockQuery fileBlockQuery);

	int getFileBlockCount(FileBlockQuery fileBlockQuery);

	String changeFileBlock(FileBlockPo fileBlockPo);

	String checkAddFileBlock(FileBlockPo fileBlockPo);

	String checkChangeFileBlock(FileBlockPo fileBlockPo);
}
