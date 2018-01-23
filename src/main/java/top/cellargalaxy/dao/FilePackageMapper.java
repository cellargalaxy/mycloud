package top.cellargalaxy.dao;

import org.apache.ibatis.annotations.*;
import top.cellargalaxy.bean.daoBean.FilePackage;

import java.util.Date;
import java.util.LinkedList;

/**
 * Created by cellargalaxy on 17-12-2.
 */
@Mapper
public interface FilePackageMapper {
	@Insert("insert into file(filename, date, description, fileLength, fileSha256, fileBytes) " +
			"values(#{filename}, #{date,jdbcType=DATE}, #{description}, #{fileLength}, #{fileSha256}, #{fileBytes})")
	int insertFilePackage(FilePackage filePackage);
	
	@Delete("delete from file where filename=#{filename} and date=#{date,jdbcType=DATE} limit 1")
	int deleteFilePackageByFilenameAndDate(@Param("filename") String filename, @Param("date") Date date);
	
	@Delete("delete from file where fileSha256=#{fileSha256} limit 1")
	int deleteFilePackageByFileSha256(@Param("fileSha256") String fileSha256);
	
	/*
	这里好坑，where后面的#{date,jdbcType=DATE}，jdbcType=DATE一定要加。
	如果不加mybatis不知道会把date映射成什么对象，可能是字符串，没有格式化自然对不上号。因为如果传入的参数改成string并且手动格式化居然能查出来。
	DATE是jdbcType的一个枚举对象，请不要小写。
	参考资料：http://www.mybatis.org/mybatis-3/zh/sqlmap-xml.html
	 */
	@Select("select filename, date, description, fileLength, fileSha256 from file where filename=#{filename} and date=#{date,jdbcType=DATE} limit 1")
	FilePackage selectFilePackageByFilenameAndDate(@Param("filename") String filename, @Param("date") Date date);
	
	@Select("select filename, date, description, fileLength, fileSha256 from file where fileSha256=#{fileSha256} limit 1")
	FilePackage selectFilePackageByFileSha256(@Param("fileSha256") String fileSha256);
	
	@Select("select filename, date, description, fileLength, fileSha256 from file where filename like concat('%',#{filename},'%') order by date desc")
	FilePackage[] selectFilePackagesByFilename(@Param("filename") String filename);
	
	@Select("select filename, date, description, fileLength, fileSha256 from file where date=#{date,jdbcType=DATE}")
	FilePackage[] selectFilePackagesByDate(@Param("date") Date date);
	
	@Select("select filename, date, description, fileLength, fileSha256 from file where description like concat('%',#{description},'%') order by date desc")
	FilePackage[] selectFilePackagesByDescription(@Param("description") String description);
	
	@Select("select filename, date, description, fileLength, fileSha256 from file order by date desc limit #{off},#{len}")
	FilePackage[] selectFilePackages(@Param("off") int off, @Param("len") int len);
	
	@Select("select filename, date, description, fileLength, fileSha256 from file")
	LinkedList<FilePackage> selectAllFilePackage();
	
	@Select("select count(*) from (select fileSha256 from file where filename=#{filename} and date=#{date,jdbcType=DATE} limit 1) as a")
	int selectFilePackageCountByFilenameAndDate(@Param("filename") String filename, @Param("date") Date date);
	
	@Select("select count(*) from file")
	int selectFilePackageCount();
	
	@Select("select filename, date, description, fileLength, fileSha256, fileBytes from file where filename=#{filename} and date=#{date,jdbcType=DATE} limit 1")
	FilePackage selectFilePackageByteByFilenameAndDate(@Param("filename") String filename, @Param("date") Date date);
	
	@Select("select filename, date, description, fileLength, fileSha256, fileBytes from file where fileSha256=#{fileSha256} limit 1")
	FilePackage selectFilePackageByteByFileSha256(@Param("fileSha256") String fileSha256);
	
	@Update("update file set description=#{description}, fileLength=#{fileLength}, fileSha256=#{fileSha256}, fileBytes=#{fileBytes} where filename=#{filename} and date=#{date,jdbcType=DATE} limit 1")
	int updateFilePackage(FilePackage filePackage);
}
