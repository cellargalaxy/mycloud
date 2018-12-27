package top.cellargalaxy.mycloud.service.fileDriver;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author cellargalaxy
 * @time 2018/10/25
 */
public class HadoopFileService {
    private static String hdfsUrl;
    private static FileSystem fileSystem;

    public HadoopFileService(MycloudConfiguration mycloudConfiguration) throws IOException {
        synchronized (HadoopFileService.class) {
            if (fileSystem == null) {
                hdfsUrl = mycloudConfiguration.getHdfsUrl();
                Configuration configuration = new Configuration();
                configuration.set("fs.defaultFS", hdfsUrl);
                fileSystem = FileSystem.get(configuration);
            }

            if (fileSystem != null) {
                Path path = new Path(hdfsUrl + "/mycloud");
                if (!fileSystem.exists(path)) {
                    fileSystem.mkdirs(path);
                }
            }
        }
    }

    public String addFile(InputStream inputStream, FileInfoPo fileInfoPo) throws IOException {
        Path path = new Path(hdfsUrl + "/mycloud/md5/" + fileInfoPo.getMd5());
        try (FSDataOutputStream fsDataOutputStream = fileSystem.create(path)) {
            IOUtils.stream(inputStream, fsDataOutputStream);
        }
        return null;
    }


    public String addFile(InputStream inputStream, OwnPo ownPo, UserPo userPo) throws IOException {
        Path path = new Path(hdfsUrl + "/mycloud/uuid/" + ownPo.getOwnUuid());
        try (FSDataOutputStream fsDataOutputStream = fileSystem.create(path)) {
            IOUtils.stream(inputStream, fsDataOutputStream);
        }
        return null;
    }


    public String removeFile(FileInfoPo fileInfoPo) throws IOException {
        Path path = new Path(hdfsUrl + "/mycloud/md5/" + fileInfoPo.getMd5());
        fileSystem.deleteOnExit(path);
        return null;
    }


    public String removeFile(OwnPo ownPo) throws IOException {
        Path path = new Path(hdfsUrl + "/mycloud/uuid/" + ownPo.getOwnUuid());
        fileSystem.deleteOnExit(path);
        return null;
    }


    public String getFile(FileInfoPo fileInfoPo, OutputStream outputStream) throws IOException {
        Path path = new Path(hdfsUrl + "/mycloud/md5/" + fileInfoPo.getMd5());
        try (InputStream inputStream = fileSystem.open(path)) {
            IOUtils.stream(inputStream, outputStream);
        }
        return null;
    }


    public String getFile(OwnPo ownPo, OutputStream outputStream) throws IOException {
        Path path = new Path(hdfsUrl + "/mycloud/uuid/" + ownPo.getOwnUuid());
        try (InputStream inputStream = fileSystem.open(path)) {
            IOUtils.stream(inputStream, outputStream);
        }
        return null;
    }
}
