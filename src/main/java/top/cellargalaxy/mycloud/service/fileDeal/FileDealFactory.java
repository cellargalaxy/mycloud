package top.cellargalaxy.mycloud.service.fileDeal;

import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;

/**
 * Created by cellargalaxy on 18-10-27.
 */
public class FileDealFactory {
	private static LocalFileDealImpl localFileDeal;

	public synchronized static FileDeal getFileDeal(MycloudConfiguration mycloudConfiguration) {
		if (MycloudConfiguration.LOCAL_START.equals(mycloudConfiguration.getInstallationType())) {
			if (localFileDeal == null) {
				localFileDeal = new LocalFileDealImpl(mycloudConfiguration);
			}
			return localFileDeal;
		}
		throw new IllegalArgumentException("非法安装类型：" + mycloudConfiguration.getInstallationType());
	}
}
