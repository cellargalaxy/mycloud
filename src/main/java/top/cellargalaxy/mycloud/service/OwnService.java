package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public interface OwnService {
	String addOwn(OwnPo ownPo);

	String removeOwn(OwnQuery ownQuery);

	OwnBo getOwn(OwnQuery ownQuery);

	int getOwnCount(OwnQuery ownQuery);

	List<OwnBo> listOwn(OwnQuery ownQuery);

	List<String> listSort(OwnQuery ownQuery);

	String changeOwn(OwnPo ownPo);

	String addOwn(UserPo userPo, OwnPo ownPo);

	String removeOwn(UserPo userPo, OwnQuery ownQuery);

	OwnBo getOwn(UserPo userPo, OwnQuery ownQuery);

	int getOwnCount(UserPo userPo, OwnQuery ownQuery);

	List<OwnBo> listOwn(UserPo userPo, OwnQuery ownQuery);

	List<String> listSort(UserPo userPo, OwnQuery ownQuery);

	String changeOwn(UserPo userPo, OwnPo ownPo);

	String checkAddOwn(OwnPo ownPo);

	String checkChangeOwn(OwnPo ownPo);
}
