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
	void addOwn(OwnPo ownPo);

	void removeOwn(OwnQuery ownQuery);

	OwnBo getOwn(OwnQuery ownQuery);

	List<OwnBo> listOwn(OwnQuery ownQuery);

	List<String> listSort(int userId);

	void changeOwn(OwnPo ownPo);

	String checkAddOwn(OwnPo ownPo);

	String checkChangeOwn(OwnPo ownPo);
}
