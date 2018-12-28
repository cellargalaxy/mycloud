package top.cellargalaxy.mycloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.service.PathService;
import top.cellargalaxy.mycloud.util.StringUtils;

import java.io.File;

/**
 * @author cellargalaxy
 * @time 2018/12/14
 */
@Service
public class PathServiceImpl implements PathService {
	private final String domain;
	private final File sqliteFile;
	private final File tmpFolder;
	private final File driveFolder;
	private final File md5Folder;
	private final File uuidFolder;

	@Autowired
	public PathServiceImpl(MycloudConfiguration mycloudConfiguration) {
		domain = mycloudConfiguration.getDomain();
		File mycloudFolder = new File(mycloudConfiguration.getMycloudPath());
		if (!mycloudFolder.exists()) {
			mycloudFolder.mkdirs();
		}
		sqliteFile = new File(mycloudConfiguration.getSqlitePath());
		tmpFolder = new File(mycloudConfiguration.getWebUploadTmpFolderPath());
		if (!tmpFolder.exists()) {
			tmpFolder.mkdirs();
		}
		driveFolder = new File(mycloudFolder.getAbsolutePath() + File.separator + "drive");
		if (!driveFolder.exists()) {
			driveFolder.mkdirs();
		}
		md5Folder = new File(driveFolder.getAbsolutePath() + File.separator + "md5");
		if (!md5Folder.exists()) {
			md5Folder.mkdirs();
		}
		uuidFolder = new File(driveFolder.getAbsolutePath() + File.separator + "uuid");
		if (!uuidFolder.exists()) {
			uuidFolder.mkdirs();
		}
	}

	@Override
	public void setUrl(OwnBo ownBo) {
		if (ownBo == null) {
			return;
		}
		if (!StringUtils.isBlank(ownBo.getMd5())) {
			ownBo.setMd5Url(domain + "/" + ownBo.getMd5());
		}
		if (!StringUtils.isBlank(ownBo.getOwnUuid())) {
			ownBo.setOwnUrl(domain + "/" + ownBo.getOwnUuid());
		}
	}

	@Override
	public void setUrl(FileInfoBo fileInfoBo) {
		if (fileInfoBo == null) {
			return;
		}
		if (!StringUtils.isBlank(fileInfoBo.getMd5())) {
			fileInfoBo.setMd5Url(domain + "/" + fileInfoBo.getMd5());
		}
	}

	@Override
	public File getMd5Folder() {
		return md5Folder;
	}

	@Override
	public File getUuidFolder() {
		return uuidFolder;
	}

	@Override
	public File getDriveFolder() {
		return driveFolder;
	}

	@Override
	public File getSqliteFile() {
		return sqliteFile;
	}
}
