package top.cellargalaxy.mycloud.service.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.schedule.UploadFileTask;
import top.cellargalaxy.mycloud.model.po.BlockPo;
import top.cellargalaxy.mycloud.model.po.FileBlockPo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.service.*;
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
	public static final String TASK_SORT = UploadFileTask.TASK_SORT;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private FileInfoService fileInfoService;
	@Autowired
	private OwnService ownService;
	@Autowired
	private BlockService blockService;
	@Autowired
	private FileBlockService fileBlockService;
	@Autowired
	private LocalFileService localFileService;

	private final int blobLength;

	@Autowired
	public UploadFileTaskExecute(MycloudConfiguration mycloudConfiguration) {
		blobLength = mycloudConfiguration.getBlobLength();
		logger.info("UploadFileTaskExecute, blobLength:{}", blobLength);
	}

	@Override
	public String executeTask(UploadFileTask uploadFileTask) throws Exception {
		logger.info("executeTask:{}", uploadFileTask);
		OwnPo ownPo = uploadFileTask.getOwnPo();
		File file = uploadFileTask.getFile();
		String contentType = uploadFileTask.getContentType();

		return uploadFile(ownPo, file, contentType);
	}

	public String uploadFile(OwnPo ownPo, File file, String contentType) throws IOException {
		logger.info("uploadFile, OwnPo:{}, file:{}, contentType:{}", ownPo, file, contentType);
		try {
			String md5 = StreamUtil.md5Hex(file);
			long fileLength = file.length();
			String mimeType = StreamUtil.getMimeType(file);
			if (mimeType != null && !mimeType.equals("application/octet-stream")) {
				contentType = mimeType;
			}

			FileInfoPo fileInfoPo = new FileInfoPo();
			fileInfoPo.setContentType(contentType);
			fileInfoPo.setMd5(md5);
			fileInfoPo.setFileLength(fileLength);
			fileInfoService.addFileInfo(fileInfoPo);

			FileInfoBo fileInfoBo = fileInfoService.getFileInfo(fileInfoPo);
			if (fileInfoBo == null) {
				return "新增文件信息失败";
			}
			String string = uploadFile(fileInfoBo, ownPo, file);
			if (string != null) {
				return string;
			}

			File localFile = localFileService.createLocalFile(fileInfoPo);
			if (localFile.getParentFile() != null && !localFile.getParentFile().exists()) {
				localFile.getParentFile().mkdirs();
			}
			file.renameTo(localFile);
			string = localFileService.addLocalFile(localFile);
			logger.info("uploadFile:addLocalFile:{}", string);
			return null;
		} finally {
			if (file != null) {
				file.delete();
			}
		}
	}

	private String uploadFile(FileInfoBo fileInfoBo, OwnPo ownPo, File file) throws IOException {
		//写数据块
		int[] blockIds;
		try (FileBlocks fileBlocks = new FileBlocks(file, blobLength)) {
			blockIds = new int[fileBlocks.getBlockCount()];
			int blockIndex = 0;
			byte[] block;
			while ((block = fileBlocks.next()) != null) {
				BlockPo blockPo = new BlockPo();
				blockPo.setBlock(block);
				String string = blockService.addBlock(blockPo);
				if (string != null) {
					return string;
				}
				blockIds[blockIndex] = blockPo.getBlockId();
				blockIndex++;
			}
		}

		//添加文件信息与数据块对应数据
		for (int blockId : blockIds) {
			FileBlockPo fileBlockPo = new FileBlockPo();
			fileBlockPo.setFileId(fileInfoBo.getFileId());
			fileBlockPo.setBlockId(blockId);
			String string = fileBlockService.addFileBlock(fileBlockPo);
			if (string != null) {
				return string;
			}
		}

		ownPo.setFileId(fileInfoBo.getFileId());
		return ownService.addOwn(ownPo);
	}
}
