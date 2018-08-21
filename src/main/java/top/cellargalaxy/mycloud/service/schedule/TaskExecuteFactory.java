package top.cellargalaxy.mycloud.service.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author cellargalaxy
 * @time 2018/7/30
 */
@Component
public class TaskExecuteFactory {
	@Autowired
	private UploadFileTaskExecute uploadFileTaskExecute;
	@Autowired
	private DownloadFileTaskExecute downloadFileTaskExecute;
	@Autowired
	private RemoveFileTaskExecute removeFileTaskExecute;
	@Autowired
	private SynchronizeTaskExecute synchronizeTaskExecute;

	public TaskExecute getTaskExecute(String taskSort) {
		if (taskSort == null) {
			return null;
		} else if (UploadFileTaskExecute.TASK_SORT.equals(taskSort)) {
			return uploadFileTaskExecute;
		} else if (DownloadFileTaskExecute.TASK_SORT.equals(taskSort)) {
			return downloadFileTaskExecute;
		} else if (RemoveFileTaskExecute.TASK_SORT.equals(taskSort)) {
			return removeFileTaskExecute;
		} else if (SynchronizeTaskExecute.TASK_SORT.equals(taskSort)) {
			return synchronizeTaskExecute;
		} else {
			return null;
		}
	}
}
