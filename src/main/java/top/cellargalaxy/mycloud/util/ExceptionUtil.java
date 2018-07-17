package top.cellargalaxy.mycloud.util;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public class ExceptionUtil {
	public static final String printException(Exception exception) {
		StringBuilder stringBuilder = new StringBuilder("Exception in thread \"" + Thread.currentThread().getName() + "\" " + exception.toString());
		for (StackTraceElement stackTraceElement: exception.getStackTrace()) {
			stringBuilder.append("\n\tat " + stackTraceElement);
		}
		return stringBuilder.toString();
	}
}
