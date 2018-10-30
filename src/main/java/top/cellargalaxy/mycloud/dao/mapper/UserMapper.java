package top.cellargalaxy.mycloud.dao.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import top.cellargalaxy.mycloud.dao.AbstractDao;
import top.cellargalaxy.mycloud.dao.UserDao;
import top.cellargalaxy.mycloud.model.bo.UserBo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.UserQuery;
import top.cellargalaxy.mycloud.util.ProviderUtil;
import top.cellargalaxy.mycloud.util.SqlUtil;
import top.cellargalaxy.mycloud.util.StringUtil;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.apache.ibatis.type.JdbcType.TIMESTAMP;

/**
 * @author cellargalaxy
 * @time 2018/9/25
 */
@ConditionalOnBean(DataSource.class)
@Mapper
public interface UserMapper extends AbstractDao<UserPo, UserBo, UserQuery> {
	@Options(useGeneratedKeys = true, keyProperty = "articleId")
	@InsertProvider(type = UserProvider.class, method = "insert")
	int insert(UserPo userPo);

	@DeleteProvider(type = UserProvider.class, method = "delete")
	int delete(UserPo userPo);

	@UpdateProvider(type = UserProvider.class, method = "update")
	int update(UserPo userPo);

	@Results(id = "userResults", value = {
			@Result(property = "userId", column = "user_id", id = true),
			@Result(property = "username", column = "username"),
			@Result(property = "password", column = "password"),
			@Result(property = "createTime", column = "create_time", javaType = Date.class, jdbcType = TIMESTAMP),
			@Result(property = "updateTime", column = "update_time", javaType = Date.class, jdbcType = TIMESTAMP),
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

	class UserProvider /*implements AbstractProvider<UserPo, UserQuery>*/ {
		private String tableName = UserDao.TABLE_NAME;
		private String userId = tableName + ".user_id=#{userId}";
		private String username = tableName + ".username=#{username}";
		private String password = tableName + ".password=#{password}";
		private String createTime = tableName + ".create_time=#{createTime,jdbcType=TIMESTAMP}";
		private String updateTime = tableName + ".update_time=#{updateTime,jdbcType=TIMESTAMP}";

		public void wheresKey(UserPo userPo, Set<String> wheres) {
			if (!StringUtil.isBlank(userPo.getUsername())) {
				wheres.add(username);
				return;
			}
			wheres.add(userId);
		}

		public void wheresAll(UserQuery userQuery, Set<String> wheres) {
			if (userQuery.getUserId() > 0) {
				wheres.add(userId);
			}
			if (!StringUtil.isBlank(userQuery.getUsername())) {
				wheres.add(username);
			}
			if (!StringUtil.isBlank(userQuery.getPassword())) {
				wheres.add(password);
			}
		}

		public void sets(UserPo userPo, Set<String> sets) {
			if (!StringUtil.isBlank(userPo.getUsername())) {
				sets.add(username);
			}
			if (!StringUtil.isBlank(userPo.getPassword())) {
				sets.add(password);
			}
			if (userPo.getUpdateTime() != null) {
				sets.add(updateTime);
			}
		}

		public String insert(UserPo userPo) {
			String string = "insert into " + tableName + "(username,password,create_time,update_time) " +
					"values(#{username},#{password},#{createTime,jdbcType=TIMESTAMP},#{updateTime,jdbcType=TIMESTAMP})";
			return string;
		}

		public String delete(UserPo userPo) {
			return ProviderUtil.delete(tableName, userPo, this::wheresKey).append(" limit 1").toString();
		}

		public String update(UserPo userPo) {
			return ProviderUtil.update(tableName, userPo, userId, this::sets, this::wheresKey).append(" limit 1").toString();
		}

		public String selectOne(UserPo userPo) {
			return ProviderUtil.selectOne(tableName, userPo, this::wheresKey).append(" limit 1").toString();
		}

		public String selectPageSome(UserQuery userQuery) {
			SqlUtil.initPageQuery(userQuery);
			return ProviderUtil.selectSome(tableName, userQuery, this::wheresAll).append(" limit #{off},#{len}").toString();
		}

		public String selectAllSome(UserQuery userQuery) {
			return ProviderUtil.selectSome(tableName, userQuery, this::wheresAll).toString();
		}

		public String selectCount(UserQuery userQuery) {
			return ProviderUtil.selectCount(tableName, userQuery, this::wheresAll).toString();
		}

		public String selectAll() {
			return ProviderUtil.selectAll(tableName).toString();
		}
	}
}
