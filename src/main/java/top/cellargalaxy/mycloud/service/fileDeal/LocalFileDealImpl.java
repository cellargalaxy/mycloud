package top.cellargalaxy.mycloud.service.fileDeal;

import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.service.fileDownload.FileDownload;
import top.cellargalaxy.mycloud.service.fileDownload.FileDownloadImpl;
import top.cellargalaxy.mycloud.util.IOUtil;
import top.cellargalaxy.mycloud.util.MimeSuffixNameUtil;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author cellargalaxy
 * @time 2018/8/29
 */
public class LocalFileDealImpl implements FileDeal {
    private final FileDownload fileDownload;
    private final File md5Folder;
    private final File uuidFolder;
    private final double localFileMaxSpaceRate;
    private final ConcurrentHashMap<String, Integer> fileWeightMap = new ConcurrentHashMap<>();

    public LocalFileDealImpl(MycloudConfiguration mycloudConfiguration) {
        fileDownload = new FileDownloadImpl(1000 * 10);
        md5Folder = new File(mycloudConfiguration.getMycloudPath() + File.separator + "mycloud" + File.separator + "md5");
        uuidFolder = new File(mycloudConfiguration.getMycloudPath() + File.separator + "mycloud" + File.separator + "uuid");
        localFileMaxSpaceRate = mycloudConfiguration.getLocalFileMaxSpaceRate();
    }

    @Override
    public String addFile(InputStream inputStream, FileInfoPo fileInfoPo) throws IOException {
        if (isFull()) {
            return "磁盘使用率已满";
        }
        File localFile = createLocalFile(fileInfoPo);
        try (OutputStream outputStream = IOUtil.getOutputStream(localFile)) {
            IOUtil.stream(inputStream, outputStream);
        } catch (IOException e) {
            localFile.delete();
            return e.getMessage();
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
        try (OutputStream outputStream = IOUtil.getOutputStream(localFile)) {
            IOUtil.stream(inputStream, outputStream);
        } catch (IOException e) {
            localFile.delete();
            return e.getMessage();
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
        try (OutputStream outputStream = IOUtil.getOutputStream(localFile)) {
            fileDownload.downloadFile(urlString, fileInfoPo, outputStream);
        } catch (IOException e) {
            localFile.delete();
            return e.getMessage();
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
        try (OutputStream outputStream = IOUtil.getOutputStream(localFile)) {
            fileDownload.downloadFile(urlString, ownPo, outputStream);
        } catch (IOException e) {
            localFile.delete();
            return e.getMessage();
        }
        String mimeType = getMimeType(localFile);
        if (mimeType != null) {
            ownPo.setContentType(mimeType);
        }
        return null;
    }

    private String getMimeType(File localFile) throws IOException {
        if (localFile != null && localFile.exists()) {
            try (InputStream inputStream = IOUtil.getInputStream(localFile)) {
                String mimeType = MimeSuffixNameUtil.getMimeType(inputStream);
                if (mimeType != null && !mimeType.trim().startsWith("application")) {
                    return mimeType;
                }
            }
        }
        return null;
    }

    @Override
    public String removeFile(FileInfoPo fileInfoPo) throws IOException {
        File localFile = createLocalFile(fileInfoPo);
        localFile.delete();
        return null;
    }

    @Override
    public String removeFile(OwnPo ownPo) throws IOException {
        File localFile = createLocalFile(ownPo);
        localFile.delete();
        return null;
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

    @Override
    public InputStream getFileInputStream(FileInfoPo fileInfoPo) throws FileNotFoundException {
        File localFile = createLocalFile(fileInfoPo);
        if (!localFile.exists()) {
            return null;
        }
        return IOUtil.getInputStream(localFile);
    }

    @Override
    public InputStream getFileInputStream(OwnBo ownBo) throws FileNotFoundException {
        File localFile = createLocalFile(ownBo);
        if (localFile.exists()) {
            return IOUtil.getInputStream(localFile);
        } else {
            FileInfoPo fileInfoPo = new FileInfoPo();
            fileInfoPo.setMd5(ownBo.getMd5());
            return getFileInputStream(fileInfoPo);
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
        try (InputStream inputStream = IOUtil.getInputStream(localFile)) {
            IOUtil.stream(inputStream, outputStream);
        }
        return null;
    }

    public String cacheFile(InputStream inputStream, FileInfoPo fileInfoPo) throws IOException {
        while (isFull()) {
            if (fileWeightMap.size() == 0) {
                return "磁盘空间已满";
            }
            deleteColdFile();
        }
        File localFile = createLocalFile(fileInfoPo);
        try (OutputStream outputStream = IOUtil.getOutputStream(localFile)) {
            IOUtil.stream(inputStream, outputStream);
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
        try (OutputStream outputStream = IOUtil.getOutputStream(localFile)) {
            IOUtil.stream(inputStream, outputStream);
        }
        return null;
    }

    private void deleteColdFile() {
        Integer coldCount = Integer.MAX_VALUE;
        long coldFilLength = Long.MIN_VALUE;
        String coldFile = null;
        for (Map.Entry<String, Integer> entry : fileWeightMap.entrySet()) {
            if (entry.getValue() < coldCount) {
                coldCount = entry.getValue();
                coldFile = entry.getKey();
            } else if (entry.getValue() == coldCount && new File(entry.getKey()).length() > coldFilLength) {
                coldCount = entry.getValue();
                coldFile = entry.getKey();
            }
        }
        if (coldFile != null) {
            new File(coldFile).delete();
        }
    }

    private File createLocalFile(FileInfoPo fileInfoPo) {
        return new File(md5Folder.getAbsolutePath() + File.separator + fileInfoPo.getMd5());
    }

    private File createLocalFile(OwnPo ownPo) {
        return new File(uuidFolder.getAbsolutePath() + File.separator + ownPo.getOwnUuid());
    }

    private boolean isFull() {
        return usedRate() > localFileMaxSpaceRate;
    }

    private double usedRate() {
        return (md5Folder.getTotalSpace() - md5Folder.getFreeSpace()) * 1.0 / md5Folder.getTotalSpace();
    }


}
