package top.cellargalaxy.mycloud.service.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.exception.GlobalException;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.TaskBo;
import top.cellargalaxy.mycloud.model.bo.schedule.RemoveFileTask;
import top.cellargalaxy.mycloud.model.bo.schedule.SynchronizeTask;
import top.cellargalaxy.mycloud.model.bo.schedule.UploadFileTask;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.TaskPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.TaskQuery;
import top.cellargalaxy.mycloud.service.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author cellargalaxy
 * @time 2018/8/21
 */
@Service
public class SynchronizeTaskExecute implements TaskExecute<SynchronizeTask> {
	public static final String TASK_SORT = SynchronizeTask.TASK_SORT;
	private Logger logger = LoggerFactory.getLogger(SynchronizeTaskExecute.class);
	private UserPo userPo;
	private volatile Date flushTime;
	@Autowired
	private MycloudConfiguration mycloudConfiguration;
	@Autowired
	private FileInfoService fileInfoService;
	@Autowired
	private FileService fileService;
	@Autowired
	private TaskService taskService;

	@Override
	public String executeTask(SynchronizeTask synchronizeTask) throws Exception {
		if (mycloudConfiguration.isRestoreFileToLocal()) {
			try {
				final Date date = new Date();
				final List<TaskBo> tasks = taskService.listTask(new TaskQuery() {{
					setStatus(TaskPo.SUCCESS_STATUS);
					setFinishTime(flushTime);
				}});
				flushTime = date;
				for (TaskBo task : tasks) {
					logger.info("executeTask:{}", task);
					if (UploadFileTask.TASK_SORT.equals(task.getTaskSort())) {
						synchronizeUploadFileTask(task);
					} else if (RemoveFileTask.TASK_SORT.equals(task.getTaskSort())) {
						synchronizeRemoveFileTask(task);
					}
				}
			} finally {
				taskService.addWaitTask(new SynchronizeTask(userPo));
			}
		}
		return null;
	}

	public String startSynchronizeTaskExecute(UserPo userPo) {
		logger.info("startSynchronizeTaskExecute:{}", userPo);
		Date date = new Date();
		setUserPo(userPo);
		if (getFlushTime() == null) {
			setFlushTime(date);
		}
		mycloudConfiguration.setRestoreFileToLocal(true);
		taskService.addWaitTask(new SynchronizeTask(userPo));
		return null;
	}

	public String stopSynchronizeTaskExecute() {
		logger.info("stopSynchronizeTaskExecute");
		mycloudConfiguration.setRestoreFileToLocal(false);
		userPo = null;
		return null;
	}

	public String synchronizeUploadFileTask(TaskBo task) {
		logger.info("synchronizeUploadFileTask:{}", task);
		Map<String, Object> map = UploadFileTask.deserialization(task.getTaskDetail());
		OwnPo ownPo = (OwnPo) (map.get(UploadFileTask.OWN_PO_KEY));
		FileInfoBo fileInfoBo = fileInfoService.getFileInfo(new FileInfoPo() {{
			setFileId(ownPo.getFileId());
		}});
		File file = fileService.createLocalFile(fileInfoBo);
		try {
			return fileService.downloadFile(fileInfoBo, file);
		} catch (IOException e) {
			e.printStackTrace();
			GlobalException.add(e);
			return "失败同步下载文件";
		}
	}

	public String synchronizeRemoveFileTask(TaskBo task) {
		logger.info("synchronizeRemoveFileTask:{}", task);
		FileInfoPo fileInfoPo = RemoveFileTask.deserialization(task.getTaskDetail());
		if (fileInfoPo == null) {
			return "任务解析失败";
		}
		FileInfoBo fileInfoBo = fileInfoService.getFileInfo(fileInfoPo);
		File file = fileService.createLocalFile(fileInfoBo);
		if (!file.delete()) {
			return "文件删除失败";
		}
		return null;
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
