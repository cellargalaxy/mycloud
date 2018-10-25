package top.cellargalaxy.mycloud.model.bo;

import top.cellargalaxy.mycloud.model.po.OwnPo;

import java.util.Objects;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
public class OwnBo extends OwnPo {
	private String username;
	private String md5;
	private String md5Url;
	private String ownUrl;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getMd5Url() {
		return md5Url;
	}

	public void setMd5Url(String md5Url) {
		this.md5Url = md5Url;
	}

	public String getOwnUrl() {
		return ownUrl;
	}

	public void setOwnUrl(String ownUrl) {
		this.ownUrl = ownUrl;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		OwnBo ownBo = (OwnBo) o;
		return Objects.equals(username, ownBo.username) &&
				Objects.equals(md5, ownBo.md5) &&
				Objects.equals(md5Url, ownBo.md5Url) &&
				Objects.equals(ownUrl, ownBo.ownUrl);
	}

	@Override
	public int hashCode() {

		return Objects.hash(super.hashCode(), username, md5, md5Url, ownUrl);
	}

	@Override
	public String toString() {
		return "OwnBo{" +
				"username='" + username + '\'' +
				", md5='" + md5 + '\'' +
				'}';
	}
}
