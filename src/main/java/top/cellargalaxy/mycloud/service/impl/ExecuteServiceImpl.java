package top.cellargalaxy.mycloud.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.model.bo.schedule.DownloadFileTask;
import top.cellargalaxy.mycloud.model.bo.schedule.RemoveFileTask;
import top.cellargalaxy.mycloud.model.bo.schedule.UploadFileTask;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.service.ExecuteService;
import top.cellargalaxy.mycloud.service.TaskService;
import top.cellargalaxy.mycloud.service.schedule.DownloadFileTaskExecute;
import top.cellargalaxy.mycloud.util.StreamUtil;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Service
public class ExecuteServiceImpl implements ExecuteService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TaskService taskService;
	@Autowired
	private DownloadFileTaskExecute downloadFileTaskExecute;

	private final File driveFolder;

	@Autowired
	public ExecuteServiceImpl(MycloudConfiguration mycloudConfiguration) {
		driveFolder = new File(mycloudConfiguration.getMycloudDrivePath());
		logger.info("ExecuteServiceImpl:{}", driveFolder);
	}

//	@Override
//	public String uploadFile(OwnPo ownPo, File file, String contentType) throws IOException {
//		logger.info("uploadFile:{}, {}, {}", ownPo, file, contentType);
//		return uploadFileTaskExecute.uploadFile(ownPo, file, contentType);
//	}

	@Override
	public String executeUploadFileTask(UserPo userPo, OwnPo ownPo, File file, String contentType) throws Exception {
		logger.info("executeUploadFileTask:{}, {}, {}, {}", userPo, ownPo, file, contentType);
		return taskService.executeTask(new UploadFileTask(userPo, ownPo, file, contentType));
	}

	@Override
	public void addUploadFileTask(UserPo userPo, OwnPo ownPo, File file, String contentType) {
		logger.info("addUploadFileTask:{}, {}, {}, {}", userPo, ownPo, file, contentType);
		taskService.addWaitTask(new UploadFileTask(userPo, ownPo, file, contentType));
	}

	@Override
	public String downloadFile(FileInfoPo fileInfoPo, File file) throws IOException {
		logger.info("downloadFile:{}, {}", fileInfoPo, file);
		try (OutputStream outputStream = StreamUtil.getOutputStream(file)) {
			return downloadFile(fileInfoPo, outputStream);
		}
	}

	@Override
	public String downloadFile(FileInfoPo fileInfoPo, OutputStream outputStream) throws IOException {
		logger.info("downloadFile:{}", fileInfoPo);
		return downloadFileTaskExecute.downloadFile(fileInfoPo, outputStream);
	}

	@Override
	public String executeDownloadFileTask(UserPo userPo, FileInfoPo fileInfoPo, File file) throws Exception {
		logger.info("executeDownloadFileTask:{}, {}, {}", userPo, fileInfoPo, file);
		return taskService.executeTask(new DownloadFileTask(userPo, fileInfoPo, file));
	}

	@Override
	public void addDownloadFileTask(UserPo userPo, FileInfoPo fileInfoPo, File file) {
		logger.info("addDownloadFileTask:{}, {}, {}", userPo, fileInfoPo, file);
		taskService.addWaitTask(new DownloadFileTask(userPo, fileInfoPo, file));
	}

//	@Override
//	public String removeFile(FileInfoPo fileInfoPo) {
//		logger.info("removeFile:{}", fileInfoPo);
//		return removeFileTaskExecute.removeFileInfo(fileInfoPo);
//	}

	@Override
	public String executeRemoveFileTask(UserPo userPo, FileInfoPo fileInfoPo) throws Exception {
		logger.info("executeRemoveFileTask:{}, {}", userPo, fileInfoPo);
		return taskService.executeTask(new RemoveFileTask(userPo, fileInfoPo));
	}

	@Override
	public void addRemoveFileTask(UserPo userPo, FileInfoPo fileInfoPo) {
		logger.info("addRemoveFileTask:{}, {}", userPo, fileInfoPo);
		taskService.addWaitTask(new RemoveFileTask(userPo, fileInfoPo));
	}
}
