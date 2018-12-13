package top.cellargalaxy.mycloud.dao;

import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.po.AuthorizationPo;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;
import top.cellargalaxy.mycloud.util.dao.IDao;

/**
 * @author cellargalaxy
 * @time 2018/10/25
 */
public interface AuthorizationDao extends IDao<AuthorizationPo, AuthorizationBo, AuthorizationQuery> {
    String TABLE_NAME = "authorization";

    static String checkInsert(AuthorizationPo authorizationPo) {
        if (authorizationPo.getUserId() < 1) {
            return "用户id不得为空";
        }
        if (authorizationPo.getPermission() == null) {
            return "权限不得为空";
        }
        return null;
    }

    static String checkUpdate(AuthorizationPo authorizationPo) {
        if (authorizationPo.getAuthorizationId() < 1) {
            return "授权id不得为空";
        }
        return null;
    }
}
