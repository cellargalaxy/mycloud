package top.cellargalaxy.mycloud.controlor.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.mycloud.controlor.user.UserUserController;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.vo.Vo;
import top.cellargalaxy.mycloud.service.FileService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cellargalaxy
 * @time 2018/8/10
 */
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping(FileAdminController.URL)
public class FileAdminController {
	public static final String URL = "/admin/file";
	private Logger logger = LoggerFactory.getLogger(FileInfoAdminController.class);

	@Autowired
	private FileService fileService;

	@PostMapping("/restoreAllFileToLocal")
	public Vo restoreAllFileToLocal(HttpServletRequest request) {
		UserPo userPo = (UserPo) request.getAttribute(UserUserController.USER_KEY);
		String string = fileService.restoreAllFileToLocal(userPo);
		logger.info("restoreAllFileToLocal:{}", string);
		return new Vo(string, null);
	}
}
