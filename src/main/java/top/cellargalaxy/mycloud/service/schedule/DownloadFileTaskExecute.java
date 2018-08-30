package top.cellargalaxy.mycloud.service.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.model.bo.BlockBo;
import top.cellargalaxy.mycloud.model.bo.FileBlockBo;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.schedule.DownloadFileTask;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.BlockQuery;
import top.cellargalaxy.mycloud.model.query.FileBlockQuery;
import top.cellargalaxy.mycloud.service.BlockService;
import top.cellargalaxy.mycloud.service.FileBlockService;
import top.cellargalaxy.mycloud.service.FileInfoService;
import top.cellargalaxy.mycloud.service.LocalFileService;
import top.cellargalaxy.mycloud.util.StreamUtil;

import java.io.*;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/30
 */
@Service
public class DownloadFileTaskExecute implements TaskExecute<DownloadFileTask> {
	public static final String TASK_SORT = DownloadFileTask.TASK_SORT;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private FileBlockService fileBlockService;
	@Autowired
	private FileInfoService fileInfoService;
	@Autowired
	private BlockService blockService;
	@Autowired
	private LocalFileService localFileService;

	@Override
	public String executeTask(DownloadFileTask downloadFileTask) throws Exception {
		logger.info("executeTask:{}", downloadFileTask);
		FileInfoPo fileInfoPo = downloadFileTask.getFileInfoPo();
		File file = downloadFileTask.getFile();

		return downloadFile(fileInfoPo, file);
	}

	public String downloadFile(FileInfoPo fileInfoPo, File file) throws IOException {
		logger.info("downloadFile:{}, {}", fileInfoPo, file);
		String string = getFileInfo(fileInfoPo);
		if (string != null) {
			return string;
		}

		if (file.getParentFile() != null && !file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (file.exists() && fileInfoPo.getMd5().equals(StreamUtil.md5Hex(file))) {
			return null;
		}

		try (OutputStream outputStream = StreamUtil.getOutputStream(file)) {
			return doDownloadFile(fileInfoPo, outputStream);
		}
	}

	public String downloadFile(FileInfoPo fileInfoPo, OutputStream outputStream) throws IOException {
		logger.info("downloadFile:{}", fileInfoPo);
		String string = getFileInfo(fileInfoPo);
		if (string != null) {
			return string;
		}
		return doDownloadFile(fileInfoPo, outputStream);
	}

	private String getFileInfo(FileInfoPo fileInfoPo) {
		if (fileInfoPo.getFileId() > 0
				&& fileInfoPo.getMd5() != null
				&& fileInfoPo.getContentType() != null
				&& fileInfoPo.getFileLength() > 0) {
			return null;
		}
		FileInfoBo fileInfoBo = fileInfoService.getFileInfo(fileInfoPo);
		if (fileInfoBo == null) {
			return "查找不到文件";
		}
		fileInfoPo.setFileId(fileInfoBo.getFileId());
		fileInfoPo.setMd5(fileInfoBo.getMd5());
		fileInfoPo.setContentType(fileInfoBo.getContentType());
		fileInfoPo.setFileLength(fileInfoBo.getFileLength());
		return null;
	}

	private String doDownloadFile(FileInfoPo fileInfoPo, OutputStream outputStream) throws IOException {
		File localFile = localFileService.getLocalFile(fileInfoPo);
		if (localFile != null && localFile.exists()) {
			try (InputStream inputStream = StreamUtil.getInputStream(localFile)) {
				StreamUtil.stream(inputStream, outputStream);
			}
			return null;
		}

		localFile = localFileService.createLocalFile(fileInfoPo);
		FileBlockQuery fileBlockQuery = new FileBlockQuery();
		fileBlockQuery.setFileId(fileInfoPo.getFileId());
		List<FileBlockBo> fileBlockBos = fileBlockService.listFileBlock(fileBlockQuery);
		if (fileBlockBos == null || fileBlockBos.size() == 0) {
			return "查找不到文件";
		}

		try (OutputStream localFileOutputStream = StreamUtil.getOutputStream(localFile)) {
			for (FileBlockBo fileBlockBo : fileBlockBos) {
				BlockQuery blockQuery = new BlockQuery();
				blockQuery.setBlockId(fileBlockBo.getBlockId());
				BlockBo blockBo = blockService.getBlock(blockQuery);
				outputStream.write(blockBo.getBlock());
				localFileOutputStream.write(blockBo.getBlock());
			}
		} catch (Exception e) {
			localFile.delete();
			throw e;
		}
		localFileService.addLocalFile(localFile);
		return null;
	}
}
