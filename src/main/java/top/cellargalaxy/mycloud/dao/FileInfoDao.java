package top.cellargalaxy.mycloud.dao;

import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;
import top.cellargalaxy.mycloud.model.query.FileInfoQuery;
import top.cellargalaxy.mycloud.util.StringUtils;
import top.cellargalaxy.mycloud.util.dao.IDao;

import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/10/25
 */
public interface FileInfoDao extends IDao<FileInfoPo, FileInfoBo, FileInfoQuery> {
    String TABLE_NAME = "file_info";

    List<String> selectAllContentType();

    static String checkInsert(FileInfoPo fileInfoPo) {
        if (StringUtils.isBlank(fileInfoPo.getMd5())) {
            return "MD5不得为空";
        }
        if (StringUtils.isBlank(fileInfoPo.getContentType())) {
            return "文件类型不得为空";
        }
        return null;
    }

    static String checkUpdate(FileInfoPo fileInfoPo) {
        if (fileInfoPo.getFileId() < 1) {
            return "文件id不得为空";
        }
        return null;
    }
}
