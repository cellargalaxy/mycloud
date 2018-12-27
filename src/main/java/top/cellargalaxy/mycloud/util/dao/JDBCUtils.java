package top.cellargalaxy.mycloud.util.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author cellargalaxy
 * @time 18-12-27
 */
public class JDBCUtils {
	public static final boolean existTable(Connection connection, String tableName) throws SQLException {
		try (ResultSet resultSet = connection.getMetaData().getTables(null, null, tableName, null)) {
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}
		}
	}
}
