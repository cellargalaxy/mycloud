package top.cellargalaxy.mycloud.util;

import top.cellargalaxy.mycloud.model.query.PageQuery;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * Created by cellargalaxy on 18-8-4.
 */
public class ProviderUtil {

	public static final <Po> StringBuilder delete(String tableName, Po po, BiConsumer<Po, Set<String>> wheresKeyFunc) {
		Set<String> wheres = new HashSet<>();
		wheresKeyFunc.accept(po, wheres);

		return SqlUtil.createDeleteSql(tableName, wheres);
	}

	public static final <Po> StringBuilder selectOne(String tableName, Po po, BiConsumer<Po, Set<String>> wheresKeyFunc) {
		Set<String> wheres = new HashSet<>();
		wheresKeyFunc.accept(po, wheres);

		return SqlUtil.createSelectSql(null, tableName, wheres);
	}

	public static final <Query extends PageQuery> StringBuilder selectSome(String tableName, Query query, BiConsumer<Query, Set<String>> wheresAllFunc) {
		SqlUtil.initPageQuery(query);

		Set<String> wheres = new HashSet<>();
		wheresAllFunc.accept(query, wheres);

		return SqlUtil.createSelectSql(null, tableName, wheres);
	}

	public static final <Query extends PageQuery> StringBuilder selectCount(String tableName, Query query, BiConsumer<Query, Set<String>> wheresAllFunc) {
		SqlUtil.initPageQuery(query);

		List<String> selects = new LinkedList<>();
		selects.add("count(*)");

		Set<String> wheres = new HashSet<>();
		wheresAllFunc.accept(query, wheres);

		return SqlUtil.createSelectSql(selects, tableName, wheres);
	}

	public static final <Po> StringBuilder update(String tableName, Po po, String defaultSet, BiConsumer<Po, Set<String>> setsFunc, BiConsumer<Po, Set<String>> wheresKeyFunc) {
		Set<String> sets = new HashSet<>();
		setsFunc.accept(po, sets);

		Set<String> wheres = new HashSet<>();
		wheresKeyFunc.accept(po, wheres);

		return SqlUtil.createUpdateSql(tableName, sets, defaultSet, wheres);
	}

}
