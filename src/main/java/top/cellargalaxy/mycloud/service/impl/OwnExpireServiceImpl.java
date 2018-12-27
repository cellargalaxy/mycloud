package top.cellargalaxy.mycloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.cellargalaxy.mycloud.dao.OwnExpireDao;
import top.cellargalaxy.mycloud.model.bo.OwnExpireBo;
import top.cellargalaxy.mycloud.model.po.OwnExpirePo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.query.OwnExpireQuery;
import top.cellargalaxy.mycloud.service.OwnExpireService;
import top.cellargalaxy.mycloud.service.OwnService;
import top.cellargalaxy.mycloud.util.serivce.ServiceUtils;

import java.util.Date;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/12/13
 */
@Transactional
@Service
public class OwnExpireServiceImpl implements OwnExpireService {
    private static final String NAME = "所属过期";
    @Autowired
    private OwnExpireDao ownExpireDao;
    @Autowired
    private OwnService ownService;

    @Override
    public String addOwnExpire(OwnPo ownPo, OwnExpirePo ownExpirePo) {
        String string = ownService.addOwn(ownPo);
        if (string != null) {
            return string;
        }
        ownExpirePo.setOwnId(ownPo.getOwnId());
        return ServiceUtils.add(ownExpirePo, NAME, OwnExpireDao::checkInsert, ownExpireDao);
    }

    @Override
    public String removeOwnExpire(OwnExpirePo ownExpirePo) {
        OwnPo ownPo = new OwnPo();
        ownPo.setOwnId(ownExpirePo.getOwnId());
        ownService.removeOwn(ownPo);
        return ServiceUtils.remove(ownExpirePo, NAME, ownExpireDao);
    }

    @Override
    public List<OwnExpireBo> listAllOwnExpire() {
        return ownExpireDao.selectAll();
    }

    @Override
    public List<OwnExpireBo> listExpireOwnExpire() {
        OwnExpireQuery ownExpireQuery = new OwnExpireQuery();
        ownExpireQuery.setOwnExpireTime(new Date());
        return ownExpireDao.selectExpireOwnExpire(ownExpireQuery);
    }

    @Override
    public List<OwnPo> listRecentExpireOwn() {
        return ownExpireDao.selectRecentExpireOwn(new OwnExpireQuery());
    }
}
