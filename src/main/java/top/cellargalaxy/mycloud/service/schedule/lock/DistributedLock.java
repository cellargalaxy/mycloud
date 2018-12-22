package top.cellargalaxy.mycloud.service.schedule.lock;

/**
 * @author cellargalaxy
 * @time 18-12-22
 */
public interface DistributedLock {
	boolean tryLock(String lockKey, int expireTime);

	boolean unLock(String lockKey);

	boolean hasLock(String lockKey);

	String getId();
}
