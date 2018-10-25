package top.cellargalaxy.mycloud.util;

import top.cellargalaxy.mycloud.dao.AbstractDao;

import java.util.function.Function;

/**
 * Created by cellargalaxy on 18-9-26.
 */
public class ServiceUtil {
	public static final <Po, Bo extends Po, Query extends Po> String add(Po po, String name, Function<Po, String> checkAdd, AbstractDao<Po, Bo, Query> abstractDao) {
		String string = checkAdd.apply(po);
		if (string != null) {
			return string;
		}
		int i = abstractDao.insert(po);
		if (i == 0) {
			return name + "空新增";
		}
		return null;
	}

	public static final <Po, Bo extends Po, Query extends Po> String remove(Po po, String name, AbstractDao<Po, Bo, Query> abstractDao) {
		int i = abstractDao.delete(po);
		if (i == 0) {
			return name + "空删除";
		}
		return null;
	}

	public static final <Po, Bo extends Po, Query extends Po> String change(Po po, String name, Function<Po, String> checkChange, AbstractDao<Po, Bo, Query> abstractDao) {
		String string = checkChange.apply(po);
		if (string != null) {
			return string;
		}
		int i = abstractDao.update(po);
		if (i == 0) {
			return name + "空更新";
		}
		return null;
	}

	public static final <Po, Bo extends Po, Query extends Po> String checkAdd(Po po, String name, Function<Po, String> checkInsert, AbstractDao<Po, Bo, Query> abstractDao) {
		String string = checkInsert.apply(po);
		if (string != null) {
			return string;
		}
		Bo bo = abstractDao.selectOne(po);
		if (bo != null) {
			return name + "已存在";
		}
		return null;
	}

	public static final <Po, Bo extends Po, Query extends Po> String checkChange(Po po, String name, Function<Po, String> checkUpdate, AbstractDao<Po, Bo, Query> abstractDao) {
		String string = checkUpdate.apply(po);
		if (string != null) {
			return string;
		}
		Bo bo = abstractDao.selectOne(po);
		if (bo == null) {
			return name + "不存在";
		}
		return null;
	}
}
