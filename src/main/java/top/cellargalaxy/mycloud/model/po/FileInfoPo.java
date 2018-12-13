package top.cellargalaxy.mycloud.model.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cellargalaxy
 * @time 2018/7/3
 */
@Data
public class FileInfoPo implements Serializable {
    private static final long serialVersionUID = 4632424672128158014L;
    private int fileId;
    private String md5;
    private long fileLength;
    private String contentType;
    private Date createTime;
}
