package top.cellargalaxy.mycloud.service.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.dao.BlockDao;
import top.cellargalaxy.mycloud.dao.FileBlockDao;
import top.cellargalaxy.mycloud.dao.FileInfoDao;
import top.cellargalaxy.mycloud.model.bo.BlockBo;
import top.cellargalaxy.mycloud.model.bo.FileBlockBo;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.schedule.DownloadFileTask;
import top.cellargalaxy.mycloud.model.bo.schedule.Task;
import top.cellargalaxy.mycloud.model.query.BlockQuery;
import top.cellargalaxy.mycloud.model.query.FileBlockQuery;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;

import java.io.*;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/30
 */
@Service
public class DownloadFileTaskExecute implements TaskExecute<DownloadFileTask> {
	public static final String TASK_SORT = DownloadFileTask.TASK_SORT;
	@Autowired
	private FileBlockDao fileBlockDao;
	@Autowired
	private FileInfoDao fileInfoDao;
	@Autowired
	private BlockDao blockDao;

	@Override
	public void executeTask(DownloadFileTask downloadFileTask) throws Exception {
		FileInfoQuery fileInfoQuery = downloadFileTask.getFileInfoQuery();
		File file = downloadFileTask.getFile();

		try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
			downloadFile(fileInfoQuery, outputStream);
		}

		downloadFileTask.setStatus(Task.SUCCESS_STATUS);
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
