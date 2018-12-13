package top.cellargalaxy.mycloud.service.impl;

import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;
import top.cellargalaxy.mycloud.service.FileInfoService;
import top.cellargalaxy.mycloud.service.FileService;
import top.cellargalaxy.mycloud.service.OwnService;
import top.cellargalaxy.mycloud.service.fileDeal.FileDeal;
import top.cellargalaxy.mycloud.service.fileDeal.FileDealFactory;
import top.cellargalaxy.mycloud.util.IOUtil;

import javax.validation.constraints.NotNull;
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
    private final FileDeal fileDeal;
    private final File rootFolder;
    @Autowired
    private FileInfoService fileInfoService;
    @Autowired
    private OwnService ownService;

    @Autowired
    public FileServiceImpl(MycloudConfiguration mycloudConfiguration) {
        String mycloudPath = mycloudConfiguration.getMycloudPath();
        fileDeal = FileDealFactory.getFileDeal(mycloudConfiguration);
        rootFolder = new File(mycloudPath + File.separator + "mycloud");
    }

    @Override
    public String addFile(InputStream inputStream, OwnBo ownBo, UserPo userPo) throws IOException {
        ownBo.setOwnUuid(UUID.randomUUID().toString());
        ownBo.setUserId(userPo.getUserId());
        String string = fileDeal.addFile(inputStream, ownBo);
        return doAddFile(string, ownBo, userPo);
    }

    @Override
    public String addFile(String urlString, OwnBo ownBo, UserPo userPo) throws IOException {
        ownBo.setOwnUuid(UUID.randomUUID().toString());
        ownBo.setUserId(userPo.getUserId());
        String string = fileDeal.addFile(urlString, ownBo);
        BeanUtils.copyProperties(p, s);
        return doAddFile(string, ownBo, userPo);
    }

    private String doAddFile(String string, OwnBo ownBo, UserPo userPo) throws IOException {
        if (string == null) {
            string = ownService.addOwn(ownBo);
        }
        if (string != null) {
            removeFile(ownBo);
            return string;
        }
        ownService.setUrl(ownBo);
        ownBo.setUsername(userPo.getUsername());
        return null;
    }

    @Override
    public String removeFile(FileInfoPo fileInfoPo) throws IOException {
        fileInfoService.removeFileInfo(fileInfoPo);
        return fileDeal.removeFile(fileInfoPo);
    }

    @Override
    public String removeFile(OwnPo ownPo) throws IOException {
        ownService.removeOwn(ownPo);
        return fileDeal.removeFile(ownPo);
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
        return fileDeal.removeFile(ownBo);
    }

    @Override
    public FileInfoPo getFileInfoPoByMd5OrUuid(String md5OrUuid) {
        if (md5OrUuid.indexOf('-') > 0) {
            OwnPo ownPo = new OwnPo();
            ownPo.setOwnUuid(md5OrUuid);
            OwnBo ownBo = ownService.getOwn(ownPo);
            if (ownBo == null) {
                return null;
            }
            FileInfoPo fileInfoPo = new FileInfoPo();
            fileInfoPo.setFileId(ownBo.getFileId());
            fileInfoPo.setMd5(ownBo.getMd5());
            fileInfoPo.setContentType(ownBo.getContentType());
            fileInfoPo.setFileLength(ownBo.getFileLength());
            fileInfoPo.setCreateTime(ownBo.getCreateTime());
            return fileInfoPo;
        } else {
            FileInfoPo fileInfoPo = new FileInfoPo();
            fileInfoPo.setMd5(md5OrUuid);
            return fileInfoService.getFileInfo(fileInfoPo);
        }
    }

    @Override
    public String getFileByMd5OrUuid(@NotNull String md5OrUuid, OutputStream outputStream) throws IOException {
        if (md5OrUuid.indexOf('-') > 0) {
            OwnBo ownBo = new OwnBo();
            ownBo.setOwnUuid(md5OrUuid);
            ownBo.setMd5(md5OrUuid);
            return fileDeal.getFile(ownBo, outputStream);
        } else {
            FileInfoPo fileInfoPo = new FileInfoPo();
            fileInfoPo.setMd5(md5OrUuid);
            return fileDeal.getFile(fileInfoPo, outputStream);
        }
    }

    @Override
    public String getTar(OutputStream outputStream) throws IOException {
        TarArchiveOutputStream tarArchiveOutputStream = IOUtil.createTarArchiveOutputStream(outputStream);
        IOUtil.archive(tarArchiveOutputStream, rootFolder);
        return null;
    }

    @Override
    public String getTar(UserPo userPo, OutputStream outputStream) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TarArchiveOutputStream tarArchiveOutputStream = IOUtil.createTarArchiveOutputStream(outputStream);
        OwnQuery ownQuery = new OwnQuery();
        ownQuery.setUserId(userPo.getUserId());
        List<OwnBo> ownBos = ownService.listAllOwn(ownQuery);
        for (OwnBo ownBo : ownBos) {
            try (InputStream inputStream = fileDeal.getFileInputStream(ownBo)) {
                if (inputStream != null) {
                    IOUtil.archiveFile(tarArchiveOutputStream, inputStream, ownBo.getFileLength(),
                            userPo.getUsername() + File.separator + dateFormat.format(ownBo.getCreateTime()) + "_" + ownBo.getOwnUuid() + "_" + ownBo.getFileName());
                }
            }
        }
        return null;
    }
}
