package top.cellargalaxy.mycloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.cellargalaxy.mycloud.dao.UserDao;
import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.bo.UserBo;
import top.cellargalaxy.mycloud.model.po.Permission;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;
import top.cellargalaxy.mycloud.model.vo.UserVo;
import top.cellargalaxy.mycloud.service.AuthorizationService;
import top.cellargalaxy.mycloud.service.UserService;
import top.cellargalaxy.mycloud.util.StringUtils;
import top.cellargalaxy.mycloud.util.serivce.ServiceUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {
    private static final String NAME = "账号";
    @Autowired
    private UserDao userDao;
    @Autowired
    private AuthorizationService authorizationService;

    @Override
    public String addUser(UserPo userPo) {
        if (userPo.getPassword() != null) {
            userPo.setPassword(StringUtils.PASSWORD_HEAD + StringUtils.encoderPassword(userPo.getPassword()));
        }
        return ServiceUtils.add(userPo, NAME, UserDao::checkInsert, userDao);
    }

    @Override
    public String removeUser(UserPo userPo) {
        return ServiceUtils.remove(userPo, NAME, userDao);
    }

    @Override
    public String changeUser(UserPo userPo) {
        if (userPo.getPassword() != null) {
            userPo.setPassword(StringUtils.PASSWORD_HEAD + StringUtils.encoderPassword(userPo.getPassword()));
        }
        return ServiceUtils.change(userPo, NAME, UserDao::checkUpdate, userDao);
    }

    @Override
    public String changeUser(UserPo userPo, UserPo newUserPo) {
        if (userPo == null) {
            return "未登录";
        }
        if (userPo.getUserId() != newUserPo.getUserId()) {
            return "不得修改他人信息";
        }
        return ServiceUtils.change(newUserPo, NAME, UserDao::checkUpdate, userDao);
    }

    @Override
    public UserBo getUser(UserPo userPo) {
        return userDao.selectOne(userPo);
    }

    @Override
    public UserVo getUserVo(UserPo userPo) {
        UserBo userBo = userDao.selectOne(userPo);
        return bo2vo(userBo);
    }

    @Override
    public List<UserBo> listAllUser() {
        return userDao.selectAll();
    }

    @Override
    public List<UserVo> listAllUserVo() {
        List<UserBo> userBos = userDao.selectAll();
        return bo2vo(userBos);
    }

    @Override
    public Permission[] listAllPermission() {
        return Permission.values();
    }

    private List<UserVo> bo2vo(List<UserBo> userBos) {
        if (userBos == null) {
            return null;
        }
        return userBos.stream().map(userBo -> bo2vo(userBo)).collect(Collectors.toList());
    }

    private UserVo bo2vo(UserBo userBo) {
        if (userBo == null) {
            return null;
        }

        AuthorizationQuery authorizationQuery = new AuthorizationQuery();
        authorizationQuery.setUserId(userBo.getUserId());
        List<AuthorizationBo> authorizationBos = authorizationService.listAuthorizationByUserId(authorizationQuery).stream().collect(Collectors.toList());
        if (authorizationBos.size() == 0) {
            authorizationBos.add(AuthorizationBo.GUEST);
        }

        UserVo userVo = new UserVo();
        userVo.setUser(userBo);
        userVo.setAuthorizations(authorizationBos);
        return userVo;
    }
}
