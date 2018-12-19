package top.cellargalaxy.mycloud.util.dao;

import org.apache.ibatis.jdbc.SQL;
import top.cellargalaxy.mycloud.util.StringUtils;
import top.cellargalaxy.mycloud.util.model.PageQuery;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * Created by cellargalaxy on 18-8-4.
 */
public class ProviderUtils {

    public static final StringBuilder limitOne(SQL sql) {
        return new StringBuilder(sql.toString()).append(" limit 1");
    }

    public static final StringBuilder limitSome(SQL sql) {
        return new StringBuilder(sql.toString()).append(" limit #{off},#{len}");
    }

    public static final SQL insert(String tableName, Class<?> clazz) {
        SQL sql = new SQL().INSERT_INTO(tableName);

        do {
            for (Field field : clazz.getDeclaredFields()) {
                String modifier = Modifier.toString(field.getModifiers());
                if (!modifier.contains("static") && !modifier.contains("final")) {
                    sql.VALUES(column(tableName, field.getName()), property(field.getName()));
                }
            }
            clazz = clazz.getSuperclass();
        } while (!Object.class.equals(clazz));

        return sql;
    }

    public static final <Po> SQL delete(String tableName, Po po, BiConsumer<Po, Set<String>> wheresKeyFunc) {
        SQL sql = new SQL().DELETE_FROM(tableName);

        Set<String> wheres = new HashSet<>();
        wheresKeyFunc.accept(po, wheres);
        if (wheres.size() == 0) {
            sql.WHERE("false");
            return sql;
        }
        where(tableName, wheres, sql);
        return sql;
    }

    public static final <Po> SQL update(String tableName, Po po, String defaultSet, BiConsumer<Po, Set<String>> setsFunc, BiConsumer<Po, Set<String>> wheresKeyFunc) {
        SQL sql = new SQL().UPDATE(tableName);

        Set<String> sets = new HashSet<>();
        setsFunc.accept(po, sets);
        if (sets.size() == 0) {
            sets.add(defaultSet);
        }
        set(tableName, sets, sql);

        Set<String> wheres = new HashSet<>();
        wheresKeyFunc.accept(po, wheres);
        if (wheres.size() == 0) {
            sql.WHERE("false");
            return sql;
        }
        where(tableName, wheres, sql);
        return sql;
    }

    public static final <Po> SQL select(String tableName, Po po, BiConsumer<Po, Set<String>> wheresFunc) {
        SQL sql = new SQL().SELECT("*").FROM(tableName);

        Set<String> wheres = new HashSet<>();
        wheresFunc.accept(po, wheres);
        if (wheres.size() == 0) {
            sql.WHERE("true");
            return sql;
        }
        where(tableName, wheres, sql);
        return sql;
    }

    public static final <Query extends PageQuery> SQL selectCount(String tableName, Query query, BiConsumer<Query, Set<String>> wheresAllFunc) {
        SQL sql = new SQL().SELECT("count(*)").FROM(tableName);

        Set<String> wheres = new HashSet<>();
        wheresAllFunc.accept(query, wheres);
        if (wheres.size() == 0) {
            sql = sql.WHERE("true");
            return sql;
        }
        where(tableName, wheres, sql);
        return sql;
    }

    public static final SQL selectAll(String tableName) {
        SQL sql = new SQL().SELECT("*").FROM(tableName);
        return sql;
    }

//    public static final void set(Map<String, String> fieldTableNameMap, Set<String> sets, SQL sql) {
//        sets.stream().forEach(set -> set(fieldTableNameMap, set, sql));
//    }
//
//    public static final void set(Map<String, String> fieldTableNameMap, String fieldName, SQL sql) {
//        sql.SET(equalSign(fieldTableNameMap.get(fieldName), fieldName).toString());
//    }
//
//    public static final void where(Map<String, String> fieldTableNameMap, Set<String> wheres, SQL sql) {
//        wheres.stream().forEach(where -> where(fieldTableNameMap, where, sql));
//    }
//
//    public static final void where(Map<String, String> fieldTableNameMap, String fieldName, SQL sql) {
//        sql.WHERE(equalSign(fieldTableNameMap.get(fieldName), fieldName));
//    }

    public static final void set(String tableName, Set<String> propertys, SQL sql) {
        propertys.stream().forEach(property -> set(tableName, property, sql));
    }

    public static final void set(String tableName, String property, SQL sql) {
        sql.SET(equalSign(tableName, property));
    }

    public static final void where(String tableName, Set<String> propertys, SQL sql) {
        propertys.stream().forEach(property -> where(tableName, property, sql));
    }

    public static final void where(String tableName, String property, SQL sql) {
        sql.WHERE(equalSign(tableName, property));
    }

    public static final void select(String tableName, Class<?> clazz, SQL sql) {
        for (Field field : clazz.getDeclaredFields()) {
            String modifier = Modifier.toString(field.getModifiers());
            if (!modifier.contains("static") && !modifier.contains("final")) {
                sql.SELECT(column(tableName, field.getName()));
            }
        }
    }

    public static final String equalSign(String tableName, String property) {
        return column(tableName, property) + "=" + property(property);
    }

    public static final String property(String property) {
        return "#{" + property + "}";
    }

    public static final String column(String tableName, String property) {
        return tableName + "." + StringUtils.lowerCamel2LowerHyphen(property);
    }
}
