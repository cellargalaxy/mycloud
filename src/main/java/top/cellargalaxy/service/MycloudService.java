package top.cellargalaxy.service;

import top.cellargalaxy.bean.FilePackage;

import java.io.File;
import java.util.Date;

/**
 * Created by cellargalaxy on 17-12-2.
 */
public interface MycloudService {
	boolean checkToken(String token);
	
	int getFailBackupCount();
	
	int getSuccessBackupCount();
	
	int getWaitBackupCount();
	
	int getFailRestoreCount();
	
	int getSuccessRestoreCount();
	
	int getWaitRestoreCount();
	
	String[] createPages(int page);
	
	FilePackage[] getFilePackages(int page);
	
	FilePackage[] getFilePackageByDate(Date date);
	
	FilePackage[] getFilePackageByFilename(String filename);
	
	FilePackage[] getFilePackageByDescription(String description);
	
	FilePackage[] getAllFilePackage();
	
	FilePackage createFilePackage(File file, Date date, String description);
	
	FilePackage createFilePackage(String filename, Date date, String description);
	
	boolean backupFilePackage(FilePackage filePackage);
	
	boolean restoreFilePackage(FilePackage filePackage);
	
	boolean removeFilePackage(FilePackage filePackage);
	
	boolean backupAllFilePackage();
	
	boolean restoreAllFilePackage();
}
