package top.cellargalaxy.mycloud.model.query;

import top.cellargalaxy.mycloud.model.po.FileBlockPo;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
public class FileBlockQuery extends FileBlockPo implements PageQuery{
	@Override
	public int getPageSize() {
		return 0;
	}

	@Override
	public void setPageSize(int pageSize) {

	}

	@Override
	public int getPage() {
		return 0;
	}

	@Override
	public void setPage(int page) {

	}

	@Override
	public int getOff() {
		return 0;
	}

	@Override
	public void setOff(int off) {

	}

	@Override
	public int getLen() {
		return 0;
	}

	@Override
	public void setLen(int len) {

	}
}
