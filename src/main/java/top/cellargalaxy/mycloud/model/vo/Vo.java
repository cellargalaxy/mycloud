package top.cellargalaxy.mycloud.model.vo;

import top.cellargalaxy.mycloud.util.ExceptionUtil;

import java.io.Serializable;

/**
 * Created by cellargalaxy on 18-7-19.
 */
public class Vo implements Serializable {
	public static final int FAIL_STATUS = 0;
	public static final int SUCCESS_STATUS = 1;
	private static final long serialVersionUID = -838272283919215475L;
	private final int status;
	private final String massage;
	private final Object data;

	public Vo() {
		this(SUCCESS_STATUS, null, null);
	}

	public Vo(String massage, Object data) {
		this(massage == null ? SUCCESS_STATUS : FAIL_STATUS, massage, data);
	}

	public Vo(Exception e) {
		this(FAIL_STATUS, "未知异常", ExceptionUtil.printException(e));
	}

	public Vo(int status, String massage, Object data) {
		this.status = status;
		this.massage = massage;
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public String getMassage() {
		return massage;
	}

	public Object getData() {
		return data;
	}

	@Override
	public String toString() {
		return "Vo{" +
				"status=" + status +
				", massage='" + massage + '\'' +
				", data=" + data +
				'}';
	}
}