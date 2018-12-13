package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.bo.OwnExpireBo;
import top.cellargalaxy.mycloud.model.po.OwnExpirePo;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/12/13
 */
public interface OwnExpireService {
    String addOwnExpire(OwnExpirePo ownExpirePo);

    String removeOwnExpire(OwnExpirePo ownExpirePo);

    List<OwnExpireBo> listExpireOwnExpire();
}
