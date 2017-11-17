package dao;

import java.io.File;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by cellargalaxy on 17-10-15.
 */
public class DBFactory {
	private static LinkedList<DB> dbs;
	
	public static LinkedList<DB> getDbs(Map<File, String> dbConfigurations) {
		if (dbs == null) {
			dbs = new LinkedList<DB>();
			for (Map.Entry<File, String> entry : dbConfigurations.entrySet()) {
				if (entry.getValue() == null) {
					continue;
				}
				if (entry.getValue().toLowerCase().trim().equals("mysql")) {
					try {
						dbs.add(new MysqlDB(entry.getKey()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return dbs;
	}
}
