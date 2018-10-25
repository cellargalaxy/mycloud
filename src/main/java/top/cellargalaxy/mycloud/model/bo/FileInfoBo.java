package top.cellargalaxy.mycloud.model.bo;

import top.cellargalaxy.mycloud.model.po.FileInfoPo;

/**
 * @author cellargalaxy
 * @time 2018/7/16
 */
public class FileInfoBo extends FileInfoPo {
	private String md5Url;

	public String getMd5Url() {
		return md5Url;
	}

	public void setMd5Url(String md5Url) {
		this.md5Url = md5Url;
	}
}
