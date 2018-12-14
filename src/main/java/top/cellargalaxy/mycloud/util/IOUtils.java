package top.cellargalaxy.mycloud.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

import java.io.*;

/**
 * @author cellargalaxy
 * @time 2018/7/5
 */
public class IOUtils {

    public static final TarArchiveOutputStream createTarArchiveOutputStream(File file) throws FileNotFoundException {
        return createTarArchiveOutputStream(IOUtils.getOutputStream(file));
    }

    public static final TarArchiveOutputStream createTarArchiveOutputStream(OutputStream outputStream) {
        TarArchiveOutputStream tarArchiveOutputStream = new TarArchiveOutputStream(outputStream);
        //如果tar里的路径长过100byte会报错，这里设置长文件名的模式
        tarArchiveOutputStream.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
        return tarArchiveOutputStream;
    }

    public static final void archive(TarArchiveOutputStream tarArchiveOutputStream, File file) throws IOException {
        String basePath = file.getName();
        if (file.isDirectory()) {
            archiveFolder(file, tarArchiveOutputStream, basePath);
        } else {
            archiveFile(tarArchiveOutputStream, file, basePath);
        }
    }

    private static void archiveFolder(File folder, TarArchiveOutputStream tarArchiveOutputStream, String basePath) throws IOException {
        File[] listFiles = folder.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.isDirectory()) {
                    archiveFolder(file, tarArchiveOutputStream, basePath + File.separator + file.getName());
                } else {
                    archiveFile(tarArchiveOutputStream, file, basePath);
                }
            }
        }
    }

    private static void archiveFile(TarArchiveOutputStream tarArchiveOutputStream, File file, String basePath) throws IOException {
        try (InputStream inputStream = IOUtils.getInputStream(file)) {
            archiveFile(tarArchiveOutputStream, inputStream, file.length(), basePath + File.separator + file.getName());
        }
    }

    public static void archiveFile(TarArchiveOutputStream tarArchiveOutputStream, InputStream inputStream, long fileLength, String archiveName) throws IOException {
        TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(archiveName);
        tarArchiveEntry.setSize(fileLength);
        tarArchiveOutputStream.putArchiveEntry(tarArchiveEntry);
        IOUtils.stream(inputStream, tarArchiveOutputStream);
        tarArchiveOutputStream.closeArchiveEntry();//这里必须写，否则会失败
    }

    public static final String md5Hex(File file) throws IOException {
        try (InputStream inputStream = getInputStream(file)) {
            return DigestUtils.md5Hex(inputStream);
        }
    }

    public static final byte[] readFile(File file) throws IOException {
        try (InputStream inputStream = getInputStream(file)) {
            int count = 0;
            byte[] bytes = new byte[(int) file.length()];
            do {
                count += inputStream.read(bytes, count, bytes.length);
            } while (count < bytes.length);
            return bytes;
        }
    }

    public static final void writeFile(byte[] bytes, File file) throws IOException {
        try (OutputStream outputStream = getOutputStream(file)) {
            outputStream.write(bytes);
        }
    }

    public static final BufferedOutputStream getOutputStream(File file) throws FileNotFoundException {
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return new BufferedOutputStream(new FileOutputStream(file));
    }

    public static final BufferedInputStream getInputStream(File file) throws FileNotFoundException {
        return new BufferedInputStream(new FileInputStream(file));
    }

    public static final BufferedWriter getWriter(File file) throws IOException {
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return new BufferedWriter(new FileWriter(file));
    }

    public static final BufferedReader getReader(File file) throws FileNotFoundException {
        return new BufferedReader(new FileReader(file));
    }

    public static final void stream(InputStream inputStream, OutputStream... outputStreams) throws IOException {
        byte[] bytes = new byte[1024];
        int len;
        while ((len = inputStream.read(bytes, 0, bytes.length)) != -1) {
            for (OutputStream outputStream : outputStreams) {
                outputStream.write(bytes, 0, len);
            }
        }
    }

    public static final long getFolderLength(File folder) {
        long length = 0;
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    length += getFolderLength(file);
                } else {
                    length += file.length();
                }
            }
        }
        return length;
    }

    public static final boolean deleteFolder(File folder) {
        deleteFileInFolder(folder);
        return folder.delete();
    }

    public static final boolean deleteFileInFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFileInFolder(file);
                }
                file.delete();
            }
        }
        files = folder.listFiles();
        return files == null || files.length == 0;
    }
}
