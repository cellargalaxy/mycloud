package top.cellargalaxy.mycloud.util.dao;

import top.cellargalaxy.mycloud.util.model.PageQuery;

import java.util.Set;

/**
 * @author cellargalaxy
 * @time 2018/12/13
 */
public class ProviderDemo<Po, Query extends PageQuery> implements IProvider<Po, Query> {
	private final String tableName;

	public ProviderDemo(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public Set<String> wheresKey(Po po) {
		return null;
	}

	@Override
	public Set<String> wheresAll(Query query) {
		return null;
	}

	@Override
	public Set<String> sets(Po po) {
		return null;
	}

	@Override
	public String insert(Po po) {
		String string = ProviderUtils.insert(tableName, po.getClass()).toString();
		return string;
	}

	@Override
	public String delete(Po po) {
		String string = ProviderUtils.limitOne(ProviderUtils.delete(tableName, wheresKey(po))).toString();
		return string;
	}

	@Override
	public String update(Po po) {
		String string = ProviderUtils.limitOne(ProviderUtils.update(tableName, sets(po), "defaultSet", wheresKey(po))).toString();
		return string;
	}

	@Override
	public String selectOne(Po po) {
		String string = ProviderUtils.limitOne(ProviderUtils.select(tableName, null, wheresKey(po))).toString();
		return string;
	}

	@Override
	public String selectPageSome(Query query) {
		SqlUtils.initPageQuery(query);
		String string = ProviderUtils.limitSome(ProviderUtils.select(tableName, null, wheresAll(query))).toString();
		return string;
	}

	@Override
	public String selectAllSome(Query query) {
		String string = ProviderUtils.select(tableName, null, wheresAll(query)).toString();
		return string;
	}

	@Override
	public String selectCount(Query query) {
		String string = ProviderUtils.selectCount(tableName, wheresAll(query)).toString();
		return string;
	}

	@Override
	public String selectAll() {
		String string = ProviderUtils.selectAll(tableName, null).toString();
		return string;
	}
}
