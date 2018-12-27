package top.cellargalaxy.mycloud.dao;

import top.cellargalaxy.mycloud.model.bo.OwnExpireBo;
import top.cellargalaxy.mycloud.model.po.OwnExpirePo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.query.OwnExpireQuery;
import top.cellargalaxy.mycloud.util.dao.IDao;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/12/13
 */
public interface OwnExpireDao extends IDao<OwnExpirePo, OwnExpireBo, OwnExpireQuery> {
    String TABLE_NAME = "own_expire";

    List<OwnExpireBo> selectExpireOwnExpire(OwnExpireQuery ownExpireQuery);

    List<OwnPo> selectRecentExpireOwn(OwnExpireQuery ownExpireQuery);

    static String checkInsert(OwnExpirePo ownExpirePo) {
        if (ownExpirePo.getOwnId() < 1) {
            return "所属id不得为空";
        }
        if (ownExpirePo.getOwnExpireTime() == null) {
            return "所属过期时间不得为空";
        }
        return null;
    }

    static String checkUpdate(OwnExpirePo ownExpirePo) {
        if (ownExpirePo.getOwnId() < 1) {
            return "所属过期id不得为空";
        }
        return null;
    }
}
