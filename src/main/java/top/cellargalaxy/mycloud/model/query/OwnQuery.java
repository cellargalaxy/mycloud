package top.cellargalaxy.mycloud.model.query;

import lombok.Data;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.util.dao.SqlUtils;
import top.cellargalaxy.mycloud.util.model.PageQuery;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
@Data
public class OwnQuery extends OwnPo implements PageQuery {
    private int maxPageSize = SqlUtils.MAX_PAGE_SIZE;
    private int pageSize = SqlUtils.MAX_PAGE_SIZE;
    private int page = 1;
    private int off;
    private int len;

    @Override
    public String toString() {
        return "AuthorizationQuery{" +
                "maxPageSize=" + maxPageSize +
                ", pageSize=" + pageSize +
                ", page=" + page +
                ", off=" + off +
                ", len=" + len +
                ", super=" + super.toString() +
                '}';
    }
}
