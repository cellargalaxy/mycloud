package top.cellargalaxy.service;

import top.cellargalaxy.bean.daoBean.FilePackage;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by cellargalaxy on 17-12-5.
 */
public interface FilePackageService {
	File moveFileToDrive(File tmpFile, Date date);
	
	FilePackage fillingFilePackageInfoAttributes(FilePackage filePackage);
	
	FilePackage fillingFilePackageAllAttributes(FilePackage filePackage);
	
	boolean writeFileBytes(FilePackage filePackage);
	
	LinkedList<FilePackage> getAllFilePackageFromDrive();
}
