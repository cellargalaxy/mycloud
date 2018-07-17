package top.cellargalaxy.mycloud.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;

/**
 * @author cellargalaxy
 * @time 2018/7/5
 */
public class StreamUtil {
	public static final String md5Hex(File file) throws IOException {
		try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
			return DigestUtils.md5Hex(inputStream);
		}
	}

	public static final byte[] readFile(File file) throws IOException {
		try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
			int count = 0;
			byte[] bytes = new byte[(int) file.length()];
			do {
				count += inputStream.read(bytes, count, bytes.length);
			} while (count < bytes.length);
			return bytes;
		}
	}

	public static final void writeFile(byte[] bytes, File file) throws IOException {
		try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
			outputStream.write(bytes);
		}
	}
}
