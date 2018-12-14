package top.cellargalaxy.mycloud.util;

import com.google.common.base.CaseFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author cellargalaxy
 * @time 2018/7/5
 */
public class StringUtils {
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

    public static final String printException(Exception exception) {
        if (exception == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder("Exception in thread \"" + Thread.currentThread().getName() + "\" " + exception.toString());
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            stringBuilder.append("\n\tat " + stackTraceElement);
        }
        return stringBuilder.toString();
    }

    /**
     * test_data -> testData
     *
     * @param string
     * @return
     */
    public static final String lowerUnderscore2LowerCamel(String string) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, string);
    }

    /**
     * testData -> test_data
     *
     * @param string
     * @return
     */
    public static final String lowerCamel2LowerHyphen(String string) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, string);
    }
}