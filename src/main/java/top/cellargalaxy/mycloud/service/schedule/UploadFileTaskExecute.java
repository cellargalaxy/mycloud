package top.cellargalaxy.mycloud.service.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.model.bo.schedule.UploadFileTask;
import top.cellargalaxy.mycloud.model.po.BlockPo;
import top.cellargalaxy.mycloud.model.po.FileBlockPo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.service.BlockService;
import top.cellargalaxy.mycloud.service.FileBlockService;
import top.cellargalaxy.mycloud.service.FileInfoService;
import top.cellargalaxy.mycloud.service.OwnService;
import top.cellargalaxy.mycloud.util.FileBlocks;
import top.cellargalaxy.mycloud.util.StreamUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author cellargalaxy
 * @time 2018/7/30
 */
@Transactional
@Service
public class UploadFileTaskExecute implements TaskExecute<UploadFileTask> {
	private Logger logger = LoggerFactory.getLogger(UploadFileTaskExecute.class);
	public static final String TASK_SORT = UploadFileTask.TASK_SORT;
	@Autowired
	private FileInfoService fileInfoService;
	@Autowired
	private OwnService ownService;
	@Autowired
	private BlockService blockService;
	@Autowired
	private FileBlockService fileBlockService;
	@Autowired
	private MycloudConfiguration mycloudConfiguration;

	@Override
	public String executeTask(UploadFileTask uploadFileTask) throws Exception {
		logger.info("executeTask:{}", uploadFileTask);
		OwnPo ownPo = uploadFileTask.getOwnPo();
		File file = uploadFileTask.getFile();
		String contentType = uploadFileTask.getContentType();

		return uploadFile(ownPo, file, contentType);
	}

	public String uploadFile(OwnPo ownPo, File file, String contentType) throws IOException {
		try {
			logger.info("uploadFile, OwnPo:{}, file:{}, contentType:{}", ownPo, file, contentType);
			String md5 = StreamUtil.md5Hex(file);
			long fileLength = file.length();

			FileInfoPo fileInfoPo = new FileInfoPo();
			fileInfoPo.setContentType(contentType);
			fileInfoPo.setMd5(md5);
			fileInfoPo.setFileLength(fileLength);

			String string = fileInfoService.addFileInfo(fileInfoPo);
			if (string != null) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return string;
			}

			//写数据块
			int[] blockIds;
			try (FileBlocks fileBlocks = new FileBlocks(file, mycloudConfiguration.getBlobLength())) {
				blockIds = new int[fileBlocks.getBlockCount()];
				int blockIndex = 0;
				byte[] block;
				while ((block = fileBlocks.next()) != null) {
					BlockPo blockPo = new BlockPo();
					blockPo.setBlock(block);
					string = blockService.addBlock(blockPo);
					if (string != null) {
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return string;
					}
					blockIds[blockIndex] = blockPo.getBlockId();
					blockIndex++;
				}
			}

			//添加文件信息与数据块对应数据
			for (int blockId : blockIds) {
				FileBlockPo fileBlockPo = new FileBlockPo();
				fileBlockPo.setFileId(fileInfoPo.getFileId());
				fileBlockPo.setBlockId(blockId);
				string = fileBlockService.addFileBlock(fileBlockPo);
				if (string != null) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return string;
				}
			}

			ownPo.setFileId(fileInfoPo.getFileId());
			string = ownService.addOwn(ownPo);
			if (string != null) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return string;
			}
			return null;
		} finally {
			if (file != null) {
				file.delete();
			}
		}
	}
}
