package top.cellargalaxy.newcloud.dao.db;

import org.apache.ibatis.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.cellargalaxy.newcloud.model.po.FileInfoPo;
import top.cellargalaxy.newcloud.model.query.FileInfoQuery;
import top.cellargalaxy.newcloud.util.SqlUtil;
import top.cellargalaxy.newcloud.util.StringUtil;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.apache.ibatis.type.JdbcType.BIGINT;
import static org.apache.ibatis.type.JdbcType.DATE;

/**
 * @author cellargalaxy
 * @time 2018/7/3
 */
@Mapper
public interface FileInfoMapper {
	@InsertProvider(type = FileInfoDaoProvider.class, method = "insert")
	int insert(FileInfoPo fileInfoPo);

	@DeleteProvider(type = FileInfoDaoProvider.class, method = "delete")
	int delete(FileInfoQuery fileInfoQuery);

	@Results(id = "fileInfoResult", value = {
			@Result(property = "fileId", column = "file_id", id = true),
			@Result(property = "fileName", column = "file_name"),
			@Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = DATE),
			@Result(property = "md5", column = "md5"),
			@Result(property = "fileLength", column = "file_length", javaType = long.class, jdbcType = BIGINT),
			@Result(property = "contentType", column = "content_type"),
			@Result(property = "userId", column = "user_id"),
			@Result(property = "sort", column = "sort"),
			@Result(property = "description", column = "description"),
			@Result(property = "updateTime", column = "update_time", javaType = Date.class, jdbcType = DATE)
	})
	@SelectProvider(type = FileInfoDaoProvider.class, method = "selectOne")
	FileInfoPo selectOne(FileInfoQuery fileInfoQuery);

	@ResultMap(value = "fileInfoResult")
	@SelectProvider(type = FileInfoDaoProvider.class, method = "selectSome")
	List<FileInfoPo> selectSome(FileInfoQuery fileInfoQuery);

	@SelectProvider(type = FileInfoDaoProvider.class, method = "selectContentType")
	List<String> selectContentType();

	@SelectProvider(type = FileInfoDaoProvider.class, method = "selectSort")
	List<String> selectSort();

	@UpdateProvider(type = FileInfoDaoProvider.class, method = "update")
	int update(FileInfoPo fileInfoPo);

	class FileInfoDaoProvider {
		public static final String TABLE_NAME = "file_info";
		private static final Logger logger = LoggerFactory.getLogger(FileInfoDaoProvider.class);
		private static final String fileId = "file_id=#{fileId}";
		private static final String fileName = "file_name=#{fileName}";
		private static final String createTime = "create_time=#{createTime,jdbcType=DATE}";
		private static final String md5 = "md5=#{md5}";
		private static final String fileLength = "file_length=#{fileLength}";
		private static final String contentType = "content_type=#{contentType}";
		private static final String userId = "user_id=#{userId}";
		private static final String sort = "sort=#{sort}";
		private static final String description = "description like CONCAT(CONCAT('%', #{description}),'%')";
		private static final String descriptionSet = "description=#{description}";
		private static final String updateTime = "update_time=#{updateTime,jdbcType=DATE}";

		public static final String insert(FileInfoPo fileInfoPo) {
			Date date = new Date();
			fileInfoPo.setCreateTime(date);
			fileInfoPo.setUpdateTime(date);
			String string = "insert into " + TABLE_NAME + "(file_name,create_time,md5,file_length,content_type,user_id,sort,description,update_time) " +
					"values(#{fileName},#{createTime,jdbcType=DATE},#{md5},#{fileLength},#{contentType},#{userId},#{sort},#{description},#{updateTime,jdbcType=DATE})";
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

		public static final String selectSort() {
			return "select distinct sort from " + TABLE_NAME;
		}

		public static final String update(FileInfoPo fileInfoPo) {
			fileInfoPo.setCreateTime(null);
			fileInfoPo.setUpdateTime(new Date());
			if (checkUpload(fileInfoPo) != null) {
				return "update " + TABLE_NAME + " set file_id=#{fileId} where false";
			}
			List<String> sets = new LinkedList<>();
			wheresAll(fileInfoPo, sets);
			List<String> wheres = new LinkedList<>();
			wheresKey(fileInfoPo, wheres);
			String string = SqlUtil.createUpdateSql(TABLE_NAME, sets, wheres).append(" limit 1").toString();
			logger.debug("update:{}, sql:{}", fileInfoPo, string);
			return string;
		}

		private static final void setsAll(FileInfoPo fileInfoPo, List<String> sets) {
			if (fileInfoPo.getFileId() > 0) {
				sets.add(fileId);
			}
			if (!StringUtil.isBlank(fileInfoPo.getFileName()) && fileInfoPo.getCreateTime() != null) {
				sets.add(fileName);
				sets.add(createTime);
			}
			if (!StringUtil.isBlank(fileInfoPo.getMd5())) {
				sets.add(md5);
			}
			if (fileInfoPo.getFileLength() > 0) {
				sets.add(fileLength);
			}
			if (!StringUtil.isBlank(fileInfoPo.getContentType())) {
				sets.add(contentType);
			}
			if (fileInfoPo.getUserId() > 0) {
				sets.add(userId);
			}
			if (!StringUtil.isBlank(fileInfoPo.getSort())) {
				sets.add(sort);
			}
			if (fileInfoPo.getUpdateTime() != null) {
				sets.add(updateTime);
			}
			if (!StringUtil.isBlank(fileInfoPo.getDescription())) {
				sets.add(descriptionSet);
			}
		}

		private static final void wheresAll(FileInfoPo fileInfoPo, List<String> wheres) {
			if (fileInfoPo.getFileId() > 0) {
				wheres.add(fileId);
			}
			if (!StringUtil.isBlank(fileInfoPo.getFileName()) && fileInfoPo.getCreateTime() != null) {
				wheres.add(fileName);
				wheres.add(createTime);
			}
			if (!StringUtil.isBlank(fileInfoPo.getMd5())) {
				wheres.add(md5);
			}
			if (fileInfoPo.getFileLength() > 0) {
				wheres.add(fileLength);
			}
			if (!StringUtil.isBlank(fileInfoPo.getContentType())) {
				wheres.add(contentType);
			}
			if (fileInfoPo.getUserId() > 0) {
				wheres.add(userId);
			}
			if (!StringUtil.isBlank(fileInfoPo.getSort())) {
				wheres.add(sort);
			}
			if (fileInfoPo.getUpdateTime() != null) {
				wheres.add(updateTime);
			}
			if (!StringUtil.isBlank(fileInfoPo.getDescription())) {
				wheres.add(description);
			}
		}

		private static final void wheresKey(FileInfoPo fileInfoPo, List<String> wheres) {
			if (fileInfoPo.getFileId() > 0) {
				wheres.add(fileId);
			} else if (!StringUtil.isBlank(fileInfoPo.getFileName()) && fileInfoPo.getCreateTime() != null) {
				wheres.add(fileName);
				wheres.add(createTime);
			}
		}

		public static final String checkInsert(FileInfoPo fileInfoPo) {
			if (StringUtil.isBlank(fileInfoPo.getFileName())) {
				return "文件名不得为空";
			}
			if (StringUtil.isBlank(fileInfoPo.getMd5())) {
				return "MD5不得为空";
			}
			if (fileInfoPo.getMd5().length() != 32) {
				return "MD5长度异常,现长度为: " + fileInfoPo.getMd5().length();
			}
			if (fileInfoPo.getFileLength() < 1) {
				return "文件长度不得为空";
			}
			if (StringUtil.isBlank(fileInfoPo.getContentType())) {
				return "文件类型不得为空";
			}
			if (StringUtil.isBlank(fileInfoPo.getSort())) {
				return "文件类别不得为空";
			}
			return null;
		}

		public static final String checkUpload(FileInfoPo fileInfoPo) {
			if (fileInfoPo.getFileId() < 1) {
				return "文件编号不得为空";
			}
			return null;
		}
	}
}
