package top.cellargalaxy.mycloud.util;

import top.cellargalaxy.mycloud.model.query.PageQuery;
import top.cellargalaxy.mycloud.util.SqlUtil;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * Created by cellargalaxy on 18-8-4.
 */
public class ProviderUtil {

	public static final <Po> String delete(String tableName, Po po, BiConsumer<Po, Set<String>> wheresKeyFunc) {
		Set<String> wheres = new HashSet<>();
		wheresKeyFunc.accept(po, wheres);

		StringBuilder sql = SqlUtil.createDeleteSql(tableName, wheres);
		String string = sql.toString();
		return string;
	}

	public static final <Po> String selectOne(String tableName, Po po, BiConsumer<Po, Set<String>> wheresKeyFunc) {
		Set<String> wheres = new HashSet<>();
		wheresKeyFunc.accept(po, wheres);

		StringBuilder sql = SqlUtil.createSelectSql(null, tableName, wheres);
		String string = sql.append(" limit 1").toString();
		return string;
	}

	public static final <Query extends PageQuery> String selectSome(String tableName, Query query, BiConsumer<Query, Set<String>> wheresAllFunc) {
		SqlUtil.initPageQuery(query);

		Set<String> wheres = new HashSet<>();
		wheresAllFunc.accept(query, wheres);

		StringBuilder sql = SqlUtil.createSelectSql(null, tableName, wheres);
		String string = sql.append(" limit #{off},#{len}").toString();
		return string;
	}

	public static final <Query extends PageQuery> String selectCount(String tableName, Query query, BiConsumer<Query, Set<String>> wheresAllFunc) {
		SqlUtil.initPageQuery(query);

		List<String> selects = new LinkedList<>();
		selects.add("count(*)");

		Set<String> wheres = new HashSet<>();
		wheresAllFunc.accept(query, wheres);

		StringBuilder sql = SqlUtil.createSelectSql(selects, tableName, wheres);
		String string = sql.toString();
		return string;
	}

	public static final <Po> String update(String tableName, Po po, String defaultSet, BiConsumer<Po, Set<String>> setsFunc, BiConsumer<Po, Set<String>> wheresKeyFunc) {
		Set<String> sets = new HashSet<>();
		setsFunc.accept(po, sets);

		Set<String> wheres = new HashSet<>();
		wheresKeyFunc.accept(po, wheres);

		String string = SqlUtil.createUpdateSql(tableName, sets, defaultSet, wheres).append(" limit 1").toString();
		return string;
	}

}
