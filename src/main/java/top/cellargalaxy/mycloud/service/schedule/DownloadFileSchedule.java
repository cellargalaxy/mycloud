package top.cellargalaxy.mycloud.service.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import top.cellargalaxy.mycloud.dao.BlockDao;
import top.cellargalaxy.mycloud.dao.FileBlockDao;
import top.cellargalaxy.mycloud.dao.FileInfoDao;
import top.cellargalaxy.mycloud.exception.GlobalException;
import top.cellargalaxy.mycloud.model.bo.BlockBo;
import top.cellargalaxy.mycloud.model.bo.FileBlockBo;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.schedule.DownloadFileTask;
import top.cellargalaxy.mycloud.model.query.BlockQuery;
import top.cellargalaxy.mycloud.model.query.FileBlockQuery;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedTransferQueue;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public class DownloadFileSchedule extends Schedule<DownloadFileTask> {
	@Autowired
	private FileInfoDao fileInfoDao;
	@Autowired
	private FileBlockDao fileBlockDao;
	@Autowired
	private BlockDao blockDao;

	@Scheduled(fixedDelay = 1000 * 5)
	public void downloadFileSchedule() {
		while (true) {
			try (DownloadFileTask downloadFileTask = getWaitTask()) {
				downloadFile(downloadFileTask);
				addFinishTask(downloadFileTask);
			} catch (Exception e) {
				e.printStackTrace();
				GlobalException.add(e, 0, "未知异常");
			}
		}
	}

	public void downloadFile(DownloadFileTask downloadFileTask) throws IOException {
		FileInfoQuery fileInfoQuery = downloadFileTask.getFileInfoQuery();
		File file = downloadFileTask.getFile();

		try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
			downloadFile(fileInfoQuery, outputStream);
		}

		downloadFileTask.setStatus(1);
	}

	public void downloadFile(FileInfoQuery fileInfoQuery, OutputStream outputStream) throws IOException {
		if (fileInfoQuery.getFileId() < 1) {
			FileInfoBo fileInfoBo = fileInfoDao.selectOne(fileInfoQuery);
			fileInfoQuery.setFileId(fileInfoBo.getFileId());
		}

		FileBlockQuery fileBlockQuery = new FileBlockQuery();
		fileBlockQuery.setFileId(fileInfoQuery.getFileId());
		List<FileBlockBo> fileBlockBos = fileBlockDao.selectSome(fileBlockQuery);

		for (FileBlockBo fileBlockBo : fileBlockBos) {
			BlockQuery blockQuery = new BlockQuery();
			blockQuery.setBlockId(fileBlockBo.getBlockId());
			BlockBo blockBo = blockDao.selectOne(blockQuery);
			outputStream.write(blockBo.getBlock());
		}
	}

}
