package top.cellargalaxy.mycloud.model.vo;

import java.io.Serializable;

/**
 * Created by cellargalaxy on 18-7-14.
 */
public class VoBean implements Serializable {
	private static final long serialVersionUID = -8443005105522701970L;
	private final int status;
	private final String massage;
	private final Object data;

	public VoBean(int status, String massage, Object data) {
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
		return "VoBean{" +
				"status=" + status +
				", massage='" + massage + '\'' +
				", data=" + data +
				'}';
	}
}
