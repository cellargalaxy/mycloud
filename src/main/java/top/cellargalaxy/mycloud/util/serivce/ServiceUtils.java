package top.cellargalaxy.mycloud.util.serivce;

import top.cellargalaxy.mycloud.util.dao.IDao;

import java.util.function.Function;

/**
 * Created by cellargalaxy on 18-9-26.
 */
public class ServiceUtils {
    public static final <Po, Bo extends Po, Query extends Po> String add(Po po, String name, Function<Po, String> checkAdd, IDao<Po, Bo, Query> iDao) {
        String string = checkAdd.apply(po);
        if (string != null) {
            return string;
        }
        int i = iDao.insert(po);
        if (i == 0) {
            return name + "空新增";
        }
        return null;
    }

    public static final <Po, Bo extends Po, Query extends Po> String remove(Po po, String name, IDao<Po, Bo, Query> iDao) {
        int i = iDao.delete(po);
        if (i == 0) {
            return name + "空删除";
        }
        return null;
    }

    public static final <Po, Bo extends Po, Query extends Po> String change(Po po, String name, Function<Po, String> checkChange, IDao<Po, Bo, Query> iDao) {
        String string = checkChange.apply(po);
        if (string != null) {
            return string;
        }
        int i = iDao.update(po);
        if (i == 0) {
            return name + "空更新";
        }
        return null;
    }
}
