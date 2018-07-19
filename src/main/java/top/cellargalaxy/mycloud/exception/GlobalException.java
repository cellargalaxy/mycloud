package top.cellargalaxy.mycloud.exception;

import top.cellargalaxy.mycloud.model.vo.ExceptionInfoVo;
import top.cellargalaxy.mycloud.util.ExceptionUtil;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public class GlobalException {
	private static final BlockingQueue<ExceptionInfoVo> EXCEPTION_INFO_VOS = new LinkedBlockingQueue<>(1000);

	public static final void add(Exception exception) {
		add(exception, 0, null);
	}

	public static final void add(Exception exception, int status, String massage) {
		ExceptionInfoVo exceptionInfoVo = new ExceptionInfoVo(exception, ExceptionUtil.printException(exception), status, massage, new Date());
		try {
			EXCEPTION_INFO_VOS.add(exceptionInfoVo);
		} catch (IllegalStateException e) {
			EXCEPTION_INFO_VOS.remove();
			EXCEPTION_INFO_VOS.add(exceptionInfoVo);
		}
	}

	public static final void clear() {
		EXCEPTION_INFO_VOS.clear();
	}

	public static final BlockingQueue<ExceptionInfoVo> get() {
		return EXCEPTION_INFO_VOS;
	}
}
