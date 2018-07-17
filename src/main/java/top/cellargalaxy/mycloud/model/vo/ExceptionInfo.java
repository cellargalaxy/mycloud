package top.cellargalaxy.mycloud.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public class ExceptionInfo implements Serializable {
	private static final long serialVersionUID = -3096389445125376855L;
	private final Exception exception;
	private final String exceptionString;
	private final int status;
	private final String massage;
	private final Date date;

	public ExceptionInfo(Exception exception, String exceptionString, int status, String massage, Date date) {
		this.exception = exception;
		this.exceptionString = exceptionString;
		this.status = status;
		this.massage = massage;
		this.date = date;
	}

	public Exception getException() {
		return exception;
	}

	public String getExceptionString() {
		return exceptionString;
	}

	public int getStatus() {
		return status;
	}

	public String getMassage() {
		return massage;
	}

	public Date getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "ExceptionInfo{" +
				"exception=" + exception +
				", exceptionString='" + exceptionString + '\'' +
				", status=" + status +
				", massage='" + massage + '\'' +
				", date=" + date +
				'}';
	}
}
