package top.cellargalaxy.mycloud.dao.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import top.cellargalaxy.mycloud.dao.OwnExpireDao;
import top.cellargalaxy.mycloud.dao.mapper.OwnExpireMapper;
import top.cellargalaxy.mycloud.model.bo.OwnExpireBo;
import top.cellargalaxy.mycloud.model.po.OwnExpirePo;
import top.cellargalaxy.mycloud.model.query.OwnExpireQuery;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/12/13
 */
@Repository
public class OwnExpireCache implements OwnExpireDao {
    @Autowired
    private OwnExpireMapper ownExpireMapper;

    public List<OwnExpireBo> selectExpireOwnExpire(OwnExpireQuery ownExpireQuery) {
        return ownExpireMapper.selectExpireOwnExpire(ownExpireQuery);
    }


    public int insert(OwnExpirePo ownExpirePo) {
        return ownExpireMapper.insert(ownExpirePo);
    }


    public int delete(OwnExpirePo ownExpirePo) {
        return ownExpireMapper.delete(ownExpirePo);
    }


    public int update(OwnExpirePo ownExpirePo) {
        return ownExpireMapper.update(ownExpirePo);
    }


    public OwnExpireBo selectOne(OwnExpirePo ownExpirePo) {
        return ownExpireMapper.selectOne(ownExpirePo);
    }


    public List<OwnExpireBo> selectPageSome(OwnExpireQuery ownExpireQuery) {
        return ownExpireMapper.selectPageSome(ownExpireQuery);
    }


    public List<OwnExpireBo> selectAllSome(OwnExpireQuery ownExpireQuery) {
        return ownExpireMapper.selectAllSome(ownExpireQuery);
    }


    public int selectCount(OwnExpireQuery ownExpireQuery) {
        return ownExpireMapper.selectCount(ownExpireQuery);
    }


    public List<OwnExpireBo> selectAll() {
        return ownExpireMapper.selectAll();
    }
}
