package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.bo.schedule.DownloadFileTask;
import top.cellargalaxy.mycloud.model.bo.schedule.UploadFileTask;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.model.vo.FileInfoOwnVo;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public interface FileService {
	FileInfoOwnVo getFileInfoOwn(FileInfoQuery fileInfoQuery);

	List<FileInfoOwnVo> listFileInfoOwn(FileInfoQuery fileInfoQuery);

	////

	void addUploadFileTask(File file, String contentType, OwnPo ownPo);

	UploadFileTask removeUploadFileTask(String taskId);

	BlockingQueue<UploadFileTask> getWaitUploadFileTasks();

	UploadFileTask getCurrentUploadFileTask();

	List<UploadFileTask> getFinishUploadFileTasks();

	////

	void addDownloadFileTask(FileInfoQuery fileInfoQuery, File file);

	void downloadFile(FileInfoQuery fileInfoQuery, OutputStream outputStream) throws IOException;

	DownloadFileTask removeDownloadFileTask(String taskId);

	BlockingQueue<DownloadFileTask> getWaitDownloadFileTasks();

	DownloadFileTask getCurrentDownloadFileTask();

	List<DownloadFileTask> getFinishDownloadFileTasks();

	////

	List<String> listContentType();
}
