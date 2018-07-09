package top.cellargalaxy.newcloud.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.cellargalaxy.newcloud.model.po.FileInfoPo;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest

public class FileInfoDaoTest {
	@Autowired
	private FileInfoDao fileInfoDao;
	@Test
	public void insert() {
		FileInfoPo fileInfoPo=new FileInfoPo();
		fileInfoPo.setFileName("图片。jpg");
		fileInfoPo.setContentType("gif");
		fileInfoPo.setFileLength(1254);
		fileInfoPo.setMd5("F95E3B336BA9BA34726EB9455FFDCF19");
		fileInfoPo.setSort("音频");
		assertEquals(1, fileInfoDao.insert(fileInfoPo));
	}

	@Test
	public void delete() {
		FileInfoPo fileInfoPo=new FileInfoPo();
		fileInfoPo.setFileId(1);
		assertEquals(1, fileInfoDao.delete(fileInfoPo));
	}

	@Test
	public void selectOne() {
		FileInfoPo fileInfoPo=new FileInfoPo();
		fileInfoPo.setFileId(2);
		System.out.println();
		System.out.println(fileInfoDao.selectOne(fileInfoPo));
		System.out.println();
	}

	@Test
	public void selectSome() {
		FileInfoPo fileInfoPo=new FileInfoPo();
		fileInfoPo.setSort("图片");
		System.out.println();
		for (FileInfoPo infoPo: fileInfoDao.selectSome(fileInfoPo)) {
			System.out.println(infoPo);
		}
		System.out.println();
	}

	@Test
	public void update() {
		FileInfoPo fileInfoPo=new FileInfoPo();
		fileInfoPo.setFileId(2);
		fileInfoPo.setFileName("图片。png");
		fileInfoPo.setContentType("png");
		assertEquals(1, fileInfoDao.update(fileInfoPo));
	}

	@Test
	public void selectContentType() {
		System.out.println();
		System.out.println(fileInfoDao.selectContentType());
		System.out.println();
	}

	@Test
	public void selectSort() {
		System.out.println();
		System.out.println(fileInfoDao.selectSort());
		System.out.println();
	}
}