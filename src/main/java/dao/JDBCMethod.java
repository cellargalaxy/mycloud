package dao;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by cellargalaxy on 17-9-16.
 */
public class JDBCMethod {
	/*
	connection.setAutoCommit(false);
	connection.commit();
	try { connection.rollback(); } catch (Exception e1) { e1.printStackTrace(); }
	 */
	
	/**
	 * 增删改
	 *
	 * @param connection
	 * @param sql
	 * @param objects
	 * @return
	 */
	public static int up(Connection connection, String sql, Object... objects) {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = creratPreparedStatement(connection, sql, objects);
			return preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 增
	 *
	 * @param connection
	 * @param tableName
	 * @param map
	 * @return
	 */
	public static int insert(Connection connection, String tableName, Map<String, Object> map) {
		StringBuffer sql = new StringBuffer("INSERT INTO " + tableName);
		StringBuffer valueName = new StringBuffer("(");
		StringBuffer value = new StringBuffer("(");
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			valueName.append(entry.getKey() + ",");
			value.append("?,");
		}
		valueName.deleteCharAt(valueName.length() - 1);
		valueName.append(")");
		value.deleteCharAt(value.length() - 1);
		value.append(")");
		sql.append(" " + valueName.toString() + " VALUES" + value.toString());
		int num = -1;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql.toString());
			int i = 1;
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				preparedStatement.setObject(i, entry.getValue());
				i++;
			}
			num = preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return num;
	}
	
	/**
	 * 删
	 *
	 * @param connection
	 * @param tableName
	 * @param name
	 * @param value
	 * @return
	 */
	public static int delete(Connection connection, String tableName, String name, Object value) {
		String sql = "DELETE FROM " + tableName + " WHERE " + name + "=?";
		int num = -1;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setObject(1, value);
			num = preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return num;
	}
	
	/**
	 * 改
	 *
	 * @param connection
	 * @param tableName
	 * @param name
	 * @param value
	 * @param map
	 * @return
	 */
	public static int update(Connection connection, String tableName, String name, Object value, Map<String, Object> map) {
		StringBuffer sql = new StringBuffer("UPDATE " + tableName + " SET ");
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			sql.append(entry.getKey() + "=? ,");
		}
		sql.deleteCharAt(sql.length() - 1);
		sql.append("WHERE " + name + "=?");
		int num = -1;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(sql.toString());
			int i = 1;
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				preparedStatement.setObject(i, entry.getValue());
				i++;
			}
			preparedStatement.setObject(map.size() + 1, value);
			num = preparedStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return num;
	}
	
	/**
	 * 查整个表
	 *
	 * @param connection
	 * @param tableName
	 * @return
	 */
	public static List<Map<String, Object>> selectTableByTableName(Connection connection, String tableName) {
		String sql = "SELECT * FROM " + tableName;
		return selectTableBySql(connection, sql);
	}
	
	/**
	 * 查表
	 *
	 * @param connection
	 * @param sql
	 * @param objects
	 * @return
	 */
	public static List<Map<String, Object>> selectTableBySql(Connection connection, String sql, Object... objects) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = creratPreparedStatement(connection, sql, objects);
			resultSet = preparedStatement.executeQuery();
			List<Map<String, Object>> maps = new LinkedList<Map<String, Object>>();
			String[] columnNames = selectColumnLabels(resultSet);
			while (resultSet.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (String columnName : columnNames) {
					map.put(columnName, resultSet.getObject(columnName));
				}
				maps.add(map);
			}
			return maps;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 查行
	 *
	 * @param connection
	 * @param tableName
	 * @param name
	 * @param value
	 * @return
	 */
	public static Map<String, Object> selectRow(Connection connection, String tableName, String name, Object value) {
		String sql = "SELECT * FROM " + tableName + " WHERE " + name + "=?";
		return selectRow(connection, sql, value);
	}
	
	/**
	 * 查行
	 *
	 * @param connection
	 * @param sql
	 * @param objects
	 * @return
	 */
	public static Map<String, Object> selectRow(Connection connection, String sql, Object... objects) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = creratPreparedStatement(connection, sql, objects);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				String[] columnNames = selectColumnLabels(resultSet);
				Map<String, Object> map = new HashMap<String, Object>();
				for (String columnName : columnNames) {
					map.put(columnName, resultSet.getObject(columnName));
				}
				return map;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 查属性或者情况
	 *
	 * @param connection
	 * @param sql
	 * @param objects
	 * @return
	 */
	public static Object selectValue(Connection connection, String sql, Object... objects) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = creratPreparedStatement(connection, sql, objects);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getObject(1);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 查表列别名，当没有指定别名时自动用回原名
	 *
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	public static String[] selectColumnLabels(ResultSet resultSet) throws SQLException {
		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
		String[] columnLabels = new String[resultSetMetaData.getColumnCount()];
		for (int i = 0; i < columnLabels.length; i++) {
			columnLabels[i] = resultSetMetaData.getColumnLabel(i + 1);
		}
		return columnLabels;
	}
	
	private static PreparedStatement creratPreparedStatement(Connection connection, String sql, Object... objects) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		for (int i = 0; i < objects.length; i++) {
			preparedStatement.setObject(i + 1, objects[i]);
		}
		return preparedStatement;
	}
}
