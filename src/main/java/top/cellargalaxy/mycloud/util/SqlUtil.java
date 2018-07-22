package top.cellargalaxy.mycloud.util;

import top.cellargalaxy.mycloud.model.query.PageQuery;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/9
 */
public class SqlUtil {
	public static final int MAX_PAGE_SIZE = 100;
	public static final void initPageQuery(PageQuery pageQuery) {
		int off = (pageQuery.getPage() - 1) * pageQuery.getPageSize();//[0,00)
		int len = pageQuery.getPageSize();//[0,MAX_PAGE_SIZE]
		if (off < 0) {
			off = 0;
			len = 0;
		} else if (len < 0) {
			len = 0;
		} else if (len > MAX_PAGE_SIZE) {
			len = MAX_PAGE_SIZE;
		}
		pageQuery.setOff(off);
		pageQuery.setLen(len);
	}

	public static final StringBuilder createDeleteSql(String tableName, List<String> wheres) {
		StringBuilder sql = new StringBuilder("delete from " + tableName + " where");
		if (wheres == null) {
			wheres = new LinkedList<>();
		}
		if (wheres.size() == 0) {
			wheres.add("false");
		}
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
		if (wheres == null) {
			wheres = new LinkedList<>();
		}
		if (wheres.size() == 0) {
			wheres.add("true");
		}
		addWhere(sql, wheres);
		return sql;
	}

	public static final StringBuilder createUpdateSql(String tableName, List<String> sets, List<String> wheres) {
		StringBuilder sql = new StringBuilder("update " + tableName + " set");
		Iterator<String> iterator = sets.iterator();
		if (iterator.hasNext()) {
			sql.append(" " + iterator.next());
		}
		while (iterator.hasNext()) {
			sql.append(", " + iterator.next());
		}
		sql.append(" where");
		if (wheres == null) {
			wheres = new LinkedList<>();
		}
		if (wheres.size() == 0) {
			wheres.add("false");
		}
		addWhere(sql, wheres);
		return sql;
	}

	private static final void addWhere(StringBuilder sql, List<String> wheres) {
		Iterator<String> iterator = wheres.iterator();
		if (iterator.hasNext()) {
			sql.append(" " + iterator.next());
		}
		while (iterator.hasNext()) {
			sql.append(" and " + iterator.next());
		}
	}
}
