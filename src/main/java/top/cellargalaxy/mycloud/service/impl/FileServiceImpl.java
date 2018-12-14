package top.cellargalaxy.mycloud.service.impl;

import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.service.FileInfoService;
import top.cellargalaxy.mycloud.service.FileService;
import top.cellargalaxy.mycloud.service.OwnService;
import top.cellargalaxy.mycloud.service.PathService;
import top.cellargalaxy.mycloud.service.fileDeal.FileDriverService;
import top.cellargalaxy.mycloud.util.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

/**
 * @author cellargalaxy
 * @time 2018/10/25
 */
@Service
public class FileServiceImpl implements FileService {
    private final PathService pathService;
    private final File driveFolder;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private OwnService ownService;
    @Autowired
    private FileDriverService fileDriverService;

    @Autowired
    public FileServiceImpl(PathService pathService) {
        this.pathService = pathService;
        driveFolder = pathService.getDriveFolder();
    }

    @Override
    public String addFile(InputStream inputStream, OwnBo ownBo, UserPo userPo) throws IOException {
        ownBo.setOwnUuid(UUID.randomUUID().toString());
        ownBo.setUserId(userPo.getUserId());
        ownBo.setUsername(userPo.getUsername());
        String string = fileDriverService.addFile(inputStream, ownBo);
        return addFile(string, ownBo);
    }

    @Override
    public String addFile(String urlString, OwnBo ownBo, UserPo userPo) throws IOException {
        ownBo.setOwnUuid(UUID.randomUUID().toString());
        ownBo.setUserId(userPo.getUserId());
        ownBo.setUsername(userPo.getUsername());
        String string = fileDriverService.addFile(urlString, ownBo);
        return addFile(string, ownBo);
    }

    private String addFile(String string, OwnBo ownBo) throws IOException {
        if (string != null) {
            fileDriverService.removeFile(ownBo);
            return string;
        }
        string = ownService.addOwn(ownBo);
        if (string != null) {
            fileDriverService.removeFile(ownBo);
            return string;
        }
        pathService.setUrl(ownBo);
        return string;
    }

    @Override
    public String removeFile(FileInfoPo fileInfoPo) throws IOException {
        fileInfoService.removeFileInfo(fileInfoPo);
        return fileDriverService.removeFile(fileInfoPo);
    }

    @Override
    public String removeFile(OwnPo ownPo) throws IOException {
        ownService.removeOwn(ownPo);
        return fileDriverService.removeFile(ownPo);
    }

    @Override
    public String removeFile(OwnPo ownPo, UserPo userPo) throws IOException {
        OwnBo ownBo = ownService.getOwn(ownPo);
        if (ownBo == null) {
            return null;
        }
        if (ownBo.getUserId() != userPo.getUserId()) {
            return "不得删除他人文件";
        }
        ownService.removeOwn(ownBo);
        return fileDriverService.removeFile(ownBo);
    }

    @Override
    public FileInfoPo getFileInfoPoByMd5OrUuid(String md5OrUuid) {
        if (md5OrUuid == null) {
            return null;
        }
        if (md5OrUuid.indexOf('-') > 0) {
            OwnPo ownPo = new OwnPo();
            ownPo.setOwnUuid(md5OrUuid);
            OwnBo ownBo = ownService.getOwn(ownPo);
            if (ownBo == null) {
                return null;
            }
            FileInfoPo fileInfoPo = new FileInfoPo();
            BeanUtils.copyProperties(ownBo, fileInfoPo);
            return fileInfoPo;
        } else {
            FileInfoPo fileInfoPo = new FileInfoPo();
            fileInfoPo.setMd5(md5OrUuid);
            return fileInfoService.getFileInfo(fileInfoPo);
        }
    }

    @Override
    public String getFileByMd5OrUuid(String md5OrUuid, OutputStream outputStream) throws IOException {
        if (md5OrUuid == null) {
            return null;
        }
        if (md5OrUuid.indexOf('-') > 0) {
            OwnBo ownBo = new OwnBo();
            ownBo.setOwnUuid(md5OrUuid);
            return fileDriverService.getFile(ownBo, outputStream);
        } else {
            FileInfoPo fileInfoPo = new FileInfoPo();
            fileInfoPo.setMd5(md5OrUuid);
            return fileDriverService.getFile(fileInfoPo, outputStream);
        }
    }

    @Override
    public String getTar(OutputStream outputStream) throws IOException {
        TarArchiveOutputStream tarArchiveOutputStream = IOUtils.createTarArchiveOutputStream(outputStream);
        IOUtils.archive(tarArchiveOutputStream, driveFolder);
        return null;
    }

    @Override
    public String getTar(UserPo userPo, OutputStream outputStream) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TarArchiveOutputStream tarArchiveOutputStream = IOUtils.createTarArchiveOutputStream(outputStream);
        OwnQuery ownQuery = new OwnQuery();
        ownQuery.setUserId(userPo.getUserId());
        List<OwnBo> ownBos = ownService.listAllOwn(ownQuery);
        for (OwnBo ownBo : ownBos) {
            try (InputStream inputStream = fileDriverService.getFileInputStream(ownBo)) {
                if (inputStream != null) {
                    IOUtils.archiveFile(tarArchiveOutputStream, inputStream, ownBo.getFileLength(),
                            userPo.getUsername() + "/" + dateFormat.format(ownBo.getCreateTime()) + "_" + ownBo.getOwnUuid() + "_" + ownBo.getFileName());
                }
            }
        }
        return null;
    }
}
