package top.cellargalaxy.mycloud.service.schedule.lock;

/**
 * @author cellargalaxy
 * @time 2018/12/19
 */
public interface ScheduleLock {
	boolean tryTmpFileCleanLock(int expireTime);

	boolean unTmpFileCleanLock();

	boolean hasTmpFileCleanLock();
}
