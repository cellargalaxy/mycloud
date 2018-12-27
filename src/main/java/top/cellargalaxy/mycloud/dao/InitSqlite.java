package top.cellargalaxy.mycloud.dao;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.jdbc.core.JdbcTemplate;
import top.cellargalaxy.mycloud.util.StringUtils;
import top.cellargalaxy.mycloud.util.dao.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author cellargalaxy
 * @time 18-12-27
 */
@Conditional(InitSqlite.SqliteCondition.class)
@Configuration
public class InitSqlite {
	public InitSqlite(JdbcTemplate jdbcTemplate) throws SQLException {
		try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
			if (!JDBCUtils.existTable(connection, AuthorizationDao.TABLE_NAME)) {
				jdbcTemplate.execute("CREATE TABLE \"authorization\" (\n" +
						"  \"authorization_id\" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
						"  \"user_id\" INTEGER NOT NULL,\n" +
						"  \"permission\" TEXT NOT NULL,\n" +
						"  \"create_time\" datetime NOT NULL,\n" +
						"  \"update_time\" datetime NOT NULL\n" +
						");");
				jdbcTemplate.execute("CREATE UNIQUE INDEX \"uk_user_id_permission\"\n" +
						"ON \"authorization\" (\n" +
						"  \"user_id\",\n" +
						"  \"permission\"\n" +
						");");
				jdbcTemplate.execute("INSERT INTO `authorization` VALUES (1,1,'ADMIN','2018-08-01 00:00:00','2018-08-01 00:00:00'),(2,1,'USER','2018-08-01 00:00:00','2018-08-01 00:00:00');");
			}

			if (!JDBCUtils.existTable(connection, FileInfoDao.TABLE_NAME)) {
				jdbcTemplate.execute("CREATE TABLE \"file_info\" (\n" +
						"  \"file_id\" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
						"  \"md5\" TEXT NOT NULL,\n" +
						"  \"file_length\" integer NOT NULL,\n" +
						"  \"content_type\" TEXT NOT NULL,\n" +
						"  \"create_time\" DATETIME NOT NULL\n" +
						");");
				jdbcTemplate.execute("CREATE INDEX \"idx_content_type\"\n" +
						"ON \"file_info\" (\n" +
						"  \"content_type\"\n" +
						");");
				jdbcTemplate.execute("CREATE UNIQUE INDEX \"uk_md5\"\n" +
						"ON \"file_info\" (\n" +
						"  \"md5\"\n" +
						");");
			}

			if (!JDBCUtils.existTable(connection, OwnDao.TABLE_NAME)) {
				jdbcTemplate.execute("CREATE TABLE \"own\" (\n" +
						"  \"own_id\" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
						"  \"own_uuid\" text NOT NULL,\n" +
						"  \"user_id\" INTEGER NOT NULL,\n" +
						"  \"file_name\" TEXT NOT NULL,\n" +
						"  \"sort\" TEXT NOT NULL DEFAULT '<default>',\n" +
						"  \"description\" TEXT,\n" +
						"  \"file_length\" integer NOT NULL,\n" +
						"  \"content_type\" TEXT NOT NULL,\n" +
						"  \"md5\" TEXT,\n" +
						"  \"file_id\" INTEGER,\n" +
						"  \"create_time\" DATETIME NOT NULL,\n" +
						"  \"update_time\" DATETIME NOT NULL\n" +
						");");
				jdbcTemplate.execute("CREATE INDEX \"idx_file_id\"\n" +
						"ON \"own\" (\n" +
						"  \"file_id\"\n" +
						");");
				jdbcTemplate.execute("CREATE INDEX \"idx_md5\"\n" +
						"ON \"own\" (\n" +
						"  \"md5\"\n" +
						");");
				jdbcTemplate.execute("CREATE INDEX \"idx_user_id_sort\"\n" +
						"ON \"own\" (\n" +
						"  \"user_id\",\n" +
						"  \"sort\"\n" +
						");");
				jdbcTemplate.execute("CREATE UNIQUE INDEX \"uk_own_uuid\"\n" +
						"ON \"own\" (\n" +
						"  \"own_uuid\"\n" +
						");");
			}

			if (!JDBCUtils.existTable(connection, OwnExpireDao.TABLE_NAME)) {
				jdbcTemplate.execute("CREATE TABLE \"own_expire\" (\n" +
						"  \"own_id\" INTEGER NOT NULL,\n" +
						"  \"own_expire_time\" datetime NOT NULL,\n" +
						"  PRIMARY KEY (\"own_id\")\n" +
						");");
				jdbcTemplate.execute("CREATE INDEX \"idx_own_expire_time\"\n" +
						"ON \"own_expire\" (\n" +
						"  \"own_expire_time\"\n" +
						");");
			}

			if (!JDBCUtils.existTable(connection, UserDao.TABLE_NAME)) {
				jdbcTemplate.execute("CREATE TABLE \"user\" (\n" +
						"  \"user_id\" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
						"  \"username\" TEXT NOT NULL,\n" +
						"  \"password\" TEXT NOT NULL,\n" +
						"  \"create_time\" datetime NOT NULL,\n" +
						"  \"update_time\" datetime NOT NULL\n" +
						");");
				jdbcTemplate.execute("CREATE UNIQUE INDEX \"uk_username\"\n" +
						"ON \"user\" (\n" +
						"  \"username\"\n" +
						");");
			}
		}
	}

	static class SqliteCondition implements Condition {

		@Override
		public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
			String type = context.getEnvironment().getProperty("mycloud.db.type");
			return !StringUtils.isBlank(type) && type.trim().toLowerCase().equals("sqlite");
		}
	}
}
