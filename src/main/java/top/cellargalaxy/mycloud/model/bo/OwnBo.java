package top.cellargalaxy.mycloud.model.bo;

import lombok.Data;
import top.cellargalaxy.mycloud.model.po.OwnPo;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
@Data
public class OwnBo extends OwnPo {
    private String username;
    private String md5Url;
    private String ownUrl;

    @Override
    public String toString() {
        return "OwnBo{" +
                "username='" + username + '\'' +
                ", md5Url='" + md5Url + '\'' +
                ", ownUrl='" + ownUrl + '\'' +
                ", super=" + super.toString() +
                '}';
    }
}
