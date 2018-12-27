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

	OwnBo getOwn(OwnPo ownPo);

	OwnBo getOwn(UserPo userPo, OwnPo ownPo);

	List<OwnBo> listPageOwn(OwnQuery ownQuery);

	List<OwnBo> listPageOwn(UserPo userPo, OwnQuery ownQuery);

	List<OwnBo> listSomeOwn(OwnQuery ownQuery);

	List<OwnBo> listAllOwn();

	int getOwnCount(OwnQuery ownQuery);

	List<String> listSort();

	List<String> listSort(UserPo userPo);
}
