package top.cellargalaxy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import top.cellargalaxy.bean.FilePackage;
import top.cellargalaxy.configuration.MycloudConfiguration;
import top.cellargalaxy.dao.FilePackageMapper;

import java.io.File;
import java.util.Date;
import java.util.List;


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
	public String[] createPages() {
		try {
			int count = filePackageMapper.selectFilePackageCount();
			int len = configuration.getListFileLength();
			return createPages(count, len);
		} catch (Exception e) {
			dealException(e);
		}
		return new String[0];
	}
	
	@Override
	public FilePackage[] getFilePackages(int page) {
		try {
			int len = configuration.getListFileLength();
			int off = (page - 1) * len;
			return createUrl(filePackageMapper.selectFilePackages(off, len));
		} catch (Exception e) {
			dealException(e);
		}
		return new FilePackage[0];
	}
	
	@Override
	public FilePackage[] getFilePackageByDate(Date date) {
		try {
			return createUrl(filePackageMapper.selectFilePackageByDate(date));
		} catch (Exception e) {
			dealException(e);
		}
		return new FilePackage[0];
	}
	
	@Override
	public FilePackage[] getFilePackageByFilename(String filename) {
		try {
			return createUrl(filePackageMapper.selectFilePackageByFilename(filename));
		} catch (Exception e) {
			dealException(e);
		}
		return new FilePackage[0];
	}
	
	@Override
	public FilePackage[] getFilePackageByDescription(String description) {
		try {
			return createUrl(filePackageMapper.selectFilePackageByDescription(description));
		} catch (Exception e) {
			dealException(e);
		}
		return new FilePackage[0];
	}
	
	@Override
	public FilePackage createFilePackage(File file, Date date, String description) {
		return filePackageService.createFilePackage(file, date, description);
	}
	
	@Override
	public FilePackage createFilePackage(String filename, Date date, String description) {
		return filePackageService.createFilePackage(filename, date, description);
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
			if (filePackage != null) {
				return filePackage.getFile() != null && filePackage.getFile().delete() &&
						filePackageMapper.deleteFilePackage(filePackage.getFilename(), filePackage.getDate()) >= 0;
			}
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}
	
	@Override
	public boolean backupAllFilePackage() {
		try {
			List<FilePackage> filePackages = filePackageService.getAllFilePackageFromDrive();
			for (FilePackage filePackage : filePackages) {
				filePackageBackup.backupFilePackage(filePackage);
			}
			return true;
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}
	
	@Override
	public boolean restoreAllFilePackage() {
		try {
			FilePackage[] filePackages = filePackageMapper.selectAllFilePackage();
			for (FilePackage filePackage : filePackages) {
				filePackageRestore.restoreFilePackage(filePackage);
			}
			return true;
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}
	
	private FilePackage[] createUrl(FilePackage[] filePackages) {
		for (FilePackage filePackage : filePackages) {
			filePackageService.fillingAttributes(filePackage);
		}
		return filePackages;
	}
	
	private String[] createPages(int count, int len) {
		String[] pages;
		if (count % len == 0) {
			pages = new String[count / len];
		} else {
			pages = new String[count / len + 1];
		}
		for (int i = 0; i < pages.length; i++) {
			pages[i] = (i + 1) + "";
		}
		return pages;
	}
	
	private void dealException(Exception e) {
		e.printStackTrace();
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}
}
