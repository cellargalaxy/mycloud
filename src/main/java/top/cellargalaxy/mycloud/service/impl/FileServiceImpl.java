package top.cellargalaxy.mycloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.dao.FileInfoDao;
import top.cellargalaxy.mycloud.model.bo.schedule.DownloadFileTask;
import top.cellargalaxy.mycloud.model.bo.schedule.UploadFileTask;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.service.FileService;
import top.cellargalaxy.mycloud.service.schedule.DownloadFileSchedule;
import top.cellargalaxy.mycloud.service.schedule.UploadFileSchedule;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Service
public class FileServiceImpl implements FileService {
	@Autowired
	private FileInfoDao fileInfoDao;
	@Autowired
	private UploadFileSchedule uploadFileSchedule;
	@Autowired
	private DownloadFileSchedule downloadFileSchedule;

	@Override
	public void addUploadFileTask(File file, String contentType, OwnPo ownPo) {
		UploadFileTask uploadFileTask = new UploadFileTask(file, contentType, ownPo);
		uploadFileSchedule.addTask(uploadFileTask);
	}

	@Override
	public UploadFileTask removeUploadFileTask(String taskId) {
		return uploadFileSchedule.removeTask(taskId);
	}

	@Override
	public BlockingQueue<UploadFileTask> getWaitUploadFileTasks() {
		return uploadFileSchedule.getWaitTasks();
	}

	@Override
	public UploadFileTask getCurrentUploadFileTask() {
		return uploadFileSchedule.getCurrentTask();
	}

	@Override
	public List<UploadFileTask> getFinishUploadFileTasks() {
		return uploadFileSchedule.getFinishTasks();
	}

	@Override
	public void addDownloadFileTask(FileInfoQuery fileInfoQuery, File file) {
		DownloadFileTask downloadFileTask = new DownloadFileTask(fileInfoQuery, file);
		downloadFileSchedule.addTask(downloadFileTask);
	}

	@Override
	public void downloadFile(FileInfoQuery fileInfoQuery, OutputStream outputStream) throws IOException {
		downloadFileSchedule.downloadFile(fileInfoQuery, outputStream);
	}

	@Override
	public DownloadFileTask removeDownloadFileTask(String taskId) {
		return downloadFileSchedule.removeTask(taskId);
	}

	@Override
	public BlockingQueue<DownloadFileTask> getWaitDownloadFileTasks() {
		return downloadFileSchedule.getWaitTasks();
	}

	@Override
	public DownloadFileTask getCurrentDownloadFileTask() {
		return downloadFileSchedule.getCurrentTask();
	}

	@Override
	public List<DownloadFileTask> getFinishDownloadFileTasks() {
		return downloadFileSchedule.getFinishTasks();
	}

	@Override
	public List<String> listContentType() {
		return fileInfoDao.selectContentType();
	}
}
