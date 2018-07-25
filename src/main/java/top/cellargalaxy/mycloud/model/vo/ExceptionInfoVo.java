package top.cellargalaxy.mycloud.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public class ExceptionInfoVo implements Serializable {
	private static final long serialVersionUID = -3096389445125376855L;
	@JsonIgnore
	private final Exception exception;
	private final String exceptionStack;
	private final int status;
	private final String massage;
	private final Date date;

	public ExceptionInfoVo(Exception exception, String exceptionStack, int status, String massage, Date date) {
		this.exception = exception;
		this.exceptionStack = exceptionStack;
		this.status = status;
		this.massage = massage;
		this.date = date;
	}

	public Exception getException() {
		return exception;
	}

	public String getExceptionStack() {
		return exceptionStack;
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
		return "ExceptionInfoVo{" +
				"exception=" + exception +
				", exceptionStack='" + exceptionStack + '\'' +
				", status=" + status +
				", massage='" + massage + '\'' +
				", date=" + date +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ExceptionInfoVo that = (ExceptionInfoVo) o;
		return status == that.status &&
				Objects.equals(exception, that.exception) &&
				Objects.equals(exceptionStack, that.exceptionStack) &&
				Objects.equals(massage, that.massage) &&
				Objects.equals(date, that.date);
	}

	@Override
	public int hashCode() {
		return Objects.hash(exception, exceptionStack, status, massage, date);
	}
}
