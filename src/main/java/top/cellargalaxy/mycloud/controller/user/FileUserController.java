package top.cellargalaxy.mycloud.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.vo.Vo;
import top.cellargalaxy.mycloud.service.FileService;
import top.cellargalaxy.mycloud.service.security.SecurityServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by cellargalaxy on 18-8-4.
 */
@PreAuthorize("hasAuthority('USER')")
@RestController
@RequestMapping(FileUserController.URL)
public class FileUserController {
	public static final String URL = "/user/file";
	@Autowired
	private FileService fileService;

	@PostMapping("/uploadFile")
	public Vo uploadFile(HttpServletRequest request, OwnBo ownBo, @RequestParam("fileDeal") MultipartFile multipartFile) throws IOException {
		UserPo userPo = SecurityServiceImpl.getSecurityUser(request);
		if (multipartFile == null || multipartFile.isEmpty()) {
			return new Vo("无上传文件", null);
		}
		ownBo.setContentType(multipartFile.getContentType());
		ownBo.setFileLength(multipartFile.getSize());
		ownBo.setFileName(multipartFile.getOriginalFilename());
		return new Vo(fileService.addFile(multipartFile.getInputStream(), ownBo, userPo), ownBo);
	}

	@PostMapping("/submitUrl")
	public Vo submitUrl(HttpServletRequest request, OwnBo ownBo, @RequestParam("url") String url) throws IOException {
		UserPo userPo = SecurityServiceImpl.getSecurityUser(request);
		return new Vo(fileService.addFile(url, ownBo, userPo), ownBo);
	}
}
