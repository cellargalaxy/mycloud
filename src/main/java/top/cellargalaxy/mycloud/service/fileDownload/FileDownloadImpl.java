package top.cellargalaxy.mycloud.service.fileDownload;

import org.springframework.stereotype.Service;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.util.IOUtils;
import top.cellargalaxy.mycloud.util.MimeSuffixNameUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by cellargalaxy on 18-10-27.
 */
@Service
public class FileDownloadImpl implements FileDownload {
	private final int connectTimeout;

	public FileDownloadImpl(MycloudConfiguration mycloudConfiguration) {
		connectTimeout = mycloudConfiguration.getDownloadUrlConnectTimeout();
	}

	@Override
	public String downloadFile(String urlString, FileInfoPo fileInfoPo, OutputStream... outputStreams) throws IOException {
		URLConnection urlConnection = createURLConnection(urlString);
		fileInfoPo.setContentType(urlConnection.getContentType());
		fileInfoPo.setFileLength(urlConnection.getContentLength());
		try (InputStream inputStream = urlConnection.getInputStream()) {
			IOUtils.stream(inputStream, outputStreams);
		}
		return null;
	}

	@Override
	public String downloadFile(String urlString, OwnPo ownPo, OutputStream... outputStreams) throws IOException {
		URLConnection urlConnection = createURLConnection(urlString);
		ownPo.setContentType(urlConnection.getContentType());
		ownPo.setFileLength(urlConnection.getContentLength());
		String suffixName = MimeSuffixNameUtils.mime2SuffixName(ownPo.getContentType());
		if (suffixName != null) {
			ownPo.setFileName(ownPo.getOwnUuid() + "." + MimeSuffixNameUtils.mime2SuffixName(ownPo.getContentType()));
		} else {
			ownPo.setFileName(ownPo.getOwnUuid());
		}
		try (InputStream inputStream = urlConnection.getInputStream()) {
			IOUtils.stream(inputStream, outputStreams);
		}
		return null;
	}

	private URLConnection createURLConnection(String urlString) throws IOException {
		URL url = new URL(urlString);
		URLConnection urlConnection = url.openConnection();
		urlConnection.setConnectTimeout(connectTimeout);
		//防止屏蔽程序抓取而返回403错误
		urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		return urlConnection;
	}
}
