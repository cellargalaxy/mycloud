package top.cellargalaxy.mycloud.dao;

import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.util.StringUtil;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/10/25
 */
public interface OwnDao extends AbstractDao<OwnPo, OwnBo, OwnQuery> {
	String TABLE_NAME = "own";

	List<String> selectAllSort(OwnQuery ownQuery);

	static String checkInsert(OwnPo ownPo) {
		if (StringUtil.isBlank(ownPo.getContentType())) {
			return "文件类型不得为空";
		}
		if (StringUtil.isBlank(ownPo.getFileName())) {
			return "文件名不得为空";
		}
		if (StringUtil.isBlank(ownPo.getOwnUuid())) {
			return "所属uuid不得为空";
		}
		if (StringUtil.isBlank(ownPo.getSort())) {
			return "分类不得为空";
		}
		if (ownPo.getUserId() < 1) {
			return "用户id不得为空";
		}
		return null;
	}

	static String checkUpdate(OwnPo ownPo) {
		if (ownPo.getOwnId() < 1) {
			return "所属id不得为空";
		}
		return null;
	}
}
