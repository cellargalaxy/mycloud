package top.cellargalaxy.mycloud.dao;

import org.junit.Test;
import top.cellargalaxy.mycloud.model.po.PermissionPo;

import static org.junit.Assert.*;

public class PermissionDaoTest {
	private final PermissionDao permissionDao;

	public PermissionDaoTest(PermissionDao permissionDao) {
		this.permissionDao = permissionDao;
	}

	@Test
	public void insert() {
		PermissionPo permissionPo = new PermissionPo();
		permissionPo.setPermissionId(1);
		permissionPo.setPermissionMark("权限mark");
		assertEquals(1, permissionDao.insert(permissionPo));
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
	public void upldate() {
	}
}