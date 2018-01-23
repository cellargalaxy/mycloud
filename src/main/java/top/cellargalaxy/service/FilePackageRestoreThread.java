package top.cellargalaxy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import top.cellargalaxy.bean.daoBean.FilePackage;
import top.cellargalaxy.dao.FilePackageMapper;

import java.util.LinkedList;

/**
 * Created by cellargalaxy on 17-12-5.
 */
@Service
@Transactional
public class FilePackageRestoreThread extends Thread implements FilePackageRestore {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private FilePackageMapper filePackageMapper;
	@Autowired
	private FilePackageService filePackageService;
	
	private final LinkedList<FilePackage> restoreFilePackages;
	private volatile boolean runable;
	private volatile int failRestoreCount;
	private volatile int successRestoreCount;
	
	public FilePackageRestoreThread() {
		super("文件恢复线程");
		restoreFilePackages = new LinkedList<FilePackage>();
		runable = true;
		failRestoreCount = 0;
		successRestoreCount = 0;
	}
	
	@Override
	public int getFailRestoreCount() {
		return failRestoreCount;
	}
	
	@Override
	public int getSuccessRestoreCount() {
		return successRestoreCount;
	}
	
	@Override
	public synchronized int getWaitRestoreCount() {
		return restoreFilePackages.size();
	}
	
	@Override
	public synchronized boolean restoreFilePackage(FilePackage filePackage) {
		try {
			if (filePackage == null) {
				return false;
			}
			restoreFilePackages.add(filePackage);
			notify();
			return true;
		} catch (Exception e) {
			dealException(e);
		}
		return false;
	}
	
	@Override
	public synchronized boolean restoreFilePackages(LinkedList<FilePackage> filePackages) {
		try {
			if (filePackages == null) {
				return false;
			}
			for (FilePackage filePackage : filePackages) {
				restoreFilePackages.add(filePackage);
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
				filePackage = restoreFilePackages.poll();
				if (filePackage == null) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			filePackage = filePackageService.fillingFilePackageInfoAttributes(filePackage);
			if (filePackage != null) {
				filePackage = filePackageMapper.selectFilePackageByteByFilenameAndDate(filePackage.getFilename(), filePackage.getDate());
				filePackage = filePackageService.fillingFilePackageInfoAttributes(filePackage);
				if (filePackage == null || !filePackageService.writeFileBytes(filePackage)) {
					failRestoreCount++;
					logger.info("下载恢复文件失败:" + filePackage);
				} else {
					successRestoreCount++;
					logger.info("下载恢复文件成功:" + filePackage);
				}
			}
		}
	}
	
	private final void dealException(Exception e) {
		e.printStackTrace();
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}
}
