package top.cellargalaxy.bean.controlorBean;

/**
 * Created by cellargalaxy on 18-1-23.
 */
public class ReturnBean {
	private final boolean result;
	private final Object data;
	
	public ReturnBean(boolean result, Object data) {
		this.result = result;
		this.data = data;
	}
	
	public boolean isResult() {
		return result;
	}
	
	public Object getData() {
		return data;
	}
	
	@Override
	public String toString() {
		return "ReturnBean{" +
				"result=" + result +
				", data=" + data +
				'}';
	}
}
