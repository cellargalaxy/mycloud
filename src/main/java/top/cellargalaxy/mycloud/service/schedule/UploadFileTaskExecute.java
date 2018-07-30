package top.cellargalaxy.mycloud.service.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.dao.BlockDao;
import top.cellargalaxy.mycloud.dao.FileBlockDao;
import top.cellargalaxy.mycloud.dao.FileInfoDao;
import top.cellargalaxy.mycloud.dao.OwnDao;
import top.cellargalaxy.mycloud.model.bo.schedule.UploadFileTask;
import top.cellargalaxy.mycloud.model.po.BlockPo;
import top.cellargalaxy.mycloud.model.po.FileBlockPo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.util.FileBlocks;
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
	private FileInfoDao fileInfoDao;
	@Autowired
	private MycloudConfiguration mycloudConfiguration;
	@Autowired
	private BlockDao blockDao;
	@Autowired
	private FileBlockDao fileBlockDao;
	@Autowired
	private OwnDao ownDao;

	@Override
	public void executeTask(UploadFileTask uploadFileTask) throws Exception {
		File file = uploadFileTask.getFile();
		String contentType = uploadFileTask.getContentType();
		String md5 = StreamUtil.md5Hex(file);
		long fileLength = file.length();
		OwnPo ownPo = uploadFileTask.getOwnPo();

		//写数据块
		int[] blockIds;
		try (FileBlocks fileBlocks = new FileBlocks(file, mycloudConfiguration.getBlobLength())) {
			blockIds = new int[fileBlocks.getBlockLengths().length];
			byte[] block;
			while ((block = fileBlocks.next()) != null) {
				BlockPo blockPo = new BlockPo();
				blockPo.setBlock(block);
				blockDao.insert(blockPo);
				blockIds[fileBlocks.getBlockIndex() - 1] = blockPo.getBlockId();
			}
		}

		//添加文件信息
		FileInfoPo fileInfoPo = new FileInfoPo();
		fileInfoPo.setMd5(md5);
		fileInfoPo.setFileLength(fileLength);
		fileInfoPo.setContentType(contentType);
		fileInfoDao.insert(fileInfoPo);

		//添加文件信息与数据块对应数据
		for (int blockId : blockIds) {
			FileBlockPo fileBlockPo = new FileBlockPo();
			fileBlockPo.setFileId(fileInfoPo.getFileId());
			fileBlockPo.setBlockId(blockId);
			fileBlockDao.insert(fileBlockPo);
		}

		//添加所属
		ownPo.setFileId(fileInfoPo.getFileId());
		ownDao.insert(ownPo);

		uploadFileTask.setStatus(1);
	}
}
