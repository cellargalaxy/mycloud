package top.cellargalaxy.mycloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.dao.FileInfoDao;
import top.cellargalaxy.mycloud.dao.OwnDao;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.service.OwnService;
import top.cellargalaxy.mycloud.util.serivce.ServiceUtils;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Transactional
@Service
public class OwnServiceImpl implements OwnService {
    private static final String NAME = "所属";
    @Autowired
    private OwnDao ownDao;
    private final String domain;

    @Autowired
    public OwnServiceImpl(MycloudConfiguration mycloudConfiguration) {
        this.domain = mycloudConfiguration.getDomain();
    }

    @Override
    public String addOwn(OwnPo ownPo) {
        return ServiceUtils.add(ownPo, NAME, this::checkAddOwn, ownDao);
    }

    @Override
    public String addOwn(UserPo userPo, OwnPo ownPo) {
        if (userPo == null) {
            return "未登录";
        }
        ownPo.setUserId(userPo.getUserId());
        return ServiceUtils.add(ownPo, NAME, this::checkAddOwn, ownDao);
    }

    private String checkAddOwn(OwnPo ownPo) {
        return OwnDao.checkInsert(ownPo);
    }

    @Override
    public String removeOwn(OwnPo ownPo) {
        return ServiceUtils.remove(ownPo, NAME, ownDao);
    }

    @Override
    public String removeOwn(UserPo userPo, OwnPo ownPo) {
        if (userPo == null) {
            return "未登录";
        }
        ownPo.setUserId(userPo.getUserId());
        return ServiceUtils.remove(ownPo, NAME, ownDao);
    }

    @Override
    public String changeOwn(OwnPo ownPo) {
        return ServiceUtils.change(ownPo, NAME, this::checkChangeOwn, ownDao);
    }

    @Override
    public String changeOwn(UserPo userPo, OwnPo ownPo) {
        if (userPo == null) {
            return "未登录";
        }
        ownPo.setUserId(userPo.getUserId());
        return ServiceUtils.change(ownPo, NAME, this::checkChangeOwn, ownDao);
    }

    private String checkChangeOwn(OwnPo ownPo) {
        return OwnDao.checkUpdate(ownPo);
    }

    @Override
    public OwnBo getOwn(OwnPo ownPo) {
        OwnBo ownBo = ownDao.selectOne(ownPo);
        setUrl(ownBo);
        return ownBo;
    }

    @Override
    public OwnBo getOwn(UserPo userPo, OwnPo ownPo) {
        if (userPo == null) {
            return null;
        }
        ownPo.setUserId(userPo.getUserId());
        OwnBo ownBo = ownDao.selectOne(ownPo);
        setUrl(ownBo);
        return ownBo;
    }

    @Override
    public List<OwnBo> listOwn(OwnQuery ownQuery) {
        List<OwnBo> ownBos = ownDao.selectPageSome(ownQuery);
        ownBos.stream().forEach(ownBo -> setUrl(ownBo));
        return ownBos;
    }

    @Override
    public List<OwnBo> listOwn(UserPo userPo, OwnQuery ownQuery) {
        if (userPo == null) {
            return null;
        }
        ownQuery.setUserId(userPo.getUserId());
        List<OwnBo> ownBos = ownDao.selectPageSome(ownQuery);
        ownBos.stream().forEach(ownBo -> setUrl(ownBo));
        return ownBos;
    }

    @Override
    public List<OwnBo> listAllOwn(OwnQuery ownQuery) {
        List<OwnBo> ownBos = ownDao.selectAllSome(ownQuery);
        ownBos.stream().forEach(ownBo -> setUrl(ownBo));
        return ownBos;
    }

    @Override
    public List<OwnBo> listAllOwn() {
        List<OwnBo> ownBos = ownDao.selectAll();
        ownBos.stream().forEach(ownBo -> setUrl(ownBo));
        return ownBos;
    }

    @Override
    public int getOwnCount(OwnQuery ownQuery) {
        return ownDao.selectCount(ownQuery);
    }

    @Override
    public List<String> listSort() {
        return ownDao.selectAllSort(new OwnQuery());
    }

    @Override
    public List<String> listSort(UserPo userPo) {
        if (userPo == null) {
            return null;
        }
        OwnQuery ownQuery = new OwnQuery();
        ownQuery.setUserId(userPo.getUserId());
        return ownDao.selectAllSort(ownQuery);
    }

    private void setUrl(OwnBo ownBo) {
        if (ownBo == null) {
            return;
        }
        ownBo.setMd5Url(domain + "/" + ownBo.getMd5());
        ownBo.setOwnUrl(domain + "/" + ownBo.getOwnUuid());
    }
}
