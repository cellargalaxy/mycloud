package top.cellargalaxy.mycloud.model.vo;

import lombok.Data;
import top.cellargalaxy.mycloud.model.bo.FileInfoBo;
import top.cellargalaxy.mycloud.model.bo.OwnBo;

import java.io.Serializable;
import java.util.List;

/**
 * @author cellargalaxy
 * @time 2018/10/25
 */
@Data
public class FileInfoVo implements Serializable {
	private static final long serialVersionUID = -8081110814279748765L;
	private FileInfoBo fileInfo;
	private List<OwnBo> owns;
}
