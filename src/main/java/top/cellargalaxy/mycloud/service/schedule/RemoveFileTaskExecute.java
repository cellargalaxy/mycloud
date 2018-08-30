package top.cellargalaxy.mycloud.service.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.cellargalaxy.mycloud.model.bo.FileBlockBo;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.bo.schedule.RemoveFileTask;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.BlockQuery;
import top.cellargalaxy.mycloud.model.query.FileBlockQuery;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.service.BlockService;
import top.cellargalaxy.mycloud.service.FileBlockService;
import top.cellargalaxy.mycloud.service.FileInfoService;
import top.cellargalaxy.mycloud.service.OwnService;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/8/20
 */
@Transactional
@Service
public class RemoveFileTaskExecute implements TaskExecute<RemoveFileTask> {
	public static final String TASK_SORT = RemoveFileTask.TASK_SORT;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private FileInfoService fileInfoService;
	@Autowired
	private FileBlockService fileBlockService;
	@Autowired
	private BlockService blockService;
	@Autowired
	private OwnService ownService;

	@Override
	public String executeTask(RemoveFileTask removeFileTask) throws Exception {
		logger.info("executeTask:{}", removeFileTask);
		FileInfoPo fileInfoPo = removeFileTask.getFileInfoPo();

		return removeFileInfo(fileInfoPo);
	}

	public String removeFileInfo(FileInfoPo fileInfoPo) {
		logger.info("removeFileInfo:{}", fileInfoPo);
		String string = fileInfoService.removeFileInfo(fileInfoPo);
		if (string != null) {
			return string;
		}

		FileBlockQuery fileBlockQuery = new FileBlockQuery();
		fileBlockQuery.setFileId(fileInfoPo.getFileId());
		List<FileBlockBo> fileBlockBos = fileBlockService.listFileBlock(fileBlockQuery);
		string = fileBlockService.removeFileBlock(fileBlockQuery);
		if (string != null) {
			return string;
		}

		for (FileBlockBo fileBlockBo : fileBlockBos) {
			BlockQuery blockQuery = new BlockQuery();
			blockQuery.setBlockId(fileBlockBo.getBlockId());
			string = blockService.removeBlock(blockQuery);
			if (string != null) {
				return string;
			}
		}

		OwnQuery ownQuery = new OwnQuery();
		ownQuery.setFileId(fileInfoPo.getFileId());
		List<OwnBo> ownBos = ownService.listOwn(ownQuery);
		for (OwnBo ownBo : ownBos) {
			string = ownService.removeOwn(ownBo);
			if (string != null) {
				return string;
			}
		}
		return null;
	}
}
