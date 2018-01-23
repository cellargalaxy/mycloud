package top.cellargalaxy.service;

import top.cellargalaxy.bean.controlorBean.Page;
import top.cellargalaxy.bean.daoBean.FilePackage;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;

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
	
	Page[] createPages(int page);
	
	FilePackage findFilePackageByFileSha256(String fileSha256);
	
	FilePackage[] findFilePackagesByFilename(String filename);
	
	FilePackage[] findFilePackagesByDate(Date date);
	
	FilePackage[] findFilePackagesByDescription(String description);
	
	FilePackage[] findFilePackages(int page);
	
	LinkedList<FilePackage> findAllFilePackage();
	
	FilePackage moveFileAndcreateFilePackage(File file, Date date, String description);
	
	FilePackage createFilePackage(String filename, Date date, String description);
	
	boolean backupFilePackage(FilePackage filePackage);
	
	boolean restoreFilePackage(FilePackage filePackage);
	
	boolean removeFilePackage(FilePackage filePackage);
	
	boolean backupAllFilePackage();
	
	boolean restoreAllFilePackage();
}
