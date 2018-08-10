package top.cellargalaxy.mycloud.service.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.model.bo.schedule.UploadFileTask;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.service.FileInfoService;
import top.cellargalaxy.mycloud.service.OwnService;
import top.cellargalaxy.mycloud.util.StreamUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author cellargalaxy
 * @time 2018/7/30
 */
@Service
public class UploadFileTaskExecute implements TaskExecute<UploadFileTask> {
	private Logger logger = LoggerFactory.getLogger(UploadFileTaskExecute.class);
	public static final String TASK_SORT = UploadFileTask.TASK_SORT;
	@Autowired
	private FileInfoService fileInfoService;
	@Autowired
	private OwnService ownService;

	@Override
	public String executeTask(UploadFileTask uploadFileTask) throws Exception {
		logger.info("executeTask:{}", uploadFileTask);
		File file = uploadFileTask.getFile();

		String contentType = uploadFileTask.getContentType();
		OwnPo ownPo = uploadFileTask.getOwnPo();

		String string = uploadFile(ownPo, file, contentType);
		if (string != null) {
			return string;
		}
		return null;
	}

	public String uploadFile(OwnPo ownPo, File file, String contentType) throws IOException {
		logger.info("uploadFile, OwnPo:{}, file:{}, contentType:{}", ownPo, file, contentType);
		String md5 = StreamUtil.md5Hex(file);
		long fileLength = file.length();

		FileInfoPo fileInfoPo = new FileInfoPo();
		fileInfoPo.setContentType(contentType);
		fileInfoPo.setMd5(md5);
		fileInfoPo.setFileLength(fileLength);

		String string = fileInfoService.addFileInfo(fileInfoPo, file);
		if (string != null) {
			return string;
		}

		ownPo.setFileId(fileInfoPo.getFileId());
		string = ownService.addOwn(ownPo);
		if (string != null) {
			return string;
		}
		return null;
	}
}
