package top.cellargalaxy.newcloud.util;

import top.cellargalaxy.newcloud.model.query.PageQuery;

import java.util.Iterator;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/9
 */
public class SqlUtil {
	public static final void initPageQuery(PageQuery pageQuery) {
		pageQuery.setLen(pageQuery.getPageSize());
		pageQuery.setOff((pageQuery.getPage() - 1) * pageQuery.getPageSize());
	}

	public static final StringBuilder createDeleteSql(String tableName, List<String> wheres) {
		StringBuilder sql = new StringBuilder("delete from " + tableName + " where");
		addWhere(sql, wheres);
		return sql;
	}

	public static final StringBuilder createSelectSql(List<String> selects, String tableName, List<String> wheres) {
		StringBuilder sql = new StringBuilder("select");
		if (selects == null || selects.size() == 0) {
			sql.append(" *");
		} else {
			Iterator<String> iterator = selects.iterator();
			if (iterator.hasNext()) {
				sql.append(" " + iterator.next());
			}
			while (iterator.hasNext()) {
				sql.append(", " + iterator.next());
			}
		}
		sql.append(" from " + tableName + " where");
		addWhere(sql, wheres);
		return sql;
	}

	public static final StringBuilder createUpdateSql(String tableName, List<String> sets, List<String> wheres) {
		StringBuilder sql = new StringBuilder("update " + tableName + " set");
		if (sets.size() > 0) {
			Iterator<String> iterator = sets.iterator();
			if (iterator.hasNext()) {
				sql.append(" " + iterator.next());
			}
			while (iterator.hasNext()) {
				sql.append(", " + iterator.next());
			}
		}
		sql.append(" where");
		addWhere(sql, wheres);
		return sql;
	}

	private static final void addWhere(StringBuilder sql, List<String> wheres) {
		if (wheres.size() == 0) {
			sql.append(" false");
		} else {
			Iterator<String> iterator = wheres.iterator();
			if (iterator.hasNext()) {
				sql.append(" " + iterator.next());
			}
			while (iterator.hasNext()) {
				sql.append(" and " + iterator.next());
			}
		}
	}
}
