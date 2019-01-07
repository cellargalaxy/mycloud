package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import top.cellargalaxy.mycloud.dao.OwnDao;
import top.cellargalaxy.mycloud.dao.OwnExpireDao;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.bo.OwnExpireBo;
import top.cellargalaxy.mycloud.model.po.OwnExpirePo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.query.OwnExpireQuery;
import top.cellargalaxy.mycloud.util.dao.IDao;
import top.cellargalaxy.mycloud.util.dao.ProviderUtils;
import top.cellargalaxy.mycloud.util.dao.SqlUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author cellargalaxy
 * @time 2018/12/13
 */
@Mapper
public interface OwnExpireMapper extends IDao<OwnExpirePo, OwnExpireBo, OwnExpireQuery> {
	@Options(useGeneratedKeys = true, keyProperty = "ownId")
	@InsertProvider(type = OwnExpireProvider.class, method = "insert")
	int insert(OwnExpirePo ownExpirePo);

	@DeleteProvider(type = OwnExpireProvider.class, method = "delete")
	int delete(OwnExpirePo ownExpirePo);

	@UpdateProvider(type = OwnExpireProvider.class, method = "update")
	int update(OwnExpirePo ownExpirePo);

	@Results(id = "ownExpireResults", value = {
			@Result(property = "ownId", column = "own_id"),
			@Result(property = "ownExpireTime", column = "own_expire_time"),
			@Result(property = "ownUuid", column = "own_uuid"),
			@Result(property = "userId", column = "user_id"),
			@Result(property = "fileName", column = "file_name"),
			@Result(property = "sort", column = "sort"),
			@Result(property = "description", column = "description"),
			@Result(property = "contentType", column = "content_type"),
			@Result(property = "fileLength", column = "file_length"),
			@Result(property = "md5", column = "md5"),
			@Result(property = "fileId", column = "file_id"),
	})
	@SelectProvider(type = OwnExpireProvider.class, method = "selectOne")
	OwnExpireBo selectOne(OwnExpirePo ownExpirePo);

	@ResultMap(value = "ownExpireResults")
	@SelectProvider(type = OwnExpireProvider.class, method = "selectPageSome")
	List<OwnExpireBo> selectPageSome(OwnExpireQuery ownExpireQuery);

	@ResultMap(value = "ownExpireResults")
	@SelectProvider(type = OwnExpireProvider.class, method = "selectAllSome")
	List<OwnExpireBo> selectAllSome(OwnExpireQuery ownExpireQuery);

	@SelectProvider(type = OwnExpireProvider.class, method = "selectCount")
	int selectCount(OwnExpireQuery ownExpireQuery);

	@ResultMap(value = "ownExpireResults")
	@SelectProvider(type = OwnExpireProvider.class, method = "selectAll")
	List<OwnExpireBo> selectAll();

	@ResultMap(value = "ownExpireResults")
	@SelectProvider(type = OwnExpireProvider.class, method = "selectExpireOwnExpire")
	List<OwnExpireBo> selectExpireOwnExpire(OwnExpireQuery ownExpireQuery);

	@ResultMap(value = "ownExpireResults")
	@SelectProvider(type = OwnExpireProvider.class, method = "selectRecentExpireOwn")
	List<OwnExpireBo> selectRecentExpireOwn(OwnExpireQuery ownExpireQuery);

	class OwnExpireProvider /*implements IProvider<OwnExpirePo,OwnExpireQuery>*/ {
		private final String tableName = OwnExpireDao.TABLE_NAME;

		public Set<String> wheresKey(OwnExpirePo ownexpirePo) {
			Set<String> wheres = new HashSet<>();
			wheres.add("ownId");
			return wheres;
		}

		public Set<String> wheresAll(OwnExpireQuery ownexpireQuery) {
			Set<String> wheres = new HashSet<>();
			if (ownexpireQuery.getOwnId() > 0) {
				wheres.add("ownId");
			}
			return wheres;
		}

		/**
		 * 只有删除，没有修改
		 *
		 * @param ownexpirePo
		 * @return
		 */
		public Set<String> sets(OwnExpirePo ownexpirePo) {
			Set<String> sets = new HashSet<>();

			return sets;
		}

		public String insert(OwnExpirePo ownexpirePo) {
			String string = ProviderUtils.insert(tableName, OwnExpirePo.class).toString();
			return string;
		}

		public String delete(OwnExpirePo ownexpirePo) {
			String string = ProviderUtils.delete(tableName, wheresKey(ownexpirePo)).toString();
			return string;
		}

		public String update(OwnExpirePo ownexpirePo) {
			String string = ProviderUtils.update(tableName, sets(ownexpirePo), "ownId", wheresKey(ownexpirePo)).toString();
			return string;
		}

		public String selectOne(OwnExpirePo ownexpirePo) {
			SQL sql = ProviderUtils.select(new SQL(), tableName, OwnExpirePo.class);
			sql = ProviderUtils.select(sql, OwnDao.TABLE_NAME, OwnPo.class, "ownId", "createTime", "updateTime");

			sql.FROM(tableName + "," + OwnDao.TABLE_NAME);

			sql = ProviderUtils.whereTrue(sql, tableName, wheresKey(ownexpirePo));
			sql.WHERE(ProviderUtils.column(tableName, "ownId") + "=" + ProviderUtils.column(OwnDao.TABLE_NAME, "ownId"));

			String string = ProviderUtils.limitOne(sql).toString();
			return string;
		}

		public String selectPageSome(OwnExpireQuery ownexpireQuery) {
			SqlUtils.initPageQuery(ownexpireQuery);

			SQL sql = ProviderUtils.select(new SQL(), tableName, OwnExpirePo.class);
			sql = ProviderUtils.select(sql, OwnDao.TABLE_NAME, OwnPo.class, "ownId", "createTime", "updateTime");

			sql.FROM(tableName + "," + OwnDao.TABLE_NAME);

			sql = ProviderUtils.whereTrue(sql, tableName, wheresAll(ownexpireQuery));
			sql.WHERE(ProviderUtils.column(tableName, "ownId") + "=" + ProviderUtils.column(OwnDao.TABLE_NAME, "ownId"));

			String string = ProviderUtils.limitSome(sql).toString();
			return string;
		}

		public String selectAllSome(OwnExpireQuery ownexpireQuery) {
			SQL sql = ProviderUtils.select(new SQL(), tableName, OwnExpirePo.class);
			sql = ProviderUtils.select(sql, OwnDao.TABLE_NAME, OwnPo.class, "ownId", "createTime", "updateTime");

			sql.FROM(tableName + "," + OwnDao.TABLE_NAME);

			sql = ProviderUtils.whereTrue(sql, tableName, wheresAll(ownexpireQuery));
			sql.WHERE(ProviderUtils.column(tableName, "ownId") + "=" + ProviderUtils.column(OwnDao.TABLE_NAME, "ownId"));

			String string = sql.toString();
			return string;
		}

		public String selectCount(OwnExpireQuery ownexpireQuery) {
			String string = ProviderUtils.selectCount(tableName, wheresAll(ownexpireQuery)).toString();
			return string;
		}

		public String selectAll() {
			SQL sql = ProviderUtils.select(new SQL(), tableName, OwnExpirePo.class);
			sql = ProviderUtils.select(sql, OwnDao.TABLE_NAME, OwnPo.class, "ownId", "createTime", "updateTime");

			sql.FROM(tableName).LEFT_OUTER_JOIN(OwnDao.TABLE_NAME + " on " + ProviderUtils.column(tableName, "ownId") + "=" + ProviderUtils.column(OwnDao.TABLE_NAME, "ownId"));

			String string = sql.toString();
			return string;
		}

		public String selectExpireOwnExpire(OwnExpireQuery ownExpireQuery) {
			SQL sql = ProviderUtils.select(new SQL(), tableName, OwnExpirePo.class);
			sql = ProviderUtils.select(sql, OwnDao.TABLE_NAME, OwnPo.class, "ownId", "createTime", "updateTime");

			sql.FROM(tableName + "," + OwnDao.TABLE_NAME);

			if (ownExpireQuery.getOwnExpireTime() != null) {
				sql.WHERE(ProviderUtils.column(tableName, "ownExpireTime") + "<=" + ProviderUtils.field("ownExpireTime"));
			} else {
				sql.WHERE("false");
			}
			sql.WHERE(ProviderUtils.column(tableName, "ownId") + "=" + ProviderUtils.column(OwnDao.TABLE_NAME, "ownId"));

			String string = sql.toString();
			return string;
		}

		public String selectRecentExpireOwn(OwnExpireQuery ownExpireQuery) {
			SqlUtils.initPageQuery(ownExpireQuery);

			SQL sql = ProviderUtils.select(new SQL(), tableName, OwnExpirePo.class);
			sql = ProviderUtils.select(sql, OwnDao.TABLE_NAME, OwnPo.class, "ownId", "createTime", "updateTime");

			sql.FROM(tableName + "," + OwnDao.TABLE_NAME);

			sql.WHERE(ProviderUtils.column(tableName, "ownId") + "=" + ProviderUtils.column(OwnDao.TABLE_NAME, "ownId"));

			sql.ORDER_BY(ProviderUtils.column(tableName, "ownExpireTime desc"));

			String string = ProviderUtils.limitSome(sql).toString();
			return string;
		}
	}
}
