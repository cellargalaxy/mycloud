package top.cellargalaxy.mycloud.service.schedule.lock;

/**
 * @author cellargalaxy
 * @time 2018/12/19
 */
public class ScheduleDistributedLock implements ScheduleLock {
	private static final String TMP_FILE_CLEAN_LOCK_KEY = "TMP_FILE_CLEAN_LOCK_KEY";

	private final DistributedLock distributedLock;

	public ScheduleDistributedLock(DistributedLock distributedLock) {
		this.distributedLock = distributedLock;
	}

	@Override
	public boolean tryTmpFileCleanLock(int expireTime) {
		return distributedLock.tryLock(TMP_FILE_CLEAN_LOCK_KEY, expireTime);
	}

	@Override
	public boolean unTmpFileCleanLock() {
		return distributedLock.unLock(TMP_FILE_CLEAN_LOCK_KEY);
	}

	@Override
	public boolean hasTmpFileCleanLock() {
		return distributedLock.hasLock(TMP_FILE_CLEAN_LOCK_KEY);
	}
}
