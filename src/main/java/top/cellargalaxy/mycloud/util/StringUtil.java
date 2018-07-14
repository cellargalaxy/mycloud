package top.cellargalaxy.mycloud.util;

import java.util.Iterator;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/5
 */
public class StringUtil {
//	public static final boolean isEmpty(String string) {
//		return string == null || string.length() == 0;
//	}

	public static final boolean isBlank(String string) {
		return string == null || string.trim().length() == 0;
	}

	public static final void appendList(StringBuilder stringBuilder, List<String> strings, String elseString, String separator) {
		if (strings == null) {
			return;
		}
		if (separator == null) {
			separator = "";
		}
		if (strings.size() == 0) {
			stringBuilder.append(elseString);
			return;
		}
		Iterator<String> iterator = strings.iterator();
		if (iterator.hasNext()) {
			stringBuilder.append(iterator.next());
		}
		while (iterator.hasNext()) {
			stringBuilder.append(separator + iterator.next());
		}
	}
}
