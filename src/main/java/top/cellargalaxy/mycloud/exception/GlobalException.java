package top.cellargalaxy.mycloud.exception;

import top.cellargalaxy.mycloud.model.vo.ExceptionInfoVo;
import top.cellargalaxy.mycloud.util.ExceptionUtil;

import java.util.Date;
import java.util.LinkedList;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public class GlobalException {
	public static final int MAX_EXCEPTION_INFO_SIZE = 100;
	private static final LinkedList<ExceptionInfoVo> EXCEPTION_INFO_VOS = new LinkedList<>();

	static {
		add(new RuntimeException("测试异常"));
	}

	public static final void add(Exception exception) {
		add(exception, 0, null);
	}

	public static final void add(Exception exception, String massage) {
		add(exception, 0, massage);
	}

	public static final synchronized void add(Exception exception, int status, String massage) {
		ExceptionInfoVo exceptionInfoVo = new ExceptionInfoVo(exception, ExceptionUtil.printException(exception), status, massage, new Date());
		if (EXCEPTION_INFO_VOS.size() >= MAX_EXCEPTION_INFO_SIZE) {
			EXCEPTION_INFO_VOS.removeLast();
		}
		EXCEPTION_INFO_VOS.addFirst(exceptionInfoVo);
	}

	public static final synchronized void clear() {
		EXCEPTION_INFO_VOS.clear();
	}

	public static final LinkedList<ExceptionInfoVo> get() {
		return EXCEPTION_INFO_VOS;
	}
}
