package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.bo.schedule.Task;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public interface FileService {
	void addUploadFileTask(UserPo userPo, OwnPo ownPo, File file, String contentType);

	String uploadFile(UserPo userPo, OwnPo ownPo, File file, String contentType) throws IOException;

	void addDownloadFileTask(UserPo userPo, FileInfoQuery fileInfoQuery, File file);

	String downloadFile(FileInfoQuery fileInfoQuery, OutputStream outputStream) throws IOException;

	Task removeTask(String taskId);

	List<Task> listWaitTask(int off, int len);

	Task getCurrentTask();

	List<Task> listFinishTask(int off, int len);
}
