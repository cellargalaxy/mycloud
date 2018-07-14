package top.cellargalaxy.mycloud.model.query;

/**
 * @author cellargalaxy
 * @time 2018/7/12
 */
public interface PageQuery {
	int getPageSize();

	void setPageSize(int pageSize);

	int getPage();

	void setPage(int page);

	int getOff();

	void setOff(int off);

	int getLen();

	void setLen(int len);
}
