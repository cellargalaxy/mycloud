package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import top.cellargalaxy.mycloud.dao.AbstractDao;
import top.cellargalaxy.mycloud.dao.FileInfoDao;
import top.cellargalaxy.mycloud.dao.OwnDao;
import top.cellargalaxy.mycloud.dao.UserDao;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
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
public interface OwnMapper extends AbstractDao<OwnPo, OwnBo, OwnQuery> {
	@Options(useGeneratedKeys = true, keyProperty = "ownId")
	@InsertProvider(type = OwnProvider.class, method = "insert")
	int insert(OwnPo ownPo);

	@DeleteProvider(type = OwnProvider.class, method = "delete")
	int delete(OwnPo ownPo);

	@UpdateProvider(type = OwnProvider.class, method = "update")
	int update(OwnPo ownPo);

	@Results(id = "ownResults", value = {
			@Result(property = "ownId", column = "own_id", id = true),
			@Result(property = "ownUuid", column = "own_uuid"),
			@Result(property = "userId", column = "user_id"),
			@Result(property = "fileId", column = "file_id"),
			@Result(property = "fileLength", column = "file_length"),
			@Result(property = "contentType", column = "content_type"),
			@Result(property = "fileName", column = "file_name"),
			@Result(property = "sort", column = "sort"),
			@Result(property = "description", column = "description"),
			@Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = TIMESTAMP),
			@Result(property = "updateTime", column = "update_time", javaType = Date.class, jdbcType = TIMESTAMP),
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

	class OwnProvider /*implements AbstractProvider<OwnPo, OwnQuery>*/ {
		private String tableName = OwnDao.TABLE_NAME;
		private String ownId = tableName + ".own_id=#{ownId}";
		private String ownUuid = tableName + ".own_uuid=#{ownUuid}";
		private String userId = tableName + ".user_id=#{userId}";
		private String fileId = tableName + ".file_id=#{fileId}";
		private String fileLength = tableName + ".file_length=#{fileLength}";
		private String contentType = tableName + ".content_type=#{contentType}";
		private String fileName = tableName + ".file_name=#{fileName}";
		private String sort = tableName + ".sort=#{sort}";
		private String description = tableName + ".description=#{description}";
		private String createTime = tableName + ".create_time=#{createTime,jdbcType=TIMESTAMP}";
		private String updateTime = tableName + ".update_time=#{updateTime,jdbcType=TIMESTAMP}";

		private List<String> createSelects() {
			List<String> selects = new LinkedList<>();
			selects.add(tableName + ".own_id");
			selects.add(tableName + ".own_uuid");
			selects.add(tableName + ".user_id");
			selects.add(tableName + ".file_id");
			selects.add(tableName + ".file_length");
			selects.add(tableName + ".content_type");
			selects.add(tableName + ".file_name");
			selects.add(tableName + ".sort");
			selects.add(tableName + ".description");
			selects.add(tableName + ".create_time");
			selects.add(tableName + ".update_time");
			selects.add(UserDao.TABLE_NAME + ".username");
			selects.add(FileInfoDao.TABLE_NAME + ".md5");
			return selects;
		}

		public void wheresKey(OwnPo ownPo, Set<String> wheres) {
			if (ownPo.getUserId() > 0) {
				wheres.add(userId);
			}
			if (!StringUtil.isBlank(ownPo.getOwnUuid())) {
				wheres.add(ownUuid);
				return;
			}
			wheres.add(ownId);
		}


		public void wheresAll(OwnQuery ownQuery, Set<String> wheres) {
			if (ownQuery.getOwnId() > 0) {
				wheres.add(ownId);
			}
			if (!StringUtil.isBlank(ownQuery.getOwnUuid())) {
				wheres.add(ownUuid);
			}
			if (ownQuery.getUserId() > 0) {
				wheres.add(userId);
			}
			if (ownQuery.getFileId() > 0) {
				wheres.add(fileId);
			}
			if (!StringUtil.isBlank(ownQuery.getContentType())) {
				wheres.add(contentType);
			}
			if (!StringUtil.isBlank(ownQuery.getSort())) {
				wheres.add(sort);
			}
		}


		public void sets(OwnPo ownPo, Set<String> sets) {
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
				sets.add(description);
			}
			if (ownPo.getUpdateTime() != null) {
				sets.add(updateTime);
			}
		}


		public String insert(OwnPo ownPo) {
			String string = "insert into " + tableName + "(own_uuid,user_id,file_id,file_length,content_type,file_name,sort,description,create_time,update_time) " +
					"values(#{ownUuid},#{userId},#{fileId},#{fileLength},#{contentType},#{fileName},#{sort},#{description},#{createTime,jdbcType=TIMESTAMP},#{updateTime,jdbcType=TIMESTAMP})";
			return string;
		}


		public String delete(OwnPo ownPo) {
			return ProviderUtil.delete(tableName, ownPo, this::wheresKey).append(" limit 1").toString();
		}


		public String update(OwnPo ownPo) {
			return ProviderUtil.update(tableName, ownPo, fileId, this::sets, this::wheresKey).append(" limit 1").toString();
		}


		public String selectOne(OwnPo ownPo) {
			List<String> selects = createSelects();

			Set<String> wheres = new HashSet<>();
			wheresKey(ownPo, wheres);

			//own left join user on own.user_id=user.user_id left join file_info on own.file_id=file_info.file_id
			StringBuilder sql = SqlUtil.createSelectSql(selects, tableName + " left join " + UserDao.TABLE_NAME + " on " + tableName + ".user_id=" + UserDao.TABLE_NAME + ".user_id left join " + FileInfoDao.TABLE_NAME + " on " + tableName + ".file_id=" + FileInfoDao.TABLE_NAME + ".file_id", wheres);
			String string = sql.append(" limit 1").toString();
			return string;
		}


		public String selectPageSome(OwnQuery ownQuery) {
			SqlUtil.initPageQuery(ownQuery);
			return selectSome(ownQuery).append(" limit #{off},#{len}").toString();
		}


		public String selectAllSome(OwnQuery ownQuery) {
			return selectSome(ownQuery).toString();
		}


		private StringBuilder selectSome(OwnQuery ownQuery) {
			List<String> selects = createSelects();

			Set<String> wheres = new HashSet<>();
			wheresAll(ownQuery, wheres);

			//own left join user on own.user_id=user.user_id left join file_info on own.file_id=file_info.file_id
			StringBuilder sql = SqlUtil.createSelectSql(selects, tableName + " left join " + UserDao.TABLE_NAME + " on " + tableName + ".user_id=" + UserDao.TABLE_NAME + ".user_id left join " + FileInfoDao.TABLE_NAME + " on " + tableName + ".file_id=" + FileInfoDao.TABLE_NAME + ".file_id", wheres);
			return sql;
		}

		public String selectCount(OwnQuery ownQuery) {
			return ProviderUtil.selectCount(tableName, ownQuery, this::wheresAll).toString();
		}


		public String selectAll() {
			List<String> selects = createSelects();

			Set<String> wheres = Collections.EMPTY_SET;

			//own left join user on own.user_id=user.user_id left join file_info on own.file_id=file_info.file_id
			StringBuilder sql = SqlUtil.createSelectSql(selects, tableName + " left join " + UserDao.TABLE_NAME + " on " + tableName + ".user_id=" + UserDao.TABLE_NAME + ".user_id left join " + FileInfoDao.TABLE_NAME + " on " + tableName + ".file_id=" + FileInfoDao.TABLE_NAME + ".file_id", wheres);
			String string = sql.toString();
			return string;
		}

		public String selectAllSort(OwnQuery ownQuery) {
			List<String> selects = Arrays.asList("distinct " + tableName + ".sort");

			Set<String> wheres = new HashSet<>();
			wheresAll(ownQuery, wheres);

			StringBuilder sql = SqlUtil.createSelectSql(selects, tableName, wheres);
			return sql.toString();
		}
	}
}
