package top.cellargalaxy.mycloud.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.util.model.Vo;
import top.cellargalaxy.mycloud.service.FileService;
import top.cellargalaxy.mycloud.service.security.SecurityServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * Created by cellargalaxy on 18-8-4.
 */
@PreAuthorize("hasAuthority('USER')")
@RestController
@RequestMapping(FileUserController.URL)
public class FileUserController {
    public static final String URL = "/user/file";
    @Autowired
    private FileService fileService;

    @PostMapping("/uploadFile")
    public Vo uploadFile(HttpServletRequest request, OwnBo ownBo, @RequestParam("file") MultipartFile multipartFile) throws IOException {
        UserPo userPo = SecurityServiceImpl.getSecurityUser(request);
        if (multipartFile == null || multipartFile.isEmpty()) {
            return new Vo("无上传文件", null);
        }
        ownBo.setContentType(multipartFile.getContentType());
        ownBo.setFileLength(multipartFile.getSize());
        ownBo.setFileName(multipartFile.getOriginalFilename());
        String string = fileService.addFile(multipartFile.getInputStream(), ownBo, userPo);
        return new Vo(string, string == null ? ownBo : null);
    }

    @PostMapping("/submitUrl")
    public Vo submitUrl(HttpServletRequest request, OwnBo ownBo, @RequestParam("url") String url) throws IOException {
        UserPo userPo = SecurityServiceImpl.getSecurityUser(request);
        String string = fileService.addFile(url, ownBo, userPo);
        return new Vo(string, string == null ? ownBo : null);
    }

    @GetMapping("/downloadTar")
    public void download(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserPo userPo = SecurityServiceImpl.getSecurityUser(request);
        response.reset();
        response.setContentType("application/x-tar");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(userPo.getUsername() + ".tar", "UTF-8"));
        try (OutputStream outputStream = response.getOutputStream()) {
            fileService.getTar(userPo, outputStream);
        }
    }
}
