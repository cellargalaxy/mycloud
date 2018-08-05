package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import top.cellargalaxy.mycloud.dao.FileInfoDao;
import top.cellargalaxy.mycloud.dao.OwnDao;
import top.cellargalaxy.mycloud.dao.UserDao;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.util.ProviderUtil;
import top.cellargalaxy.mycloud.util.SqlUtil;
import top.cellargalaxy.mycloud.util.StringUtil;

import java.util.*;

import static org.apache.ibatis.type.JdbcType.TIMESTAMP;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
@Mapper
public interface OwnMapper {
	@InsertProvider(type = OwnProviderUtil.class, method = "insert")
	@Options(useGeneratedKeys = true, keyProperty = "ownId")
	int insert(OwnPo ownPo);

	@DeleteProvider(type = OwnProviderUtil.class, method = "delete")
	int delete(OwnPo ownPo);

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
	@SelectProvider(type = OwnProviderUtil.class, method = "selectOne")
	OwnBo selectOne(OwnPo ownPo);

	@ResultMap(value = "ownResult")
	@SelectProvider(type = OwnProviderUtil.class, method = "selectSome")
	List<OwnBo> selectSome(OwnQuery ownQuery);

	@SelectProvider(type = OwnProviderUtil.class, method = "selectCount")
	int selectCount(OwnQuery ownQuery);

	@SelectProvider(type = OwnProviderUtil.class, method = "selectSort")
	List<String> selectSort(OwnQuery ownQuery);

	@UpdateProvider(type = OwnProviderUtil.class, method = "update")
	int update(OwnPo ownPo);

	class OwnProviderUtil {
		private String tableName = OwnDao.TABLE_NAME;
		private String ownId = tableName + ".own_id=#{ownId}";
		private String userId = tableName + ".user_id=#{userId}";
		private String fileId = tableName + ".file_id=#{fileId}";
		private String fileName = tableName + ".file_name=#{fileName}";
		private String sort = tableName + ".sort=#{sort}";
		private String description = tableName + ".description like CONCAT(CONCAT('%', #{description}),'%')";
		private String descriptionSet = tableName + ".description=#{description}";
		private String createTime = tableName + ".create_time=#{createTime,jdbcType=TIMESTAMP}";
		private String updateTime = tableName + ".update_time=#{updateTime,jdbcType=TIMESTAMP}";

		public String insert(OwnPo ownPo) {
			init(ownPo);
			Date date = new Date();
			ownPo.setCreateTime(date);
			ownPo.setUpdateTime(date);

			String string = "insert into " + tableName + "(user_id,file_id,file_name,sort,description,create_time,update_time) " +
					"values(#{userId},#{fileId},#{fileName},#{sort},#{description},#{createTime,jdbcType=TIMESTAMP},#{updateTime,jdbcType=TIMESTAMP})";
			return string;
		}

		public String delete(OwnPo ownPo) {
			return ProviderUtil.delete(tableName, ownPo, this::wheresKey).append(" limit 1").toString();
		}

		public String selectOne(OwnPo ownPo) {
			List<String> selects = new LinkedList<>();
			selects.add(tableName + ".own_id");
			selects.add(tableName + ".user_id");
			selects.add(tableName + ".file_id");
			selects.add(tableName + ".file_name");
			selects.add(tableName + ".sort");
			selects.add(tableName + ".description");
			selects.add(tableName + ".create_time");
			selects.add(tableName + ".update_time");
			selects.add(UserDao.TABLE_NAME + ".username");
			selects.add(FileInfoDao.TABLE_NAME + ".md5");
			selects.add(FileInfoDao.TABLE_NAME + ".file_length");
			selects.add(FileInfoDao.TABLE_NAME + ".content_type");

			Set<String> wheres = new HashSet<>();
			wheres.add(tableName + ".user_id=" + UserDao.TABLE_NAME + ".user_id");
			wheres.add(tableName + ".file_id=" + FileInfoDao.TABLE_NAME + ".file_id");
			wheresKey(ownPo, wheres);

			StringBuilder sql = SqlUtil.createSelectSql(selects, tableName + "," + UserDao.TABLE_NAME + "," + FileInfoDao.TABLE_NAME, wheres);
			String string = sql.append(" limit 1").toString();
			return string;
		}

		public String selectSome(OwnQuery ownQuery) {
			SqlUtil.initPageQuery(ownQuery);
			List<String> selects = new LinkedList<>();
			selects.add(tableName + ".own_id");
			selects.add(tableName + ".user_id");
			selects.add(tableName + ".file_id");
			selects.add(tableName + ".file_name");
			selects.add(tableName + ".sort");
			selects.add(tableName + ".description");
			selects.add(tableName + ".create_time");
			selects.add(tableName + ".update_time");
			selects.add(UserDao.TABLE_NAME + ".username");
			selects.add(FileInfoDao.TABLE_NAME + ".md5");
			selects.add(FileInfoDao.TABLE_NAME + ".file_length");
			selects.add(FileInfoDao.TABLE_NAME + ".content_type");

			Set<String> wheres = new HashSet<>();
			wheres.add(tableName + ".user_id=" + UserDao.TABLE_NAME + ".user_id");
			wheres.add(tableName + ".file_id=" + FileInfoDao.TABLE_NAME + ".file_id");
			wheresAll(ownQuery, wheres);

			StringBuilder sql = SqlUtil.createSelectSql(selects, tableName + "," + UserDao.TABLE_NAME + "," + FileInfoDao.TABLE_NAME, wheres);
			String string = sql.append(" limit #{off},#{len}").toString();
			return string;
		}

		public String selectCount(OwnQuery ownQuery) {
			SqlUtil.initPageQuery(ownQuery);

			List<String> selects = new LinkedList<>();
			selects.add("count(*)");

			Set<String> wheres = new HashSet<>();
			wheres.add(tableName + ".user_id=" + UserDao.TABLE_NAME + ".user_id");
			wheres.add(tableName + ".file_id=" + FileInfoDao.TABLE_NAME + ".file_id");
			wheresAll(ownQuery, wheres);

			StringBuilder sql = SqlUtil.createSelectSql(selects, tableName + "," + UserDao.TABLE_NAME + "," + FileInfoDao.TABLE_NAME, wheres);
			String string = sql.toString();
			return string;
		}

		public String selectSort(OwnQuery ownQuery) {
			List<String> selects = new LinkedList<>();
			selects.add("distinct sort");

			Set<String> wheres = new HashSet<>();
			wheresAll(ownQuery, wheres);

			StringBuilder sql = SqlUtil.createSelectSql(selects, tableName, wheres);
			String string = sql.toString();
			return string;
		}

		public String update(OwnPo ownPo) {
			init(ownPo);
			ownPo.setCreateTime(null);
			ownPo.setUpdateTime(new Date());

			return ProviderUtil.update(tableName, ownPo, ownId, this::sets, this::wheresKey).append(" limit 1").toString();
		}

		private void wheresAll(OwnQuery ownQuery, Set<String> wheres) {
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

		private void wheresKey(OwnPo ownPo, Set<String> wheres) {
			if (ownPo.getOwnId() > 0) {
				wheres.add(ownId);
			} else if (ownPo.getUserId() > 0 && ownPo.getFileId() > 0) {
				wheres.add(userId);
				wheres.add(fileId);
			}
		}

		private void sets(OwnPo ownPo, Set<String> sets) {
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

		private void init(OwnPo ownPo) {
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
