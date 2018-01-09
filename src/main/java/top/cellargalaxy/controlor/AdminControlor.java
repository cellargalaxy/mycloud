package top.cellargalaxy.controlor;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.cellargalaxy.bean.FilePackage;
import top.cellargalaxy.service.MycloudService;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by cellargalaxy on 17-12-2.
 */
@Controller
@RequestMapping("/admin")
public class AdminControlor {
	@Autowired
	private MycloudService service;
	
	@GetMapping("")
	public String admin(Model model, HttpSession session,
	                    @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
	                    @RequestParam(value = "filename", required = false) String filename,
	                    @RequestParam(value = "description", required = false) String description) {
		if (date != null) {
			return admin(model, service.getFailBackupCount(), service.getSuccessBackupCount(), service.getWaitBackupCount(),
					service.getFailRestoreCount(), service.getSuccessRestoreCount(), service.getWaitRestoreCount(),
					session.getAttribute("token"), service.getFilePackageByDate(date), new String[0]);
		}
		if (filename != null) {
			return admin(model, service.getFailBackupCount(), service.getSuccessBackupCount(), service.getWaitBackupCount(),
					service.getFailRestoreCount(), service.getSuccessRestoreCount(), service.getWaitRestoreCount(),
					session.getAttribute("token"), service.getFilePackageByFilename(filename), new String[0]);
		}
		if (description != null) {
			return admin(model, service.getFailBackupCount(), service.getSuccessBackupCount(), service.getWaitBackupCount(),
					service.getFailRestoreCount(), service.getSuccessRestoreCount(), service.getWaitRestoreCount(),
					session.getAttribute("token"), service.getFilePackageByDescription(description), new String[0]);
		}
		return "redirect:/admin/1";
	}
	
	@GetMapping("/{page}")
	public String admin(Model model, HttpSession session, @PathVariable("page") int page) {
		return admin(model, service.getFailBackupCount(), service.getSuccessBackupCount(), service.getWaitBackupCount(),
				service.getFailRestoreCount(), service.getSuccessRestoreCount(), service.getWaitRestoreCount(),
				session.getAttribute("token"), service.getFilePackages(page), service.createPages(page));
	}
	
	@ResponseBody
	@PostMapping("/backup")
	public String backup(String filename, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
		FilePackage filePackage = service.createFilePackage(filename, date, null);
		if (filePackage == null) {
			return createJSONObject(false, "备份文件对象创建失败").toString();
		}
		if (!service.backupFilePackage(filePackage)) {
			return createJSONObject(false, "文件失败添加到备份队列").toString();
		}
		return createJSONObject(true, "文件已添加到备份队列").toString();
	}
	
	@ResponseBody
	@PostMapping("/restore")
	public String restore(String filename, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
		FilePackage filePackage = service.createFilePackage(filename, date, null);
		if (filePackage == null) {
			return createJSONObject(false, "恢复文件对象创建失败").toString();
		}
		if (!service.restoreFilePackage(filePackage)) {
			return createJSONObject(false, "文件失败添加到恢复队列").toString();
		}
		return createJSONObject(true, "文件已添加到恢复队列").toString();
	}
	
	@ResponseBody
	@PostMapping("/remove")
	public String remove(String filename, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
		FilePackage filePackage = service.createFilePackage(filename, date, null);
		if (filePackage == null) {
			return createJSONObject(false, "删除文件对象创建失败").toString();
		}
		if (!service.removeFilePackage(filePackage)) {
			return createJSONObject(false, "文件删除失败").toString();
		}
		return createJSONObject(true, "文件删除成功").toString();
	}
	
	@ResponseBody
	@PostMapping("/backupAll")
	public String backupAll() {
		if (!service.backupAllFilePackage()) {
			return createJSONObject(false, "全部文件失败添加到备份队列").toString();
		}
		return createJSONObject(true, "全部文件已添加到备份队列").toString();
	}
	
	@ResponseBody
	@PostMapping("/restoreAll")
	public String restoreAll() {
		if (!service.restoreAllFilePackage()) {
			return createJSONObject(false, "全部文件失败添加到备份队列").toString();
		}
		return createJSONObject(true, "全部文件已添加到备份队列").toString();
	}
	
	private String admin(Model model, int failBackupCount, int successBackupCount, int waitBackupCount, int failRestoreCount, int successRestoreCount, int waitRestoreCount,
	                     Object token, FilePackage[] filePackages, String[] pages) {
		model.addAttribute("failBackupCount", failBackupCount);
		model.addAttribute("successBackupCount", successBackupCount);
		model.addAttribute("waitBackupCount", waitBackupCount);
		model.addAttribute("failRestoreCount", failRestoreCount);
		model.addAttribute("successRestoreCount", successRestoreCount);
		model.addAttribute("waitRestoreCount", waitRestoreCount);
		model.addAttribute("token", token);
		model.addAttribute("files", filePackages);
		model.addAttribute("pages", pages);
		return "admin";
	}
	
	private JSONObject createJSONObject(boolean result, String data) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", result);
		jsonObject.put("data", data);
		return jsonObject;
	}
}
