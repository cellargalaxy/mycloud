package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.cellargalaxy.mycloud.dao.FileInfoDao;
import top.cellargalaxy.mycloud.dao.OwnDao;
import top.cellargalaxy.mycloud.dao.UserDao;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.util.SqlUtil;
import top.cellargalaxy.mycloud.util.StringUtil;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.apache.ibatis.type.JdbcType.TIMESTAMP;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
@Mapper
public interface OwnMapper {
	@InsertProvider(type = OwnProvider.class, method = "insert")
	@Options(useGeneratedKeys = true, keyProperty = "ownId")
	int insert(OwnPo ownPo);

	@DeleteProvider(type = OwnProvider.class, method = "delete")
	int delete(OwnQuery ownQuery);

	@Results(id = "ownResult", value = {
			@Result(property = "ownId", column = "own_id", id = true),
			@Result(property = "userId", column = "user_id"),
			@Result(property = "fileId", column = "file_id"),
			@Result(property = "fileName", column = "file_name"),
			@Result(property = "sort", column = "sort"),
			@Result(property = "description", column = "description"),
			@Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = TIMESTAMP),
			@Result(property = "updateTime", column = "update_time", javaType = Date.class, jdbcType = TIMESTAMP),
			@Result(property = "username", column = "username"),
			@Result(property = "md5", column = "md5"),
			@Result(property = "fileLength", column = "file_length"),
			@Result(property = "contentType", column = "content_type")
	})
	@SelectProvider(type = OwnProvider.class, method = "selectOne")
	OwnBo selectOne(OwnQuery ownQuery);

	@ResultMap(value = "ownResult")
	@SelectProvider(type = OwnProvider.class, method = "selectSome")
	List<OwnBo> selectSome(OwnQuery ownQuery);

	@SelectProvider(type = OwnProvider.class, method = "selectCount")
	int selectCount(OwnQuery ownQuery);

	@SelectProvider(type = OwnProvider.class, method = "selectSort")
	List<String> selectSort(OwnQuery ownQuery);

	@UpdateProvider(type = OwnProvider.class, method = "update")
	int update(OwnPo ownPo);

	class OwnProvider {
		private static final String TABLE_NAME = OwnDao.TABLE_NAME;
		private static final String ownId = TABLE_NAME + ".own_id=#{ownId}";
		private static final String userId = TABLE_NAME + ".user_id=#{userId}";
		private static final String fileId = TABLE_NAME + ".file_id=#{fileId}";
		private static final String fileName = TABLE_NAME + ".file_name=#{fileName}";
		private static final String sort = TABLE_NAME + ".sort=#{sort}";
		private static final String description = TABLE_NAME + ".description like CONCAT(CONCAT('%', #{description}),'%')";
		private static final String descriptionSet = TABLE_NAME + ".description=#{description}";
		private static final String createTime = TABLE_NAME + ".create_time=#{createTime,jdbcType=TIMESTAMP}";
		private static final String updateTime = TABLE_NAME + ".update_time=#{updateTime,jdbcType=TIMESTAMP}";

		public static final String insert(OwnPo ownPo) {
			init(ownPo);
			Date date = new Date();
			ownPo.setCreateTime(date);
			ownPo.setUpdateTime(date);
			String string = "insert into " + TABLE_NAME + "(user_id,file_id,file_name,sort,description,create_time,update_time) " +
					"values(#{userId},#{fileId},#{fileName},#{sort},#{description},#{createTime,jdbcType=TIMESTAMP},#{updateTime,jdbcType=TIMESTAMP})";
			return string;
		}

		public static final String delete(OwnQuery ownQuery) {
			List<String> wheres = new LinkedList<>();
			wheresAll(ownQuery, wheres);
			StringBuilder sql = SqlUtil.createDeleteSql(TABLE_NAME, wheres);
			String string = sql.toString();
			return string;
		}

		public static final String selectOne(OwnQuery ownQuery) {
			List<String> selects = new LinkedList<>();
			selects.add(TABLE_NAME + ".own_id");
			selects.add(TABLE_NAME + ".user_id");
			selects.add(TABLE_NAME + ".file_id");
			selects.add(TABLE_NAME + ".file_name");
			selects.add(TABLE_NAME + ".sort");
			selects.add(TABLE_NAME + ".description");
			selects.add(TABLE_NAME + ".create_time");
			selects.add(TABLE_NAME + ".update_time");
			selects.add(UserDao.TABLE_NAME + ".username");
			selects.add(FileInfoDao.TABLE_NAME + ".md5");
			selects.add(FileInfoDao.TABLE_NAME + ".file_length");
			selects.add(FileInfoDao.TABLE_NAME + ".content_type");
			List<String> wheres = new LinkedList<>();
			wheres.add(TABLE_NAME + ".user_id=" + UserDao.TABLE_NAME + ".user_id");
			wheres.add(TABLE_NAME + ".file_id=" + FileInfoDao.TABLE_NAME + ".file_id");
			wheresKey(ownQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(selects, TABLE_NAME + "," + UserDao.TABLE_NAME + "," + FileInfoDao.TABLE_NAME, wheres);
			String string = sql.append(" limit 1").toString();
			return string;
		}

		public static final String selectSome(OwnQuery ownQuery) {
			SqlUtil.initPageQuery(ownQuery);
			List<String> selects = new LinkedList<>();
			selects.add(TABLE_NAME + ".own_id");
			selects.add(TABLE_NAME + ".user_id");
			selects.add(TABLE_NAME + ".file_id");
			selects.add(TABLE_NAME + ".file_name");
			selects.add(TABLE_NAME + ".sort");
			selects.add(TABLE_NAME + ".description");
			selects.add(TABLE_NAME + ".create_time");
			selects.add(TABLE_NAME + ".update_time");
			selects.add(UserDao.TABLE_NAME + ".username");
			selects.add(FileInfoDao.TABLE_NAME + ".md5");
			selects.add(FileInfoDao.TABLE_NAME + ".file_length");
			selects.add(FileInfoDao.TABLE_NAME + ".content_type");
			List<String> wheres = new LinkedList<>();
			wheres.add(TABLE_NAME + ".user_id=" + UserDao.TABLE_NAME + ".user_id");
			wheres.add(TABLE_NAME + ".file_id=" + FileInfoDao.TABLE_NAME + ".file_id");
			wheresAll(ownQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(selects, TABLE_NAME + "," + UserDao.TABLE_NAME + "," + FileInfoDao.TABLE_NAME, wheres);
			String string = sql.append(" limit #{off},#{len}").toString();
			return string;
		}

		public static final String selectCount(OwnQuery ownQuery) {
			SqlUtil.initPageQuery(ownQuery);
			List<String> selects = new LinkedList<>();
			selects.add("count(*)");
			List<String> wheres = new LinkedList<>();
			wheres.add(TABLE_NAME + ".user_id=" + UserDao.TABLE_NAME + ".user_id");
			wheres.add(TABLE_NAME + ".file_id=" + FileInfoDao.TABLE_NAME + ".file_id");
			wheresAll(ownQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(selects, TABLE_NAME + "," + UserDao.TABLE_NAME + "," + FileInfoDao.TABLE_NAME, wheres);
			String string = sql.toString();
			return string;
		}

		public static final String selectSort(OwnQuery ownQuery) {
			List<String> selects = new LinkedList<>();
			selects.add("distinct sort");
			List<String> wheres = new LinkedList<>();
			wheresAll(ownQuery, wheres);
			StringBuilder sql = SqlUtil.createSelectSql(selects, TABLE_NAME, wheres);
			String string = sql.toString();
			return string;
		}

		public static final String update(OwnPo ownPo) {
			init(ownPo);
			ownPo.setCreateTime(null);
			ownPo.setUpdateTime(new Date());
			List<String> sets = new LinkedList<>();
			sets(ownPo, sets);
			if (sets.size() == 0) {
				return "update " + TABLE_NAME + " set own_id=#{ownId} where false";
			}
			List<String> wheres = new LinkedList<>();
			wheresKey(ownPo, wheres);
			String string = SqlUtil.createUpdateSql(TABLE_NAME, sets, wheres).append(" limit 1").toString();
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
			} else if (ownPo.getUserId() > 0 && ownPo.getFileId() > 0) {
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

		private static final void init(OwnPo ownPo) {
			if (ownPo.getFileName() != null) {
				ownPo.setFileName(ownPo.getFileName().trim());
			}
			if (ownPo.getSort() != null) {
				ownPo.setSort(ownPo.getSort().trim());
			}
			if (ownPo.getDescription() != null) {
				ownPo.setDescription(ownPo.getDescription().trim());
			}
		}
	}
}
