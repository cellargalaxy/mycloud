package top.cellargalaxy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.cellargalaxy.bean.FilePackage;

import java.util.Calendar;
import java.util.List;

/**
 * Created by cellargalaxy on 18-1-10.
 */
@Component
public class SynchronizeFilePackage {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MycloudService mycloudService;
	@Autowired
	private FilePackageService filePackageService;
	
	@Scheduled(fixedDelay = 1000 * 60 * 60)
	public void synchronizeFilePackage() {
		FilePackage[] dbFilePackages = mycloudService.getAllFilePackage();
		List<FilePackage> driveFilePackages = filePackageService.getAllFilePackageFromDrive();
		main:
		for (FilePackage dbFilePackage : dbFilePackages) {
			for (FilePackage driveFilePackage : driveFilePackages) {
				if (equalsFilePackage(dbFilePackage, driveFilePackage)) {
					continue main;
				}
			}
			mycloudService.restoreFilePackage(dbFilePackage);
		}
		main:
		for (FilePackage driveFilePackage : driveFilePackages) {
			for (FilePackage dbFilePackage : dbFilePackages) {
				if (equalsFilePackage(dbFilePackage, driveFilePackage)) {
					continue main;
				}
			}
			mycloudService.backupFilePackage(driveFilePackage);
		}
		logger.info("完成一次文件同步");
	}
	
	private boolean equalsFilePackage(FilePackage filePackage1, FilePackage filePackage2) {
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(filePackage1.getDate());
		calendar2.setTime(filePackage2.getDate());
		return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
				calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
				calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH) &&
				filePackage1.getFilename().equals(filePackage2.getFilename());
	}
}
