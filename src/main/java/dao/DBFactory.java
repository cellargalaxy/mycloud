package dao;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Set;

/**
 * Created by cellargalaxy on 17-10-15.
 */
public class DBFactory {
	private static final String PATH = new File(DBFactory.class.getResource("").getPath()).getParentFile().getAbsolutePath() + "/";
	private static final File DB_CONF_FILE = new File(PATH + "db.properties");
	private static DB[] dbs;
	
	public static DB[] getDbs() {
		if (dbs == null) {
			try {
				Properties properties = new Properties();
				properties.load(new InputStreamReader(new FileInputStream(DB_CONF_FILE)));
				Set<String> names = properties.stringPropertyNames();
				dbs = new DB[names.size()];
				int i = 0;
				for (String name : names) {
					DB db = new MysqlDB(new File(PATH + properties.getProperty(name)));
					dbs[i] = db;
					i++;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dbs;
	}
}
