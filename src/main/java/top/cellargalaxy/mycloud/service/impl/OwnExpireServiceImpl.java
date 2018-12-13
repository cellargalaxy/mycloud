package top.cellargalaxy.mycloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.cellargalaxy.mycloud.dao.OwnExpireDao;
import top.cellargalaxy.mycloud.model.bo.OwnExpireBo;
import top.cellargalaxy.mycloud.model.po.OwnExpirePo;
import top.cellargalaxy.mycloud.model.query.OwnExpireQuery;
import top.cellargalaxy.mycloud.service.OwnExpireService;
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

    @Override
    public String addOwnExpire(OwnExpirePo ownExpirePo) {
        return ServiceUtils.add(ownExpirePo, NAME, this::checkAddOwnExpire, ownExpireDao);
    }

    private String checkAddOwnExpire(OwnExpirePo ownExpirePo) {
        return OwnExpireDao.checkInsert(ownExpirePo);
    }

    @Override
    public String removeOwnExpire(OwnExpirePo ownExpirePo) {
        return ServiceUtils.remove(ownExpirePo, NAME, ownExpireDao);
    }

    @Override
    public List<OwnExpireBo> listExpireOwnExpire() {
        OwnExpireQuery ownExpireQuery = new OwnExpireQuery();
        ownExpireQuery.setExpireTime(new Date());
        return ownExpireDao.selectExpireOwnExpire(ownExpireQuery);
    }
}
