package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import top.cellargalaxy.mycloud.dao.UserDao;
import top.cellargalaxy.mycloud.model.bo.UserBo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.UserQuery;
import top.cellargalaxy.mycloud.util.StringUtils;
import top.cellargalaxy.mycloud.util.dao.IDao;
import top.cellargalaxy.mycloud.util.dao.ProviderUtils;
import top.cellargalaxy.mycloud.util.dao.SqlUtils;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author cellargalaxy
 * @time 2018/9/25
 */
@ConditionalOnBean(DataSource.class)
@Mapper
public interface UserMapper extends IDao<UserPo, UserBo, UserQuery> {
	@Options(useGeneratedKeys = true, keyProperty = "userId")
	@InsertProvider(type = UserProvider.class, method = "insert")
	int insert(UserPo userPo);

	@DeleteProvider(type = UserProvider.class, method = "delete")
	int delete(UserPo userPo);

	@UpdateProvider(type = UserProvider.class, method = "update")
	int update(UserPo userPo);

	@Results(id = "userResults", value = {
			@Result(property = "userId", column = "user_id"),
			@Result(property = "username", column = "username"),
			@Result(property = "password", column = "password"),
			@Result(property = "createTime", column = "create_time"),
			@Result(property = "updateTime", column = "update_time"),
	})
	@SelectProvider(type = UserProvider.class, method = "selectOne")
	UserBo selectOne(UserPo userPo);

	@ResultMap(value = "userResults")
	@SelectProvider(type = UserProvider.class, method = "selectPageSome")
	List<UserBo> selectPageSome(UserQuery userQuery);

	@ResultMap(value = "userResults")
	@SelectProvider(type = UserProvider.class, method = "selectAllSome")
	List<UserBo> selectAllSome(UserQuery userQuery);

	@SelectProvider(type = UserProvider.class, method = "selectCount")
	int selectCount(UserQuery userQuery);

	@ResultMap(value = "userResults")
	@SelectProvider(type = UserProvider.class, method = "selectAll")
	List<UserBo> selectAll();

	class UserProvider /*implements IProvider<UserPo,UserQuery>*/ {
		private final String tableName = UserDao.TABLE_NAME;

		public Set<String> wheresKey(UserPo userPo) {
			Set<String> wheres = new HashSet<>();
			if (userPo.getUserId() > 0) {
				wheres.add("userId");
				return wheres;
			}
			if (!StringUtils.isBlank(userPo.getUsername())) {
				wheres.add("username");
				return wheres;
			}
			wheres.add("username");
			return wheres;
		}

		public Set<String> wheresAll(UserQuery userQuery) {
			Set<String> wheres = new HashSet<>();
			if (userQuery.getUserId() > 0) {
				wheres.add("userId");
			}
			if (!StringUtils.isBlank(userQuery.getUsername())) {
				wheres.add("username");
			}
			return wheres;
		}

		public Set<String> sets(UserPo userPo) {
			Set<String> sets = new HashSet<>();
			if (!StringUtils.isBlank(userPo.getUsername())) {
				sets.add("username");
			}
			if (!StringUtils.isBlank(userPo.getPassword())) {
				sets.add("password");
			}
			if (userPo.getUpdateTime() != null) {
				sets.add("updateTime");
			}
			return sets;
		}

		public String insert(UserPo userPo) {
			String string = ProviderUtils.insert(tableName, UserPo.class, "userId").toString();
			return string;
		}

		public String delete(UserPo userPo) {
			String string = ProviderUtils.delete(tableName, wheresKey(userPo)).toString();
			return string;
		}

		public String update(UserPo userPo) {
			String string = ProviderUtils.update(tableName, sets(userPo), "defaultSet", wheresKey(userPo)).toString();
			return string;
		}

		public String selectOne(UserPo userPo) {
			String string = ProviderUtils.limitOne(ProviderUtils.select(tableName, null, wheresKey(userPo))).toString();
			return string;
		}

		public String selectPageSome(UserQuery userQuery) {
			SqlUtils.initPageQuery(userQuery);
			String string = ProviderUtils.limitSome(ProviderUtils.select(tableName, null, wheresAll(userQuery))).toString();
			return string;
		}

		public String selectAllSome(UserQuery userQuery) {
			String string = ProviderUtils.select(tableName, null, wheresAll(userQuery)).toString();
			return string;
		}

		public String selectCount(UserQuery userQuery) {
			String string = ProviderUtils.selectCount(tableName, wheresAll(userQuery)).toString();
			return string;
		}

		public String selectAll() {
			String string = ProviderUtils.selectAll(tableName, null).toString();
			return string;
		}
	}
}
