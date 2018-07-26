package top.cellargalaxy.mycloud.model.bo;

import top.cellargalaxy.mycloud.model.po.FileInfoPo;

import java.util.Objects;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
public class FileInfoBo extends FileInfoPo {
	private String url="https://i.loli.net/2018/04/10/5accdcbcb1738.jpg";

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "FileInfoBo{" +
				"super=" + super.toString() +
				", url='" + url + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		FileInfoBo that = (FileInfoBo) o;
		return Objects.equals(url, that.url);
	}

	@Override
	public int hashCode() {

		return Objects.hash(super.hashCode(), url);
	}
}
