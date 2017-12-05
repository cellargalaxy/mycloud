package top.cellargalaxy.service;

import top.cellargalaxy.bean.FilePackage;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by cellargalaxy on 17-12-5.
 */
public interface FilePackageService {
	FilePackage createFilePackage(File file, Date date, String description);
	
	FilePackage createFilePackage(String filename, Date date, String description);
	
	FilePackage createFilePackage(File moveFile, String description);
	
	void fillingAttributes(FilePackage filePackage);
	
	List<FilePackage> getAllFilePackageFromDrive();
	
	boolean readFileBytes(FilePackage filePackage);
	
	boolean writeFileBytes(FilePackage filePackage);
}
