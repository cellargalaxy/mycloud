package top.cellargalaxy.mycloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.service.FileService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

/**
 * Created by cellargalaxy on 18-8-5.
 */
@RestController
@RequestMapping(RootController.URL)
public class RootController {
    public static final String URL = "";
    private static final long expires = 1000 * 60 * 60 * 24 * 30 * 12 * 10;
    @Autowired
    private FileService fileService;

    @GetMapping("/{md5OrUuid}")
    public void download(HttpServletResponse response, @PathVariable("md5OrUuid") String md5OrUuid) throws IOException {
        response.reset();
        FileInfoPo fileInfoPo = fileService.getFileInfoPoByMd5OrUuid(md5OrUuid);
        if (fileInfoPo == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        response.setHeader("cache-control", "max-age=" + expires);
        response.setHeader("expires", new Date(System.currentTimeMillis() + expires * 100).toString());
        response.setContentLength((int) fileInfoPo.getFileLength());
        response.setContentType(fileInfoPo.getContentType());
        try (OutputStream outputStream = response.getOutputStream()) {
            fileService.getFileByMd5OrUuid(md5OrUuid, outputStream);
        }
    }
}