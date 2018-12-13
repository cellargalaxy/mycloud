package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import top.cellargalaxy.mycloud.dao.FileInfoDao;
import top.cellargalaxy.mycloud.dao.OwnDao;
import top.cellargalaxy.mycloud.dao.UserDao;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.util.StringUtils;
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
public interface OwnMapper extends IDao<OwnPo, OwnBo, OwnQuery> {
    @Options(useGeneratedKeys = true, keyProperty = "ownId")
    @InsertProvider(type = OwnProvider.class, method = "insert")
    int insert(OwnPo ownPo);

    @DeleteProvider(type = OwnProvider.class, method = "delete")
    int delete(OwnPo ownPo);

    @UpdateProvider(type = OwnProvider.class, method = "update")
    int update(OwnPo ownPo);

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
            @Result(property = "username", column = "username"),
            @Result(property = "md5", column = "md5"),
    })
    @SelectProvider(type = OwnProvider.class, method = "selectOne")
    OwnBo selectOne(OwnPo ownPo);

    @ResultMap(value = "ownResults")
    @SelectProvider(type = OwnProvider.class, method = "selectPageSome")
    List<OwnBo> selectPageSome(OwnQuery ownQuery);

    @ResultMap(value = "ownResults")
    @SelectProvider(type = OwnProvider.class, method = "selectAllSome")
    List<OwnBo> selectAllSome(OwnQuery ownQuery);

    @SelectProvider(type = OwnProvider.class, method = "selectCount")
    int selectCount(OwnQuery ownQuery);

    @ResultMap(value = "ownResults")
    @SelectProvider(type = OwnProvider.class, method = "selectAll")
    List<OwnBo> selectAll();

    @SelectProvider(type = OwnProvider.class, method = "selectAllSort")
    List<String> selectAllSort(OwnQuery ownQuery);

    class OwnProvider /*implements IProvider<OwnPo, OwnQuery>*/ {
        private String tableName = OwnDao.TABLE_NAME;

        public void wheresKey(OwnPo ownPo, Set<String> wheres) {
            if (ownPo.getOwnId() > 0) {
                wheres.add("ownId");
                return;
            }
            if (!StringUtils.isBlank(ownPo.getOwnUuid())) {
                wheres.add("ownUuid");
                return;
            }
        }


        public void wheresAll(OwnQuery ownQuery, Set<String> wheres) {
            if (ownQuery.getOwnId() > 0) {
                wheres.add("ownId");
            }
            if (!StringUtils.isBlank(ownQuery.getOwnUuid())) {
                wheres.add("ownUuid");
            }
            if (ownQuery.getUserId() > 0) {
                wheres.add("userId");
            }
            if (ownQuery.getFileId() > 0) {
                wheres.add("fileId");
            }
            if (!StringUtils.isBlank(ownQuery.getContentType())) {
                wheres.add("contentType");
            }
            if (!StringUtils.isBlank(ownQuery.getSort())) {
                wheres.add("sort");
            }
        }


        public void sets(OwnPo ownPo, Set<String> sets) {
            if (ownPo.getFileId() > 0) {
                sets.add("fileId");
            }
            if (!StringUtils.isBlank(ownPo.getFileName())) {
                sets.add("fileName");
            }
            if (!StringUtils.isBlank(ownPo.getSort())) {
                sets.add("sort");
            }
            if (!StringUtils.isBlank(ownPo.getDescription())) {
                sets.add("description");
            }
            if (ownPo.getUpdateTime() != null) {
                sets.add("updateTime");
            }
        }


        public String insert(OwnPo ownPo) {
            String string = ProviderUtils.insert(tableName, ownPo).toString();
            return string;
        }


        public String delete(OwnPo ownPo) {
            String string = ProviderUtils.limitOne(ProviderUtils.delete(tableName, ownPo, this::wheresKey)).toString();
            return string;
        }


        public String update(OwnPo ownPo) {
            String string = ProviderUtils.limitOne(ProviderUtils.update(tableName, ownPo, "ownId", this::sets, this::wheresKey)).toString();
            return string;
        }

        public String selectOne(OwnPo ownPo) {
            SQL sql = new SQL()
                    .SELECT(tableName + ".own_id")
                    .SELECT(tableName + ".own_uuid")
                    .SELECT(tableName + ".user_id")
                    .SELECT(tableName + ".file_id")
                    .SELECT(tableName + ".file_length")
                    .SELECT(tableName + ".content_type")
                    .SELECT(tableName + ".file_name")
                    .SELECT(tableName + ".sort")
                    .SELECT(tableName + ".description")
                    .SELECT(tableName + ".create_time")
                    .SELECT(tableName + ".update_time")
                    .SELECT(UserDao.TABLE_NAME + ".username")
                    .SELECT(FileInfoDao.TABLE_NAME + ".md5")
                    //own left join user on own.user_id=user.user_id left join file_info on own.file_id=file_info.file_id
                    //.FROM(tableName + " left join " + UserDao.TABLE_NAME + " on " + tableName + ".user_id=" + UserDao.TABLE_NAME + ".user_id left join " + FileInfoDao.TABLE_NAME + " on " + tableName + ".file_id=" + FileInfoDao.TABLE_NAME + ".file_id")
                    .FROM(tableName)
                    .LEFT_OUTER_JOIN(UserDao.TABLE_NAME)
                    .LEFT_OUTER_JOIN(FileInfoDao.TABLE_NAME);
            Set<String> wheres = new HashSet<>();
            wheresKey(ownPo, wheres);
            ProviderUtils.where(ownPo, wheres, sql);
            String string = ProviderUtils.limitOne(sql).toString();
            return string;
        }


        public String selectPageSome(OwnQuery ownQuery) {
            SqlUtils.initPageQuery(ownQuery);
            SQL sql = new SQL()
                    .SELECT(tableName + ".own_id")
                    .SELECT(tableName + ".own_uuid")
                    .SELECT(tableName + ".user_id")
                    .SELECT(tableName + ".file_id")
                    .SELECT(tableName + ".file_length")
                    .SELECT(tableName + ".content_type")
                    .SELECT(tableName + ".file_name")
                    .SELECT(tableName + ".sort")
                    .SELECT(tableName + ".description")
                    .SELECT(tableName + ".create_time")
                    .SELECT(tableName + ".update_time")
                    .SELECT(UserDao.TABLE_NAME + ".username")
                    .SELECT(FileInfoDao.TABLE_NAME + ".md5")
                    //own left join user on own.user_id=user.user_id left join file_info on own.file_id=file_info.file_id
                    //.FROM(tableName + " left join " + UserDao.TABLE_NAME + " on " + tableName + ".user_id=" + UserDao.TABLE_NAME + ".user_id left join " + FileInfoDao.TABLE_NAME + " on " + tableName + ".file_id=" + FileInfoDao.TABLE_NAME + ".file_id")
                    .FROM(tableName)
                    .LEFT_OUTER_JOIN(UserDao.TABLE_NAME)
                    .LEFT_OUTER_JOIN(FileInfoDao.TABLE_NAME);
            Set<String> wheres = new HashSet<>();
            wheresAll(ownQuery, wheres);
            ProviderUtils.where(ownQuery, wheres, sql);
            String string = ProviderUtils.limitOne(sql).toString();
            return string;
        }


        public String selectAllSome(OwnQuery ownQuery) {
            SQL sql = new SQL()
                    .SELECT(tableName + ".own_id")
                    .SELECT(tableName + ".own_uuid")
                    .SELECT(tableName + ".user_id")
                    .SELECT(tableName + ".file_id")
                    .SELECT(tableName + ".file_length")
                    .SELECT(tableName + ".content_type")
                    .SELECT(tableName + ".file_name")
                    .SELECT(tableName + ".sort")
                    .SELECT(tableName + ".description")
                    .SELECT(tableName + ".create_time")
                    .SELECT(tableName + ".update_time")
                    .SELECT(UserDao.TABLE_NAME + ".username")
                    .SELECT(FileInfoDao.TABLE_NAME + ".md5")
                    //own left join user on own.user_id=user.user_id left join file_info on own.file_id=file_info.file_id
                    //.FROM(tableName + " left join " + UserDao.TABLE_NAME + " on " + tableName + ".user_id=" + UserDao.TABLE_NAME + ".user_id left join " + FileInfoDao.TABLE_NAME + " on " + tableName + ".file_id=" + FileInfoDao.TABLE_NAME + ".file_id")
                    .FROM(tableName)
                    .LEFT_OUTER_JOIN(UserDao.TABLE_NAME)
                    .LEFT_OUTER_JOIN(FileInfoDao.TABLE_NAME);
            Set<String> wheres = new HashSet<>();
            wheresAll(ownQuery, wheres);
            ProviderUtils.where(ownQuery, wheres, sql);
            String string = sql.toString();
            return string;
        }

        public String selectCount(OwnQuery ownQuery) {
            String string = ProviderUtils.selectCount(tableName, ownQuery, this::wheresAll).toString();
            return string;
        }


        public String selectAll() {
            SQL sql = new SQL()
                    .SELECT(tableName + ".own_id")
                    .SELECT(tableName + ".own_uuid")
                    .SELECT(tableName + ".user_id")
                    .SELECT(tableName + ".file_id")
                    .SELECT(tableName + ".file_length")
                    .SELECT(tableName + ".content_type")
                    .SELECT(tableName + ".file_name")
                    .SELECT(tableName + ".sort")
                    .SELECT(tableName + ".description")
                    .SELECT(tableName + ".create_time")
                    .SELECT(tableName + ".update_time")
                    .SELECT(UserDao.TABLE_NAME + ".username")
                    .SELECT(FileInfoDao.TABLE_NAME + ".md5")
                    //own left join user on own.user_id=user.user_id left join file_info on own.file_id=file_info.file_id
                    //.FROM(tableName + " left join " + UserDao.TABLE_NAME + " on " + tableName + ".user_id=" + UserDao.TABLE_NAME + ".user_id left join " + FileInfoDao.TABLE_NAME + " on " + tableName + ".file_id=" + FileInfoDao.TABLE_NAME + ".file_id")
                    .FROM(tableName)
                    .LEFT_OUTER_JOIN(UserDao.TABLE_NAME)
                    .LEFT_OUTER_JOIN(FileInfoDao.TABLE_NAME);
            String string = sql.toString();
            return string;
        }

        public String selectAllSort(OwnQuery ownQuery) {
            SQL sql = new SQL()
                    .SELECT("distinct " + tableName + ".sort")
                    .FROM(tableName);
            Set<String> wheres = new HashSet<>();
            wheresAll(ownQuery, wheres);
            ProviderUtils.where(ownQuery, wheres, sql);
            String string = sql.toString();
            return string;
        }
    }
}
