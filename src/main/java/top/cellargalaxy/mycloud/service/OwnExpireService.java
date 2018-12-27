package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.bo.OwnExpireBo;
import top.cellargalaxy.mycloud.model.po.OwnExpirePo;
import top.cellargalaxy.mycloud.model.po.OwnPo;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/12/13
 */
public interface OwnExpireService {
	String addOwnExpire(OwnPo ownPo, OwnExpirePo ownExpirePo);

	String removeOwnExpire(OwnExpirePo ownExpirePo);

	List<OwnExpireBo> listAllOwnExpire();

	List<OwnExpireBo> listExpireOwnExpire();

	List<OwnPo> listRecentExpireOwn();
}
