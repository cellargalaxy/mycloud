package top.cellargalaxy.newcloud.model.query;


import top.cellargalaxy.newcloud.model.po.FileInfoPo;

/**
 * @author cellargalaxy
 * @time 2018/7/5
 */
public class FileInfoQuery extends FileInfoPo {
	private int pageSize;
	private int page;
	private int off;
	private int len;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getOff() {
		return off;
	}

	public void setOff(int off) {
		this.off = off;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	@Override
	public String toString() {
		return "FileInfoQuery{" +
				"super=" + super.toString() +
				", pageSize=" + pageSize +
				", page=" + page +
				", off=" + off +
				", len=" + len +
				'}';
	}
}
