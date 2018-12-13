package top.cellargalaxy.mycloud.util.dao;

import java.util.Set;

/**
 * @author cellargalaxy
 * @time 2018/8/10
 */
public interface IProvider<Po, Query> {
    void wheresKey(Po po, Set<String> wheres);

    void wheresAll(Query query, Set<String> wheres);

    void sets(Po po, Set<String> sets);

    String insert(Po po);

    String delete(Po po);

    String update(Po po);

    String selectOne(Po po);

    String selectPageSome(Query query);

    String selectAllSome(Query query);

    String selectCount(Query query);

    String selectAll();
}
