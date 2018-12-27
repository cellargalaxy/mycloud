package top.cellargalaxy.mycloud.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.service.FileInfoService;
import top.cellargalaxy.mycloud.util.model.Vo;

/**
 * @author cellargalaxy
 * @time 2018/10/25
 */
@PreAuthorize("hasAuthority('USER')")
@RestController
@RequestMapping(FileInfoUserController.URL)
public class FileInfoUserController {
	public static final String URL = "/user/fileInfo";
	@Autowired
	private FileInfoService fileInfoService;

	@GetMapping("/getFileInfo")
	public Vo getFileInfo(@RequestParam("md5") String md5) {
		FileInfoPo fileInfoPo = new FileInfoPo();
		fileInfoPo.setMd5(md5);
		return new Vo(null, fileInfoService.getFileInfo(fileInfoPo));
	}
}
