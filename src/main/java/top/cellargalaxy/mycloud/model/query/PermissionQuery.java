package top.cellargalaxy.mycloud.model.query;

import top.cellargalaxy.mycloud.model.po.PermissionPo;
import top.cellargalaxy.mycloud.util.SqlUtil;

import java.util.Objects;

/**
 * Created by cellargalaxy on 18-7-11.
 */
public class PermissionQuery extends PermissionPo implements PageQuery {
	private int pageSize = SqlUtil.MAX_PAGE_SIZE;
	private int page = 1;
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
		return "PermissionQuery{" +
				"super=" + super.toString() +
				", pageSize=" + pageSize +
				", page=" + page +
				", off=" + off +
				", len=" + len +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		PermissionQuery that = (PermissionQuery) o;
		return pageSize == that.pageSize &&
				page == that.page &&
				off == that.off &&
				len == that.len;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), pageSize, page, off, len);
	}
}
