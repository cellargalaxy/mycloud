package top.cellargalaxy.mycloud.service.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.model.bo.BlockBo;
import top.cellargalaxy.mycloud.model.bo.FileBlockBo;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.schedule.DownloadFileTask;
import top.cellargalaxy.mycloud.model.bo.schedule.Task;
import top.cellargalaxy.mycloud.model.query.BlockQuery;
import top.cellargalaxy.mycloud.model.query.FileBlockQuery;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.service.BlockService;
import top.cellargalaxy.mycloud.service.FileBlockService;
import top.cellargalaxy.mycloud.service.FileInfoService;

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
	private FileBlockService fileBlockService;
	@Autowired
	private FileInfoService fileInfoService;
	@Autowired
	private BlockService blockService;

	@Override
	public void executeTask(DownloadFileTask downloadFileTask) throws Exception {
		FileInfoQuery fileInfoQuery = downloadFileTask.getFileInfoQuery();
		File file = downloadFileTask.getFile();

		try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
			String string = downloadFile(fileInfoQuery, outputStream);
			if (string != null) {
				downloadFileTask.setStatus(Task.FAIL_STATUS);
				downloadFileTask.setMassage(string);
			} else {
				downloadFileTask.setStatus(Task.SUCCESS_STATUS);
			}
		}
	}

	public String downloadFile(FileInfoQuery fileInfoQuery, OutputStream outputStream) throws IOException {
		FileInfoBo fileInfoBo = fileInfoService.getFileInfo(fileInfoQuery);
		if (fileInfoBo == null) {
			return "查找不到文件";
		}
		fileInfoQuery.setFileId(fileInfoBo.getFileId());
		fileInfoQuery.setMd5(fileInfoBo.getMd5());
		fileInfoQuery.setContentType(fileInfoBo.getContentType());
		fileInfoQuery.setFileLength(fileInfoBo.getFileLength());

		FileBlockQuery fileBlockQuery = new FileBlockQuery();
		fileBlockQuery.setFileId(fileInfoQuery.getFileId());
		List<FileBlockBo> fileBlockBos = fileBlockService.listFileBlock(fileBlockQuery);
		if (fileBlockBos == null || fileBlockBos.size() == 0) {
			return "查找不到文件";
		}

		for (FileBlockBo fileBlockBo : fileBlockBos) {
			BlockQuery blockQuery = new BlockQuery();
			blockQuery.setBlockId(fileBlockBo.getBlockId());
			BlockBo blockBo = blockService.getBlock(blockQuery);
			outputStream.write(blockBo.getBlock());
		}
		return null;
	}

}
