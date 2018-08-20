package top.cellargalaxy.mycloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.schedule.DownloadFileTask;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.service.FileInfoService;
import top.cellargalaxy.mycloud.service.FileService;
import top.cellargalaxy.mycloud.service.TaskService;
import top.cellargalaxy.mycloud.service.schedule.DownloadFileTaskExecute;
import top.cellargalaxy.mycloud.service.schedule.RemoveFileTaskExecute;
import top.cellargalaxy.mycloud.service.schedule.TaskSynchronizeSchedule;
import top.cellargalaxy.mycloud.service.schedule.UploadFileTaskExecute;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;

import com.sun.management.OperatingSystemMXBean;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Service
public class FileServiceImpl implements FileService {
	@Autowired
	private FileInfoService fileInfoService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private TaskSynchronizeSchedule taskSynchronizeSchedule;
	@Autowired
	private UploadFileTaskExecute uploadFileTaskExecute;
	@Autowired
	private DownloadFileTaskExecute downloadFileTaskExecute;
	@Autowired
	private RemoveFileTaskExecute removeFileTaskExecute;
	@Autowired
	private MycloudConfiguration mycloudConfiguration;

	@Override
	public Map getDriveInfo() {
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

	@Override
	public String uploadFile(OwnPo ownPo, File file, String contentType) throws IOException {
		return uploadFileTaskExecute.uploadFile(ownPo, file, contentType);
	}

	@Override
	public String downloadFile(FileInfoPo fileInfoPo, OutputStream outputStream) throws IOException {
		return downloadFileTaskExecute.downloadFile(fileInfoPo, outputStream);
	}

	@Override
	public String removeFile(FileInfoPo fileInfoPo) {
		return removeFileTaskExecute.removeFileInfo(fileInfoPo);
	}

	@Override
	public File createLocalFile(FileInfoPo fileInfoPo) {
		return new File(mycloudConfiguration.getMycloudDrivePath() + File.separator + fileInfoPo.getMd5());
	}

	@Override
	public String restoreAllFileToLocal(UserPo userPo) {
		List<FileInfoBo> fileInfoBos = fileInfoService.listAllFileInfo();
		for (FileInfoBo fileInfoBo : fileInfoBos) {
			taskService.addWaitTask(new DownloadFileTask(userPo, fileInfoBo, createLocalFile(fileInfoBo)));
		}
		return null;
	}

	@Override
	public String startRestoreAllFileToLocal(UserPo userPo) {
		Date date = new Date();
		restoreAllFileToLocal(userPo);
		taskSynchronizeSchedule.setUserPo(userPo);
		taskSynchronizeSchedule.setFlushTime(date);
		mycloudConfiguration.setRestoreFileToLocal(true);
		return null;
	}

	@Override
	public String stopRestoreAllFileToLocal() {
		mycloudConfiguration.setRestoreFileToLocal(false);
		taskSynchronizeSchedule.setUserPo(null);
		return null;
	}

	@Override
	public String deleteAllFileFromLocal() {
		File folder = new File(mycloudConfiguration.getMycloudDrivePath());
		File[] files = folder.listFiles();
		if (files != null) {
			for (File file : files) {
				if (!file.delete()) {
					return "删除失败:" + file;
				}
			}
		}
		if (!folder.delete()) {
			return "删除失败:" + folder;
		}
		return null;
	}

}
