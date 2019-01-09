package top.cellargalaxy.mycloud.util.dao;

import java.sql.*;
import java.util.*;

/**
 * @author cellargalaxy
 * @time 18-12-27
 */
public class JDBCUtils {
    /*
	connection.setAutoCommit(false);
	connection.commit();
	try { connection.rollback(); } catch (Exception e1) { e1.printStackTrace(); }
	 */

    public final static int createTableIfNotExist(Connection connection, String tableName, String createTableSql) throws SQLException {
        if (existTable(connection, tableName)) {
            return 0;
        }
        return executeUpdate(connection, createTableSql);
    }

    /**
     * @param connection
     * @param sql        熟sql
     * @return
     * @throws SQLException
     */
    public static final int executeUpdate(Connection connection, String sql) throws SQLException {
        try (PreparedStatement preparedStatement = createPreparedStatement(connection, sql, Collections.EMPTY_LIST)) {
            return preparedStatement.executeUpdate();
        }
    }

    public final static boolean existTable(Connection connection, String tableName) throws SQLException {
        try (ResultSet resultSet = connection.getMetaData().getTables(null, null, tableName, null)) {
            if (resultSet == null) {
                return false;
            }
            return resultSet.next();
        }
    }

    public final static boolean existColumn(Connection connection, String tableName, String columnName) throws SQLException {
        try (ResultSet resultSet = connection.getMetaData().getColumns(null, null, tableName, columnName)) {
            return resultSet.next();
        }
    }

    public static int insert(Connection connection, String tableName, Map<String, Object> map) throws SQLException {
        StringBuilder sql = new StringBuilder("insert into " + tableName + "(");
        StringBuilder value = new StringBuilder(" values(");
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            sql.append(entry.getKey());
            value.append("?");
        }
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            sql.append("," + entry.getKey());
            value.append(",?");
        }
        sql.append(")");
        value.append(")");
        sql.append(value);
        try (PreparedStatement preparedStatement = createPreparedStatement(connection, sql.toString(), map.values())) {
            return preparedStatement.executeUpdate();
        }
    }

    public static int delete(Connection connection, String tableName, Map<String, Object> where) throws SQLException {
        StringBuilder sql = new StringBuilder("delete from " + tableName);
        if (where == null || where.size() == 0) {
            sql.append(" where false");
        } else {
            Iterator<Map.Entry<String, Object>> iterator = where.entrySet().iterator();
            if (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                sql.append(" where " + entry.getKey() + "=?");
            }
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                sql.append(" and " + entry.getKey() + "=?");
            }
        }

        try (PreparedStatement preparedStatement = createPreparedStatement(connection, sql.toString(), where != null ? where.values() : Collections.EMPTY_LIST)) {
            return preparedStatement.executeUpdate();
        }
    }

    public static int update(Connection connection, String tableName, Map<String, Object> set, Map<String, Object> where) throws SQLException {
        StringBuilder sql = new StringBuilder("update " + tableName + " set");

        Iterator<Map.Entry<String, Object>> iterator = set.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            sql.append(" " + entry.getKey() + "=?");
        }
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            sql.append(", " + entry.getKey() + "=?");
        }

        if (where == null || where.size() == 0) {
            sql.append(" where false");
        } else {
            iterator = where.entrySet().iterator();
            if (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                sql.append(" where " + entry.getKey() + "=?");
            }
            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                sql.append(" and " + entry.getKey() + "=?");
            }
        }

        List<Object> objects = new LinkedList<>();
        objects.addAll(set.values());
        objects.addAll(where.values());
        try (PreparedStatement preparedStatement = createPreparedStatement(connection, sql.toString(), objects)) {
            return preparedStatement.executeUpdate();
        }
    }

    public static Map<String, Object>[] selectAll(Connection connection, String tableName, Map<String, Object> where) throws SQLException {
        StringBuilder sql = createSelectSql(tableName, where);
        return executeQuery(connection, sql.toString(), where != null ? where.values() : Collections.EMPTY_LIST);
    }

    public static Map<String, Object>[] selectSome(Connection connection, String tableName, Map<String, Object> where, int off, int len) throws SQLException {
        StringBuilder sql = createSelectSql(tableName, where);
        sql.append(" limit ?,?");
        List<Object> list = new LinkedList<>();
        list.addAll(where != null ? where.values() : Collections.EMPTY_LIST);
        list.add(off);
        list.add(len);
        return executeQuery(connection, sql.toString(), list);
    }

    /**
     * @param connection
     * @param tableName
     * @param where      为空则where true
     * @return
     * @throws SQLException
     */
    public static Map<String, Object> selectOne(Connection connection, String tableName, Map<String, Object> where) throws SQLException {
        StringBuilder sql = createSelectSql(tableName, where);
        sql.append(" limit 1");
        Map<String, Object>[] maps = executeQuery(connection, sql.toString(), where != null ? where.values() : Collections.EMPTY_LIST);
        if (maps == null || maps.length == 0) {
            return null;
        }
        return maps[0];
    }

    /**
     * 执行select
     *
     * @param connection
     * @param sql        待渲染的sql
     * @param objects    如果sql没有占位符，可为null
     * @return
     * @throws SQLException
     */
    private static final Map<String, Object>[] executeQuery(Connection connection, String sql, Collection<Object> objects) throws SQLException {
        try (PreparedStatement preparedStatement = createPreparedStatement(connection, sql, objects)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.last();
                Map<String, Object>[] maps = new Map[resultSet.getRow()];
                resultSet.beforeFirst();
                int num = 0;
                String[] columnNames = selectColumnLabels(resultSet);
                while (resultSet.next()) {
                    maps[num] = new HashMap<>();
                    for (String columnName : columnNames) maps[num].put(columnName, resultSet.getObject(columnName));
                    num++;
                }
                return maps;
            }
        }
    }

    /**
     * @param tableName
     * @param where     为空则where true
     * @return
     */
    private static final StringBuilder createSelectSql(String tableName, Map<String, Object> where) {
        StringBuilder sql = new StringBuilder("select * from " + tableName + " where");
        if (where == null || where.size() == 0) {
            sql.append(" true");
            return sql;
        }
        Iterator<Map.Entry<String, Object>> iterator = where.entrySet().iterator();
        if (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            sql.append(" " + entry.getKey() + "=?");
        }
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            sql.append(" and " + entry.getKey() + "=?");
        }
        return sql;
    }

    /**
     * 查表列别名，当没有指定别名时自动用回原名
     *
     * @param resultSet
     * @return
     * @throws SQLException
     */
    public final static String[] selectColumnLabels(ResultSet resultSet) throws SQLException {
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        String[] columnLabels = new String[resultSetMetaData.getColumnCount()];
        for (int i = 0; i < columnLabels.length; i++) {
            columnLabels[i] = resultSetMetaData.getColumnLabel(i + 1);
        }
        return columnLabels;
    }


    /**
     * 查所需属性或者情况
     *
     * @param connection
     * @param sql        待渲染的sql
     * @param objects    如果sql没有占位符，可为null
     * @return
     * @throws SQLException
     */
    public final static Object selectValue(Connection connection, String sql, Collection<Object> objects) throws SQLException {
        try (PreparedStatement preparedStatement = createPreparedStatement(connection, sql, objects)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getObject(1);
                }
                return null;
            }
        }
    }

    /**
     * @param connection
     * @param sql        待渲染的sql
     * @param objects    如果sql没有占位符，可为null
     * @return
     * @throws SQLException
     */
    private final static PreparedStatement createPreparedStatement(Connection connection, String sql, Collection<Object> objects) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if (objects != null) {
            int i = 0;
            for (Object object : objects) {
                preparedStatement.setObject(i + 1, object);
                i++;
            }
        }
        return preparedStatement;
    }
}
