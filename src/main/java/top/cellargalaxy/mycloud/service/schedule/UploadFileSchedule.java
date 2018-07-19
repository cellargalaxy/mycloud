package top.cellargalaxy.mycloud.service.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.cellargalaxy.mycloud.configuration.Configuration;
import top.cellargalaxy.mycloud.dao.BlockDao;
import top.cellargalaxy.mycloud.dao.FileBlockDao;
import top.cellargalaxy.mycloud.dao.FileInfoDao;
import top.cellargalaxy.mycloud.dao.OwnDao;
import top.cellargalaxy.mycloud.exception.GlobalException;
import top.cellargalaxy.mycloud.model.bo.schedule.UploadFileTask;
import top.cellargalaxy.mycloud.model.po.BlockPo;
import top.cellargalaxy.mycloud.model.po.FileBlockPo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.util.FileBlocks;
import top.cellargalaxy.mycloud.util.StreamUtil;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Component
public class UploadFileSchedule extends Schedule<UploadFileTask> {
	@Autowired
	private FileInfoDao fileInfoDao;
	@Autowired
	private Configuration configuration;
	@Autowired
	private BlockDao blockDao;
	@Autowired
	private FileBlockDao fileBlockDao;
	@Autowired
	private OwnDao ownDao;

	@Scheduled(fixedDelay = 1000 * 5)
	public void uploadFileSchedule() {
		while (true) {
			try (UploadFileTask uploadFileTask = getWaitTask()) {
				uploadFileSchedule(uploadFileTask);
				addFinishTask(uploadFileTask);
			} catch (Exception e) {
				e.printStackTrace();
				GlobalException.add(e, 0, "未知异常");
			}
		}
	}

	public void uploadFileSchedule(UploadFileTask uploadFileTask) throws IOException {
		File file = uploadFileTask.getFile();
		String contentType = uploadFileTask.getContentType();
		String md5 = StreamUtil.md5Hex(file);
		long fileLength = file.length();
		OwnPo ownPo = uploadFileTask.getOwnPo();

		//写数据块
		int[] blockIds;
		try (FileBlocks fileBlocks = new FileBlocks(file, configuration.getBlobLength())) {
			blockIds = new int[fileBlocks.getBlockLengths().length];
			byte[] block;
			while ((block = fileBlocks.next()) != null) {
				BlockPo blockPo = new BlockPo();
				blockPo.setBlock(block);
				blockDao.insert(blockPo);
				blockIds[fileBlocks.getBlockIndex() - 1] = blockPo.getBlockId();
			}
		}

		//添加文件信息
		FileInfoPo fileInfoPo = new FileInfoPo();
		fileInfoPo.setMd5(md5);
		fileInfoPo.setFileLength(fileLength);
		fileInfoPo.setContentType(contentType);
		fileInfoDao.insert(fileInfoPo);

		//添加文件信息与数据块对应数据
		for (int blockId : blockIds) {
			FileBlockPo fileBlockPo = new FileBlockPo();
			fileBlockPo.setFileId(fileInfoPo.getFileId());
			fileBlockPo.setBlockId(blockId);
			fileBlockDao.insert(fileBlockPo);
		}

		//添加所属
		ownPo.setFileId(fileInfoPo.getFileId());
		ownDao.insert(ownPo);

		uploadFileTask.setStatus(1);
	}
}
