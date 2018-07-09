package top.cellargalaxy.newcloud.model.query;


import top.cellargalaxy.newcloud.model.po.FileInfoPo;

/**
 * @author cellargalaxy
 * @time 2018/7/5
 */
public class FileInfoQuery extends FileInfoPo {
	private int pageSize;
	private int page;

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

	@Override
	public String toString() {
		return "FileInfoQuery{" +
				"super=" + super.toString() +
				", pageSize=" + pageSize +
				", page=" + page +
				'}';
	}
}
