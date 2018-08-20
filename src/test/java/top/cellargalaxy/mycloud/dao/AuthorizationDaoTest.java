package top.cellargalaxy.mycloud.dao;

import org.junit.Test;
import top.cellargalaxy.mycloud.model.bo.AuthorizationBo;
import top.cellargalaxy.mycloud.model.po.AuthorizationPo;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;

import static org.junit.Assert.assertEquals;

public class AuthorizationDaoTest {
	private final AuthorizationDao authorizationDao;

	public AuthorizationDaoTest(AuthorizationDao authorizationDao) {
		this.authorizationDao = authorizationDao;
	}

	@Test
	public void insert() {
		AuthorizationPo authorizationPo = new AuthorizationPo();
		authorizationPo.setPermissionId(1);
		authorizationPo.setUserId(1);
		assertEquals(1, authorizationDao.insert(authorizationPo));
	}

	@Test
	public void delete() {
		AuthorizationQuery authorizationQuery = new AuthorizationQuery();
		authorizationQuery.setAuthorizationId(3);
		assertEquals(1, authorizationDao.delete(authorizationQuery));
	}

	@Test
	public void selectOne() {
		AuthorizationQuery authorizationQuery = new AuthorizationQuery();
		authorizationQuery.setAuthorizationId(1);
		System.out.println();
		System.out.println(authorizationDao.selectOne(authorizationQuery));
		System.out.println();
	}

	@Test
	public void selectSome() {
		AuthorizationQuery authorizationQuery = new AuthorizationQuery();
		authorizationQuery.setUserId(1);
		System.out.println();
		for (AuthorizationBo authorizationBo : authorizationDao.selectSome(authorizationQuery)) {
			System.out.println(authorizationBo);
		}
		System.out.println();
	}

	@Test
	public void update() {
		AuthorizationPo authorizationPo = new AuthorizationPo();
		authorizationPo.setAuthorizationId(1);
		authorizationPo.setUserId(2);
		assertEquals(1, authorizationDao.update(authorizationPo));
	}
}