package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import top.cellargalaxy.mycloud.dao.AbstractDao;
import top.cellargalaxy.mycloud.dao.FileInfoDao;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.util.ProviderUtil;
import top.cellargalaxy.mycloud.util.SqlUtil;
import top.cellargalaxy.mycloud.util.StringUtil;

import javax.sql.DataSource;
import java.util.*;

import static org.apache.ibatis.type.JdbcType.TIMESTAMP;

/**
 * @author cellargalaxy
 * @time 2018/10/25
 */
@ConditionalOnBean(DataSource.class)
@Mapper
public interface FileInfoMapper extends AbstractDao<FileInfoPo, FileInfoBo, FileInfoQuery> {
	@Options(useGeneratedKeys = true, keyProperty = "fileId")
	@InsertProvider(type = FileInfoProvider.class, method = "insert")
	int insert(FileInfoPo fileInfoPo);

	@DeleteProvider(type = FileInfoProvider.class, method = "delete")
	int delete(FileInfoPo fileInfoPo);

	@UpdateProvider(type = FileInfoProvider.class, method = "update")
	int update(FileInfoPo fileInfoPo);

	@Results(id = "fileInfoResults", value = {
			@Result(property = "fileId", column = "file_id", id = true),
			@Result(property = "md5", column = "md5"),
			@Result(property = "fileLength", column = "file_length"),
			@Result(property = "contentType", column = "content_type"),
			@Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = TIMESTAMP),
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

	class FileInfoProvider /*implements AbstractProvider<FileInfoPo, FileInfoQuery>*/ {
		private String tableName = FileInfoDao.TABLE_NAME;
		private String fileId = tableName + ".file_id=#{fileId}";
		private String md5 = tableName + ".md5=#{md5}";
		private String fileLength = tableName + ".file_length=#{fileLength}";
		private String contentType = tableName + ".content_type=#{contentType}";
		private String createTime = tableName + ".create_time=#{createTime,jdbcType=TIMESTAMP}";

		private List<String> createSelects() {
			List<String> selects = new LinkedList<>();
			selects.add(tableName + ".file_id");
			selects.add(tableName + ".md5");
			selects.add(tableName + ".file_length");
			selects.add(tableName + ".content_type");
			selects.add(tableName + ".create_time");
			return selects;
		}

		public void wheresKey(FileInfoPo fileInfoPo, Set<String> wheres) {
			if (fileInfoPo.getFileId() > 0) {
				wheres.add(fileId);
				return;
			}
			if (!StringUtil.isBlank(fileInfoPo.getMd5())) {
				wheres.add(md5);
				return;
			}
			wheres.add(fileId);
		}


		public void wheresAll(FileInfoQuery fileInfoQuery, Set<String> wheres) {
			if (fileInfoQuery.getFileId() > 0) {
				wheres.add(fileId);
			}
			if (!StringUtil.isBlank(fileInfoQuery.getMd5())) {
				wheres.add(md5);
			}
			if (!StringUtil.isBlank(fileInfoQuery.getContentType())) {
				wheres.add(contentType);
			}
		}


		public void sets(FileInfoPo fileInfoPo, Set<String> sets) {

		}


		public String insert(FileInfoPo fileInfoPo) {
			String string = "insert into " + tableName + "(md5,file_length,content_type,create_time) " +
					"values(#{md5},#{fileLength},#{contentType},#{createTime,jdbcType=TIMESTAMP})";
			return string;
		}


		public String delete(FileInfoPo fileInfoPo) {
			return ProviderUtil.delete(tableName, fileInfoPo, this::wheresKey).append(" limit 1").toString();
		}


		public String update(FileInfoPo fileInfoPo) {
			return ProviderUtil.update(tableName, fileInfoPo, fileId, this::sets, this::wheresKey).append(" limit 1").toString();
		}


		public String selectOne(FileInfoPo fileInfoPo) {
			List<String> selects = createSelects();

			Set<String> wheres = new HashSet<>();
			wheresKey(fileInfoPo, wheres);

			StringBuilder sql = SqlUtil.createSelectSql(selects, tableName, wheres);
			String string = sql.append(" limit 1").toString();
			return string;
		}


		public String selectPageSome(FileInfoQuery fileInfoQuery) {
			SqlUtil.initPageQuery(fileInfoQuery);
			return selectSome(fileInfoQuery).append(" limit #{off},#{len}").toString();
		}


		public String selectAllSome(FileInfoQuery fileInfoQuery) {
			return selectSome(fileInfoQuery).toString();
		}

		private StringBuilder selectSome(FileInfoQuery fileInfoQuery) {
			List<String> selects = createSelects();

			Set<String> wheres = new HashSet<>();
			wheresAll(fileInfoQuery, wheres);

			StringBuilder sql = SqlUtil.createSelectSql(selects, tableName, wheres);
			return sql;
		}

		public String selectCount(FileInfoQuery fileInfoQuery) {
			return ProviderUtil.selectCount(tableName, fileInfoQuery, this::wheresAll).toString();
		}


		public String selectAll() {
			List<String> selects = createSelects();

			Set<String> wheres = Collections.EMPTY_SET;

			StringBuilder sql = SqlUtil.createSelectSql(selects, tableName, wheres);
			String string = sql.toString();
			return string;
		}

		public String selectAllContentType() {
			List<String> selects = Arrays.asList("distinct " + tableName + ".content_type");

			Set<String> wheres = Collections.EMPTY_SET;

			StringBuilder sql = SqlUtil.createSelectSql(selects, tableName, wheres);
			String string = sql.toString();
			return string;
		}
	}
}
