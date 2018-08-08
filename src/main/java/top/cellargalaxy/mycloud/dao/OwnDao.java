package top.cellargalaxy.mycloud.dao;

import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.util.StringUtil;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
public interface OwnDao {
	String TABLE_NAME = "own";

	int insert(OwnPo ownPo);

	int delete(OwnPo ownPo);

	OwnBo selectOne(OwnPo ownPo);

	List<OwnBo> selectSome(OwnQuery ownQuery);

	int selectCount(OwnQuery ownQuery);

	List<String> selectSort(OwnQuery ownQuery);

	int update(OwnPo ownPo);

	static String checkInsert(OwnPo ownPo) {
		if (ownPo.getUserId() < 0) {
			return "用户id不得为空";
		}
		if (ownPo.getFileId() < 1) {
			return "文件id不得为空";
		}
		if (StringUtil.isBlank(ownPo.getFileName())) {
			return "文件名不得为空";
		}
		if (StringUtil.isBlank(ownPo.getSort())) {
			return "分类不得为空";
		}
		return null;
	}

	static String checkUpdate(OwnPo ownPo) {
		if (ownPo.getOwnId() < 1 && (ownPo.getUserId() < 1 || ownPo.getFileId() < 1)) {
			return "所属id或者userId与fileId组合不得为空";
		}
		return null;
	}
}
