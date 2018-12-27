package top.cellargalaxy.mycloud.model.bo;

import lombok.Data;
import top.cellargalaxy.mycloud.model.po.OwnExpirePo;

/**
 * @author cellargalaxy
 * @time 2018/12/13
 */
@Data
public class OwnExpireBo extends OwnExpirePo {
	private String ownUuid;
	private int userId;
	private String fileName;
	private String sort;
	private String description;
	private long fileLength;
	private String contentType;
	private String md5;
	private int fileId;

	@Override
	public String toString() {
		return "OwnExpireBo{" +
				"ownUuid='" + ownUuid + '\'' +
				", userId=" + userId +
				", fileName='" + fileName + '\'' +
				", sort='" + sort + '\'' +
				", description='" + description + '\'' +
				", fileLength=" + fileLength +
				", contentType='" + contentType + '\'' +
				", md5='" + md5 + '\'' +
				", fileId=" + fileId +
				", super=" + super.toString() +
				'}';
	}
}
