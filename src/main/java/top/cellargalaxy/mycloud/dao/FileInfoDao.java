package top.cellargalaxy.mycloud.dao;

import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.util.StringUtil;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/10/25
 */
public interface FileInfoDao extends AbstractDao<FileInfoPo, FileInfoBo, FileInfoQuery> {
	String TABLE_NAME = "file_info";

	List<String> selectAllContentType();

	static String checkInsert(FileInfoPo fileInfoPo) {
		if (StringUtil.isBlank(fileInfoPo.getMd5())) {
			return "MD5不得为空";
		}
		if (StringUtil.isBlank(fileInfoPo.getContentType())) {
			return "文件类型不得为空";
		}
		return null;
	}

	static String checkUpdate(FileInfoPo fileInfoPo) {
		if (fileInfoPo.getFileId() < 1) {
			return "文件id不得为空";
		}
		return null;
	}
}
