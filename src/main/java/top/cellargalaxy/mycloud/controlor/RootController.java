package top.cellargalaxy.mycloud.controlor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.service.FileInfoService;
import top.cellargalaxy.mycloud.service.FileService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by cellargalaxy on 18-8-5.
 */
@RestController
@RequestMapping(RootController.URL)
public class RootController {
	public static final String URL = "";
	private Logger logger = LoggerFactory.getLogger(RootController.class);
	@Autowired
	private FileService fileService;
	@Autowired
	private FileInfoService fileInfoService;

	@GetMapping("/{md5}")
	public void download(HttpServletResponse response, @PathVariable("md5") String md5) throws IOException {
		logger.info("download:{}", md5);
		FileInfoQuery fileInfoQuery = new FileInfoQuery() {{
			setMd5(md5);
		}};
		FileInfoPo fileInfoPo = fileInfoService.getFileInfo(fileInfoQuery);
		if (fileInfoPo == null) {
			return;
		}
		response.setContentLength((int) fileInfoPo.getFileLength());
		response.setContentType(fileInfoPo.getContentType());
		fileService.downloadFile(fileInfoQuery, response.getOutputStream());
		response.getOutputStream().close();
	}
}
