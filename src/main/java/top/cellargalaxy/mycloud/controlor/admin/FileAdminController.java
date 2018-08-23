package top.cellargalaxy.mycloud.controlor.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.mycloud.controlor.user.UserUserController;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.po.TaskPo;
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

	@GetMapping("/getDriveInfo")
	public Vo getDriveInfo() {
		logger.info("restoreAllFileToLocal");
		return new Vo(null, fileService.getDriveInfo());
	}

	@PostMapping("/removeFile")
	public Vo removeFileTask(HttpServletRequest request, FileInfoPo fileInfoPo) {
		UserPo userPo = (UserPo) request.getAttribute(UserUserController.USER_KEY);
		String string = fileService.executeRemoveFileTask(userPo, fileInfoPo);
		logger.info("removeFile:{}", string);
		return new Vo(string, null);
	}

	@PostMapping("/restoreAllFileToLocal")
	public Vo restoreAllFileToLocal(HttpServletRequest request) {
		UserPo userPo = (UserPo) request.getAttribute(UserUserController.USER_KEY);
		String string = fileService.restoreAllFileToLocal(userPo);
		logger.info("restoreAllFileToLocal:{}", string);
		return new Vo(string, null);
	}

	@PostMapping("/startRestoreAllFileToLocal")
	public Vo startRestoreAllFileToLocal(HttpServletRequest request) {
		UserPo userPo = (UserPo) request.getAttribute(UserUserController.USER_KEY);
		String string = fileService.startRestoreAllFileToLocal(userPo);
		logger.info("startRestoreAllFileToLocal:{}", string);
		return new Vo(string, null);
	}

	@PostMapping("/stopRestoreAllFileToLocal")
	public Vo stopRestoreAllFileToLocal() {
		String string = fileService.stopRestoreAllFileToLocal();
		logger.info("stopRestoreAllFileToLocal:{}", string);
		return new Vo(string, null);
	}

	@PostMapping("/deleteAllFileFromLocal")
	public Vo deleteAllFileFromLocal() {
		String string = fileService.deleteAllFileFromLocal();
		logger.info("deleteAllFileFromLocal:{}", string);
		return new Vo(string, null);
	}
}
