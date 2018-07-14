package top.cellargalaxy.mycloud.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.cellargalaxy.mycloud.model.po.AuthorizationPo;
import top.cellargalaxy.mycloud.model.query.AuthorizationQuery;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorizationDaoTest {
	@Autowired
	private AuthorizationDao authorizationDao;

	@Test
	public void insert() {
		AuthorizationPo authorizationPo=new AuthorizationPo();
		authorizationPo.setPermissionId(1);
		authorizationPo.setUserId(2);
		assertEquals(1, authorizationDao.insert(authorizationPo));
	}

	@Test
	public void delete() {
		AuthorizationPo authorizationPo=new AuthorizationPo();
		authorizationPo.setAuthorizationId(1);
		assertEquals(1, authorizationDao.delete(authorizationPo));
	}

	@Test
	public void selectOne() {
		AuthorizationQuery authorizationQuery=new AuthorizationQuery();
		authorizationQuery.setAuthorizationId(2);
		System.out.println();
		System.out.println(authorizationDao.selectOne(authorizationQuery));
		System.out.println();
	}

	@Test
	public void selectSome() {
		AuthorizationQuery authorizationQuery=new AuthorizationQuery();
		authorizationQuery.setUserId(2);
		System.out.println();
		for (AuthorizationPo authorizationPo: authorizationDao.selectSome(authorizationQuery)) {
			System.out.println(authorizationPo);
		}
		System.out.println();
	}

	@Test
	public void update() {
		AuthorizationPo authorizationPo=new AuthorizationPo();
		authorizationPo.setAuthorizationId(3);
		authorizationPo.setPermissionId(1);
		authorizationPo.setUserId(2);
		assertEquals(1, authorizationDao.update(authorizationPo));
	}
}