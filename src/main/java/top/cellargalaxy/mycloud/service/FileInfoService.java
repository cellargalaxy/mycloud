package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.model.vo.FileInfoVo;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/7/20
 */
public interface FileInfoService {
    String addFileInfo(FileInfoPo fileInfoPo);

    String removeFileInfo(FileInfoPo fileInfoPo);

    FileInfoBo getFileInfo(FileInfoPo fileInfoPo);

    FileInfoVo getFileInfoVo(FileInfoPo fileInfoPo);

    List<FileInfoBo> listFileInfo(FileInfoQuery fileInfoQuery);

    List<FileInfoVo> listFileInfoVo(FileInfoQuery fileInfoQuery);

    List<FileInfoBo> listAllFileInfo();

    int getFileInfoCount(FileInfoQuery fileInfoQuery);

    List<String> listContentType();
}
