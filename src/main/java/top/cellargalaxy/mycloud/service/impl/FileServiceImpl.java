package top.cellargalaxy.mycloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.model.bo.schedule.DownloadFileTask;
import top.cellargalaxy.mycloud.model.bo.schedule.Task;
import top.cellargalaxy.mycloud.model.bo.schedule.UploadFileTask;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.service.FileService;
import top.cellargalaxy.mycloud.service.schedule.TaskSchedule;

import java.io.File;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Service
public class FileServiceImpl implements FileService {
	@Autowired
	private TaskSchedule taskSchedule;

	@Override
	public void addUploadFileTask(UserPo userPo, OwnPo ownPo, File file, String contentType) {
		taskSchedule.addTask(new UploadFileTask(userPo, ownPo, file, contentType));
	}

	@Override
	public void addDownloadFileTask(UserPo userPo, FileInfoQuery fileInfoQuery, File file) {
		taskSchedule.addTask(new DownloadFileTask(userPo, fileInfoQuery, file));
	}

	@Override
	public Task removeTask(String taskId) {
		return taskSchedule.removeTask(taskId);
	}

	@Override
	public List<Task> listWaitTask(int off, int len) {
		return taskSchedule.listWaitTask(off, len);
	}

	@Override
	public Task getCurrentTask() {
		return taskSchedule.getCurrentTask();
	}

	@Override
	public List<Task> listFinishTask(int off, int len) {
		return taskSchedule.listFinishTask(off, len);
	}
}
