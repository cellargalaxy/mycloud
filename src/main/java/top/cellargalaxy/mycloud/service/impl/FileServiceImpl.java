package top.cellargalaxy.mycloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.service.FileInfoService;
import top.cellargalaxy.mycloud.service.FileService;
import top.cellargalaxy.mycloud.service.OwnService;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * @author cellargalaxy
 * @time 2018/10/25
 */
@Service
public class FileServiceImpl implements FileService {
	private final LocalFileService localFileService;
	@Autowired
	private FileInfoService fileInfoService;
	@Autowired
	private OwnService ownService;

	@Autowired
	public FileServiceImpl(MycloudConfiguration mycloudConfiguration) {
		this.localFileService = new LocalFileService(mycloudConfiguration);
	}

	@Override
	public String addFile(InputStream inputStream, FileInfoPo fileInfoPo) throws IOException {
		String string = localFileService.addFile(inputStream, fileInfoPo);
		if (string == null) {
			string = fileInfoService.addFileInfo(fileInfoPo);
		}
		if (string != null) {
			removeFile(fileInfoPo);
		}
		return string;
	}

	@Override
	public String addFile(InputStream inputStream, OwnPo ownPo, UserPo userPo) throws IOException {
		ownPo.setOwnUuid(UUID.randomUUID().toString());
		ownPo.setUserId(userPo.getUserId());
		String string = localFileService.addFile(inputStream, ownPo);
		if (string == null) {
			string = ownService.addOwn(ownPo);
		}
		if (string != null) {
			removeFile(ownPo);
		}
		return string;
	}

	@Override
	public String removeFile(FileInfoPo fileInfoPo) throws IOException {
		fileInfoService.removeFileInfo(fileInfoPo);
		return localFileService.deleteFile(fileInfoPo);
	}

	@Override
	public String removeFile(OwnPo ownPo) throws IOException {
		ownService.removeOwn(ownPo);
		return localFileService.deleteFile(ownPo);
	}

	@Override
	public FileInfoPo getFileInfoPoByMd5OrUuid(String md5OrUuid) {
		if (md5OrUuid.indexOf('-') > 0) {
			OwnPo ownPo = new OwnPo();
			ownPo.setOwnUuid(md5OrUuid);
			OwnBo ownBo = ownService.getOwn(ownPo);
			FileInfoPo fileInfoPo = new FileInfoPo();
			fileInfoPo.setFileId(ownBo.getFileId());
			fileInfoPo.setMd5(ownBo.getMd5());
			fileInfoPo.setContentType(ownBo.getContentType());
			fileInfoPo.setFileLength(ownBo.getFileLength());
			fileInfoPo.setCreateTime(ownBo.getCreateTime());
			return fileInfoPo;
		} else {
			FileInfoPo fileInfoPo = new FileInfoPo();
			fileInfoPo.setMd5(md5OrUuid);
			return fileInfoService.getFileInfo(fileInfoPo);
		}
	}

	@Override
	public String getFileByMd5OrUuid(@NotNull String md5OrUuid, OutputStream outputStream) throws IOException {
		if (md5OrUuid.indexOf('-') > 0) {
			OwnPo ownPo = new OwnPo();
			ownPo.setOwnUuid(md5OrUuid);
			return getFile(ownPo, outputStream);
		} else {
			FileInfoPo fileInfoPo = new FileInfoPo();
			fileInfoPo.setMd5(md5OrUuid);
			return getFile(fileInfoPo, outputStream);
		}
	}

	@Override
	public String getFile(FileInfoPo fileInfoPo, OutputStream outputStream) throws IOException {
		return localFileService.getFile(fileInfoPo, outputStream);
	}

	@Override
	public String getFile(OwnPo ownPo, OutputStream outputStream) throws IOException {
		return localFileService.getFile(ownPo, outputStream);
	}
}
