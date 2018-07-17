package top.cellargalaxy.mycloud.dao;

import org.junit.Test;
import top.cellargalaxy.mycloud.model.po.FileInfoPo;

import static org.junit.Assert.*;

public class FileInfoDaoTest {
	private final FileInfoDao fileInfoDao;

	public FileInfoDaoTest(FileInfoDao fileInfoDao) {
		this.fileInfoDao = fileInfoDao;
	}

	@Test
	public void insert() {
		FileInfoPo fileInfoPo = new FileInfoPo();
		fileInfoPo.setMd5("012345678901234567s901a345678901");
		fileInfoPo.setFileLength(171410969);
		fileInfoPo.setContentType("jpg");
		assertEquals(1, fileInfoDao.insert(fileInfoPo));
		System.out.println(fileInfoPo);
	}

	@Test
	public void delete() {
	}

	@Test
	public void selectOne() {
	}

	@Test
	public void selectSome() {
	}

	@Test
	public void selectContentType() {
	}

	@Test
	public void update() {
	}
}