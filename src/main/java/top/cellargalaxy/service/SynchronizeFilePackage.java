package top.cellargalaxy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.cellargalaxy.bean.daoBean.FilePackage;

import java.util.*;

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
	
	@Scheduled(initialDelay = 1000 * 60 * 10, fixedDelay = 1000 * 60 * 60 * 3)
	public void synchronizeFilePackage() {
		LinkedList<FilePackage> dbFilePackages = mycloudService.findAllFilePackage();
		LinkedList<FilePackage> driveFilePackages = filePackageService.getAllFilePackageFromDrive();
		if (dbFilePackages == null || driveFilePackages == null) {
			return;
		}
		Iterator<FilePackage> dbIterator = dbFilePackages.iterator();
		Iterator<FilePackage> driveIterator = driveFilePackages.iterator();
		main:
		while (driveIterator.hasNext()) {
			FilePackage driveFilePackage = driveIterator.next();
			driveFilePackage = filePackageService.fillingFilePackageInfoAttributes(driveFilePackage);
			while (dbIterator.hasNext()) {
				FilePackage dbFilePackage = dbIterator.next();
				if (dbFilePackage.getFilename().equals(driveFilePackage.getFilename()) && equalsDate(dbFilePackage.getDate(), driveFilePackage.getDate())) {
					driveFilePackage = filePackageService.fillingFilePackageAllAttributes(driveFilePackage);
					if (dbFilePackage.getFileSha256().equals(driveFilePackage.getFileSha256())) {
						dbIterator.remove();
						driveIterator.remove();
						continue main;
					}
				}
			}
		}
		while (driveIterator.hasNext()) {
			driveIterator.next().getFile().delete();
		}
		while (dbIterator.hasNext()) {
			mycloudService.restoreFilePackage(dbIterator.next());
		}
		logger.info("完成一次文件同步");
	}
	
	private final boolean equalsDate(Date date1, Date date2) {
		Calendar calendar1 = Calendar.getInstance();
		Calendar calendar2 = Calendar.getInstance();
		calendar1.setTime(date1);
		calendar2.setTime(date2);
		return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
				calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
				calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
	}
	
}
