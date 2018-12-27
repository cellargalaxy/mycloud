package top.cellargalaxy.mycloud.service.schedule.lock;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author cellargalaxy
 * @time 18-12-27
 */
public class ScheduleLocalLock implements ScheduleLock {
	private static final Lock TMP_FILE_CLEAN_LOCK = new ReentrantLock();
	private static final ThreadLocal<Boolean> HAS_TMP_FILE_CLEAN_LOCK = ThreadLocal.withInitial(() -> false);

	@Override
	public boolean tryTmpFileCleanLock(int expireTime) {
		try {
			boolean hasLock = TMP_FILE_CLEAN_LOCK.tryLock(expireTime, TimeUnit.MILLISECONDS);
			if (hasLock) {
				HAS_TMP_FILE_CLEAN_LOCK.set(true);
			}
			return hasLock;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean unTmpFileCleanLock() {
		TMP_FILE_CLEAN_LOCK.unlock();
		HAS_TMP_FILE_CLEAN_LOCK.set(false);
		return true;
	}

	@Override
	public boolean hasTmpFileCleanLock() {
		return HAS_TMP_FILE_CLEAN_LOCK.get();
	}
}
