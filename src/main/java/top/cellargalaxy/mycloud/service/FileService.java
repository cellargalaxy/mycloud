package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.bo.schedule.Task;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;

import java.io.File;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public interface FileService {
	void addUploadFileTask(OwnPo ownPo, File file, String contentType);

	void addDownloadFileTask(UserPo userPo, FileInfoQuery fileInfoQuery, File file);

	Task removeTask(String taskId);

	List<Task> listWaitTask(int off, int len);

	Task getCurrentTask();

	List<Task> listFinishTask(int off, int len);

}
