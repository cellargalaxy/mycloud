package top.cellargalaxy.mycloud.service.schedule;

/**
 * @author cellargalaxy
 * @time 2018/12/19
 */
public interface ScheduleLock {
    boolean tryTmpFileCleanLock();
}
