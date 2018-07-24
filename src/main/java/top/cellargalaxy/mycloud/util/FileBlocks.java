package top.cellargalaxy.mycloud.util;

import java.io.*;
import java.util.Arrays;

/**
 * @author cellargalaxy
 * @time 2018/7/17
 */
public class FileBlocks implements Closeable {
	private final File file;
	private final int blockLength;
	private final InputStream inputStream;
	private int blockIndex;
	private final int[] blockLengths;

	public FileBlocks(File file, int blockLength) throws FileNotFoundException {
		this.file = file;
		this.blockLength = blockLength;
		inputStream = new BufferedInputStream(new FileInputStream(file));
		blockIndex = 0;
		int remainder = (int) (file.length() % blockLength);
		int blockCount = (int) (file.length() / blockLength);
		if (remainder > 0) {
			blockCount++;
		}
		blockLengths = new int[blockCount];
		for (int i = 0; i < blockLengths.length - 1; i++) {
			blockLengths[i] = blockLength;
		}
		if (remainder > 0) {
			blockLengths[blockCount - 1] = remainder;
		} else {
			blockLengths[blockCount - 1] = blockLength;
		}
	}

	public byte[] next() throws IOException {
		if (blockIndex >= blockLengths.length) {
			return null;
		}
		byte[] bytes = new byte[blockLengths[blockIndex++]];
		int count = 0;
		do {
			count += inputStream.read(bytes, count, bytes.length);
		} while (count < bytes.length);
		return bytes;
	}

	public int getBlockIndex() {
		return blockIndex;
	}

	public File getFile() {
		return file;
	}

	public long getBlockLength() {
		return blockLength;
	}

	public int[] getBlockLengths() {
		return blockLengths;
	}

	@Override
	public String toString() {
		return "FileBlocks{" +
				"file=" + file +
				", blockLength=" + blockLength +
				", inputStream=" + inputStream +
				", blockIndex=" + blockIndex +
				", blockLengths=" + Arrays.toString(blockLengths) +
				'}';
	}

	@Override
	public void close() throws IOException {
		inputStream.close();
	}
}