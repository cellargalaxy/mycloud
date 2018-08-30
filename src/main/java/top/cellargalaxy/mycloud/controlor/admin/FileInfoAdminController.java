package top.cellargalaxy.mycloud.controlor.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.model.vo.Vo;
import top.cellargalaxy.mycloud.service.FileInfoService;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
@PreAuthorize("hasAuthority('ADMIN')")
@RestController
@RequestMapping(FileInfoAdminController.URL)
public class FileInfoAdminController {
	public static final String URL = "/admin/fileInfo";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private FileInfoService fileInfoService;

	//不允许直接删除fileInfo，要在fileContriller里删除，修改同理
//	@PostMapping("/removeFileInfo")
//	public Vo removeFileInfo(FileInfoQuery fileInfoQuery) {
//		String string = fileInfoService.removeFileInfo(fileInfoQuery);
//		logger.info("removeFileInfo:{}", string);
//		return new Vo(string, null);
//	}

	@GetMapping("/getFileInfo")
	public Vo getFileInfo(FileInfoQuery fileInfoQuery) {
		logger.info("getFileInfo");
		return new Vo(null, fileInfoService.getFileInfo(fileInfoQuery));
	}

	@GetMapping("/getFileInfoCount")
	public Vo getFileInfoCount(FileInfoQuery fileInfoQuery) {
		logger.info("getFileInfoCount");
		return new Vo(null, fileInfoService.getFileInfoCount(fileInfoQuery));
	}

	@GetMapping("/listFileInfo")
	public Vo listFileInfo(FileInfoQuery fileInfoQuery) {
		logger.info("listFileInfo");
		return new Vo(null, fileInfoService.listFileInfo(fileInfoQuery));
	}

	@GetMapping("/getFileInfoOwn")
	public Vo getFileInfoOwn(FileInfoQuery fileInfoQuery) {
		logger.info("getFileInfoOwn");
		return new Vo(null, fileInfoService.getFileInfoOwn(fileInfoQuery));
	}

	@GetMapping("/listFileInfoOwn")
	public Vo listFileInfoOwn(FileInfoQuery fileInfoQuery) {
		logger.info("listFileInfoOwn");
		return new Vo(null, fileInfoService.listFileInfoOwn(fileInfoQuery));
	}

	@GetMapping("/listContentType")
	public Vo listContentType() {
		logger.info("listContentType");
		return new Vo(null, fileInfoService.listContentType());
	}

//	@PostMapping("/changeFileInfo")
//	public Vo changeFileInfo(FileInfoPo fileInfoPo) {
//		String string = fileInfoService.changeFileInfo(fileInfoPo);
//		logger.info("changeFileInfo:{}", string);
//		return new Vo(string, null);
//	}

	@GetMapping("/checkAddFileInfo")
	public Vo checkAddFileInfo(FileInfoPo fileInfoPo) {
		String string = fileInfoService.checkAddFileInfo(fileInfoPo);
		logger.info("checkAddFileInfo:{}", string);
		return new Vo(string, null);
	}

	@GetMapping("/checkChangeFileInfo")
	public Vo checkChangeFileInfo(FileInfoPo fileInfoPo) {
		String string = fileInfoService.checkChangeFileInfo(fileInfoPo);
		logger.info("checkChangeFileInfo:{}", string);
		return new Vo(string, null);
	}
}
