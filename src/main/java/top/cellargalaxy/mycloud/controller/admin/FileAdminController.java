package top.cellargalaxy.mycloud.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.vo.Vo;
import top.cellargalaxy.mycloud.service.FileService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @author cellargalaxy
 * @time 2018/8/10
 */
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping(FileAdminController.URL)
public class FileAdminController {
	public static final String URL = "/admin/file";
	@Autowired
	private FileService fileService;

	@PostMapping("/removeFile")
	public Vo removeFile(FileInfoPo fileInfoPo) throws Exception {
		return new Vo(fileService.removeFile(fileInfoPo), null);
	}

	@GetMapping("/downloadTar")
	public void download(HttpServletResponse response) throws IOException {
		response.reset();
		response.setContentType("application/x-tar");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("mycloud.tar", "UTF-8"));
		try (OutputStream outputStream = response.getOutputStream()) {
			fileService.getTar(outputStream);
		}
	}
}
