package top.cellargalaxy.bean.controlorBean;

/**
 * Created by cellargalaxy on 18-1-23.
 */
public class Page {
	private final String name;
	private final String url;
	private final boolean current;
	
	public Page(String name, String url, boolean current) {
		this.name = name;
		this.url = url;
		this.current = current;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUrl() {
		return url;
	}
	
	public boolean isCurrent() {
		return current;
	}
	
	@Override
	public String toString() {
		return "Page{" +
				"name='" + name + '\'' +
				", url='" + url + '\'' +
				", current=" + current +
				'}';
	}
}
