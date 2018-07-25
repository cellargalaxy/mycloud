package top.cellargalaxy.mycloud.model.query;

import top.cellargalaxy.mycloud.model.po.AuthorizationPo;

import java.util.Objects;

/**
 * Created by cellargalaxy on 18-7-12.
 */
public class AuthorizationQuery extends AuthorizationPo implements PageQuery {
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
		return "AuthorizationQuery{" +
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
		AuthorizationQuery that = (AuthorizationQuery) o;
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
