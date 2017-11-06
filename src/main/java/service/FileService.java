package service;

import bean.DBPackage;
import bean.FilePackage;
import dao.DB;

import java.util.Date;

/**
 * Created by cellargalaxy on 17-10-15.
 */
public interface FileService {
	void backup(FilePackage filePackage);
	
	void restore(String dbName, FilePackage filePackage);
	
	void restore(String dbName);
	
	DBPackage findDB(String dbName);
	
	DBPackage findDBByDate(String dbName, Date uploadDate);
	
	DBPackage findDBByDescription(String dbName, String description);
	
	boolean deleteFile(String dbName, FilePackage filePackage);
	
	int getBackupCount();
	
	int getYetBackup();
	
	int getRestoreCount();
	
	int getYetRestore();
	
	DB[] getDbs();
}
