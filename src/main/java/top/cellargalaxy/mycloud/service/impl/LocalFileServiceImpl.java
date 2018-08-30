package top.cellargalaxy.mycloud.service.impl;

import com.sun.management.OperatingSystemMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.service.LocalFileService;
import top.cellargalaxy.mycloud.util.StreamUtil;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cellargalaxy
 * @time 2018/8/29
 */
@Service
public class LocalFileServiceImpl implements LocalFileService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final File driveFolder;
	private final long localFileMaxSpace;
	private final ConcurrentHashMap<String, Integer> fileWeightMap = new ConcurrentHashMap<>();

	@Autowired
	public LocalFileServiceImpl(MycloudConfiguration mycloudConfiguration) {
		driveFolder = new File(mycloudConfiguration.getMycloudDrivePath());
		localFileMaxSpace = mycloudConfiguration.getLocalFileMaxSpace();
		logger.info("LocalFileServiceImpl, driveFolder:{}, localFileMaxSpace:{}", driveFolder, localFileMaxSpace);
	}

	@Override
	public Map getDriveInfo() {
		logger.info("getDriveInfo");
		OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		return new HashMap() {{
			put("freeSpace", driveFolder.getFreeSpace());
			put("useSpace", StreamUtil.getFolderLength(driveFolder));
			put("totalSpace", driveFolder.getTotalSpace());
			put("usableSpace", driveFolder.getUsableSpace());
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
	public File createLocalFile(FileInfoPo fileInfoPo) {
		logger.info("createLocalFile:{}", fileInfoPo);
		return new File(driveFolder.getAbsolutePath() + File.separator + fileInfoPo.getMd5());
	}

	@Override
	public String addLocalFile(File file) {
		logger.info("addLocalFile:{}", file);
		if (fileWeightMap.get(file.getAbsolutePath()) != null) {
			return null;
		}
		if (file.length() > localFileMaxSpace) {
			file.delete();
			return "文件过大，不保存";
		}
		//当fileWeightMap为空，如果本地有漏网之鱼的文件，把他添加到fileWeightMap里
		if (fileWeightMap.size() > 0) {
			File[] files = driveFolder.listFiles();
			if (files != null) {
				for (File localFile : files) {
					fileWeightMap.put(localFile.getAbsolutePath(), 0);
				}
			}
		}
		while (StreamUtil.getFolderLength(driveFolder) > localFileMaxSpace) {
			File removeFile = pollMinWeightFile();
			if (removeFile == null) {
				removeAllLocalFile();
				break;
			}
			removeFile.delete();
		}
		if (StreamUtil.getFolderLength(driveFolder) > localFileMaxSpace) {
			file.delete();
			return "没有能继续删除以腾出空间的文件";
		}
		fileWeightMap.put(file.getAbsolutePath(), 0);
		return null;
	}

	@Override
	public String removeLocalFile(FileInfoPo fileInfoPo) {
		File file = createLocalFile(fileInfoPo);
		fileWeightMap.remove(file.getAbsolutePath());
		file.delete();
		return null;
	}

	private File pollMinWeightFile() {
		File removeFile = null;
		int minWeight = Integer.MAX_VALUE;
		long fileLength = -1;
		for (Map.Entry<String, Integer> entry : fileWeightMap.entrySet()) {
			File file = new File(entry.getKey());
			if (entry.getValue() < minWeight && file.length() > fileLength) {
				removeFile = file;
				minWeight = entry.getValue();
				fileLength = removeFile.length();
			}
		}
		return removeFile;
	}

	@Override
	public String removeAllLocalFile() {
		logger.info("removeAllLocalFile");
		fileWeightMap.clear();
		if (!StreamUtil.deleteFileInFolder(driveFolder)) {
			return "删除失败";
		}
		return null;
	}

	@Override
	public File getLocalFile(FileInfoPo fileInfoPo) {
		logger.info("getLocalFile:{}", fileInfoPo);
		File file = createLocalFile(fileInfoPo);
		if (file.exists()) {
			Integer integer = fileWeightMap.get(file.getAbsolutePath());
			if (integer == null) {
				integer = 0;
			}
			fileWeightMap.put(file.getAbsolutePath(), integer + 1);
			return file;
		} else {
			return null;
		}
	}
}
