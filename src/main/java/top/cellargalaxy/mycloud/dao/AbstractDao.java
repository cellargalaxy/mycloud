package top.cellargalaxy.mycloud.dao;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/8/9
 */
public interface AbstractDao<Po, Bo extends Po, Query extends Po> {
	int insert(Po po);

	int delete(Po po);

	Bo selectOne(Po po);

	List<Bo> selectSome(Query query);

	int selectCount(Query query);

	List<Bo> selectAll();

	int update(Po po);
}
