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
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.vo.Vo;
import top.cellargalaxy.mycloud.service.ExecuteService;
import top.cellargalaxy.mycloud.service.LocalFileService;

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
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExecuteService executeService;
	@Autowired
	private LocalFileService localFileService;

	@GetMapping("/getDriveInfo")
	public Vo getDriveInfo() {
		logger.info("getDriveInfo");
		return new Vo(null, localFileService.getDriveInfo());
	}

	@PostMapping("/removeFile")
	public Vo removeFileTask(HttpServletRequest request, FileInfoPo fileInfoPo) throws Exception {
		UserPo userPo = (UserPo) request.getAttribute(UserUserController.USER_KEY);
		String string = executeService.executeRemoveFileTask(userPo, fileInfoPo);
		logger.info("removeFile:{}", string);
		return new Vo(string, null);
	}

	@PostMapping("/removeAllLocalFile")
	public Vo removeAllLocalFile() {
		String string = localFileService.removeAllLocalFile();
		logger.info("removeAllLocalFile:{}", string);
		return new Vo(string, null);
	}
}
