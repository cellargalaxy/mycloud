package top.cellargalaxy.mycloud.util.dao;

import top.cellargalaxy.mycloud.util.model.PageQuery;

/**
 * @author cellargalaxy
 * @time 2018/12/13
 */
@Deprecated
public abstract class AbstractProvider<Po, Query extends PageQuery> implements IProvider<Po, Query> {
    private final String tableName;

    public AbstractProvider(String tableName) {
        this.tableName = tableName;
    }

    protected abstract String getDefaultSet();

    @Override
    public String insert(Po po) {
        String string = ProviderUtils.insert(tableName, po).toString();
        return string;
    }

    @Override
    public String delete(Po po) {
        String string = ProviderUtils.limitOne(ProviderUtils.delete(tableName, po, this::wheresKey)).toString();
        return string;
    }

    @Override
    public String update(Po po) {
        String string = ProviderUtils.limitOne(ProviderUtils.update(tableName, po, getDefaultSet(), this::sets, this::wheresKey)).toString();
        return string;
    }

    @Override
    public String selectOne(Po po) {
        String string = ProviderUtils.limitOne(ProviderUtils.select(tableName, po, this::wheresKey)).toString();
        return string;
    }

    @Override
    public String selectPageSome(Query query) {
        SqlUtils.initPageQuery(query);
        String string = ProviderUtils.limitSome(ProviderUtils.select(tableName, query, this::wheresAll)).toString();
        return string;
    }

    @Override
    public String selectAllSome(Query query) {
        String string = ProviderUtils.select(tableName, query, this::wheresAll).toString();
        return string;
    }

    @Override
    public String selectCount(Query query) {
        String string = ProviderUtils.selectCount(tableName, query, this::wheresAll).toString();
        return string;
    }

    @Override
    public String selectAll() {
        String string = ProviderUtils.selectAll(tableName).toString();
        return string;
    }
}
