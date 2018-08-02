package top.cellargalaxy.mycloud.controlor.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.mycloud.exception.GlobalException;
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
@RequestMapping(FileInfoApiController.URL)
public class FileInfoApiController {
	public static final String URL = "/admin/fileInfo";
	private Logger logger = LoggerFactory.getLogger(FileInfoApiController.class);
	@Autowired
	private FileInfoService fileInfoService;

//	@PostMapping("/addFileInfo")
//	public Vo addFileInfo(FileInfoPo fileInfoPo) {
//		try {
//			String string = fileInfoService.addFileInfo(fileInfoPo);
//			logger.info("addFileInfo:{}", string);
//			return new Vo(string, null);
//		} catch (Exception e) {
//			e.printStackTrace();
//			GlobalException.add(e);
//			return new Vo(e);
//		}
//	}
//
//	@PostMapping("/removeFileInfo")
//	public Vo removeFileInfo(FileInfoQuery fileInfoQuery) {
//		try {
//			String string = fileInfoService.removeFileInfo(fileInfoQuery);
//			logger.info("removeFileInfo:{}", string);
//			return new Vo(string, null);
//		} catch (Exception e) {
//			e.printStackTrace();
//			GlobalException.add(e);
//			return new Vo(e);
//		}
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
		try {
			logger.info("listFileInfoOwn");
			return new Vo(null, fileInfoService.listFileInfoOwn(fileInfoQuery));
		} catch (Exception e) {
			e.printStackTrace();
			GlobalException.add(e);
			return new Vo(e);
		}
	}

	@GetMapping("/listContentType")
	public Vo listContentType() {
		logger.info("listContentType");
		return new Vo(null, fileInfoService.listContentType());
	}

//	@PostMapping("/changeFileInfo")
//	public Vo changeFileInfo(FileInfoPo fileInfoPo) {
//		try {
//			String string = fileInfoService.changeFileInfo(fileInfoPo);
//			logger.info("changeFileInfo:{}", string);
//			return new Vo(string, null);
//		} catch (Exception e) {
//			e.printStackTrace();
//			GlobalException.add(e);
//			return new Vo(e);
//		}
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
