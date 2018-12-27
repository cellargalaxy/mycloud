package top.cellargalaxy.mycloud.model.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cellargalaxy
 * @time 2018/7/9
 */
@Data
public class UserPo implements Serializable {
	private static final long serialVersionUID = -8175567370336139965L;
	private int userId;
	private String username;
	@JsonIgnore
	private String password;
	private Date createTime;
	private Date updateTime;

	@Override
	public String toString() {
		return "UserPo{" +
				"userId=" + userId +
				", username='" + username + '\'' +
				", createTime=" + createTime +
				", updateTime=" + updateTime +
				'}';
	}
}
