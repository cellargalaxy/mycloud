package top.cellargalaxy.mycloud.model.po;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by cellargalaxy on 18-7-11.
 */
@Data
public class AuthorizationPo implements Serializable {
	private static final long serialVersionUID = 1523109653163477354L;
	private int authorizationId;
	private int userId;
	private Permission permission;
	private Date createTime;
	private Date updateTime;
}
