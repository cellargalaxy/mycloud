package top.cellargalaxy.mycloud.model.query;

import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.util.SqlUtil;

import java.util.Objects;

/**
 * @author cellargalaxy
 * @time 2018/7/5
 */
public class FileInfoQuery extends FileInfoPo implements PageQuery {
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
		return "FileInfoQuery{" +
				"pageSize=" + pageSize +
				", page=" + page +
				", off=" + off +
				", len=" + len +
				", super=" + super.toString() +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		FileInfoQuery that = (FileInfoQuery) o;
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
