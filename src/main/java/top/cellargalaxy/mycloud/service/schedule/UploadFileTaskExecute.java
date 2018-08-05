package top.cellargalaxy.mycloud.service.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.model.bo.schedule.Task;
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
	public static final String TASK_SORT = UploadFileTask.TASK_SORT;
	@Autowired
	private FileInfoService fileInfoService;
	@Autowired
	private OwnService ownService;

	@Override
	public void executeTask(UploadFileTask uploadFileTask) throws Exception {
		File file = uploadFileTask.getFile();

		String contentType = uploadFileTask.getContentType();
		OwnPo ownPo = uploadFileTask.getOwnPo();

		String string = uploadFile(ownPo, file, contentType);
		if (string != null) {
			uploadFileTask.setStatus(Task.FAIL_STATUS);
			uploadFileTask.setMassage(string);
		} else {
			uploadFileTask.setStatus(Task.SUCCESS_STATUS);
		}
	}

	public String uploadFile(OwnPo ownPo, File file, String contentType) throws IOException {
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
