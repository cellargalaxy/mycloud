package top.cellargalaxy.mycloud.util;

import eu.medsea.mimeutil.MimeUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;

/**
 * @author cellargalaxy
 * @time 2018/7/5
 */
public class StreamUtil {
	static {
		MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
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

	public static final OutputStream getOutputStream(File file) throws FileNotFoundException {
		if (file.getParentFile() != null && !file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		return new BufferedOutputStream(new FileOutputStream(file));
	}

	public static final InputStream getInputStream(File file) throws FileNotFoundException {
		return new BufferedInputStream(new FileInputStream(file));
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

	public static final String getMimeType(File file) {
		if (file == null || !file.exists()) {
			return null;
		}
		Collection mimeType = MimeUtil.getMimeTypes(file);
		return mimeType != null ? mimeType.toString() : null;
	}
}
