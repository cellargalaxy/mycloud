package top.cellargalaxy.mycloud.exception;

import top.cellargalaxy.mycloud.model.vo.ExceptionInfoVo;
import top.cellargalaxy.mycloud.util.ExceptionUtil;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public class GlobalException {
	public static final int MAX_EXCEPTION_INFO_SIZE = 100;
	private static final LinkedBlockingQueue<ExceptionInfoVo> EXCEPTION_INFO_VOS=new LinkedBlockingQueue <>(MAX_EXCEPTION_INFO_SIZE);

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
		try {
			EXCEPTION_INFO_VOS.add(exceptionInfoVo);
		}catch (Exception e){
			EXCEPTION_INFO_VOS.poll();
			EXCEPTION_INFO_VOS.offer(exceptionInfoVo);
		}
	}

	public static final synchronized void clear() {
		EXCEPTION_INFO_VOS.clear();
	}

	public static final Collection<ExceptionInfoVo> get() {
		return EXCEPTION_INFO_VOS;
	}
}
