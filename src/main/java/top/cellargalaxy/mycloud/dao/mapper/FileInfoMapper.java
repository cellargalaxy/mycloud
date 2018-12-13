package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import top.cellargalaxy.mycloud.dao.FileInfoDao;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.util.StringUtils;
import top.cellargalaxy.mycloud.util.dao.IDao;
import top.cellargalaxy.mycloud.util.dao.ProviderUtils;
import top.cellargalaxy.mycloud.util.dao.SqlUtils;

import java.util.List;
import java.util.Set;

/**
 * @author cellargalaxy
 * @time 2018/10/25
 */
@Mapper
public interface FileInfoMapper extends IDao<FileInfoPo, FileInfoBo, FileInfoQuery> {
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    @InsertProvider(type = FileInfoProvider.class, method = "insert")
    int insert(FileInfoPo fileInfoPo);

    @DeleteProvider(type = FileInfoProvider.class, method = "delete")
    int delete(FileInfoPo fileInfoPo);

    @UpdateProvider(type = FileInfoProvider.class, method = "update")
    int update(FileInfoPo fileInfoPo);

    @Results(id = "fileInfoResults", value = {
            @Result(property = "fileId", column = "file_id"),
            @Result(property = "md5", column = "md5"),
            @Result(property = "fileLength", column = "file_length"),
            @Result(property = "contentType", column = "content_type"),
            @Result(property = "createTime", column = "create_time"),
    })
    @SelectProvider(type = FileInfoProvider.class, method = "selectOne")
    FileInfoBo selectOne(FileInfoPo fileInfoPo);

    @ResultMap(value = "fileInfoResults")
    @SelectProvider(type = FileInfoProvider.class, method = "selectPageSome")
    List<FileInfoBo> selectPageSome(FileInfoQuery fileInfoQuery);

    @ResultMap(value = "fileInfoResults")
    @SelectProvider(type = FileInfoProvider.class, method = "selectAllSome")
    List<FileInfoBo> selectAllSome(FileInfoQuery fileInfoQuery);

    @SelectProvider(type = FileInfoProvider.class, method = "selectCount")
    int selectCount(FileInfoQuery fileInfoQuery);

    @ResultMap(value = "fileInfoResults")
    @SelectProvider(type = FileInfoProvider.class, method = "selectAll")
    List<FileInfoBo> selectAll();

    @SelectProvider(type = FileInfoProvider.class, method = "selectAllContentType")
    List<String> selectAllContentType();

    class FileInfoProvider /*implements IProvider<FileInfoPo, FileInfoQuery>*/ {
        private String tableName = FileInfoDao.TABLE_NAME;

        public void wheresKey(FileInfoPo fileInfoPo, Set<String> wheres) {
            if (fileInfoPo.getFileId() > 0) {
                wheres.add("fileId");
                return;
            }
            if (!StringUtils.isBlank(fileInfoPo.getMd5())) {
                wheres.add("md5");
                return;
            }
        }


        public void wheresAll(FileInfoQuery fileInfoQuery, Set<String> wheres) {
            if (fileInfoQuery.getFileId() > 0) {
                wheres.add("fileId");
            }
            if (!StringUtils.isBlank(fileInfoQuery.getMd5())) {
                wheres.add("md5");
            }
            if (!StringUtils.isBlank(fileInfoQuery.getContentType())) {
                wheres.add("contentType");
            }
        }


        public void sets(FileInfoPo fileInfoPo, Set<String> sets) {

        }

        public String insert(FileInfoPo fileInfoPo) {
            String string = ProviderUtils.insert(tableName, fileInfoPo).toString();
            return string;
        }


        public String delete(FileInfoPo fileInfoPo) {
            String string = ProviderUtils.limitOne(ProviderUtils.delete(tableName, fileInfoPo, this::wheresKey)).toString();
            return string;
        }


        public String update(FileInfoPo fileInfoPo) {
            String string = ProviderUtils.limitOne(ProviderUtils.update(tableName, fileInfoPo, "fileId", this::sets, this::wheresKey)).toString();
            return string;
        }


        public String selectOne(FileInfoPo fileInfoPo) {
            String string = ProviderUtils.limitOne(ProviderUtils.select(tableName, fileInfoPo, this::wheresKey)).toString();
            return string;
        }


        public String selectPageSome(FileInfoQuery fileInfoQuery) {
            SqlUtils.initPageQuery(fileInfoQuery);
            String string = ProviderUtils.limitSome(ProviderUtils.select(tableName, fileInfoQuery, this::wheresAll)).toString();
            return string;
        }


        public String selectAllSome(FileInfoQuery fileInfoQuery) {
            String string = ProviderUtils.select(tableName, fileInfoQuery, this::wheresAll).toString();
            return string;
        }


        public String selectCount(FileInfoQuery fileInfoQuery) {
            String string = ProviderUtils.selectCount(tableName, fileInfoQuery, this::wheresAll).toString();
            return string;
        }


        public String selectAll() {
            String string = ProviderUtils.selectAll(tableName).toString();
            return string;
        }

        public String selectAllContentType() {
            SQL sql = new SQL()
                    .SELECT("distinct " + tableName + ".content_type")
                    .FROM(tableName);
            String string = sql.toString();
            return string;
        }
    }
}
