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
import top.cellargalaxy.mycloud.service.fileDeal.FileDeal;
import top.cellargalaxy.mycloud.service.fileDeal.FileDealFactory;

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
	private final FileDeal fileDeal;
	@Autowired
	private FileInfoService fileInfoService;
	@Autowired
	private OwnService ownService;

	@Autowired
	public FileServiceImpl(MycloudConfiguration mycloudConfiguration) {
		this.fileDeal = FileDealFactory.getFileDeal(mycloudConfiguration);
	}

	@Override
	public String addFile(InputStream inputStream, OwnBo ownBo, UserPo userPo) throws IOException {
		ownBo.setOwnUuid(UUID.randomUUID().toString());
		ownBo.setUserId(userPo.getUserId());
		String string = fileDeal.addFile(inputStream, ownBo);
		return addFile(string, ownBo);
	}

	@Override
	public String addFile(String urlString, OwnBo ownBo, UserPo userPo) throws IOException {
		ownBo.setOwnUuid(UUID.randomUUID().toString());
		ownBo.setUserId(userPo.getUserId());
		String string = fileDeal.addFile(urlString, ownBo);
		return addFile(string, ownBo);
	}

	private String addFile(String string, OwnBo ownBo) throws IOException {
		if (string == null) {
			string = ownService.addOwn(ownBo);
		}
		if (string != null) {
			removeFile(ownBo);
			return string;
		}
		OwnBo newOwnBo = ownService.getOwn(ownBo);
		ownBo.setMd5(newOwnBo.getMd5());
		ownBo.setMd5Url(newOwnBo.getMd5Url());
		ownBo.setOwnUrl(newOwnBo.getOwnUrl());
		ownBo.setUsername(newOwnBo.getUsername());
		return null;
	}
	@Override
	public String removeFile(FileInfoPo fileInfoPo) throws IOException {
		fileInfoService.removeFileInfo(fileInfoPo);
		return fileDeal.removeFile(fileInfoPo);
	}

	@Override
	public String removeFile(OwnPo ownPo) throws IOException {
		ownService.removeOwn(ownPo);
		return fileDeal.removeFile(ownPo);
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
			return fileDeal.getFile(ownPo, outputStream);
		} else {
			FileInfoPo fileInfoPo = new FileInfoPo();
			fileInfoPo.setMd5(md5OrUuid);
			return fileDeal.getFile(fileInfoPo, outputStream);
		}
	}
}
