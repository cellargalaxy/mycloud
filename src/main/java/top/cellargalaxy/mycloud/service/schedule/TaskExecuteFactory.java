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

	public TaskExecute getTaskExecute(String taskSort) {
		if (taskSort == null) {
			return null;
		}
		if (taskSort.equals(UploadFileTaskExecute.TASK_SORT)) {
			return uploadFileTaskExecute;
		}
		if (taskSort.equals(DownloadFileTaskExecute.TASK_SORT)) {
			return downloadFileTaskExecute;
		}
		return null;
	}
}
