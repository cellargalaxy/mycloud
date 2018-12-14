package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import top.cellargalaxy.mycloud.dao.OwnDao;
import top.cellargalaxy.mycloud.dao.OwnExpireDao;
import top.cellargalaxy.mycloud.dao.UserDao;
import top.cellargalaxy.mycloud.model.bo.OwnExpireBo;
import top.cellargalaxy.mycloud.model.po.OwnExpirePo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.query.OwnExpireQuery;
import top.cellargalaxy.mycloud.util.dao.ProviderUtils;
import top.cellargalaxy.mycloud.util.dao.SqlUtils;

import java.util.List;
import java.util.Set;

/**
 * @author cellargalaxy
 * @time 2018/12/13
 */
@Mapper
public interface OwnExpireMapper extends OwnExpireDao {
    @Options(useGeneratedKeys = true, keyProperty = "ownId")
    @InsertProvider(type = OwnExpireProvider.class, method = "insert")
    int insert(OwnExpirePo ownExpirePo);

    @DeleteProvider(type = OwnExpireProvider.class, method = "delete")
    int delete(OwnExpirePo ownExpirePo);

    @UpdateProvider(type = OwnExpireProvider.class, method = "update")
    int update(OwnExpirePo ownExpirePo);

    @Results(id = "ownExpireResults", value = {
            @Result(property = "ownId", column = "own_id"),
            @Result(property = "expireTime", column = "expire_time"),
    })
    @SelectProvider(type = OwnExpireProvider.class, method = "selectOne")
    OwnExpireBo selectOne(OwnExpirePo ownExpirePo);

    @ResultMap(value = "ownExpireResults")
    @SelectProvider(type = OwnExpireProvider.class, method = "selectPageSome")
    List<OwnExpireBo> selectPageSome(OwnExpireQuery ownExpireQuery);

    @ResultMap(value = "ownExpireResults")
    @SelectProvider(type = OwnExpireProvider.class, method = "selectAllSome")
    List<OwnExpireBo> selectAllSome(OwnExpireQuery ownExpireQuery);

    @SelectProvider(type = OwnExpireProvider.class, method = "selectCount")
    int selectCount(OwnExpireQuery ownExpireQuery);

    @ResultMap(value = "ownExpireResults")
    @SelectProvider(type = OwnExpireProvider.class, method = "selectAll")
    List<OwnExpireBo> selectAll();

    @ResultMap(value = "ownExpireResults")
    @SelectProvider(type = OwnExpireProvider.class, method = "selectExpireOwnExpire")
    List<OwnExpireBo> selectExpireOwnExpire(OwnExpireQuery ownExpireQuery);

    @Results(id = "ownResults", value = {
            @Result(property = "ownId", column = "own_id"),
            @Result(property = "ownUuid", column = "own_uuid"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "fileLength", column = "file_length"),
            @Result(property = "contentType", column = "content_type"),
            @Result(property = "fileName", column = "file_name"),
            @Result(property = "fileId", column = "file_id"),
            @Result(property = "sort", column = "sort"),
            @Result(property = "description", column = "description"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
    })
    @SelectProvider(type = OwnExpireProvider.class, method = "selectRecentExpireOwn")
    List<OwnPo> selectRecentExpireOwn(OwnExpireQuery ownExpireQuery);

    class OwnExpireProvider /*implements IProvider<OwnExpirePo, OwnExpireQuery>*/ {
        private String tableName = OwnExpireDao.TABLE_NAME;

        public void wheresKey(OwnExpirePo ownExpirePo, Set<String> wheres) {
            if (ownExpirePo.getOwnId() > 0) {
                wheres.add("ownId");
                return;
            }
        }


        public void wheresAll(OwnExpireQuery ownExpireQuery, Set<String> wheres) {
            if (ownExpireQuery.getOwnId() > 0) {
                wheres.add("ownId");
            }
        }


        public void sets(OwnExpirePo ownExpirePo, Set<String> sets) {

        }


        public String insert(OwnExpirePo ownExpirePo) {
            String string = ProviderUtils.insert(tableName, ownExpirePo).toString();
            return string;
        }


        public String delete(OwnExpirePo ownExpirePo) {
            String string = ProviderUtils.limitOne(ProviderUtils.delete(tableName, ownExpirePo, this::wheresKey)).toString();
            return string;
        }


        public String update(OwnExpirePo ownExpirePo) {
            String string = ProviderUtils.limitOne(ProviderUtils.update(tableName, ownExpirePo, "ownId", this::sets, this::wheresKey)).toString();
            return string;
        }


        public String selectOne(OwnExpirePo ownExpirePo) {
            String string = ProviderUtils.limitOne(ProviderUtils.select(tableName, ownExpirePo, this::wheresKey)).toString();
            return string;
        }


        public String selectPageSome(OwnExpireQuery ownExpireQuery) {
            SqlUtils.initPageQuery(ownExpireQuery);
            String string = ProviderUtils.limitSome(ProviderUtils.select(tableName, ownExpireQuery, this::wheresAll)).toString();
            return string;
        }


        public String selectAllSome(OwnExpireQuery ownExpireQuery) {
            String string = ProviderUtils.select(tableName, ownExpireQuery, this::wheresAll).toString();
            return string;
        }


        public String selectCount(OwnExpireQuery ownExpireQuery) {
            String string = ProviderUtils.selectCount(tableName, ownExpireQuery, this::wheresAll).toString();
            return string;
        }


        public String selectAll() {
            String string = ProviderUtils.selectAll(tableName).toString();
            return string;
        }

        public String selectExpireOwnExpire(OwnExpireQuery ownExpireQuery) {
            SQL sql = new SQL()
                    .SELECT("*")
                    .FROM(tableName);
            if (ownExpireQuery.getExpireTime() == null) {
                sql.WHERE("false");
            } else {
                sql.WHERE("expire_time<=#{expireTime}");
            }
            String string = sql.toString();
            return string;
        }


        public String selectRecentExpireOwn(OwnExpireQuery ownExpireQuery) {
            SqlUtils.initPageQuery(ownExpireQuery);
            SQL sql = new SQL()
                    .SELECT("*")
                    .FROM(tableName)
                    .FROM(OwnDao.TABLE_NAME)
                    .WHERE(tableName + ".own_id=" + OwnDao.TABLE_NAME + ".own_id")
                    .ORDER_BY("expire_time desc");
            String string = ProviderUtils.limitSome(sql).toString();
            return string;
        }
    }
}
