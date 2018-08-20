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

	public TaskExecute getTaskExecute(String taskSort) {
		if (taskSort == null) {
			return null;
		} else if (taskSort.equals(UploadFileTaskExecute.TASK_SORT)) {
			return uploadFileTaskExecute;
		} else if (taskSort.equals(DownloadFileTaskExecute.TASK_SORT)) {
			return downloadFileTaskExecute;
		} else if (taskSort.equals(RemoveFileTaskExecute.TASK_SORT)) {
			return removeFileTaskExecute;
		} else {
			return null;
		}
	}
}
