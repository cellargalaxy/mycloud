package top.cellargalaxy.mycloud.controller.guest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.OwnExpirePo;
import top.cellargalaxy.mycloud.service.FileService;
import top.cellargalaxy.mycloud.util.model.Vo;

import java.io.IOException;

/**
 * @author cellargalaxy
 * @time 2018/12/14
 */
@RestController
@RequestMapping(FileGuestController.URL)
public class FileGuestController {
	public static final String URL = "/guest/file";

	@Autowired
	private FileService fileService;

	@PostMapping("/uploadTmpFile")
	public Vo uploadTmpFile(OwnBo ownBo, OwnExpirePo ownExpirePo, @RequestParam("file") MultipartFile multipartFile) throws IOException {
		if (multipartFile == null || multipartFile.isEmpty()) {
			return new Vo("无上传文件", null);
		}
		ownBo.setContentType(multipartFile.getContentType());
		ownBo.setFileLength(multipartFile.getSize());
		ownBo.setFileName(multipartFile.getOriginalFilename());
		String string = fileService.addTmpFile(multipartFile.getInputStream(), ownBo, ownExpirePo);
		return new Vo(string, string == null ? ownBo : null);
	}
}
