package top.cellargalaxy.newcloud.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.cellargalaxy.newcloud.model.po.FileDataPo;
import top.cellargalaxy.newcloud.model.query.FileDataQuery;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileDataDaoTest {
	@Autowired
	private FileDataDao fileDataDao;

	@Test
	public void insert() {
		FileDataPo fileDataPo = new FileDataPo();
		fileDataPo.setFileId(2);
		fileDataPo.setFileData(new byte[]{0, 1, 2, 3, 4});
		assertEquals(1, fileDataDao.insert(fileDataPo));
	}

	@Test
	public void delete() {
		FileDataQuery fileDataQuery = new FileDataQuery();
		fileDataQuery.setFileId(2);
		assertEquals(1, fileDataDao.delete(fileDataQuery));
	}

	@Test
	public void select() {
		FileDataQuery fileDataQuery = new FileDataQuery();
		fileDataQuery.setFileId(2);
		FileDataPo fileDataPo = fileDataDao.select(fileDataQuery);
		System.out.println();
		System.out.println(fileDataPo + " ; " + (fileDataPo != null ? (Arrays.toString(fileDataPo.getFileData())) : "null"));
		System.out.println();
	}

	@Test
	public void update() {
		FileDataPo fileDataPo = new FileDataPo();
		fileDataPo.setFileId(2);
		fileDataPo.setFileData(new byte[]{5, 6, 7, 8, 9});
		assertEquals(1, fileDataDao.update(fileDataPo));
	}
}