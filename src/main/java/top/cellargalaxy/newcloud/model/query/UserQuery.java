package top.cellargalaxy.newcloud.model.query;

import top.cellargalaxy.newcloud.model.po.UserPo;

/**
 * @author cellargalaxy
 * @time 2018/7/9
 */
public class UserQuery extends UserPo implements PageQuery {
	private int pageSize;
	private int page;
	private int off;
	private int len;

	@Override
	public int getPageSize() {
		return pageSize;
	}

	@Override
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	@Override
	public int getPage() {
		return page;
	}

	@Override
	public void setPage(int page) {
		this.page = page;
	}

	@Override
	public int getOff() {
		return off;
	}

	@Override
	public void setOff(int off) {
		this.off = off;
	}

	@Override
	public int getLen() {
		return len;
	}

	@Override
	public void setLen(int len) {
		this.len = len;
	}

	@Override
	public String toString() {
		return "UserQuery{" +
				"super=" + super.toString() +
				", pageSize=" + pageSize +
				", page=" + page +
				", off=" + off +
				", len=" + len +
				'}';
	}
}
