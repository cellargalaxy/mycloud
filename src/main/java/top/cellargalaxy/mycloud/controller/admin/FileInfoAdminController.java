package top.cellargalaxy.mycloud.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.service.FileInfoService;
import top.cellargalaxy.mycloud.util.model.Vo;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping(FileInfoAdminController.URL)
public class FileInfoAdminController {
	public static final String URL = "/admin/fileInfo";
	@Autowired
	private FileInfoService fileInfoService;

	@GetMapping("/getFileInfo")
	public Vo getFileInfo(FileInfoPo fileInfoPo) {
		return new Vo(null, fileInfoService.getFileInfo(fileInfoPo));
	}

	@GetMapping("/getFileInfoVo")
	public Vo getFileInfoVo(FileInfoPo fileInfoPo) {
		return new Vo(null, fileInfoService.getFileInfoVo(fileInfoPo));
	}

	@GetMapping("/listFileInfo")
	public Vo listFileInfo(FileInfoQuery fileInfoQuery) {
		return new Vo(null, fileInfoService.listPageFileInfo(fileInfoQuery));
	}

	@GetMapping("/listFileInfoVo")
	public Vo listFileInfoVo(FileInfoQuery fileInfoQuery) {
		return new Vo(null, fileInfoService.listPageFileInfoVo(fileInfoQuery));
	}

	@GetMapping("/getFileInfoCount")
	public Vo getFileInfoCount(FileInfoQuery fileInfoQuery) {
		return new Vo(null, fileInfoService.getFileInfoCount(fileInfoQuery));
	}

	@GetMapping("/listContentType")
	public Vo listContentType() {
		return new Vo(null, fileInfoService.listContentType());
	}
}
