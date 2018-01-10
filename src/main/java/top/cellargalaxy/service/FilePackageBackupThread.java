package top.cellargalaxy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import top.cellargalaxy.bean.FilePackage;
import top.cellargalaxy.configuration.MycloudConfiguration;
import top.cellargalaxy.dao.FilePackageMapper;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by cellargalaxy on 17-12-5.
 */
@Service
@Transactional
public class FilePackageBackupThread extends Thread implements FilePackageBackup {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private FilePackageMapper filePackageMapper;
	@Autowired
	private FilePackageService filePackageService;
	@Autowired
	private MycloudConfiguration configuration;
	
	private final LinkedList<FilePackage> backupFilePackages;
	private volatile boolean runable;
	private volatile int failBackupCount;
	private volatile int successBackupCount;
	
	public FilePackageBackupThread() {
		super("文件备份线程");
		backupFilePackages = new LinkedList<FilePackage>();
		runable = true;
		failBackupCount = 0;
		successBackupCount = 0;
	}
	
	@Override
	public int getFailBackupCount() {
		return failBackupCount;
	}
	
	@Override
	public int getSuccessBackupCount() {
		return successBackupCount;
	}
	
	@Override
	public synchronized int getWaitBackupCount() {
		return backupFilePackages.size();
	}
	
	@Override
	public synchronized boolean backupFilePackage(FilePackage filePackage) {
		try {
			backupFilePackages.add(filePackage);
			notify();
			return true;
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}
	
	@Override
	public synchronized boolean backupFilePackages(List<FilePackage> filePackages) {
		try {
			for (FilePackage filePackage : filePackages) {
				backupFilePackages.add(filePackage);
			}
			notify();
			return true;
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}
	
	@Override
	public void interrupt() {
		runable = false;
		super.interrupt();
	}
	
	@Override
	public void run() {
		while (runable) {
			FilePackage filePackage;
			synchronized (this) {
				filePackage = backupFilePackages.poll();
				if (filePackage == null) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			if (filePackage != null && filePackage.getFile() != null && filePackage.getFile().length() <= configuration.getBlobMaxLength()) {
				if (!filePackageService.readFileBytes(filePackage)) {
					failBackupCount++;
					logger.info("读取备份文件失败:" + filePackage);
				} else if (existFilePackage(filePackage)) {
					if (updateFilePackage(filePackage)) {
						successBackupCount++;
						logger.info("更新备份文件成功:" + filePackage);
					} else {
						failBackupCount++;
						logger.info("更新备份文件失败:" + filePackage);
					}
				} else {
					if (insertFilePackage(filePackage)) {
						successBackupCount++;
						logger.info("备份备份文件成功:" + filePackage);
					} else {
						failBackupCount++;
						logger.info("备份备份文件失败:" + filePackage);
					}
				}
			}
		}
	}
	
	private boolean existFilePackage(FilePackage filePackage) {
		try {
			return filePackageMapper.selectFilePackageCountByFilenameAndDate(filePackage.getFilename(), filePackage.getDate()) > 0;
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}
	
	private boolean insertFilePackage(FilePackage filePackage) {
		try {
			return filePackageMapper.insertFilePackage(filePackage) > 0;
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}
	
	private boolean updateFilePackage(FilePackage filePackage) {
		try {
			return filePackageMapper.updateFilePackage(filePackage) > 0;
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}
	
	private void dealException(Exception e) {
		e.printStackTrace();
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}
}
