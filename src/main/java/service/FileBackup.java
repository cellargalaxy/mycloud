package service;

import bean.DBPackage;
import bean.FilePackage;

/**
 * Created by cellargalaxy on 17-10-15.
 */
public interface FileBackup {
	void backupFile(FilePackage filePackage);
	
	void restore(String dbName, FilePackage filePackage);
	
	void restore(String dbName);
	
	DBPackage findDBPackage(String dbName);
	
	boolean deleteFilePackage(String dbName, FilePackage filePackage);
	
	int getBackupCount();
	
	int getYetBackup();
	
	int getRestoreCount();
	
	int getYetRestore();
}
