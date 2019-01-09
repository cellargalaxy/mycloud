package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.bo.OwnExpireBo;

import java.io.File;

/**
 * @author cellargalaxy
 * @time 2018/12/14
 */
public interface PathService {
	void setUrl(OwnBo ownBo);

	void setUrl(OwnExpireBo ownExpireBo);

	void setUrl(FileInfoBo fileInfoBo);

	File getMycloudFolder();

	File getDriveFolder();

	File getMd5Folder();

	File getUuidFolder();

	File getSqliteFile();
}
