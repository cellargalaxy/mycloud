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

    public static final <Po> SQL insert(String tableName, Po po) {
        SQL sql = new SQL().INSERT_INTO(tableName);
        for (Field field : po.getClass().getDeclaredFields()) {
            String modifier = Modifier.toString(field.getModifiers());
            if (!modifier.contains("static") && !modifier.contains("final")) {
                sql.VALUES(StringUtils.lowerCamel2LowerHyphen(field.getName()), "#{" + field.getName() + "}");
            }
        }
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
        where(po, wheres, sql);
        return sql;
    }

    public static final <Po> SQL update(String tableName, Po po, String defaultSet, BiConsumer<Po, Set<String>> setsFunc, BiConsumer<Po, Set<String>> wheresKeyFunc) {
        SQL sql = new SQL().UPDATE(tableName);

        Set<String> sets = new HashSet<>();
        setsFunc.accept(po, sets);
        if (sets.size() == 0) {
            sets.add(defaultSet);
        }
        set(po, sets, sql);

        Set<String> wheres = new HashSet<>();
        wheresKeyFunc.accept(po, wheres);
        if (wheres.size() == 0) {
            sql.WHERE("false");
            return sql;
        }
        where(po, wheres, sql);
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
        where(po, wheres, sql);
        return sql;
    }

    public static final <Query extends PageQuery> SQL selectCount(String tableName, Query query, BiConsumer<Query, Set<String>> wheresAllFunc) {
        SQL sql = new SQL().SELECT("count(*)").FROM(tableName);

        Set<String> wheres = new HashSet<>();
        wheresAllFunc.accept(query, wheres);
        if (wheres.size() == 0) {
            sql.WHERE("true");
            return sql;
        }
        where(query, wheres, sql);
        return sql;
    }

    public static final SQL selectAll(String tableName) {
        SQL sql = new SQL().SELECT("*").FROM(tableName);
        return sql;
    }

    public static final <Po> void set(Po po, Set<String> sets, SQL sql) {
        for (Field field : po.getClass().getDeclaredFields()) {
            String modifier = Modifier.toString(field.getModifiers());
            if (!modifier.contains("static") && !modifier.contains("final")) {
                if (sets.contains(field.getName())) {
                    sql.SET(StringUtils.lowerCamel2LowerHyphen(field.getName()) + "=#{" + field.getName() + "}");
                }
            }
        }
    }

    public static final <Po> void where(Po po, Set<String> wheres, SQL sql) {
        for (Field field : po.getClass().getDeclaredFields()) {
            String modifier = Modifier.toString(field.getModifiers());
            if (!modifier.contains("static") && !modifier.contains("final")) {
                if (wheres.contains(field.getName())) {
                    sql.WHERE(StringUtils.lowerCamel2LowerHyphen(field.getName()) + "=#{" + field.getName() + "}");
                }
            }
        }
    }
}
