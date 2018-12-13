package top.cellargalaxy.mycloud.util.dao;

import top.cellargalaxy.mycloud.util.model.PageQuery;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author cellargalaxy
 * @time 2018/7/9
 */
public class SqlUtils {
    public static final int MAX_PAGE_SIZE = 100;

    public static final void initPageQuery(PageQuery pageQuery) {
        int off = (pageQuery.getPage() - 1) * pageQuery.getPageSize();//[0,00)
        int len = pageQuery.getPageSize();//[0,MAX_PAGE_SIZE]
        if (off < 0) {
            off = 0;
            len = 0;
        } else if (len < 0) {
            len = 0;
        } else if (len > pageQuery.getMaxPageSize()) {
            len = pageQuery.getMaxPageSize();
        }
        pageQuery.setOff(off);
        pageQuery.setLen(len);
    }

    public static final StringBuilder createDeleteSql(String tableName, Set<String> wheres) {
        StringBuilder sql = new StringBuilder("delete from " + tableName);
        addWhere(sql, wheres, "false");
        return sql;
    }

    public static final StringBuilder createSelectSql(List<String> selects, String tableName, Set<String> wheres) {
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

        sql.append(" from " + tableName);
        addWhere(sql, wheres, "true");
        return sql;
    }

    public static final StringBuilder createUpdateSql(String tableName, Set<String> sets, String defaultSet, Set<String> wheres) {
        StringBuilder sql = new StringBuilder("update " + tableName + " set");
        if (sets == null || sets.size() == 0) {
            sql.append(" " + defaultSet);
        } else {
            Iterator<String> iterator = sets.iterator();
            if (iterator.hasNext()) {
                sql.append(" " + iterator.next());
            }
            while (iterator.hasNext()) {
                sql.append(", " + iterator.next());
            }
        }

        addWhere(sql, wheres, "false");
        return sql;
    }

    private static final void addWhere(StringBuilder sql, Set<String> wheres, String defaultWhere) {
        if (wheres == null || wheres.size() == 0) {
            sql.append(" where " + defaultWhere);
            return;
        }
        Iterator<String> iterator = wheres.iterator();
        if (iterator.hasNext()) {
            sql.append(" where " + iterator.next());
        }
        while (iterator.hasNext()) {
            sql.append(" and " + iterator.next());
        }
    }
}
