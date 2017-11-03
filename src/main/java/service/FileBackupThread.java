package service;

import bean.DBPackage;
import bean.FilePackage;
import dao.DB;

import java.util.Date;
import java.util.LinkedList;

/**
 * Created by cellargalaxy on 17-10-15.
 */
public class FileBackupThread extends Thread implements FileBackup {
	private final LinkedList<FilePackage> backupFilePackages;
	private final LinkedList<DBPackage> restoreDBPackages;
	private volatile boolean runable;
	private final DB[] dbs;
	private int backupCount;
	private int yetBackup;
	private int restoreCount;
	private int yetRestore;
	
	public FileBackupThread(DB[] dbs) {
		this.dbs = dbs;
		backupFilePackages = new LinkedList<FilePackage>();
		restoreDBPackages = new LinkedList<DBPackage>();
		runable = true;
	}
	
	public void backup(FilePackage filePackage) {
		synchronized (this) {
			backupFilePackages.add(filePackage);
			this.notify();
		}
		backupCount++;
	}
	
	public void restore(String dbName, FilePackage filePackage) {
		DBPackage dbPackage = new DBPackage(dbName, new FilePackage[]{filePackage});
		synchronized (this) {
			restoreDBPackages.add(dbPackage);
			this.notify();
		}
		restoreCount++;
	}
	
	public void restore(String dbName) {
		for (DB db : dbs) {
			if (db.getDbName().equals(dbName)) {
				FilePackage[] filePackages = db.selectAllFilePackageInfo();
				DBPackage dbPackage = new DBPackage(dbName, filePackages);
				synchronized (this) {
					restoreDBPackages.add(dbPackage);
					this.notify();
				}
				restoreCount += filePackages.length;
				return;
			}
		}
	}
	
	public DBPackage findDB(String dbName) {
		DBPackage dbPackage;
		for (DB db : dbs) {
			if (db.getDbName().equals(dbName)) {
				FilePackage[] filePackages = db.selectAllFilePackageInfo();
				dbPackage = new DBPackage(db.getDbName(), filePackages);
				return dbPackage;
			}
		}
		dbPackage = new DBPackage(null, new FilePackage[0]);
		return dbPackage;
	}
	
	@Override
	public DBPackage findDBByDate(String dbName, Date upDate) {
		DBPackage dbPackage;
		for (DB db : dbs) {
			if (db.getDbName().equals(dbName)) {
				FilePackage[] filePackages = db.selectFilePackagesInfoByDate(upDate);
				dbPackage = new DBPackage(db.getDbName(), filePackages);
				return dbPackage;
			}
		}
		dbPackage = new DBPackage(null, new FilePackage[0]);
		return dbPackage;
	}
	
	public boolean deleteFile(String dbName, FilePackage filePackage) {
		for (DB db : dbs) {
			if (db.getDbName().equals(dbName)) {
				if (db.deleteFilePackage(filePackage)) {
					return filePackage.getFile().delete();
				} else {
					return false;
				}
			}
		}
		return true;
	}
	
	public int getBackupCount() {
		return backupCount;
	}
	
	public void setBackupCount(int backupCount) {
		this.backupCount = backupCount;
	}
	
	public int getYetBackup() {
		return yetBackup;
	}
	
	public void setYetBackup(int yetBackup) {
		this.yetBackup = yetBackup;
	}
	
	public int getRestoreCount() {
		return restoreCount;
	}
	
	public void setRestoreCount(int restoreCount) {
		this.restoreCount = restoreCount;
	}
	
	public int getYetRestore() {
		return yetRestore;
	}
	
	public void setYetRestore(int yetRestore) {
		this.yetRestore = yetRestore;
	}
	
	@Override
	public void interrupt() {
		runable = false;
		super.interrupt();
	}
	
	private void doBackup(FilePackage backupFilePackage) {
		System.out.println("开始备份：" + backupFilePackage.getUrl());
		for (DB db : dbs) {
			db.deleteFilePackage(backupFilePackage);
			for (int i = 0; i < 3 && !db.insertFile(backupFilePackage); i++) ;
		}
		System.out.println("备份完成：" + backupFilePackage.getUrl());
		yetBackup++;
	}
	
	private void doRestore(DBPackage restoreDBPackage) {
		for (DB db : dbs) {
			if (db.getDbName().equals(restoreDBPackage.getDbName())) {
				for (FilePackage filePackage : restoreDBPackage.getFilePackages()) {
					System.out.println("开始恢复：" + filePackage.getUrl());
					if (db.selectFilePackageBlob(filePackage)) {
						System.out.println("恢复成功：" + filePackage.getUrl());
					} else {
						System.out.println("恢复失败：" + filePackage.getUrl());
					}
					yetRestore++;
				}
				return;
			}
		}
	}
	
	@Override
	public void run() {
		while (runable) {
			FilePackage backupFilePackage;
			DBPackage restoreDBPackage;
			synchronized (this) {
				do {
					backupFilePackage = backupFilePackages.poll();
					restoreDBPackage = restoreDBPackages.poll();
					if (runable && backupFilePackage == null && restoreDBPackage == null) {
						try {
							this.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} else {
						break;
					}
				} while (true);
			}
			if (backupFilePackage != null) {
				doBackup(backupFilePackage);
			}
			if (restoreDBPackage != null) {
				doRestore(restoreDBPackage);
			}
		}
	}
	
	public DB[] getDbs() {
		return dbs;
	}
}
