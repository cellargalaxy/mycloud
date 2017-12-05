package top.cellargalaxy.service;

import top.cellargalaxy.bean.FilePackage;

/**
 * Created by cellargalaxy on 17-12-5.
 */
public interface FilePackageBackup {
	int getFailBackupCount();
	
	int getSuccessBackupCount();
	
	int getWaitBackupCount();
	
	boolean backupFilePackage(FilePackage filePackage);
}
