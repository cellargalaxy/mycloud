package top.cellargalaxy.mycloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.cellargalaxy.mycloud.dao.AuthorizationDao;
import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.po.AuthorizationPo;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;
import top.cellargalaxy.mycloud.service.AuthorizationService;
import top.cellargalaxy.mycloud.util.serivce.ServiceUtils;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Transactional
@Service
public class AuthorizationServiceImpl implements AuthorizationService {
    private static final String NAME = "授权";
    @Autowired
    private AuthorizationDao authorizationDao;

    @Override
    public String addAuthorization(AuthorizationPo authorizationPo) {
        return ServiceUtils.add(authorizationPo, NAME, this::checkAddAuthorization, authorizationDao);
    }

    private String checkAddAuthorization(AuthorizationPo authorizationPo) {
        return AuthorizationDao.checkInsert(authorizationPo);
//        return ServiceUtils.checkAdd(authorizationPo, NAME, AuthorizationDao::checkInsert, authorizationDao);
    }

    @Override
    public String removeAuthorization(AuthorizationPo authorizationPo) {
        return ServiceUtils.remove(authorizationPo, NAME, authorizationDao);
    }

    @Override
    public List<AuthorizationBo> listAuthorizationByUserId(AuthorizationQuery authorizationQuery) {
        int page = authorizationQuery.getPage();
        int pageSize = authorizationQuery.getPageSize();
        int userId = authorizationQuery.getUserId();

        authorizationQuery = new AuthorizationQuery();
        authorizationQuery.setPage(page);
        authorizationQuery.setPageSize(pageSize);
        authorizationQuery.setUserId(userId);

        return authorizationDao.selectAllSome(authorizationQuery);
    }
}
