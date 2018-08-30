package top.cellargalaxy.mycloud.service.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.TaskBo;
import top.cellargalaxy.mycloud.model.bo.schedule.RemoveFileTask;
import top.cellargalaxy.mycloud.model.bo.schedule.SynchronizeTask;
import top.cellargalaxy.mycloud.model.bo.schedule.UploadFileTask;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.TaskPo;
import top.cellargalaxy.mycloud.model.query.TaskQuery;
import top.cellargalaxy.mycloud.service.*;

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
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private volatile Date flushTime;
	@Autowired
	private FileInfoService fileInfoService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private LocalFileService localFileService;

	@Override
	public String executeTask(SynchronizeTask synchronizeTask) throws Exception {
		try {
			final Date date = new Date();
			TaskQuery taskQuery = new TaskQuery();
			taskQuery.setStatus(TaskPo.SUCCESS_STATUS);
			taskQuery.setFinishTime(flushTime);
			taskQuery.setTaskSort(RemoveFileTask.TASK_SORT);
			List<TaskBo> tasks = taskService.listTask(taskQuery);
			flushTime = date;
			for (TaskBo task : tasks) {
				logger.info("executeTask:{}", task);
				synchronizeRemoveFileTask(task);
			}
		} finally {
			taskService.addWaitTask(new SynchronizeTask());
		}
		return null;
	}

	public String synchronizeUploadFileTask(TaskBo task) {
		logger.info("synchronizeUploadFileTask:{}", task);
		Map<String, Object> map = UploadFileTask.deserialization(task.getTaskDetail());
		OwnPo ownPo = (OwnPo) (map.get(UploadFileTask.OWN_PO_KEY));
		if (ownPo == null) {
			return "任务解析失败";
		}
		FileInfoPo fileInfoPo = new FileInfoPo();
		fileInfoPo.setFileId(ownPo.getFileId());
		FileInfoBo fileInfoBo = fileInfoService.getFileInfo(fileInfoPo);
		//留空
		throw new RuntimeException("synchronizeUploadFileTask未实现");
	}

	public String synchronizeRemoveFileTask(TaskBo task) {
		logger.info("synchronizeRemoveFileTask:{}", task);
		FileInfoPo fileInfoPo = RemoveFileTask.deserialization(task.getTaskDetail());
		if (fileInfoPo == null) {
			return "任务解析失败";
		}
		FileInfoBo fileInfoBo = fileInfoService.getFileInfo(fileInfoPo);
		return localFileService.removeLocalFile(fileInfoBo);
	}

	public Date getFlushTime() {
		return flushTime;
	}

	public void setFlushTime(Date flushTime) {
		this.flushTime = flushTime;
	}
}
