package top.cellargalaxy.controlor;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.cellargalaxy.bean.FilePackage;
import top.cellargalaxy.service.MycloudService;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by cellargalaxy on 17-12-2.
 */
@Controller
@RequestMapping("/api")
public class ApiControlor {
	private static final int uploadFileBackupStatus = 1;
	private static final int uploadFileSuccess = 1;
	private static final int uploadFileFail = 0;
	@Autowired
	private MycloudService service;
	
	@ResponseBody
	@PostMapping("/uploadFile")
	public String uploadFile(@RequestParam("file") MultipartFile multipartFile,
	                         @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
	                         @RequestParam(value = "description", required = false) String description,
	                         @RequestParam(value = "status", required = false) Integer status) {
		File file = saveFile(multipartFile);
		if (file == null) {
			return createUploadFileJSONObject(uploadFileFail, "空文件或者文件保存失败", null).toString();
		}
		if (date == null) {
			date = new Date();
		}
		FilePackage filePackage = service.createFilePackage(file, date, description);
		if (filePackage == null) {
			return createUploadFileJSONObject(uploadFileFail, "文件移动失败", null).toString();
		}
		if (status != null && status.equals(uploadFileBackupStatus) && !service.backupFilePackage(filePackage)) {
			return createUploadFileJSONObject(uploadFileFail, "文件失败添加到备份队列", null).toString();
		}
		return createUploadFileJSONObject(uploadFileSuccess, "成功上传" + file.getName(), filePackage.getUrl()).toString();
	}
	
	@ResponseBody
	@GetMapping("/inquireByDate")
	public String inquireByDate(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
		JSONArray jsonArray = new JSONArray();
		FilePackage[] filePackages = service.getFilePackageByDate(date);
		if (filePackages != null) {
			for (FilePackage filePackage : filePackages) {
				jsonArray.put(filePackage.toJSONObject());
			}
		}
		return jsonArray.toString();
	}
	
	private File saveFile(MultipartFile multipartFile) {
		BufferedOutputStream bufferedOutputStream = null;
		try {
			if (multipartFile == null || multipartFile.isEmpty()) {
				return null;
			}
			File file = new File("src/main/resources/static/file/" + multipartFile.getOriginalFilename());
			file.getParentFile().mkdirs();
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
			bufferedOutputStream.write(multipartFile.getBytes());
			bufferedOutputStream.flush();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedOutputStream != null) {
				try {
					bufferedOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	private JSONObject createUploadFileJSONObject(int success, String message, String url) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("success", success);
		jsonObject.put("message", message);
		jsonObject.put("url", url);
		return jsonObject;
	}
}
