package top.cellargalaxy.mycloud.util.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by cellargalaxy on 18-7-19.
 */
@Data
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

	public Vo(Exception e) {
		this(FAIL_STATUS, "未知异常", e.getMessage());
	}

	public Vo(String massage, Object data) {
		this(massage == null ? SUCCESS_STATUS : FAIL_STATUS, massage, data);
	}

	public Vo(int status, String massage, Object data) {
		this.status = status;
		this.massage = massage;
		this.data = data;
	}
}
