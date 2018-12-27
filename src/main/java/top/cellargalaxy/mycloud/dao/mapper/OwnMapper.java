package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
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
			@Result(property = "fileName", column = "file_name"),
			@Result(property = "sort", column = "sort"),
			@Result(property = "description", column = "description"),
			@Result(property = "contentType", column = "content_type"),
			@Result(property = "fileLength", column = "file_length"),
			@Result(property = "md5", column = "md5"),
			@Result(property = "fileId", column = "file_id"),
			@Result(property = "createTime", column = "create_time"),
			@Result(property = "updateTime", column = "update_time"),
			@Result(property = "username", column = "username"),
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

	class OwnProvider /*implements IProvider<OwnPo,OwnQuery>*/ {
		private final String tableName = OwnDao.TABLE_NAME;

		public Set<String> wheresKey(OwnPo ownPo) {
			Set<String> wheres = new HashSet<>();
			if (ownPo.getOwnId() > 0) {
				wheres.add("ownId");
				return wheres;
			}
			if (!StringUtils.isBlank(ownPo.getOwnUuid())) {
				wheres.add("ownUuid");
				return wheres;
			}
			wheres.add("ownId");
			return wheres;
		}

		public Set<String> wheresAll(OwnQuery ownQuery) {
			Set<String> wheres = new HashSet<>();
			if (ownQuery.getOwnId() > 0) {
				wheres.add("ownId");
			}
			if (!StringUtils.isBlank(ownQuery.getOwnUuid())) {
				wheres.add("ownUuid");
			}
			if (ownQuery.getUserId() > 0) {
				wheres.add("userId");
			}
			if (!StringUtils.isBlank(ownQuery.getSort())) {
				wheres.add("sort");
			}
			if (!StringUtils.isBlank(ownQuery.getMd5())) {
				wheres.add("md5");
			}
			if (ownQuery.getFileId() > 0) {
				wheres.add("fileId");
			}
			return wheres;
		}

		public Set<String> sets(OwnPo ownPo) {
			Set<String> sets = new HashSet<>();
			if (!StringUtils.isBlank(ownPo.getFileName())) {
				sets.add("fileName");
			}
			if (!StringUtils.isBlank(ownPo.getSort())) {
				sets.add("sort");
			}
			if (!StringUtils.isBlank(ownPo.getDescription())) {
				sets.add("description");
			}
			if (!StringUtils.isBlank(ownPo.getMd5())) {
				sets.add("md5");
			}
			if (ownPo.getFileId() > 0) {
				sets.add("fileId");
			}
			if (ownPo.getUpdateTime() != null) {
				sets.add("updateTime");
			}
			return sets;
		}

		public String insert(OwnPo ownPo) {
			String string = ProviderUtils.insert(tableName, OwnPo.class).toString();
			return string;
		}

		public String delete(OwnPo ownPo) {
			String string = ProviderUtils.delete(tableName, wheresKey(ownPo)).toString();
			return string;
		}

		public String update(OwnPo ownPo) {
			String string = ProviderUtils.update(tableName, sets(ownPo), "ownId", wheresKey(ownPo)).toString();
			return string;
		}

		public String selectOne(OwnPo ownPo) {
			SQL sql = ProviderUtils.select(new SQL(), tableName, OwnPo.class);
			sql.SELECT(UserDao.TABLE_NAME + ".username");

			sql.FROM(tableName).LEFT_OUTER_JOIN(UserDao.TABLE_NAME + " on " + ProviderUtils.column(tableName, "userId") + "=" + ProviderUtils.column(UserDao.TABLE_NAME, "userId"));

			sql = ProviderUtils.whereTrue(sql, tableName, wheresKey(ownPo));

			String string = ProviderUtils.limitOne(sql).toString();
			return string;
		}

		public String selectPageSome(OwnQuery ownQuery) {
			SqlUtils.initPageQuery(ownQuery);

			SQL sql = ProviderUtils.select(new SQL(), tableName, OwnPo.class);
			sql.SELECT(UserDao.TABLE_NAME + ".username");

			sql.FROM(tableName).LEFT_OUTER_JOIN(UserDao.TABLE_NAME + " on " + ProviderUtils.column(tableName, "userId") + "=" + ProviderUtils.column(UserDao.TABLE_NAME, "userId"));

			sql = ProviderUtils.whereTrue(sql, tableName, wheresAll(ownQuery));

			String string = ProviderUtils.limitSome(sql).toString();
			return string;
		}

		public String selectAllSome(OwnQuery ownQuery) {
			SQL sql = ProviderUtils.select(new SQL(), tableName, OwnPo.class);
			sql.SELECT(UserDao.TABLE_NAME + ".username");

			sql.FROM(tableName).LEFT_OUTER_JOIN(UserDao.TABLE_NAME + " on " + ProviderUtils.column(tableName, "userId") + "=" + ProviderUtils.column(UserDao.TABLE_NAME, "userId"));

			sql = ProviderUtils.whereTrue(sql, tableName, wheresAll(ownQuery));

			String string = sql.toString();
			return string;
		}

		public String selectCount(OwnQuery ownQuery) {
			String string = ProviderUtils.selectCount(tableName, wheresAll(ownQuery)).toString();
			return string;
		}

		public String selectAll() {
			SQL sql = ProviderUtils.select(new SQL(), tableName, OwnPo.class);
			sql.SELECT(UserDao.TABLE_NAME + ".username");

			sql.FROM(tableName).LEFT_OUTER_JOIN(UserDao.TABLE_NAME + " on " + ProviderUtils.column(tableName, "userId") + "=" + ProviderUtils.column(UserDao.TABLE_NAME, "userId"));

			String string = sql.toString();
			return string;
		}

		public String selectAllSort(OwnQuery ownQuery) {
			SQL sql = new SQL().SELECT("distinct " + ProviderUtils.column(tableName, "sort"));

			sql.FROM(tableName);

			sql = ProviderUtils.whereTrue(sql, tableName, wheresAll(ownQuery));

			String string = sql.toString();
			return string;
		}
	}
}
