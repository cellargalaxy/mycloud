package top.cellargalaxy.mycloud.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.schedule.DownloadFileTask;
import top.cellargalaxy.mycloud.model.bo.schedule.RemoveFileTask;
import top.cellargalaxy.mycloud.model.bo.schedule.UploadFileTask;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.service.FileInfoService;
import top.cellargalaxy.mycloud.service.FileService;
import top.cellargalaxy.mycloud.service.TaskService;
import top.cellargalaxy.mycloud.service.schedule.DownloadFileTaskExecute;
import top.cellargalaxy.mycloud.service.schedule.RemoveFileTaskExecute;
import top.cellargalaxy.mycloud.service.schedule.SynchronizeTaskExecute;
import top.cellargalaxy.mycloud.service.schedule.UploadFileTaskExecute;

import java.io.*;
import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;
import top.cellargalaxy.mycloud.util.StreamUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Service
public class FileServiceImpl implements FileService {
	private Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
	@Autowired
	private FileInfoService fileInfoService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private UploadFileTaskExecute uploadFileTaskExecute;
	@Autowired
	private DownloadFileTaskExecute downloadFileTaskExecute;
	@Autowired
	private RemoveFileTaskExecute removeFileTaskExecute;
	@Autowired
	private SynchronizeTaskExecute synchronizeTaskExecute;
	@Autowired
	private MycloudConfiguration mycloudConfiguration;

	@Override
	public Map getDriveInfo() {
		logger.info("getDriveInfo");
		File file = new File(mycloudConfiguration.getMycloudDrivePath());
		OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		return new HashMap() {{
			put("freeSpace", file.getFreeSpace());
			put("totalSpace", file.getTotalSpace());
			put("usableSpace", file.getUsableSpace());
			put("systemCpuLoad", operatingSystemMXBean.getSystemCpuLoad());
			put("processCpuTime", operatingSystemMXBean.getProcessCpuTime());
			put("processCpuLoad", operatingSystemMXBean.getProcessCpuLoad());
			put("freeSwapSpaceSize", operatingSystemMXBean.getFreeSwapSpaceSize());
			put("totalSwapSpaceSize", operatingSystemMXBean.getTotalSwapSpaceSize());
			put("freePhysicalMemorySize", operatingSystemMXBean.getFreePhysicalMemorySize());
			put("totalPhysicalMemorySize", operatingSystemMXBean.getTotalPhysicalMemorySize());
			put("committedVirtualMemorySize", operatingSystemMXBean.getCommittedVirtualMemorySize());
		}};
	}

//	@Override
//	public String uploadFile(OwnPo ownPo, File file, String contentType) throws IOException {
//		logger.info("uploadFile:{}, {}, {}", ownPo, file, contentType);
//		return uploadFileTaskExecute.uploadFile(ownPo, file, contentType);
//	}

	@Override
	public String executeUploadFileTask(UserPo userPo, OwnPo ownPo, File file, String contentType) {
		logger.info("executeUploadFileTask:{}, {}, {}, {}", userPo, ownPo, file, contentType);
		return taskService.executeTask(new UploadFileTask(userPo, ownPo, file, contentType));
	}

	@Override
	public void addUploadFileTask(UserPo userPo, OwnPo ownPo, File file, String contentType) {
		logger.info("addUploadFileTask:{}, {}, {}, {}", userPo, ownPo, file, contentType);
		taskService.addWaitTask(new UploadFileTask(userPo, ownPo, file, contentType));
	}

	@Override
	public String downloadFile(FileInfoPo fileInfoPo, File file) throws IOException {
		logger.info("downloadFile:{}, {}", fileInfoPo, file);
		try (OutputStream outputStream = StreamUtil.getOutputStream(file)) {
			return downloadFile(fileInfoPo, outputStream);
		}
	}

	@Override
	public String downloadFile(FileInfoPo fileInfoPo, OutputStream outputStream) throws IOException {
		logger.info("downloadFile:{}", fileInfoPo);
		return downloadFileTaskExecute.downloadFile(fileInfoPo, outputStream);
	}

	@Override
	public String executeDownloadFileTask(UserPo userPo, FileInfoPo fileInfoPo, File file) {
		logger.info("executeDownloadFileTask:{}, {}, {}", userPo, fileInfoPo, file);
		return taskService.executeTask(new DownloadFileTask(userPo, fileInfoPo, file));
	}

	@Override
	public void addDownloadFileTask(UserPo userPo, FileInfoPo fileInfoPo, File file) {
		logger.info("addDownloadFileTask:{}, {}, {}", userPo, fileInfoPo, file);
		taskService.addWaitTask(new DownloadFileTask(userPo, fileInfoPo, file));
	}

//	@Override
//	public String removeFile(FileInfoPo fileInfoPo) {
//		logger.info("removeFile:{}", fileInfoPo);
//		return removeFileTaskExecute.removeFileInfo(fileInfoPo);
//	}

	@Override
	public String executeRemoveFileTask(UserPo userPo, FileInfoPo fileInfoPo) {
		logger.info("executeRemoveFileTask:{}, {}", userPo, fileInfoPo);
		return taskService.executeTask(new RemoveFileTask(userPo, fileInfoPo));
	}

	@Override
	public void addRemoveFileTask(UserPo userPo, FileInfoPo fileInfoPo) {
		logger.info("addRemoveFileTask:{}, {}", userPo, fileInfoPo);
		taskService.addWaitTask(new RemoveFileTask(userPo, fileInfoPo));
	}

	@Override
	public File createLocalFile(FileInfoPo fileInfoPo) {
		logger.info("createLocalFile:{}", fileInfoPo);
		return new File(mycloudConfiguration.getMycloudDrivePath() + File.separator + fileInfoPo.getMd5());
	}

	@Override
	public String restoreAllFileToLocal(UserPo userPo) {
		logger.info("restoreAllFileToLocal:{}", userPo);
		List<FileInfoBo> fileInfoBos = fileInfoService.listAllFileInfo();
		for (FileInfoBo fileInfoBo : fileInfoBos) {
			taskService.addWaitTask(new DownloadFileTask(userPo, fileInfoBo, createLocalFile(fileInfoBo)));
		}
		return null;
	}

	@Override
	public String startRestoreAllFileToLocal(UserPo userPo) {
		logger.info("startRestoreAllFileToLocal:{}", userPo);
		String string = synchronizeTaskExecute.startSynchronizeTaskExecute(userPo);
		restoreAllFileToLocal(userPo);
		return string;
	}

	@Override
	public String stopRestoreAllFileToLocal() {
		logger.info("stopRestoreAllFileToLocal");
		return synchronizeTaskExecute.stopSynchronizeTaskExecute();
	}

	@Override
	public String deleteAllFileFromLocal() {
		logger.info("deleteAllFileFromLocal");
		File folder = new File(mycloudConfiguration.getMycloudDrivePath());
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				file.delete();
			}
		}
		folder.delete();
		if (folder.exists()) {
			return "删除失败";
		}
		return null;
	}

}
