package top.cellargalaxy.newcloud.dao.db;

import org.apache.ibatis.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.cellargalaxy.newcloud.dao.FileInfoDao;
import top.cellargalaxy.newcloud.dao.OwnDao;
import top.cellargalaxy.newcloud.dao.UserDao;
import top.cellargalaxy.newcloud.model.bo.OwnBo;
import top.cellargalaxy.newcloud.model.po.OwnPo;
import top.cellargalaxy.newcloud.model.query.OwnQuery;
import top.cellargalaxy.newcloud.util.SqlUtil;
import top.cellargalaxy.newcloud.util.StringUtil;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.apache.ibatis.type.JdbcType.DATE;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
@Mapper
public interface OwnMapper {
	@InsertProvider(type = OwnProvider.class, method = "insert")
	int insert(OwnPo ownPo);

	@DeleteProvider(type = OwnProvider.class, method = "delete")
	int delete(OwnQuery ownQuery);

	@Results(id = "ownResult", value = {
			@Result(property = "ownId", column = "own_id", id = true),
			@Result(property = "user_id", column = "user_id"),
			@Result(property = "fileId", column = "file_id"),
			@Result(property = "fileName", column = "file_name"),
			@Result(property = "sort", column = "sort"),
			@Result(property = "description", column = "description"),
			@Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = DATE),
			@Result(property = "updateTime", column = "update_time", javaType = Date.class, jdbcType = DATE),
			@Result(property = "userName", column = "user_name"),
			@Result(property = "md5", column = "md5"),
			@Result(property = "fileLength", column = "file_length"),
			@Result(property = "contentType", column = "content_type")
	})
	@SelectProvider(type = OwnProvider.class, method = "selectOne")
	OwnBo selectOne(OwnQuery ownQuery);

	@ResultMap(value = "ownResult")
	@SelectProvider(type = OwnProvider.class, method = "selectSome")
	List<OwnBo> selectSome(OwnQuery ownQuery);

	@SelectProvider(type = OwnProvider.class, method = "selectSort")
	List<String> selectSort(int userId);

	@UpdateProvider(type = OwnProvider.class, method = "update")
	int update(OwnPo ownPo);

	class OwnProvider {
		private static final Logger logger = LoggerFactory.getLogger(OwnProvider.class);
		private static final String TABLE_NAME = OwnDao.TABLE_NAME;
		private static final String ownId = "own_id=#{ownId}";
		private static final String userId = "user_id=#{userId}";
		private static final String fileId = "file_id=#{fileId}";
		private static final String fileName = "file_name=#{fileName}";
		private static final String sort = "sort=#{sort}";
		private static final String description = "description like CONCAT(CONCAT('%', #{description}),'%')";
		private static final String descriptionSet = "description=#{description}";
		private static final String createTime = "create_time=#{createTime,jdbcType=DATE}";
		private static final String updateTime = "update_time=#{updateTime,jdbcType=DATE}";

		public static final String insert(OwnPo ownPo) {
			Date date = new Date();
			ownPo.setCreateTime(date);
			ownPo.setUpdateTime(date);
			String string = "insert into " + TABLE_NAME + "(own_id,user_id,file_id,file_name,sort,description,create_time,update_time) " +
					"values(#{ownId},#{userId},#{fileId},#{fileName},#{sort},#{description},#{createTime,jdbcType=DATE},#{updateTime,jdbcType=DATE})";
			logger.debug("insert:{}, sql:{}", ownPo, string);
			return string;
		}

		public static final String delete(OwnQuery ownQuery) {
			List<String> wheres = new LinkedList<>();
			wheresAll(ownQuery, wheres);
			StringBuilder sql = SqlUtil.createDeleteSql(TABLE_NAME, wheres);
			String string = sql.toString();
			logger.debug("delete:{}, sql:{}", ownQuery, string);
			return string;
		}

		public static final String selectOne(OwnQuery ownQuery) {
			List<String> selects = new LinkedList<>();
			selects.add("own_id");
			selects.add("user_id");
			selects.add("file_id");
			selects.add("file_name");
			selects.add("sort");
			selects.add("description");
			selects.add("create_time");
			selects.add("update_time");
			selects.add("user_name");
			selects.add("md5");
			selects.add("file_length");
			selects.add("content_type");
			List<String> wheres = new LinkedList<>();
			wheresKey(ownQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(selects, TABLE_NAME + "," + UserDao.TABLE_NAME + "," + FileInfoDao.TABLE_NAME, wheres);
			String string = sql.append(" limit 1").toString();
			logger.debug("selectOne:{}, sql:{}", ownQuery, string);
			return string;
		}

		public static final String selectSome(OwnQuery ownQuery) {
			List<String> selects = new LinkedList<>();
			selects.add("own_id");
			selects.add("user_id");
			selects.add("file_id");
			selects.add("file_name");
			selects.add("sort");
			selects.add("description");
			selects.add("create_time");
			selects.add("update_time");
			selects.add("user_name");
			selects.add("md5");
			selects.add("file_length");
			selects.add("content_type");
			List<String> wheres = new LinkedList<>();
			wheresAll(ownQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(selects, TABLE_NAME + "," + UserDao.TABLE_NAME + "," + FileInfoDao.TABLE_NAME, wheres);
			SqlUtil.initPageQuery(ownQuery);
			if (ownQuery.getPageSize() > 0 && ownQuery.getPage() > 0) {
				sql.append(" limit #{off},#{len}");
			}
			String string = sql.toString();
			logger.debug("selectSome:{}, sql:{}", ownQuery, string);
			return string;
		}

		public static final String selectSort(int userId) {
			return "select distinct sort from " + TABLE_NAME + " where user_id=" + userId;
		}

		public static final String update(OwnPo ownPo) {
			ownPo.setCreateTime(null);
			ownPo.setUpdateTime(new Date());
			if (OwnDao.checkUpdate(ownPo) != null) {
				return "update " + TABLE_NAME + " set own_id=#{ownId} where false";
			}
			List<String> sets = new LinkedList<>();
			sets(ownPo, sets);
			List<String> wheres = new LinkedList<>();
			wheresKey(ownPo, wheres);
			String string = SqlUtil.createUpdateSql(TABLE_NAME, sets, wheres).append(" limit 1").toString();
			logger.debug("update:{}, sql:{}", ownPo, string);
			return string;
		}

		private static final void wheresAll(OwnQuery ownQuery, List<String> wheres) {
			if (ownQuery.getOwnId() > 0) {
				wheres.add(ownId);
			}
			if (ownQuery.getUserId() > 0) {
				wheres.add(userId);
			}
			if (ownQuery.getFileId() > 0) {
				wheres.add(fileId);
			}
			if (!StringUtil.isBlank(ownQuery.getFileName())) {
				wheres.add(fileName);
			}
			if (!StringUtil.isBlank(ownQuery.getSort())) {
				wheres.add(sort);
			}
			if (!StringUtil.isBlank(ownQuery.getDescription())) {
				wheres.add(description);
			}
			if (ownQuery.getCreateTime() != null) {
				wheres.add(createTime);
			}
			if (ownQuery.getUpdateTime() != null) {
				wheres.add(updateTime);
			}
		}

		private static final void wheresKey(OwnPo ownPo, List<String> wheres) {
			if (ownPo.getOwnId() > 0) {
				wheres.add(ownId);
			}
			if (ownPo.getUserId() > 0 && ownPo.getFileId() > 0) {
				wheres.add(userId);
				wheres.add(fileId);
			}
		}

		private static final void sets(OwnPo ownPo, List<String> sets) {
			if (ownPo.getUserId() > 0) {
				sets.add(userId);
			}
			if (ownPo.getFileId() > 0) {
				sets.add(fileId);
			}
			if (!StringUtil.isBlank(ownPo.getFileName())) {
				sets.add(fileName);
			}
			if (!StringUtil.isBlank(ownPo.getSort())) {
				sets.add(sort);
			}
			if (!StringUtil.isBlank(ownPo.getDescription())) {
				sets.add(descriptionSet);
			}
			if (ownPo.getUpdateTime() != null) {
				sets.add(updateTime);
			}
		}
	}
}
