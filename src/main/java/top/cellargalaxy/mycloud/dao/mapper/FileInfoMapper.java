package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.cellargalaxy.mycloud.dao.FileInfoDao;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.util.SqlUtil;
import top.cellargalaxy.mycloud.util.StringUtil;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.apache.ibatis.type.JdbcType.BIGINT;
import static org.apache.ibatis.type.JdbcType.TIMESTAMP;

/**
 * @author cellargalaxy
 * @time 2018/7/3
 */
@Mapper
public interface FileInfoMapper {
	@InsertProvider(type = FileInfoProvider.class, method = "insert")
	@Options(useGeneratedKeys = true, keyProperty = "fileId")
	int insert(FileInfoPo fileInfoPo);

	@DeleteProvider(type = FileInfoProvider.class, method = "delete")
	int delete(FileInfoQuery fileInfoQuery);

	@Results(id = "fileInfoResult", value = {
			@Result(property = "fileId", column = "file_id", id = true),
			@Result(property = "md5", column = "md5"),
			@Result(property = "fileLength", column = "file_length", javaType = long.class, jdbcType = BIGINT),
			@Result(property = "contentType", column = "content_type"),
			@Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = TIMESTAMP)
	})
	@SelectProvider(type = FileInfoProvider.class, method = "selectOne")
	FileInfoBo selectOne(FileInfoQuery fileInfoQuery);

	@ResultMap(value = "fileInfoResult")
	@SelectProvider(type = FileInfoProvider.class, method = "selectSome")
	List<FileInfoBo> selectSome(FileInfoQuery fileInfoQuery);

	@SelectProvider(type = FileInfoProvider.class, method = "selectContentType")
	List<String> selectContentType();

	@UpdateProvider(type = FileInfoProvider.class, method = "update")
	int update(FileInfoPo fileInfoPo);

	class FileInfoProvider {
		private static final Logger logger = LoggerFactory.getLogger(FileInfoProvider.class);
		private static final String TABLE_NAME = FileInfoDao.TABLE_NAME;
		private static final String fileId = TABLE_NAME + ".file_id=#{fileId}";
		private static final String md5 = TABLE_NAME + ".md5=#{md5}";
		private static final String fileLength = TABLE_NAME + ".file_length=#{fileLength}";
		private static final String contentType = TABLE_NAME + ".content_type=#{contentType}";
		private static final String createTime = TABLE_NAME + ".create_time=#{createTime,jdbcType=TIMESTAMP}";

		public static final String insert(FileInfoPo fileInfoPo) {
			fileInfoPo.setCreateTime(new Date());
			String string = "insert into " + TABLE_NAME + "(md5,file_length,content_type,create_time) " +
					"values(#{md5},#{fileLength},#{contentType},#{createTime,jdbcType=TIMESTAMP})";
			logger.debug("insert:{}, sql:{}", fileInfoPo, string);
			return string;
		}

		public static final String delete(FileInfoQuery fileInfoQuery) {
			List<String> wheres = new LinkedList<>();
			wheresAll(fileInfoQuery, wheres);
			StringBuilder sql = SqlUtil.createDeleteSql(TABLE_NAME, wheres);
			String string = sql.toString();
			logger.debug("delete:{}, sql:{}", fileInfoQuery, string);
			return string;
		}

		public static final String selectOne(FileInfoQuery fileInfoQuery) {
			List<String> wheres = new LinkedList<>();
			wheresKey(fileInfoQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(null, TABLE_NAME, wheres);
			String string = sql.append(" limit 1").toString();
			logger.debug("selectOne:{}, sql:{}", fileInfoQuery, string);
			return string;
		}

		public static final String selectSome(FileInfoQuery fileInfoQuery) {
			List<String> wheres = new LinkedList<>();
			wheresAll(fileInfoQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(null, TABLE_NAME, wheres);
			SqlUtil.initPageQuery(fileInfoQuery);
			if (fileInfoQuery.getPageSize() > 0 && fileInfoQuery.getPage() > 0) {
				sql.append(" limit #{off},#{len}");
			}
			String string = sql.toString();
			logger.debug("selectSome:{}, sql:{}", fileInfoQuery, string);
			return string;
		}

		public static final String selectContentType() {
			return "select distinct content_type from " + TABLE_NAME;
		}

		public static final String update(FileInfoPo fileInfoPo) {
			if (FileInfoDao.checkUpdate(fileInfoPo) != null) {
				return "update " + TABLE_NAME + " set file_id=#{fileId} where false";
			}
			fileInfoPo.setCreateTime(null);
			List<String> sets = new LinkedList<>();
			sets(fileInfoPo, sets);
			List<String> wheres = new LinkedList<>();
			wheresKey(fileInfoPo, wheres);
			String string = SqlUtil.createUpdateSql(TABLE_NAME, sets, wheres).append(" limit 1").toString();
			logger.debug("update:{}, sql:{}", fileInfoPo, string);
			return string;
		}

		private static final void wheresAll(FileInfoQuery fileInfoQuery, List<String> wheres) {
			if (fileInfoQuery.getFileId() > 0) {
				wheres.add(fileId);
			}
			if (!StringUtil.isBlank(fileInfoQuery.getMd5())) {
				wheres.add(md5);
			}
			if (fileInfoQuery.getFileLength() > 0) {
				wheres.add(fileLength);
			}
			if (!StringUtil.isBlank(fileInfoQuery.getContentType())) {
				wheres.add(contentType);
			}
			if (fileInfoQuery.getCreateTime() != null) {
				wheres.add(createTime);
			}
		}

		private static final void wheresKey(FileInfoPo fileInfoPo, List<String> wheres) {
			if (fileInfoPo.getFileId() > 0) {
				wheres.add(fileId);
			} else if (!StringUtil.isBlank(fileInfoPo.getMd5())) {
				wheres.add(md5);
			}
		}

		private static final void sets(FileInfoPo fileInfoPo, List<String> sets) {
			if (!StringUtil.isBlank(fileInfoPo.getMd5())) {
				sets.add(md5);
			}
			if (fileInfoPo.getFileLength() > 0) {
				sets.add(fileLength);
			}
			if (!StringUtil.isBlank(fileInfoPo.getContentType())) {
				sets.add(contentType);
			}
		}
	}
}
