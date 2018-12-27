package top.cellargalaxy.mycloud.dao;

import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.util.StringUtils;
import top.cellargalaxy.mycloud.util.dao.IDao;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/10/25
 */
public interface OwnDao extends IDao<OwnPo, OwnBo, OwnQuery> {
	String TABLE_NAME = "own";

	/**
	 * 用户id小于0，是为了包含GUEST的id。但这点需要在service层进行检验
	 *
	 * @param ownPo
	 * @return
	 */
	static String checkInsert(OwnPo ownPo) {
		if (StringUtils.isBlank(ownPo.getOwnUuid())) {
			return "所属uuid不得为空";
		}
		if (ownPo.getUserId() < 0) {
			return "用户id不得为空";
		}
		if (StringUtils.isBlank(ownPo.getFileName())) {
			return "文件名不得为空";
		}
		if (StringUtils.isBlank(ownPo.getContentType())) {
			return "文件类型不得为空";
		}
		if (ownPo.getFileLength() < 0) {
			return "文件大小不得小于零";
		}
		return null;
	}

	static String checkUpdate(OwnPo ownPo) {
		if (ownPo.getOwnId() < 1) {
			return "所属id不得为空";
		}
		return null;
	}

	List<String> selectAllSort(OwnQuery ownQuery);
}
