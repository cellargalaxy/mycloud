package top.cellargalaxy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.cellargalaxy.bean.FilePackage;
import top.cellargalaxy.dao.FilePackageMapper;

import java.util.LinkedList;

/**
 * Created by cellargalaxy on 17-12-5.
 */
@Service
@Transactional
public class FilePackageRestoreThread extends Thread implements FilePackageRestore {
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
	public int getWaitRestoreCount() {
		return restoreFilePackages.size();
	}
	
	@Override
	public synchronized boolean restoreFilePackage(FilePackage filePackage) {
		restoreFilePackages.add(filePackage);
		notify();
		return true;
	}
	
	@Override
	public void interrupt() {
		runable = false;
		super.interrupt();
	}
	
	@Override
	public void run() {
		while (runable) {
			synchronized (this) {
				FilePackage filePackage = restoreFilePackages.poll();
				if (filePackage != null) {
					filePackage = filePackageMapper.selectFilePackageByte(filePackage.getFilename(), filePackage.getDate());
					filePackageService.fillingAttributes(filePackage);
					if (filePackage == null || !filePackageService.writeFileBytes(filePackage)) {
						failRestoreCount++;
					} else {
						successRestoreCount++;
					}
				} else {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
