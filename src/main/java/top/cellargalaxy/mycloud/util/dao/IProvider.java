package top.cellargalaxy.mycloud.util.dao;

import java.util.Set;

/**
 * @author cellargalaxy
 * @time 2018/8/10
 */
public interface IProvider<Po, Query> {
	Set<String> wheresKey(Po po);

	Set<String> wheresAll(Query query);

	Set<String> sets(Po po);

	String insert(Po po);

	String delete(Po po);

	String update(Po po);

	String selectOne(Po po);

	String selectPageSome(Query query);

	String selectAllSome(Query query);

	String selectCount(Query query);

	String selectAll();
}
