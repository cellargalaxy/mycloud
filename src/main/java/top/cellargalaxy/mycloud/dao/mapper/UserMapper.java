package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import top.cellargalaxy.mycloud.dao.UserDao;
import top.cellargalaxy.mycloud.model.bo.UserBo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.UserQuery;
import top.cellargalaxy.mycloud.util.StringUtils;
import top.cellargalaxy.mycloud.util.dao.IDao;
import top.cellargalaxy.mycloud.util.dao.ProviderUtils;
import top.cellargalaxy.mycloud.util.dao.SqlUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Set;

/**
 * @author cellargalaxy
 * @time 2018/9/25
 */
@ConditionalOnBean(DataSource.class)
@Mapper
public interface UserMapper extends IDao<UserPo, UserBo, UserQuery> {
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    @InsertProvider(type = UserProvider.class, method = "insert")
    int insert(UserPo userPo);

    @DeleteProvider(type = UserProvider.class, method = "delete")
    int delete(UserPo userPo);

    @UpdateProvider(type = UserProvider.class, method = "update")
    int update(UserPo userPo);

    @Results(id = "userResults", value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "username", column = "username"),
            @Result(property = "password", column = "password"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
    })
    @SelectProvider(type = UserProvider.class, method = "selectOne")
    UserBo selectOne(UserPo userPo);

    @ResultMap(value = "userResults")
    @SelectProvider(type = UserProvider.class, method = "selectPageSome")
    List<UserBo> selectPageSome(UserQuery userQuery);

    @ResultMap(value = "userResults")
    @SelectProvider(type = UserProvider.class, method = "selectAllSome")
    List<UserBo> selectAllSome(UserQuery userQuery);

    @SelectProvider(type = UserProvider.class, method = "selectCount")
    int selectCount(UserQuery userQuery);

    @ResultMap(value = "userResults")
    @SelectProvider(type = UserProvider.class, method = "selectAll")
    List<UserBo> selectAll();

    class UserProvider /*implements IProvider<UserPo,UserQuery>*/ {
        private final String tableName = UserDao.TABLE_NAME;

        public void wheresKey(UserPo userPo, Set<String> wheres) {
            if (userPo.getUserId() > 0) {
                wheres.add("userId");
                return;
            }
            if (!StringUtils.isBlank(userPo.getUsername())) {
                wheres.add("username");
                return;
            }
        }

        public void wheresAll(UserQuery userQuery, Set<String> wheres) {
            if (userQuery.getUserId() > 0) {
                wheres.add("userId");
            }
            if (!StringUtils.isBlank(userQuery.getUsername())) {
                wheres.add("username");
            }
        }

        public void sets(UserPo userPo, Set<String> sets) {
            if (!StringUtils.isBlank(userPo.getUsername())) {
                sets.add("username");
            }
            if (!StringUtils.isBlank(userPo.getPassword())) {
                sets.add("password");
            }
            if (userPo.getUpdateTime() != null) {
                sets.add("updateTime");
            }
        }


        public String insert(UserPo userPo) {
            String string = ProviderUtils.insert(tableName, userPo).toString();
            return string;
        }


        public String delete(UserPo userPo) {
            String string = ProviderUtils.limitOne(ProviderUtils.delete(tableName, userPo, this::wheresKey)).toString();
            return string;
        }


        public String update(UserPo userPo) {
            String string = ProviderUtils.limitOne(ProviderUtils.update(tableName, userPo, "userId", this::sets, this::wheresKey)).toString();
            return string;
        }


        public String selectOne(UserPo userPo) {
            String string = ProviderUtils.limitOne(ProviderUtils.select(tableName, userPo, this::wheresKey)).toString();
            return string;
        }


        public String selectPageSome(UserQuery userQuery) {
            SqlUtils.initPageQuery(userQuery);
            String string = ProviderUtils.limitSome(ProviderUtils.select(tableName, userQuery, this::wheresAll)).toString();
            return string;
        }


        public String selectAllSome(UserQuery userQuery) {
            String string = ProviderUtils.select(tableName, userQuery, this::wheresAll).toString();
            return string;
        }


        public String selectCount(UserQuery userQuery) {
            String string = ProviderUtils.selectCount(tableName, userQuery, this::wheresAll).toString();
            return string;
        }


        public String selectAll() {
            String string = ProviderUtils.selectAll(tableName).toString();
            return string;
        }


    }
}
