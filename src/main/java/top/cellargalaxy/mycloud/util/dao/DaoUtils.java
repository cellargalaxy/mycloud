package top.cellargalaxy.mycloud.util.dao;

/**
 * @author cellargalaxy
 * @time 18-12-20
 */
public class DaoUtils {

	public static void main(String[] args) {
		System.out.println(assemblyCode("User"));
	}

	public static final String assemblyCode(String beanName) {
		String lowBeanName = beanName.toLowerCase();
		return "\t@Options(useGeneratedKeys = true, keyProperty = \"" + lowBeanName + "Id\")\n" +
				"\t@InsertProvider(type = " + beanName + "Provider.class, method = \"insert\")\n" +
				"\tint insert(" + beanName + "Po " + lowBeanName + "Po);\n" +
				"\n" +
				"\t@DeleteProvider(type = " + beanName + "Provider.class, method = \"delete\")\n" +
				"\tint delete(" + beanName + "Po " + lowBeanName + "Po);\n" +
				"\n" +
				"\t@UpdateProvider(type = " + beanName + "Provider.class, method = \"update\")\n" +
				"\tint update(" + beanName + "Po " + lowBeanName + "Po);\n" +
				"\n" +
				"\t@Results(id = \"" + lowBeanName + "Results\", value = {\n" +
				"\t\t\t@Result(property = \"" + lowBeanName + "Id\", column = \"" + lowBeanName + "_id\"),\n" +
				"\t})\n" +
				"\t@SelectProvider(type = " + beanName + "Provider.class, method = \"selectOne\")\n" +
				"\t" + beanName + "Bo selectOne(" + beanName + "Po " + lowBeanName + "Po);\n" +
				"\n" +
				"\t@ResultMap(value = \"" + lowBeanName + "Results\")\n" +
				"\t@SelectProvider(type = " + beanName + "Provider.class, method = \"selectPageSome\")\n" +
				"\tList<" + beanName + "Bo> selectPageSome(" + beanName + "Query " + lowBeanName + "Query);\n" +
				"\n" +
				"\t@ResultMap(value = \"" + lowBeanName + "Results\")\n" +
				"\t@SelectProvider(type = " + beanName + "Provider.class, method = \"selectAllSome\")\n" +
				"\tList<" + beanName + "Bo> selectAllSome(" + beanName + "Query " + lowBeanName + "Query);\n" +
				"\n" +
				"\t@SelectProvider(type = " + beanName + "Provider.class, method = \"selectCount\")\n" +
				"\tint selectCount(" + beanName + "Query " + lowBeanName + "Query);\n" +
				"\n" +
				"\t@ResultMap(value = \"" + lowBeanName + "Results\")\n" +
				"\t@SelectProvider(type = " + beanName + "Provider.class, method = \"selectAll\")\n" +
				"\tList<" + beanName + "Bo> selectAll();\n" +
				"\n" +
				"\tclass " + beanName + "Provider /*implements IProvider<" + beanName + "Po," + beanName + "Query>*/ {\n" +
				"\t\tprivate final String tableName = " + beanName + "Dao.TABLE_NAME;\n" +
				"\n" +
				"\t\tpublic Set<String> wheresKey(" + beanName + "Po " + lowBeanName + "Po) {\n" +
				"\t\t\tSet<String> wheres=new HashSet<>();\n" +
				"\t\t\t\n" +
				"\t\t\treturn wheres;\n" +
				"\t\t}\n" +
				"\n" +
				"\t\tpublic Set<String> wheresAll(" + beanName + "Query " + lowBeanName + "Query) {\n" +
				"\t\t\tSet<String> wheres=new HashSet<>();\n" +
				"\t\t\t\n" +
				"\t\t\treturn wheres;\n" +
				"\t\t}\n" +
				"\n" +
				"\t\tpublic Set<String> sets(" + beanName + "Po " + lowBeanName + "Po) {\n" +
				"\t\t\tSet<String> sets=new HashSet<>();\n" +
				"\t\t\t\n" +
				"\t\t\treturn sets;\n" +
				"\t\t}\n" +
				"\n" +
				"\t\tpublic String insert(" + beanName + "Po " + lowBeanName + "Po) {\n" +
				"\t\t\tString string = ProviderUtils.insert(tableName, " + beanName + "Po.class).toString();\n" +
				"\t\t\treturn string;\n" +
				"\t\t}\n" +
				"\n" +
				"\t\tpublic String delete(" + beanName + "Po " + lowBeanName + "Po) {\n" +
				"\t\t\tString string = ProviderUtils.limitOne(ProviderUtils.delete(tableName, wheresKey(" + lowBeanName + "Po))).toString();\n" +
				"\t\t\treturn string;\n" +
				"\t\t}\n" +
				"\t\t\n" +
				"\t\tpublic String update(" + beanName + "Po " + lowBeanName + "Po) {\n" +
				"\t\t\tString string = ProviderUtils.limitOne(ProviderUtils.update(tableName, sets(" + lowBeanName + "Po), \"defaultSet\", wheresKey(" + lowBeanName + "Po))).toString();\n" +
				"\t\t\treturn string;\n" +
				"\t\t}\n" +
				"\t\t\n" +
				"\t\tpublic String selectOne(" + beanName + "Po " + lowBeanName + "Po) {\n" +
				"\t\t\tString string = ProviderUtils.limitOne(ProviderUtils.select(tableName, null, wheresKey(" + lowBeanName + "Po))).toString();\n" +
				"\t\t\treturn string;\n" +
				"\t\t}\n" +
				"\n" +
				"\t\tpublic String selectPageSome(" + beanName + "Query " + lowBeanName + "Query) {\n" +
				"\t\t\tSqlUtils.initPageQuery(" + lowBeanName + "Query);\n" +
				"\t\t\tString string = ProviderUtils.limitSome(ProviderUtils.select(tableName, null, wheresAll(" + lowBeanName + "Query))).toString();\n" +
				"\t\t\treturn string;\n" +
				"\t\t}\n" +
				"\n" +
				"\t\tpublic String selectAllSome(" + beanName + "Query " + lowBeanName + "Query) {\n" +
				"\t\t\tString string = ProviderUtils.select(tableName, null, wheresAll(" + lowBeanName + "Query)).toString();\n" +
				"\t\t\treturn string;\n" +
				"\t\t}\n" +
				"\t\t\n" +
				"\t\tpublic String selectCount(" + beanName + "Query " + lowBeanName + "Query) {\n" +
				"\t\t\tString string = ProviderUtils.selectCount(tableName, wheresAll(" + lowBeanName + "Query)).toString();\n" +
				"\t\t\treturn string;\n" +
				"\t\t}\n" +
				"\t\t\n" +
				"\t\tpublic String selectAll() {\n" +
				"\t\t\tString string = ProviderUtils.selectAll(tableName, null).toString();\n" +
				"\t\t\treturn string;\n" +
				"\t\t}\n" +
				"\t}";
	}
}
