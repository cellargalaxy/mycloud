package top.cellargalaxy.controlor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.cellargalaxy.bean.controlorBean.Page;
import top.cellargalaxy.bean.controlorBean.ReturnBean;
import top.cellargalaxy.bean.daoBean.FilePackage;
import top.cellargalaxy.service.MycloudService;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by cellargalaxy on 17-12-2.
 */
@Controller
@RequestMapping(AdminControlor.ADMIN_CONTROLOR_URL)
public class AdminControlor {
	public static final String ADMIN_CONTROLOR_URL = "/admin";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private MycloudService mycloudService;
	
	@GetMapping("")
	public String admin(Model model, HttpSession session,
	                    @RequestParam(value = "fileSha256", required = false) String fileSha256,
	                    @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
	                    @RequestParam(value = "filename", required = false) String filename,
	                    @RequestParam(value = "description", required = false) String description) {
		if (fileSha256 != null) {
			return admin(model, mycloudService.getFailBackupCount(), mycloudService.getSuccessBackupCount(), mycloudService.getWaitBackupCount(),
					mycloudService.getFailRestoreCount(), mycloudService.getSuccessRestoreCount(), mycloudService.getWaitRestoreCount(),
					session.getAttribute("token"), new FilePackage[]{mycloudService.findFilePackageByFileSha256(fileSha256)}, new Page[0]);
		}
		if (date != null) {
			return admin(model, mycloudService.getFailBackupCount(), mycloudService.getSuccessBackupCount(), mycloudService.getWaitBackupCount(),
					mycloudService.getFailRestoreCount(), mycloudService.getSuccessRestoreCount(), mycloudService.getWaitRestoreCount(),
					session.getAttribute("token"), mycloudService.findFilePackagesByDate(date), new Page[0]);
		}
		if (filename != null) {
			return admin(model, mycloudService.getFailBackupCount(), mycloudService.getSuccessBackupCount(), mycloudService.getWaitBackupCount(),
					mycloudService.getFailRestoreCount(), mycloudService.getSuccessRestoreCount(), mycloudService.getWaitRestoreCount(),
					session.getAttribute("token"), mycloudService.findFilePackagesByFilename(filename), new Page[0]);
		}
		if (description != null) {
			return admin(model, mycloudService.getFailBackupCount(), mycloudService.getSuccessBackupCount(), mycloudService.getWaitBackupCount(),
					mycloudService.getFailRestoreCount(), mycloudService.getSuccessRestoreCount(), mycloudService.getWaitRestoreCount(),
					session.getAttribute("token"), mycloudService.findFilePackagesByDescription(description), new Page[0]);
		}
		return "redirect:/admin/1";
	}
	
	@GetMapping("/{page}")
	public String admin(Model model, HttpSession session, @PathVariable("page") int page) {
		return admin(model, mycloudService.getFailBackupCount(), mycloudService.getSuccessBackupCount(), mycloudService.getWaitBackupCount(),
				mycloudService.getFailRestoreCount(), mycloudService.getSuccessRestoreCount(), mycloudService.getWaitRestoreCount(),
				session.getAttribute("token"), mycloudService.findFilePackages(page), mycloudService.createPages(page));
	}
	
	@ResponseBody
	@PostMapping("/backup")
	public ReturnBean backup(String filename, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
		FilePackage filePackage = mycloudService.createFilePackage(filename, date, null);
		if (filePackage == null) {
			logger.info("备份文件对象创建失败");
			return new ReturnBean(false, "备份文件对象创建失败");
		}
		if (!mycloudService.backupFilePackage(filePackage)) {
			logger.info("文件失败添加到备份队列" + filePackage);
			return new ReturnBean(false, "文件失败添加到备份队列");
		}
		logger.info("文件已添加到备份队列" + filePackage);
		return new ReturnBean(true, "文件已添加到备份队列");
	}
	
	@ResponseBody
	@PostMapping("/restore")
	public ReturnBean restore(String filename, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
		FilePackage filePackage = mycloudService.createFilePackage(filename, date, null);
		if (filePackage == null) {
			logger.info("恢复文件对象创建失败");
			return new ReturnBean(false, "恢复文件对象创建失败");
		}
		if (!mycloudService.restoreFilePackage(filePackage)) {
			logger.info("文件失败添加到恢复队列" + filePackage);
			return new ReturnBean(false, "文件失败添加到恢复队列");
		}
		logger.info("文件已添加到恢复队列" + filePackage);
		return new ReturnBean(true, "文件已添加到恢复队列");
	}
	
	@ResponseBody
	@PostMapping("/remove")
	public ReturnBean remove(String filename, @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
		FilePackage filePackage = mycloudService.createFilePackage(filename, date, null);
		if (filePackage == null) {
			logger.info("删除文件对象创建失败");
			return new ReturnBean(false, "删除文件对象创建失败");
		}
		if (!mycloudService.removeFilePackage(filePackage)) {
			logger.info("文件删除失败" + filePackage);
			return new ReturnBean(false, "文件删除失败");
		}
		logger.info("文件删除成功" + filePackage);
		return new ReturnBean(true, "文件删除成功");
	}
	
	@ResponseBody
	@PostMapping("/backupAll")
	public ReturnBean backupAll() {
		if (!mycloudService.backupAllFilePackage()) {
			logger.info("全部文件失败添加到备份队列");
			return new ReturnBean(false, "全部文件失败添加到备份队列");
		}
		logger.info("全部文件已添加到备份队列");
		return new ReturnBean(true, "全部文件已添加到备份队列");
	}
	
	@ResponseBody
	@PostMapping("/restoreAll")
	public ReturnBean restoreAll() {
		if (!mycloudService.restoreAllFilePackage()) {
			logger.info("全部文件失败添加到备份队列");
			return new ReturnBean(false, "全部文件失败添加到备份队列");
		}
		logger.info("全部文件已添加到备份队列");
		return new ReturnBean(true, "全部文件已添加到备份队列");
	}
	
	private final String admin(Model model, int failBackupCount, int successBackupCount, int waitBackupCount, int failRestoreCount, int successRestoreCount, int waitRestoreCount,
	                           Object token, FilePackage[] filePackages, Page[] pages) {
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
}
