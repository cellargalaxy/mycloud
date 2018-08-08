package top.cellargalaxy.mycloud.util;

/**
 * @author cellargalaxy
 * @time 2018/7/5
 */
public class StringUtil {
	public static final boolean isBlank(String string) {
		return string == null || string.trim().length() == 0;
	}

	public static final String createUrl(String domain, String md5) {
		return domain + "/" + md5;
	}
}
