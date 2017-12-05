package top.cellargalaxy.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.cellargalaxy.bean.FilePackage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by cellargalaxy on 17-12-2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FilePackageMapperTest {
	@Autowired
	private FilePackageMapper filePackageMapper;
	
	@Test
	public void test() throws IOException {
	
	}
	
//	private void selectFilePackageByte(){
//		FilePackage filePackage=filePackageMapper.selectFilePackageByte("子系统",new Date());
//		System.out.println(new String(filePackage.getFilebytes()));
//	}
//
//	private void deleteFilePackage(){
//		System.out.println(filePackageMapper.deleteFilePackage("ftp.sh",new Date()));
//	}
//
//	private void updateFilePackage() throws IOException {
//		FilePackage filePackage=new FilePackage(new File("/home/cellargalaxy/file/ftp.sh"),new Date(),"描述44");
//		System.out.println(filePackageMapper.updateFilePackage(filePackage));
//	}
//
//	private void selectFilePackageCount(){
//		System.out.println(filePackageMapper.selectFilePackageCount());
//	}
//
//	private void selectFilePackageCountByFilenameAndDate(){
//		System.out.println(filePackageMapper.selectFilePackageCountByFilenameAndDate("子系统",new Date()));
//	}
//
//	private void selectFilePackages(){
//		System.out.println(Arrays.toString(filePackageMapper.selectFilePackages(0,3)));
//	}
//
//	private void selectFilePackageByDescription(){
//		System.out.println(Arrays.toString(filePackageMapper.selectFilePackageByDescription("述")));
//	}
//
//	private void selectFilePackageByFilename(){
//		System.out.println(Arrays.toString(filePackageMapper.selectFilePackageByFilename("sh")));
//	}
//
//	private void selectFilePackageByDate(){
//		System.out.println(Arrays.toString(filePackageMapper.selectFilePackageByDate(new Date())));
//	}
//
//	private void selectDescriptionByFilenameAndDate(){
//		System.out.println(filePackageMapper.selectDescriptionByFilenameAndDate("ftp.sh",new Date()));
//	}
//
//	private void insertFilePackage() throws IOException {
//		FilePackage filePackage=new FilePackage(new File("/home/cellargalaxy/file/ftp.sh"),new Date(),"描述4");
//		System.out.println(filePackageMapper.insertFilePackage(filePackage));
//	}
}
