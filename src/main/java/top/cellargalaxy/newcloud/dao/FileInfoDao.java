package top.cellargalaxy.newcloud.dao;

import top.cellargalaxy.newcloud.model.po.FileInfoPo;
import top.cellargalaxy.newcloud.model.query.FileInfoQuery;
import top.cellargalaxy.newcloud.model.bo.FileInfoBo;
import top.cellargalaxy.newcloud.util.StringUtil;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/3
 */
public interface FileInfoDao {
	String TABLE_NAME = "file_info";

	int insert(FileInfoPo fileInfoPo);

	int delete(FileInfoQuery fileInfoQuery);

	FileInfoBo selectOne(FileInfoQuery fileInfoQuery);

	List<FileInfoBo> selectSome(FileInfoQuery fileInfoQuery);

	List<String> selectContentType();

	int update(FileInfoPo fileInfoPo);

	static String checkInsert(FileInfoPo fileInfoPo) {
		if (StringUtil.isBlank(fileInfoPo.getMd5())) {
			return "MD5不得为空";
		}
		if (fileInfoPo.getMd5().length() != 32) {
			return "MD5长度异常,现长度为: " + fileInfoPo.getMd5().length();
		}
		if (fileInfoPo.getFileLength() < 1) {
			return "文件长度不得为空";
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
