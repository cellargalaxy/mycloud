package top.cellargalaxy.mycloud.util.dao;

import org.apache.ibatis.jdbc.SQL;
import top.cellargalaxy.mycloud.util.StringUtils;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by cellargalaxy on 18-8-4.
 */
public class ProviderUtils {

	public static final SQL selectAll(String tableName, Set<String> selectFieldNames) {
		return select(tableName, selectFieldNames, Collections.EMPTY_SET);
	}

	public static final SQL selectCount(String tableName, Set<String> whereFieldNames) {
		SQL sql = new SQL().SELECT("count(*)").FROM(tableName);
		return whereTrue(sql, tableName, whereFieldNames);
	}

	public static final SQL select(SQL sql, String tableName, Class<?> clazz, String... ignores) {
		createSelect(tableName, clazz, ignores).stream().forEach(select -> sql.SELECT(select));
		return sql;
	}

	public static final SQL select(String tableName, Set<String> selectFieldNames) {
		SQL sql = new SQL();
		createSelect(tableName, selectFieldNames).stream().forEach(select -> sql.SELECT(select));
		return sql;
	}

	public static final SQL select(String tableName, Set<String> selectFieldNames, Set<String> whereFieldNames) {
		SQL sql = new SQL();
		createSelect(tableName, selectFieldNames).stream().forEach(select -> sql.SELECT(select));
		sql.FROM(tableName);
		createWhereTrue(tableName, whereFieldNames).stream().forEach(where -> sql.WHERE(where));
		return sql;
	}

	public static final SQL update(String tableName, Set<String> setFieldNames, String defaultSet, Set<String> whereFieldNames) {
		SQL sql = new SQL().UPDATE(tableName);
		createSet(null, setFieldNames, defaultSet).stream().forEach(set -> sql.SET(set));
		createWhereFalse(null, whereFieldNames).stream().forEach(where -> sql.WHERE(where));
		return sql;
	}

	public static final SQL delete(String tableName, Set<String> whereFieldNames) {
		SQL sql = new SQL().DELETE_FROM(tableName);
		createWhereFalse(null, whereFieldNames).stream().forEach(where -> sql.WHERE(where));
		return sql;
	}

	public static final SQL insert(String tableName, Class<?> clazz, String... ignores) {
		Set<String> fieldNames = createFieldName(clazz, ignores);
		return insert(tableName, fieldNames);
	}

	public static final SQL insert(String tableName, Set<String> fieldNames) {
		SQL sql = new SQL().INSERT_INTO(tableName);
		fieldNames.stream().forEach(fieldName -> sql.VALUES(StringUtils.lowerCamel2LowerHyphen(fieldName), field(fieldName)));
		return sql;
	}

	public static final SQL whereTrue(SQL sql, String tableName, Set<String> whereFieldNames) {
		createWhereTrue(tableName, whereFieldNames).stream().forEach(where -> sql.WHERE(where));
		return sql;
	}

	public static final SQL whereFalse(SQL sql, String tableName, Set<String> whereFieldNames) {
		createWhereFalse(tableName, whereFieldNames).stream().forEach(where -> sql.WHERE(where));
		return sql;
	}

	public static final Set<String> createSelect(String tableName, Class<?> clazz, String... ignores) {
		Set<String> selectFieldNames = createFieldName(clazz, ignores);
		return createSelect(tableName, selectFieldNames);
	}

	public static final Set<String> createSelect(String tableName, Set<String> fieldNames) {
		if (fieldNames == null || fieldNames.size() == 0) {
			Set<String> select = new HashSet<>();
			select.add("*");
			return select;
		}
		return fieldNames.stream().map(select -> column(tableName, select)).collect(Collectors.toSet());
	}

	public static final Set<String> createSet(String tableName, Set<String> fieldNames, String defaultSet) {
		if (fieldNames == null || fieldNames.size() == 0) {
			Set<String> sets = new HashSet<>();
			sets.add(equalSign(tableName, defaultSet));
			return sets;
		}
		return fieldNames.stream().map(fieldName -> equalSign(tableName, fieldName)).collect(Collectors.toSet());
	}

	public static final Set<String> createWhereTrue(String tableName, Set<String> fieldNames) {
		if (fieldNames == null || fieldNames.size() == 0) {
			Set<String> wheres = new HashSet<>();
			wheres.add("1");
			return wheres;
		}
		return fieldNames.stream().map(fieldName -> equalSign(tableName, fieldName)).collect(Collectors.toSet());
	}

	public static final Set<String> createWhereFalse(String tableName, Set<String> fieldNames) {
		if (fieldNames == null || fieldNames.size() == 0) {
			Set<String> wheres = new HashSet<>();
			wheres.add("0");
			return wheres;
		}
		return fieldNames.stream().map(fieldName -> equalSign(tableName, fieldName)).collect(Collectors.toSet());
	}

	public static final Set<String> createFieldName(Class<?> clazz, String... ignores) {
		Set<String> fieldNames = Arrays.stream(clazz.getDeclaredFields())
				.filter(field -> {
					String modifier = Modifier.toString(field.getModifiers());
					return !modifier.contains("static") && !modifier.contains("final");
				})
				.map(field -> field.getName()).collect(Collectors.toSet());
		for (String ignore : ignores) {
			fieldNames.remove(ignore);
		}
		return fieldNames;
	}

	/**
	 * table_name.field_name=#{fieldName}
	 *
	 * @param tableName
	 * @param fieldName
	 * @return
	 */
	public static final String equalSign(String tableName, String fieldName) {
		return column(tableName, fieldName) + "=" + field(fieldName);
	}

	/**
	 * fieldName > #{fieldName}
	 *
	 * @param fieldName
	 * @return
	 */
	public static final String field(String fieldName) {
		return "#{" + fieldName + "}";
	}

	/**
	 * tableName.fieldName > table_name.field_name
	 *
	 * @param tableName
	 * @param fieldName
	 * @return
	 */
	public static final String column(String tableName, String fieldName) {
		if (StringUtils.isBlank(tableName)) {
			return StringUtils.lowerCamel2LowerHyphen(fieldName);
		} else {
			return StringUtils.lowerCamel2LowerHyphen(tableName) + "." + StringUtils.lowerCamel2LowerHyphen(fieldName);
		}
	}

	public static final StringBuilder limitOne(SQL sql) {
		return new StringBuilder(sql.toString()).append(" limit 1");
	}

	public static final StringBuilder limitSome(SQL sql) {
		return new StringBuilder(sql.toString()).append(" limit #{off},#{len}");
	}

}
