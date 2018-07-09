package top.cellargalaxy.newcloud.dao;

import org.apache.ibatis.annotations.*;
import top.cellargalaxy.newcloud.model.po.FileInfoPo;
import top.cellargalaxy.newcloud.model.query.FileInfoQuery;
import top.cellargalaxy.newcloud.util.SqlUtil;
import top.cellargalaxy.newcloud.util.StringUtil;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.apache.ibatis.type.JdbcType.DATE;
import static org.apache.ibatis.type.JdbcType.BIGINT;

/**
 * @author cellargalaxy
 * @time 2018/7/3
 */
@Mapper
public interface FileInfoDao {
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
			@Result(property = "uploadTime", column = "upload_time", javaType = Date.class, jdbcType = DATE)
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


	static String checkInsert(FileInfoPo fileInfoPo) {
		if (StringUtil.isEmpty(fileInfoPo.getFileName())) {
			return "文件名不得为空";
		}
		if (StringUtil.isEmpty(fileInfoPo.getMd5())) {
			return "MD5不得为空";
		}
		if (fileInfoPo.getMd5().length() != 32) {
			return "MD5长度异常,现长度为: " + fileInfoPo.getMd5().length();
		}
		if (fileInfoPo.getFileLength() < 1) {
			return "文件长度不得为空";
		}
		if (StringUtil.isEmpty(fileInfoPo.getContentType())) {
			return "文件类型不得为空";
		}
		if (StringUtil.isEmpty(fileInfoPo.getSort())) {
			return "文件类别不得为空";
		}
		return null;
	}

	static String checkUplate(FileInfoPo fileInfoPo) {
		if (fileInfoPo.getFileId() < 1) {
			return "文件编号不得为空";
		}
		if (fileInfoPo.getCreateTime() == null) {
			return "创建日期不得为空";
		}
		return null;
	}

	class FileInfoDaoProvider {
		public static final String TABLE_NAME = "file_info";

		public String insert(FileInfoPo fileInfoPo) {
			Date date = new Date();
			fileInfoPo.setCreateTime(date);
			fileInfoPo.setUploadTime(date);
			return "insert into " + TABLE_NAME + "(file_name,create_time,md5,file_length,content_type,sort,description,upload_time) " +
					"values(#{fileName},#{createTime,jdbcType=DATE},#{md5},#{fileLength},#{contentType},#{sort},#{description},#{uploadTime,jdbcType=DATE})";
		}

		public String delete(FileInfoQuery fileInfoQuery) {
			List<String> wheres = new LinkedList<>();
			if (fileInfoQuery.getFileId() > 0) {
				wheres.add("file_id=#{fileId}");
			} else if (!StringUtil.isEmpty(fileInfoQuery.getFileName()) && fileInfoQuery.getCreateTime() != null) {
				wheres.add("file_name=#{fileName} and create_time=#{createTime,jdbcType=DATE}");
			}
			StringBuilder sql = SqlUtil.createDeleteSql(TABLE_NAME, wheres);
			return sql.append(" limit 1").toString();
		}

		public String selectOne(FileInfoQuery fileInfoQuery) {
			List<String> wheres = new LinkedList<>();
			if (fileInfoQuery.getFileId() > 0) {
				wheres.add("file_id=#{fileId}");
			} else if (!StringUtil.isEmpty(fileInfoQuery.getFileName()) && fileInfoQuery.getCreateTime() != null) {
				wheres.add("file_name=#{fileName} and create_time=#{createTime,jdbcType=DATE}");
			}
			StringBuilder sql = SqlUtil.createSelectSql(null, TABLE_NAME, wheres);
			return sql.append(" limit 1").toString();
		}

		public String selectSome(FileInfoQuery fileInfoQuery) {
			List<String> wheres = new LinkedList<>();
			if (fileInfoQuery.getFileId() > 0) {
				wheres.add("file_id=#{fileId}");
			}
			if (fileInfoQuery.getFileName() != null && fileInfoQuery.getFileName().length() > 0 && fileInfoQuery.getCreateTime() != null) {
				wheres.add("file_name=#{fileName} and create_time=#{createTime,jdbcType=DATE}");
			}
			if (fileInfoQuery.getContentType() != null && fileInfoQuery.getContentType().length() > 0) {
				wheres.add("content_type=#{contentType}");
			}
			if (fileInfoQuery.getSort() != null && fileInfoQuery.getSort().length() > 0) {
				wheres.add("sort=#{sort}");
			}
			if (fileInfoQuery.getUploadTime() != null) {
				wheres.add("upload_time=#{uploadTime,jdbcType=DATE}");
			}
			StringBuilder sql = SqlUtil.createSelectSql(null, TABLE_NAME, wheres);
			if (fileInfoQuery.getPageSize() > 0 && fileInfoQuery.getPage() > 0) {
				sql.append(" limit 1");//////////
			}
			return sql.toString();
		}

		public String selectContentType() {
			return "select distinct content_type from " + TABLE_NAME;
		}

		public String selectSort() {
			return "select distinct sort from " + TABLE_NAME;
		}

		public String update(FileInfoPo fileInfoPo) {
			fileInfoPo.setUploadTime(new Date());
			if (checkUplate(fileInfoPo) != null) {
				return "update " + TABLE_NAME + " set file_id=#{fileId} where false";
			} else {
				StringBuilder sql = new StringBuilder("update " + TABLE_NAME + " set");
				if (fileInfoPo.getMd5() != null && fileInfoPo.getMd5().length() == 32) {
					sql.append(" md5=#{md5},");
				}
				if (fileInfoPo.getFileLength() > 0) {
					sql.append(" file_length=#{fileLength},");
				}
				if (fileInfoPo.getContentType() != null && fileInfoPo.getContentType().length() > 0) {
					sql.append(" content_type=#{contentType},");
				}
				if (fileInfoPo.getSort() != null && fileInfoPo.getSort().length() > 0) {
					sql.append(" sort=#{sort},");
				}
				if (fileInfoPo.getDescription() != null && fileInfoPo.getDescription().length() > 0) {
					sql.append(" description=#{description},");
				}
				sql.append(" upload_time=#{uploadTime,jdbcType=DATE} where");
				sql = whereMainKey(fileInfoPo, sql);
				return removeEndsWith(sql.toString(), "and") + " limit 1";
			}
		}
	}
}
