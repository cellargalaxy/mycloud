package top.cellargalaxy.mycloud.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.service.FileService;
import top.cellargalaxy.mycloud.util.model.Vo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
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

	@PostMapping("/removeFileByFileInfo")
	public Vo removeFileByFileInfo(FileInfoPo fileInfoPo) throws Exception {
		return new Vo(fileService.removeFile(fileInfoPo), null);
	}

	@PostMapping("/removeFileByOwn")
	public Vo removeFileByOwn(OwnPo ownPo) throws Exception {
		return new Vo(fileService.removeFile(ownPo), null);
	}

	@GetMapping("/downloadTar")
	public void download(HttpServletResponse response) throws IOException {
		response.reset();
		response.setContentType("application/x-tar");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("mycloudDrive.tar", "UTF-8"));
		try (OutputStream outputStream = response.getOutputStream()) {
			fileService.getTar(outputStream);
		}
	}

	/**
	 * 无法直接通过流替换sqltile文件
	 *
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 */
//	@PostMapping("/uploadRestoreTarFile")
	public Vo uploadRestoreTarFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
		if (multipartFile == null || multipartFile.isEmpty()) {
			return new Vo("无上传文件", null);
		}
		try (InputStream inputStream = multipartFile.getInputStream()) {
			String string = fileService.restoreTar(inputStream);
			return new Vo(string, null);
		}
	}
}
