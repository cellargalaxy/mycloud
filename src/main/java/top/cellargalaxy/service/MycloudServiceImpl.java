package top.cellargalaxy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import top.cellargalaxy.bean.controlorBean.Page;
import top.cellargalaxy.bean.daoBean.FilePackage;
import top.cellargalaxy.configuration.MycloudConfiguration;
import top.cellargalaxy.dao.FilePackageMapper;

import java.io.File;
import java.util.Date;
import java.util.LinkedList;

/**
 * Created by cellargalaxy on 17-12-2.
 */
@Service
@Transactional
public class MycloudServiceImpl implements MycloudService {
	@Autowired
	private MycloudConfiguration configuration;
	@Autowired
	private FilePackageMapper filePackageMapper;
	@Autowired
	private FilePackageService filePackageService;
	@Autowired
	private FilePackageBackup filePackageBackup;
	@Autowired
	private FilePackageRestore filePackageRestore;
	
	@Override
	public boolean checkToken(String token) {
		try {
			return token != null && token.equals(configuration.getToken());
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}
	
	@Override
	public int getFailBackupCount() {
		try {
			return filePackageBackup.getFailBackupCount();
		} catch (Exception e) {
			dealException(e);
		}
		return 0;
	}
	
	@Override
	public int getSuccessBackupCount() {
		try {
			return filePackageBackup.getSuccessBackupCount();
		} catch (Exception e) {
			dealException(e);
		}
		return 0;
	}
	
	@Override
	public int getWaitBackupCount() {
		try {
			return filePackageBackup.getWaitBackupCount();
		} catch (Exception e) {
			dealException(e);
		}
		return 0;
	}
	
	@Override
	public int getFailRestoreCount() {
		try {
			return filePackageRestore.getFailRestoreCount();
		} catch (Exception e) {
			dealException(e);
		}
		return 0;
	}
	
	@Override
	public int getSuccessRestoreCount() {
		try {
			return filePackageRestore.getSuccessRestoreCount();
		} catch (Exception e) {
			dealException(e);
		}
		return 0;
	}
	
	@Override
	public int getWaitRestoreCount() {
		try {
			return filePackageRestore.getWaitRestoreCount();
		} catch (Exception e) {
			dealException(e);
		}
		return 0;
	}
	
	@Override
	public Page[] createPages(int page) {
		try {
			int count = filePackageMapper.selectFilePackageCount();
			int len = configuration.getListFileLength();
			return createPages(page, count, len);
		} catch (Exception e) {
			dealException(e);
		}
		return new Page[0];
	}
	
	@Override
	public FilePackage findFilePackageByFileSha256(String fileSha256) {
		try {
			return filePackageService.fillingFilePackageInfoAttributes(filePackageMapper.selectFilePackageByFileSha256(fileSha256));
		} catch (Exception e) {
			dealException(e);
		}
		return null;
	}
	
	@Override
	public FilePackage[] findFilePackages(int page) {
		try {
			int len = configuration.getListFileLength();
			int off = (page - 1) * len;
			return fillingFilePackagesInfoAttributes(filePackageMapper.selectFilePackages(off, len));
		} catch (Exception e) {
			dealException(e);
		}
		return new FilePackage[0];
	}
	
	@Override
	public FilePackage[] findFilePackagesByDate(Date date) {
		try {
			return fillingFilePackagesInfoAttributes(filePackageMapper.selectFilePackagesByDate(date));
		} catch (Exception e) {
			dealException(e);
		}
		return new FilePackage[0];
	}
	
	@Override
	public FilePackage[] findFilePackagesByFilename(String filename) {
		try {
			return fillingFilePackagesInfoAttributes(filePackageMapper.selectFilePackagesByFilename(filename));
		} catch (Exception e) {
			dealException(e);
		}
		return new FilePackage[0];
	}
	
	@Override
	public FilePackage[] findFilePackagesByDescription(String description) {
		try {
			return fillingFilePackagesInfoAttributes(filePackageMapper.selectFilePackagesByDescription(description));
		} catch (Exception e) {
			dealException(e);
		}
		return new FilePackage[0];
	}
	
	@Override
	public LinkedList<FilePackage> findAllFilePackage() {
		try {
			return fillingFilePackagesInfoAttributes(filePackageMapper.selectAllFilePackage());
		} catch (Exception e) {
			dealException(e);
		}
		return null;
	}
	
	@Override
	public FilePackage moveFileAndcreateFilePackage(File tmpFile, Date date, String description) {
		if (tmpFile == null || date == null) {
			return null;
		}
		File driveFile = filePackageService.moveFileToDrive(tmpFile, date);
		if (driveFile == null) {
			return null;
		}
		FilePackage filePackage = new FilePackage();
		filePackage.setFile(driveFile);
		filePackage.setDate(date);
		filePackage.setDescription(description);
		return filePackageService.fillingFilePackageInfoAttributes(filePackage);
	}
	
	@Override
	public FilePackage createFilePackage(String filename, Date date, String description) {
		if (filename == null || date == null) {
			return null;
		}
		FilePackage filePackage = new FilePackage();
		filePackage.setFilename(filename);
		filePackage.setDate(date);
		filePackage.setDescription(description);
		return filePackageService.fillingFilePackageInfoAttributes(filePackage);
	}
	
	@Override
	public boolean backupFilePackage(FilePackage filePackage) {
		try {
			return filePackageBackup.backupFilePackage(filePackage);
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}
	
	@Override
	public boolean restoreFilePackage(FilePackage filePackage) {
		try {
			return filePackageRestore.restoreFilePackage(filePackage);
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}
	
	@Override
	public boolean removeFilePackage(FilePackage filePackage) {
		try {
			if (filePackage == null) {
				return false;
			}
			File file = filePackage.getFile();
			boolean drive = file != null && (!file.exists() || file.delete());
			boolean db = filePackageMapper.deleteFilePackageByFilenameAndDate(filePackage.getFilename(), filePackage.getDate()) > 0 ||
					filePackageMapper.deleteFilePackageByFileSha256(filePackage.getFileSha256()) > 0;
			return drive && db;
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}
	
	@Override
	public boolean backupAllFilePackage() {
		try {
			return filePackageBackup.backupFilePackages(filePackageService.getAllFilePackageFromDrive());
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}
	
	@Override
	public boolean restoreAllFilePackage() {
		try {
			return filePackageRestore.restoreFilePackages(filePackageMapper.selectAllFilePackage());
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}
	
	private final FilePackage[] fillingFilePackagesInfoAttributes(FilePackage[] filePackages) {
		if (filePackages == null) {
			return null;
		}
		for (FilePackage filePackage : filePackages) {
			filePackageService.fillingFilePackageInfoAttributes(filePackage);
		}
		return filePackages;
	}
	
	private final LinkedList<FilePackage> fillingFilePackagesInfoAttributes(LinkedList<FilePackage> filePackages) {
		if (filePackages == null) {
			return null;
		}
		for (FilePackage filePackage : filePackages) {
			filePackageService.fillingFilePackageInfoAttributes(filePackage);
		}
		return filePackages;
	}
	
	private final Page[] createPages(int page, int count, int len) {
		int pageCount;
		if (count % len == 0) {
			pageCount = count / len;
		} else {
			pageCount = (count / len) + 1;
		}
		int pagesLength = configuration.getPagesLength();
		int start = page - (pagesLength / 2);
		int end = page + (pagesLength / 2);
		if (start < 1) {
			start = 1;
		}
		if (pageCount < 1) {
			pageCount = 1;
		}
		if (end > pageCount) {
			end = pageCount;
		}
		Page[] pages = new Page[end - start + 3];
		pages[0] = new Page("首页", "1", page == 1);
		pages[pages.length - 1] = new Page("尾页", pageCount + "", page == pageCount);
		for (int i = 1; i < pages.length - 1; i++) {
			pages[i] = new Page((start + i - 1) + "", (start + i - 1) + "", page == (start + i - 1));
		}
		return pages;
	}
	
	private final void dealException(Exception e) {
		e.printStackTrace();
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}
}
