package top.cellargalaxy.mycloud.controlor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import top.cellargalaxy.mycloud.model.vo.VoBean;
import top.cellargalaxy.mycloud.service.FileService;

/**
 * Created by cellargalaxy on 18-7-14.
 */
@Controller
@RequestMapping(FileControlor.URL)
public class FileControlor {
	public static final String URL = "/api/file";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private FileService fileService;

	@ResponseBody
	@PostMapping("/uploadFile")
	public VoBean uploadFile(@RequestParam("file") MultipartFile multipartFile) {
		return new VoBean(1,"上传（不）成功",null);
	}
}
