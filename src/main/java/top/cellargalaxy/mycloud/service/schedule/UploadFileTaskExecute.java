package top.cellargalaxy.mycloud.service.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.dao.OwnDao;
import top.cellargalaxy.mycloud.model.bo.schedule.Task;
import top.cellargalaxy.mycloud.model.bo.schedule.UploadFileTask;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.service.FileInfoService;
import top.cellargalaxy.mycloud.util.StreamUtil;

import java.io.File;

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
	private OwnDao ownDao;

	@Override
	public void executeTask(UploadFileTask uploadFileTask) throws Exception {
		File file = uploadFileTask.getFile();
		String contentType = uploadFileTask.getContentType();
		String md5 = StreamUtil.md5Hex(file);
		long fileLength = file.length();

		FileInfoPo fileInfoPo = new FileInfoPo();
		fileInfoPo.setContentType(contentType);
		fileInfoPo.setMd5(md5);
		fileInfoPo.setFileLength(fileLength);

		fileInfoService.addFileInfo(fileInfoPo, file);

		//添加所属
		OwnPo ownPo = uploadFileTask.getOwnPo();
		ownPo.setFileId(fileInfoPo.getFileId());
		ownDao.insert(ownPo);

		uploadFileTask.setStatus(Task.SUCCESS_STATUS);
	}
}
