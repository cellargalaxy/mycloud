package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import top.cellargalaxy.mycloud.dao.AuthorizationDao;
import top.cellargalaxy.mycloud.dao.UserDao;
import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.po.AuthorizationPo;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;
import top.cellargalaxy.mycloud.util.dao.IDao;
import top.cellargalaxy.mycloud.util.dao.ProviderUtils;
import top.cellargalaxy.mycloud.util.dao.SqlUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author cellargalaxy
 * @time 2018/10/25
 */
@Mapper
public interface AuthorizationMapper extends IDao<AuthorizationPo, AuthorizationBo, AuthorizationQuery> {
    @Options(useGeneratedKeys = true, keyProperty = "authorizationId")
    @InsertProvider(type = AuthorizationProvider.class, method = "insert")
    int insert(AuthorizationPo authorizationPo);

    @DeleteProvider(type = AuthorizationProvider.class, method = "delete")
    int delete(AuthorizationPo authorizationPo);

    @UpdateProvider(type = AuthorizationProvider.class, method = "update")
    int update(AuthorizationPo authorizationPo);

    @Results(id = "authorizationResults", value = {
            @Result(property = "authorizationId", column = "authorization_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "permission", column = "permission"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "username", column = "username"),
    })
    @SelectProvider(type = AuthorizationProvider.class, method = "selectOne")
    AuthorizationBo selectOne(AuthorizationPo authorizationPo);

    @ResultMap(value = "authorizationResults")
    @SelectProvider(type = AuthorizationProvider.class, method = "selectPageSome")
    List<AuthorizationBo> selectPageSome(AuthorizationQuery authorizationQuery);

    @ResultMap(value = "authorizationResults")
    @SelectProvider(type = AuthorizationProvider.class, method = "selectAllSome")
    List<AuthorizationBo> selectAllSome(AuthorizationQuery authorizationQuery);

    @SelectProvider(type = AuthorizationProvider.class, method = "selectCount")
    int selectCount(AuthorizationQuery authorizationQuery);

    @ResultMap(value = "authorizationResults")
    @SelectProvider(type = AuthorizationProvider.class, method = "selectAll")
    List<AuthorizationBo> selectAll();

    class AuthorizationProvider /*implements IProvider<AuthorizationPo, AuthorizationQuery>*/ {
        private String tableName = AuthorizationDao.TABLE_NAME;

        public void wheresKey(AuthorizationPo authorizationPo, Set<String> wheres) {
            if (authorizationPo.getAuthorizationId() > 0) {
                wheres.add("authorizationId");
                return;
            }
            if (authorizationPo.getUserId() > 0 && authorizationPo.getPermission() != null) {
                wheres.add("userId");
                wheres.add("permission");
                return;
            }
        }

        public void wheresAll(AuthorizationQuery authorizationQuery, Set<String> wheres) {
            if (authorizationQuery.getAuthorizationId() > 0) {
                wheres.add("authorizationId");
            }
            if (authorizationQuery.getUserId() > 0) {
                wheres.add("userId");
            }
            if (authorizationQuery.getPermission() != null) {
                wheres.add("permission");
            }
        }

        public void sets(AuthorizationPo authorizationPo, Set<String> sets) {
            if (authorizationPo.getUserId() > 0) {
                sets.add("userId");
            }
            if (authorizationPo.getPermission() != null) {
                sets.add("permission");
            }
            if (authorizationPo.getUpdateTime() != null) {
                sets.add("updateTime");
            }
        }

        public String insert(AuthorizationPo authorizationPo) {
            String string = ProviderUtils.insert(tableName, authorizationPo).toString();
            return string;
        }

        public String delete(AuthorizationPo authorizationPo) {
            String string = ProviderUtils.limitOne(ProviderUtils.delete(tableName, authorizationPo, this::wheresKey)).toString();
            return string;
        }

        public String update(AuthorizationPo authorizationPo) {
            String string = ProviderUtils.limitOne(ProviderUtils.update(tableName, authorizationPo, "userId", this::sets, this::wheresKey)).toString();
            return string;
        }

        public String selectOne(AuthorizationPo authorizationPo) {
            SQL sql = new SQL()
                    .SELECT(tableName + ".authorization_id")
                    .SELECT(tableName + ".user_id")
                    .SELECT(tableName + ".permission")
                    .SELECT(tableName + ".create_time")
                    .SELECT(tableName + ".update_time")
                    .SELECT(UserDao.TABLE_NAME + ".username")
                    .FROM(tableName)
                    .WHERE(tableName + ".user_id=" + UserDao.TABLE_NAME + ".user_id");
            Set<String> wheres = new HashSet<>();
            wheresKey(authorizationPo, wheres);
            ProviderUtils.where(authorizationPo, wheres, sql);
            String string = ProviderUtils.limitOne(sql).toString();
            return string;
        }

        public String selectPageSome(AuthorizationQuery authorizationQuery) {
            SqlUtils.initPageQuery(authorizationQuery);
            SQL sql = new SQL()
                    .SELECT(tableName + ".authorization_id")
                    .SELECT(tableName + ".user_id")
                    .SELECT(tableName + ".permission")
                    .SELECT(tableName + ".create_time")
                    .SELECT(tableName + ".update_time")
                    .SELECT(UserDao.TABLE_NAME + ".username")
                    .FROM(tableName)
                    .WHERE(tableName + ".user_id=" + UserDao.TABLE_NAME + ".user_id");
            Set<String> wheres = new HashSet<>();
            wheresAll(authorizationQuery, wheres);
            ProviderUtils.where(authorizationQuery, wheres, sql);
            String string = ProviderUtils.limitSome(sql).toString();
            return string;
        }

        public String selectAllSome(AuthorizationQuery authorizationQuery) {
            SQL sql = new SQL()
                    .SELECT(tableName + ".authorization_id")
                    .SELECT(tableName + ".user_id")
                    .SELECT(tableName + ".permission")
                    .SELECT(tableName + ".create_time")
                    .SELECT(tableName + ".update_time")
                    .SELECT(UserDao.TABLE_NAME + ".username")
                    .FROM(tableName)
                    .WHERE(tableName + ".user_id=" + UserDao.TABLE_NAME + ".user_id");
            Set<String> wheres = new HashSet<>();
            wheresAll(authorizationQuery, wheres);
            ProviderUtils.where(authorizationQuery, wheres, sql);
            String string = sql.toString();
            return string;
        }

        public String selectCount(AuthorizationQuery authorizationQuery) {
            String string = ProviderUtils.selectCount(tableName, authorizationQuery, this::wheresAll).toString();
            return string;
        }

        public String selectAll() {
            SQL sql = new SQL()
                    .SELECT(tableName + ".authorization_id")
                    .SELECT(tableName + ".user_id")
                    .SELECT(tableName + ".permission")
                    .SELECT(tableName + ".create_time")
                    .SELECT(tableName + ".update_time")
                    .SELECT(UserDao.TABLE_NAME + ".username")
                    .FROM(tableName)
                    .WHERE(tableName + ".user_id=" + UserDao.TABLE_NAME + ".user_id");
            String string = sql.toString();
            return string;
        }
    }
}
