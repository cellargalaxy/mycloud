package top.cellargalaxy.mycloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.service.PathService;

import java.io.File;

/**
 * @author cellargalaxy
 * @time 2018/12/14
 */
@Service
public class PathServiceImpl implements PathService {
    private final String domain;
    private final File tmpFolder;
    private final File driveFolder;
    private final File md5Folder;
    private final File uuidFolder;

    @Autowired
    public PathServiceImpl(MycloudConfiguration mycloudConfiguration) {
        domain = mycloudConfiguration.getDomain();
        File mycloudFolder = new File(mycloudConfiguration.getMycloudPath());
        tmpFolder = new File(mycloudFolder.getAbsolutePath() + File.separator + "tmp");
        driveFolder = new File(mycloudFolder.getAbsolutePath() + File.separator + "drive");
        md5Folder = new File(driveFolder.getAbsolutePath() + File.separator + "md5");
        uuidFolder = new File(driveFolder.getAbsolutePath() + File.separator + "uuid");
    }

    @Override
    public void setUrl(OwnBo ownBo) {
        ownBo.setMd5Url(domain + "/" + ownBo.getMd5());
        ownBo.setOwnUrl(domain + "/" + ownBo.getOwnUuid());
    }

    @Override
    public void setUrl(FileInfoBo fileInfoBo) {
        fileInfoBo.setMd5Url(domain + "/" + fileInfoBo.getMd5());
    }

    @Override
    public File getMd5Folder() {
        return md5Folder;
    }

    @Override
    public File getUuidFolder() {
        return uuidFolder;
    }

    @Override
    public File getDriveFolder() {
        return driveFolder;
    }

    @Override
    public File getTmpFolder() {
        return tmpFolder;
    }
}