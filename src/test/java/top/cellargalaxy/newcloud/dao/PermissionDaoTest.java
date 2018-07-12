package top.cellargalaxy.newcloud.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.cellargalaxy.newcloud.model.po.PermissionPo;
import top.cellargalaxy.newcloud.model.query.PermissionQuery;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PermissionDaoTest {
	@Autowired
	private PermissionDao permissionDao;

	@Test
	public void insert() {
		PermissionPo permissionPo = new PermissionPo();
		permissionPo.setPermissionId(1);
		permissionPo.setPermissionMark("aaa");
		assertEquals(1, permissionDao.insert(permissionPo));
	}

	@Test
	public void delete() {
		PermissionQuery permissionQuery=new PermissionQuery();
		permissionQuery.setPermissionId(1);
		assertEquals(1, permissionDao.delete(permissionQuery));
	}

	@Test
	public void selectOne() {
		PermissionQuery permissionQuery=new PermissionQuery();
		permissionQuery.setPermissionId(2);
		System.out.println();
		System.out.println(permissionDao.selectOne(permissionQuery));
		System.out.println();
	}

	@Test
	public void selectSome() {
		PermissionQuery permissionQuery=new PermissionQuery();
		permissionQuery.setPermissionMark("a");
		System.out.println();
		for (PermissionPo permissionPo: permissionDao.selectSome(permissionQuery)) {
			System.out.println(permissionPo);
		}
		System.out.println();
	}

	@Test
	public void upload() {
		PermissionPo permissionPo=new PermissionPo();
		permissionPo.setPermissionId(3);
		permissionPo.setPermissionMark("bab");
		assertEquals(1, permissionDao.upload(permissionPo));
	}
}