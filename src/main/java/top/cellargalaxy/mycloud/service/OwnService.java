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

	String addOwn(UserPo userPo, OwnPo ownPo);

	String removeOwn(OwnPo ownPo);

	String removeOwn(UserPo userPo, OwnPo ownPo);

	String changeOwn(OwnPo ownPo);

	String changeOwn(UserPo userPo, OwnPo ownPo);

	String checkAddOwn(OwnPo ownPo);

	String checkChangeOwn(OwnPo ownPo);

	OwnBo getOwn(OwnPo ownPo);

	OwnBo getOwn(UserPo userPo, OwnPo ownPo);

	List<OwnBo> listOwn(OwnQuery ownQuery);

	List<OwnBo> listAllOwn(OwnQuery ownQuery);

	List<OwnBo> listOwn(UserPo userPo, OwnQuery ownQuery);

	int getOwnCount(OwnQuery ownQuery);

	List<String> listSort();

	List<String> listSort(UserPo userPo);
}
