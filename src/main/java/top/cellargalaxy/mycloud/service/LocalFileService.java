package top.cellargalaxy.mycloud.service;

import top.cellargalaxy.mycloud.model.po.FileInfoPo;

import java.io.File;
import java.util.Map;

/**
 * @author cellargalaxy
 * @time 2018/8/29
 */
public interface LocalFileService {
	Map getDriveInfo();

	File createLocalFile(FileInfoPo fileInfoPo);

	/**
	 * 主要逻辑是检查空间是否超出，如果是则尝试腾空间，如果空间任然不够，则会删除此文件。
	 * 所以其实这个方法并不会移动文件到drive，因此要求这个文件已经在drive里。
	 *
	 * @param file 所需要添加的本地文件，求这个文件已经在drive里。
	 * @return
	 */
	String addLocalFile(File file);

	String removeLocalFile(FileInfoPo fileInfoPo);

	String removeAllLocalFile();

	/**
	 * 如果本地存在此文件则返回，并对此文件的查询数加一。如果不存在则返回null
	 *
	 * @param fileInfoPo
	 * @return
	 */
	File getLocalFile(FileInfoPo fileInfoPo);
}
