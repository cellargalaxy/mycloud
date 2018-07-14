package top.cellargalaxy.mycloud.util;

import java.io.*;

/**
 * @author cellargalaxy
 * @time 2018/7/5
 */
public class FileUtil {
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
