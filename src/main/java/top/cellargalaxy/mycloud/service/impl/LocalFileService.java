package top.cellargalaxy.mycloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.util.StreamUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cellargalaxy
 * @time 2018/8/29
 */
public class LocalFileService {
	private final File md5Folder;
	private final File uuidFolder;
	private final double localFileMaxSpaceRate;
	private final ConcurrentHashMap<String, Integer> fileWeightMap = new ConcurrentHashMap<>();

	@Autowired
	public LocalFileService(MycloudConfiguration mycloudConfiguration) {
		md5Folder = new File(mycloudConfiguration.getMycloudPath() + File.separator + "md5");
		uuidFolder = new File(mycloudConfiguration.getMycloudPath() + File.separator + "uuid");
		localFileMaxSpaceRate = mycloudConfiguration.getLocalFileMaxSpaceRate();
	}


	public String addFile(InputStream inputStream, FileInfoPo fileInfoPo) throws IOException {
		if (isFull()) {
			return "磁盘使用率已满";
		}
		File localFile = createLocalFile(fileInfoPo);
		try (OutputStream outputStream = StreamUtil.getOutputStream(localFile)) {
			StreamUtil.stream(inputStream, outputStream);
		}
		return null;
	}


	public String addFile(InputStream inputStream, OwnPo ownPo) throws IOException {
		if (isFull()) {
			return "磁盘使用率已满";
		}
		File localFile = createLocalFile(ownPo);
		try (OutputStream outputStream = StreamUtil.getOutputStream(localFile)) {
			StreamUtil.stream(inputStream, outputStream);
		}
		return null;
	}


	public String deleteFile(FileInfoPo fileInfoPo) {
		File localFile = createLocalFile(fileInfoPo);
		localFile.delete();
		return null;
	}


	public String deleteFile(OwnPo ownPo) {
		File localFile = createLocalFile(ownPo);
		localFile.delete();
		return null;
	}


	public String getFile(FileInfoPo fileInfoPo, OutputStream outputStream) throws IOException {
		File localFile = createLocalFile(fileInfoPo);
		return getFile(localFile, outputStream);
	}


	public String getFile(OwnPo ownPo, OutputStream outputStream) throws IOException {
		File localFile = createLocalFile(ownPo);
		return getFile(localFile, outputStream);
	}

	private String getFile(File localFile, OutputStream outputStream) throws IOException {
		if (!localFile.exists()) {
			return "文件不存在";
		}
		Integer integer = fileWeightMap.get(localFile.getAbsolutePath());
		if (integer == null) {
			integer = 0;
		}
		fileWeightMap.put(localFile.getAbsolutePath(), integer + 1);
		try (InputStream inputStream = StreamUtil.getInputStream(localFile)) {
			StreamUtil.stream(inputStream, outputStream);
		}
		return null;
	}

	public String cacheFile(InputStream inputStream, FileInfoPo fileInfoPo) throws IOException {
		while (isFull()) {
			if (fileWeightMap.size() == 0) {
				return "磁盘空间已满";
			}
			deleteColdFile();
		}
		File localFile = createLocalFile(fileInfoPo);
		try (OutputStream outputStream = StreamUtil.getOutputStream(localFile)) {
			StreamUtil.stream(inputStream, outputStream);
		}
		return null;
	}

	public String cacheFile(InputStream inputStream, OwnPo ownPo) throws IOException {
		while (isFull()) {
			if (fileWeightMap.size() == 0) {
				return "磁盘空间已满";
			}
			deleteColdFile();
		}
		File localFile = createLocalFile(ownPo);
		try (OutputStream outputStream = StreamUtil.getOutputStream(localFile)) {
			StreamUtil.stream(inputStream, outputStream);
		}
		return null;
	}

	private void deleteColdFile() {
		Integer coldCount = Integer.MAX_VALUE;
		long coldFilLength = Long.MIN_VALUE;
		String coldFile = null;
		for (Map.Entry<String, Integer> entry : fileWeightMap.entrySet()) {
			if (entry.getValue() < coldCount) {
				coldCount = entry.getValue();
				coldFile = entry.getKey();
			} else if (entry.getValue() == coldCount && new File(entry.getKey()).length() > coldFilLength) {
				coldCount = entry.getValue();
				coldFile = entry.getKey();
			}
		}
		if (coldFile != null) {
			new File(coldFile).delete();
		}
	}

	private File createLocalFile(FileInfoPo fileInfoPo) {
		return new File(md5Folder.getAbsolutePath() + File.separator + fileInfoPo.getMd5());
	}

	private File createLocalFile(OwnPo ownPo) {
		return new File(uuidFolder.getAbsolutePath() + File.separator + ownPo.getOwnUuid());
	}

	private boolean isFull() {
		return usedRate() > localFileMaxSpaceRate;
	}

	private double usedRate() {
		return (md5Folder.getTotalSpace() - md5Folder.getFreeSpace()) * 1.0 / md5Folder.getTotalSpace();
	}


}
