package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
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

	List<OwnBo> listOwn(OwnQuery ownQuery);

	List<String> listSort(OwnQuery ownQuery);

	String changeOwn(OwnPo ownPo);

	String checkAddOwn(OwnPo ownPo);

	String checkChangeOwn(OwnPo ownPo);
}
