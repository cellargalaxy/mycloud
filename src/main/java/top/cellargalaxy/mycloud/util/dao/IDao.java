package top.cellargalaxy.mycloud.util.dao;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/8/9
 */
public interface IDao<Po, Bo extends Po, Query extends Po> {
	int insert(Po po);

	int delete(Po po);

	int update(Po po);

	Bo selectOne(Po po);

	List<Bo> selectPageSome(Query query);

	List<Bo> selectAllSome(Query query);

	int selectCount(Query query);

	List<Bo> selectAll();
}
