package top.cellargalaxy.mycloud.controlor.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.model.vo.Vo;
import top.cellargalaxy.mycloud.service.FileInfoService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by cellargalaxy on 18-8-4.
 */
@PreAuthorize("hasAuthority('USER')")
@RestController
@RequestMapping(FileInfoUserController.URL)
public class FileInfoUserController {
	public static final String URL = "/user/fileInfo";
	private Logger logger = LoggerFactory.getLogger(FileInfoUserController.class);
	@Autowired
	private FileInfoService fileInfoService;

	@GetMapping("/getFileInfo")
	public Vo getFileInfo(HttpServletRequest request, FileInfoQuery fileInfoQuery) {
		UserPo userPo = (UserPo) request.getAttribute(UserUserController.USER_KEY);
		logger.info("getFileInfo:{}", userPo);
		return new Vo(null, fileInfoService.getFileInfo(userPo,fileInfoQuery));
	}
}
