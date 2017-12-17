package top.cellargalaxy.service;

import top.cellargalaxy.bean.FilePackage;

/**
 * Created by cellargalaxy on 17-12-5.
 */
public interface FilePackageRestore {
	int getFailRestoreCount();
	
	int getSuccessRestoreCount();
	
	int getWaitRestoreCount();
	
	boolean restoreFilePackage(FilePackage filePackage);
	
	boolean restoreFilePackages(FilePackage[] filePackages);
}
