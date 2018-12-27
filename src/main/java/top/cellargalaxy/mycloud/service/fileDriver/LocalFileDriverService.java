package top.cellargalaxy.mycloud.service.fileDriver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.service.PathService;
import top.cellargalaxy.mycloud.service.fileDownload.FileDownload;
import top.cellargalaxy.mycloud.util.IOUtils;
import top.cellargalaxy.mycloud.util.MimeSuffixNameUtils;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cellargalaxy
 * @time 2018/8/29
 */
@Service
public class LocalFileDriverService implements FileDriverService {
    private final File md5Folder;
    private final File uuidFolder;
    private final double localFileMaxSpaceRate;
    private final ConcurrentHashMap<String, Integer> fileWeightMap = new ConcurrentHashMap<>();
    @Autowired
    private FileDownload fileDownload;

    @Autowired
    public LocalFileDriverService(MycloudConfiguration mycloudConfiguration, PathService pathService) {
        md5Folder = pathService.getMd5Folder();
        uuidFolder = pathService.getUuidFolder();
        localFileMaxSpaceRate = mycloudConfiguration.getLocalFileMaxSpaceRate();
    }

    @Override
    public String addFile(InputStream inputStream, FileInfoPo fileInfoPo) throws IOException {
        if (isFull()) {
            return "磁盘使用率已满";
        }
        File localFile = createLocalFile(fileInfoPo);
        try (OutputStream outputStream = IOUtils.getOutputStream(localFile)) {
            IOUtils.stream(inputStream, outputStream);
        } catch (IOException e) {
            localFile.delete();
            throw e;
        }
        String mimeType = getMimeType(localFile);
        if (mimeType != null) {
            fileInfoPo.setContentType(mimeType);
        }
        return null;
    }

    @Override
    public String addFile(InputStream inputStream, OwnPo ownPo) throws IOException {
        if (isFull()) {
            return "磁盘使用率已满";
        }
        File localFile = createLocalFile(ownPo);
        try (OutputStream outputStream = IOUtils.getOutputStream(localFile)) {
            IOUtils.stream(inputStream, outputStream);
        } catch (IOException e) {
            localFile.delete();
            throw e;
        }
        String mimeType = getMimeType(localFile);
        if (mimeType != null) {
            ownPo.setContentType(mimeType);
        }
        return null;
    }

    @Override
    public String addFile(String urlString, FileInfoPo fileInfoPo) throws IOException {
        if (isFull()) {
            return "磁盘使用率已满";
        }
        File localFile = createLocalFile(fileInfoPo);
        try (OutputStream outputStream = IOUtils.getOutputStream(localFile)) {
            fileDownload.downloadFile(urlString, fileInfoPo, outputStream);
        } catch (IOException e) {
            localFile.delete();
            throw e;
        }
        String mimeType = getMimeType(localFile);
        if (mimeType != null) {
            fileInfoPo.setContentType(mimeType);
        }
        return null;
    }

    @Override
    public String addFile(String urlString, OwnPo ownPo) throws IOException {
        if (isFull()) {
            return "磁盘使用率已满";
        }
        File localFile = createLocalFile(ownPo);
        try (OutputStream outputStream = IOUtils.getOutputStream(localFile)) {
            fileDownload.downloadFile(urlString, ownPo, outputStream);
        } catch (IOException e) {
            localFile.delete();
            throw e;
        }
        String mimeType = getMimeType(localFile);
        if (mimeType != null) {
            ownPo.setContentType(mimeType);
        }
        return null;
    }

    private String getMimeType(File localFile) throws IOException {
        if (localFile != null && localFile.exists()) {
            try (InputStream inputStream = IOUtils.getInputStream(localFile)) {
                String mimeType = MimeSuffixNameUtils.getMimeType(inputStream);
                if (mimeType != null && !mimeType.trim().toLowerCase().startsWith("application")) {
                    return mimeType;
                }
            }
        }
        return null;
    }

    @Override
    public String removeFile(FileInfoPo fileInfoPo) throws IOException {
        File localFile = createLocalFile(fileInfoPo);
        return localFile.exists() && !localFile.delete() ? "删除失败" : null;
    }

    @Override
    public String removeFile(OwnPo ownPo) throws IOException {
        File localFile = createLocalFile(ownPo);
        return localFile.exists() && !localFile.delete() ? "删除失败" : null;
    }

    @Override
    public String getFile(FileInfoPo fileInfoPo, OutputStream outputStream) throws IOException {
        File localFile = createLocalFile(fileInfoPo);
        return getFile(localFile, outputStream);
    }

    @Override
    public String getFile(OwnBo ownBo, OutputStream outputStream) throws IOException {
        File localFile = createLocalFile(ownBo);
        if (localFile.exists()) {
            return getFile(localFile, outputStream);
        } else {
            FileInfoPo fileInfoPo = new FileInfoPo();
            fileInfoPo.setMd5(ownBo.getMd5());
            return getFile(fileInfoPo, outputStream);
        }
    }

    private String getFile(File localFile, OutputStream outputStream) throws IOException {
        if (!localFile.exists()) {
            return "文件不存在";
        }
        Integer integer = fileWeightMap.get(localFile.getAbsolutePath());
        if (integer == null) {
            integer = 0;
        }
        fileWeightMap.put(localFile.getAbsolutePath(), integer + 1);
        try (InputStream inputStream = IOUtils.getInputStream(localFile)) {
            IOUtils.stream(inputStream, outputStream);
        }
        return null;
    }

    @Override
    public InputStream getFileInputStream(FileInfoPo fileInfoPo) throws FileNotFoundException {
        File localFile = createLocalFile(fileInfoPo);
        if (!localFile.exists()) {
            return null;
        }
        return IOUtils.getInputStream(localFile);
    }

    @Override
    public InputStream getFileInputStream(OwnBo ownBo) throws FileNotFoundException {
        File localFile = createLocalFile(ownBo);
        if (localFile.exists()) {
            return IOUtils.getInputStream(localFile);
        } else {
            FileInfoPo fileInfoPo = new FileInfoPo();
            fileInfoPo.setMd5(ownBo.getMd5());
            return getFileInputStream(fileInfoPo);
        }
    }

    public String cacheFile(InputStream inputStream, FileInfoPo fileInfoPo) throws IOException {
        while (isFull()) {
            if (fileWeightMap.size() == 0) {
                return "磁盘空间已满";
            }
            deleteColdFile();
        }
        File localFile = createLocalFile(fileInfoPo);
        try (OutputStream outputStream = IOUtils.getOutputStream(localFile)) {
            IOUtils.stream(inputStream, outputStream);
        }
        return null;
    }

    public String cacheFile(InputStream inputStream, OwnPo ownPo) throws IOException {
        while (isFull()) {
            if (fileWeightMap.size() == 0) {
                return "磁盘空间已满";
            }
            deleteColdFile();
        }
        File localFile = createLocalFile(ownPo);
        try (OutputStream outputStream = IOUtils.getOutputStream(localFile)) {
            IOUtils.stream(inputStream, outputStream);
        }
        return null;
    }

    private void deleteColdFile() {
        Integer coldCount = Integer.MAX_VALUE;
        File coldFile = null;
        for (Map.Entry<String, Integer> entry : fileWeightMap.entrySet()) {
            if (coldFile == null || entry.getValue() < coldCount) {
                coldCount = entry.getValue();
                coldFile = new File(entry.getKey());
            } else if (entry.getValue() == coldCount && new File(entry.getKey()).length() > coldFile.length()) {
                coldCount = entry.getValue();
                coldFile = new File(entry.getKey());
            }
        }
        if (coldFile != null) {
            coldFile.delete();
        }
    }

    private File createLocalFile(FileInfoPo fileInfoPo) {
        return new File(md5Folder.getAbsolutePath() + File.separator + fileInfoPo.getMd5());
    }

    private File createLocalFile(OwnPo ownPo) {
        File file = new File(uuidFolder.getAbsolutePath() + File.separator + ownPo.getOwnUuid());
        if (file.exists()) {
            return file;
        }
        return new File(md5Folder.getAbsolutePath() + File.separator + ownPo.getMd5());
    }

    private boolean isFull() {
        return usedRate() > localFileMaxSpaceRate;
    }

    private double usedRate() {
        return (md5Folder.getTotalSpace() - md5Folder.getFreeSpace()) * 1.0 / md5Folder.getTotalSpace();
    }
}
