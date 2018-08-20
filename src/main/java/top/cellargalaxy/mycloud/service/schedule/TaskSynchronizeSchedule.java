package top.cellargalaxy.mycloud.service.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.exception.GlobalException;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.TaskBo;
import top.cellargalaxy.mycloud.model.bo.schedule.RemoveFileTask;
import top.cellargalaxy.mycloud.model.bo.schedule.UploadFileTask;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.TaskQuery;
import top.cellargalaxy.mycloud.service.FileInfoService;
import top.cellargalaxy.mycloud.service.FileService;
import top.cellargalaxy.mycloud.service.TaskService;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author cellargalaxy
 * @time 2018/8/13
 */
@Component
public class TaskSynchronizeSchedule {
	private Logger logger = LoggerFactory.getLogger(TaskSynchronizeSchedule.class);
	private volatile UserPo userPo;
	private volatile Date flushTime;
	@Autowired
	private MycloudConfiguration mycloudConfiguration;
	@Autowired
	private FileInfoService fileInfoService;
	@Autowired
	private FileService fileService;
	@Autowired
	private TaskService taskService;

	@Scheduled(fixedDelay = 1000 * 60 * 5)
	public void schedule() {
		if (mycloudConfiguration.isRestoreFileToLocal()) {
			final Date date = new Date();
			final List<TaskBo> tasks = taskService.listTask(new TaskQuery() {{
				setFinishTime(flushTime);
			}});
			flushTime = date;
			for (TaskBo task : tasks) {
				logger.info("schedule:{}", task);
				if (task.getTaskSort().equals(UploadFileTask.TASK_SORT)) {
					uploadFileTask(task);
				} else if (task.getTaskSort().equals(RemoveFileTask.TASK_SORT)) {
					removeFileTask(task);
				}
			}
		}
	}

	private void uploadFileTask(TaskBo task) {
		Map<String, Object> map = UploadFileTask.deserialization(task.getTaskDetail());
		OwnPo ownPo = (OwnPo) (map.get(UploadFileTask.OWN_PO_KEY));
		FileInfoBo fileInfoBo = fileInfoService.getFileInfo(new FileInfoPo() {{
			setFileId(ownPo.getFileId());
		}});
		File file = fileService.createLocalFile(fileInfoBo);
		try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
			fileService.downloadFile(fileInfoBo, outputStream);
			logger.info("schedule:成功同步下载文件:{}", file);
		} catch (IOException e) {
			e.printStackTrace();
			GlobalException.add(e);
			logger.info("schedule:失败同步下载文件:{}", file);
		}
	}

	private void removeFileTask(TaskBo task) {
		FileInfoPo fileInfoPo = RemoveFileTask.deserialization(task.getTaskDetail());
		if (fileInfoPo != null) {
			FileInfoBo fileInfoBo = fileInfoService.getFileInfo(fileInfoPo);
			File file = fileService.createLocalFile(fileInfoBo);
			boolean b = file.delete();
			logger.info("schedule:删除文件:{}, result:{}", file, b);
		}
	}

	public UserPo getUserPo() {
		return userPo;
	}

	public void setUserPo(UserPo userPo) {
		this.userPo = userPo;
	}

	public Date getFlushTime() {
		return flushTime;
	}

	public void setFlushTime(Date flushTime) {
		this.flushTime = flushTime;
	}
}
