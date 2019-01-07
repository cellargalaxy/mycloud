package top.cellargalaxy.mycloud.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.bo.OwnExpireBo;
import top.cellargalaxy.mycloud.service.PathService;
import top.cellargalaxy.mycloud.util.StringUtils;

import java.io.File;

/**
 * @author cellargalaxy
 * @time 2018/12/14
 */
@Service
public class PathServiceImpl implements PathService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final String domain;
	private final File sqliteFile;
	private final File tmpFolder;
	private final File driveFolder;
	private final File md5Folder;
	private final File uuidFolder;

	@Autowired
	public PathServiceImpl(MycloudConfiguration mycloudConfiguration) {
		domain = mycloudConfiguration.getDomain();
		logger.info("domain: {}", domain);

		File mycloudFolder = new File(mycloudConfiguration.getMycloudPath());
		if (!mycloudFolder.exists()) {
			mycloudFolder.mkdirs();
		}
		logger.info("mycloudFolder: {}", mycloudFolder);

		sqliteFile = new File(mycloudConfiguration.getSqlitePath());
		logger.info("sqliteFile: {}", sqliteFile);

		tmpFolder = new File(mycloudConfiguration.getWebUploadTmpFolderPath());
		if (!tmpFolder.exists()) {
			tmpFolder.mkdirs();
		}
		logger.info("tmpFolder: {}", tmpFolder);

		driveFolder = new File(mycloudFolder.getAbsolutePath() + File.separator + "drive");
		if (!driveFolder.exists()) {
			driveFolder.mkdirs();
		}
		logger.info("driveFolder: {}", driveFolder);

		md5Folder = new File(driveFolder.getAbsolutePath() + File.separator + "md5");
		if (!md5Folder.exists()) {
			md5Folder.mkdirs();
		}
		logger.info("md5Folder: {}", md5Folder);

		uuidFolder = new File(driveFolder.getAbsolutePath() + File.separator + "uuid");
		if (!uuidFolder.exists()) {
			uuidFolder.mkdirs();
		}
		logger.info("uuidFolder: {}", uuidFolder);
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
	public void setUrl(OwnExpireBo ownExpireBo) {
		if (ownExpireBo == null) {
			return;
		}
		if (!StringUtils.isBlank(ownExpireBo.getMd5())) {
			ownExpireBo.setMd5Url(domain + "/" + ownExpireBo.getMd5());
		}
		if (!StringUtils.isBlank(ownExpireBo.getOwnUuid())) {
			ownExpireBo.setOwnUrl(domain + "/" + ownExpireBo.getOwnUuid());
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
