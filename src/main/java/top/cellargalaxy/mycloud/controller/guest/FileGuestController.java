package top.cellargalaxy.mycloud.controller.guest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.service.security.SecurityServiceImpl;
import top.cellargalaxy.mycloud.util.model.Vo;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author cellargalaxy
 * @time 2018/12/14
 */
@PreAuthorize("hasAuthority('GUEST')")
@RestController
@RequestMapping(FileGuestController.URL)
public class FileGuestController {
    public static final String URL = "/guest/file";

//    @PostMapping("/uploadFile")
//    public Vo uploadFile(HttpServletRequest request, OwnBo ownBo, @RequestParam("file") MultipartFile multipartFile) throws IOException {
//        UserPo userPo = SecurityServiceImpl.getSecurityUser(request);
//        if (multipartFile == null || multipartFile.isEmpty()) {
//            return new Vo("无上传文件", null);
//        }
//        ownBo.setContentType(multipartFile.getContentType());
//        ownBo.setFileLength(multipartFile.getSize());
//        ownBo.setFileName(multipartFile.getOriginalFilename());
//        String string = fileService.addFile(multipartFile.getInputStream(), ownBo, userPo);
//        return new Vo(string, string == null ? ownBo : null);
//    }
}
