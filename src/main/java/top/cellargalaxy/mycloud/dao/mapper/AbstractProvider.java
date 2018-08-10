package top.cellargalaxy.mycloud.dao.mapper;

/**
 * @author cellargalaxy
 * @time 2018/8/10
 */
public interface AbstractProvider<Po, Query extends Po> {
	String insert(Po po);

	String delete(Po po);

	String selectOne(Po po);

	String selectSome(Query query);

	String selectCount(Query query);

	String selectAll();

	String update(Po po);
}
