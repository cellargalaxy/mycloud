package top.cellargalaxy.mycloud.model.bo;

import lombok.Data;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
@Data
public class FileInfoBo extends FileInfoPo {
    private String md5Url;

    @Override
    public String toString() {
        return "FileInfoBo{" +
                "md5Url='" + md5Url + '\'' +
                ", super=" + super.toString() +
                '}';
    }
}
