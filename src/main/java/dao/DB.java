package dao;

import bean.FilePackage;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by cellargalaxy on 17-10-15.
 */
public abstract class DB {
	private DataSource dataSource;
	private final String dbName;
	
	public DB(File confFile) throws Exception {
		Properties properties = new Properties();
		properties.load(new BufferedInputStream(new FileInputStream(confFile)));
		dataSource = DruidDataSourceFactory.createDataSource(properties);
		dbName = confFile.getName();
	}
	
	Connection createConnection() {
		if (dataSource != null) {
			try {
				return dataSource.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public abstract boolean insertFile(FilePackage filePackage);
	
	public abstract FilePackage[] selectAllFilePackageInfo();
	
	public abstract FilePackage selectFilePackageInfo(FilePackage filePackage);
	
	public abstract boolean selectFilePackageBlob(FilePackage filePackage);
	
	public abstract boolean deleteFilePackage(FilePackage filePackage);
	
	public String getDbName() {
		return dbName;
	}
}
