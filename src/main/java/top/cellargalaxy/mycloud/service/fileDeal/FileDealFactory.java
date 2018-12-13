package top.cellargalaxy.mycloud.service.fileDeal;

import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.util.StringUtils;

/**
 * Created by cellargalaxy on 18-10-27.
 */
public class FileDealFactory {
    private static LocalFileDealImpl localFileDeal;

    public synchronized static FileDeal getFileDeal(MycloudConfiguration mycloudConfiguration) {
        if (StringUtils.isBlank(mycloudConfiguration.getHdfsUrl())) {
            if (localFileDeal == null) {
                localFileDeal = new LocalFileDealImpl(mycloudConfiguration);
            }
            return localFileDeal;
        }
        throw null;
    }
}
