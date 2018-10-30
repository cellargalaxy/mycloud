package top.cellargalaxy.mycloud.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author cellargalaxy
 * @time 2018/7/5
 */
public class StringUtil {
	public static final String PASSWORD_HEAD = "{bcrypt}";
	public static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

	public static final boolean isBlank(String string) {
		return string == null || string.trim().length() == 0;
	}

	public static final String encoderPassword(CharSequence rawPassword) {
		return PASSWORD_ENCODER.encode(rawPassword);
	}

	public static final boolean matchesPassword(CharSequence rawPassword, String encodedPassword) {
		return PASSWORD_ENCODER.matches(rawPassword, encodedPassword);
	}
}