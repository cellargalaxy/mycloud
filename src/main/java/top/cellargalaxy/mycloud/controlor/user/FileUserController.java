package top.cellargalaxy.mycloud.controlor.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.cellargalaxy.mycloud.configuration.MycloudConfiguration;
import top.cellargalaxy.mycloud.exception.GlobalException;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.vo.Vo;
import top.cellargalaxy.mycloud.service.FileService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by cellargalaxy on 18-8-4.
 */
@PreAuthorize("hasAuthority('USER')")
@RestController
@RequestMapping(FileUserController.URL)
public class FileUserController {
	public static final String URL = "/user/file";
	private Logger logger = LoggerFactory.getLogger(FileUserController.class);
	@Autowired
	private FileService fileService;

	@Autowired
	private MycloudConfiguration mycloudConfiguration;

	@PostMapping("/uploadFile")
	public Vo uploadFile(HttpServletRequest request, OwnPo ownPo, @RequestParam("files") MultipartFile[] multipartFiles) {
		UserPo userPo = (UserPo) request.getAttribute(UserUserController.USER_KEY);
		if (multipartFiles == null || multipartFiles.length == 0) {
			logger.info("uploadFile, result:无上传文件, userPo:{}", userPo);
			return new Vo("无上传文件", null);
		}
		try {
			for (MultipartFile multipartFile : multipartFiles) {
				File file = new File(mycloudConfiguration.getTmpPath() + File.separator + UUID.randomUUID().toString());
				multipartFile.transferTo(file);
				ownPo.setFileName(multipartFile.getName());
				fileService.addUploadFileTask(userPo, ownPo, file, multipartFile.getContentType());
			}
			logger.info("uploadFile, result:文件上传成功, userPo:{}", userPo);
			return new Vo(null, null);
		} catch (IOException e) {
			e.printStackTrace();
			GlobalException.add(e);
			logger.info("uploadFile, result:文件上传异常, userPo:{}", userPo);
			return new Vo("文件上传异常", e);
		}
	}

}
