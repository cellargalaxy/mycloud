package top.cellargalaxy.mycloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.schedule.DownloadFileTask;
import top.cellargalaxy.mycloud.model.bo.schedule.UploadFileTask;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.service.FileInfoService;
import top.cellargalaxy.mycloud.service.FileService;
import top.cellargalaxy.mycloud.service.TaskService;
import top.cellargalaxy.mycloud.service.schedule.DownloadFileTaskExecute;
import top.cellargalaxy.mycloud.service.schedule.UploadFileTaskExecute;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Service
public class FileServiceImpl implements FileService {
	@Autowired
	private TaskService taskService;
	@Autowired
	private UploadFileTaskExecute uploadFileTaskExecute;
	@Autowired
	private DownloadFileTaskExecute downloadFileTaskExecute;
	@Autowired
	private FileInfoService fileInfoService;
	@Autowired
	private MycloudConfiguration mycloudConfiguration;

	@Override
	public void addUploadFileTask(UserPo userPo, OwnPo ownPo, File file, String contentType) {
		taskService.addWaitTask(new UploadFileTask(userPo, ownPo, file, contentType));
	}

	@Override
	public String uploadFile(UserPo userPo, OwnPo ownPo, File file, String contentType) throws IOException {
		return uploadFileTaskExecute.uploadFile(ownPo, file, contentType);
	}

	@Override
	public void addDownloadFileTask(UserPo userPo, FileInfoPo fileInfoPo, File file) {
		taskService.addWaitTask(new DownloadFileTask(userPo, fileInfoPo, file));
	}

	@Override
	public String downloadFile(FileInfoPo fileInfoPo, OutputStream outputStream) throws IOException {
		return downloadFileTaskExecute.downloadFile(fileInfoPo, outputStream);
	}

	@Override
	public String restoreAllFileToLocal(UserPo userPo) {
		List<FileInfoBo> fileInfoBos = fileInfoService.listAllFileInfo();
		for (FileInfoBo fileInfoBo : fileInfoBos) {
			addDownloadFileTask(userPo, fileInfoBo, new File(mycloudConfiguration.getMycloudDrivePath() + File.separator + fileInfoBo.getMd5()));
		}
		return null;
	}
}
