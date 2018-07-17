package top.cellargalaxy.mycloud.exception;

import top.cellargalaxy.mycloud.model.vo.ExceptionInfo;
import top.cellargalaxy.mycloud.util.ExceptionUtil;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public class GlobalException {
	private static final BlockingQueue<ExceptionInfo> exceptionInfos = new LinkedBlockingQueue<>(1000);

	public static final void add(Exception exception, int status, String massage) {
		ExceptionInfo exceptionInfo = new ExceptionInfo(exception, ExceptionUtil.printException(exception), status, massage, new Date());
		try {
			exceptionInfos.add(exceptionInfo);
		} catch (IllegalStateException e) {
			exceptionInfos.remove();
			exceptionInfos.add(exceptionInfo);
		}
	}

	public static final void clear() {
		exceptionInfos.clear();
	}

	public static final BlockingQueue<ExceptionInfo> get() {
		return exceptionInfos;
	}
}
