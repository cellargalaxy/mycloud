package top.cellargalaxy.mycloud.dao;

import org.junit.Test;
import top.cellargalaxy.mycloud.model.bo.OwnBo;
import top.cellargalaxy.mycloud.model.po.OwnPo;
import top.cellargalaxy.mycloud.model.query.OwnQuery;

import static org.junit.Assert.*;

public class OwnDaoTest {
	private final OwnDao ownDao;

	public OwnDaoTest(OwnDao ownDao) {
		this.ownDao = ownDao;
	}

	@Test
	public void insert() {
		OwnPo ownPo = new OwnPo();
		ownPo.setUserId(1);
		ownPo.setFileId(1);
		ownPo.setFileName("文件名。jpg");
		ownPo.setSort("图片");
		assertEquals(1, ownDao.insert(ownPo));
	}

	@Test
	public void delete() {
		OwnQuery ownQuery = new OwnQuery();
		ownQuery.setOwnId(2);
		assertEquals(1, ownDao.delete(ownQuery));
	}

	@Test
	public void selectOne() {
		OwnQuery ownQuery = new OwnQuery();
		ownQuery.setOwnId(1);
		System.out.println();
		System.out.println(ownDao.selectOne(ownQuery));
		System.out.println();
	}

	@Test
	public void selectSome() {
		OwnQuery ownQuery = new OwnQuery();
		ownQuery.setUserId(1);
		System.out.println();
		for (OwnBo ownBo : ownDao.selectSome(ownQuery)) {
			System.out.println(ownBo);
		}
		System.out.println();
	}

	@Test
	public void selectSort() {
		OwnQuery ownQuery = new OwnQuery();
		ownQuery.setUserId(1);
		System.out.println();
		System.out.println(ownDao.selectSort(ownQuery));
		System.out.println();
	}

	@Test
	public void update() {
		OwnPo ownPo = new OwnPo();
		ownPo.setOwnId(1);
		ownPo.setFileId(2);
		assertEquals(1, ownDao.update(ownPo));
	}
}