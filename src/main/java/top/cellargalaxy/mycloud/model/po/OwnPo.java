package top.cellargalaxy.mycloud.model.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
@Data
public class OwnPo implements Serializable {
    private static final long serialVersionUID = -1711152764871510164L;
    private int ownId;
    private String ownUuid;
    private int userId;
    private long fileLength;
    private String contentType;
    private String fileName;
    private int fileId;
    private String sort;
    private String description;
    private Date createTime;
    private Date updateTime;
}
