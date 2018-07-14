package top.cellargalaxy.mycloud.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.UserQuery;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
	@Autowired
	private UserDao userDao;

	@Test
	public void insert() {
		UserPo userPo = new UserPo();
		userPo.setUserName("huaji2");
		userPo.setUserPassword("qqq");
		assertEquals(1, userDao.insert(userPo));
	}

	@Test
	public void delete() {
		UserQuery userQuery = new UserQuery();
		userQuery.setUserId(1);
		assertEquals(1, userDao.delete(userQuery));
	}

	@Test
	public void selectOne() {
		UserQuery userQuery = new UserQuery();
		userQuery.setUserName("huaji");
		System.out.println();
		System.out.println(userDao.selectOne(userQuery));
		System.out.println();
		System.out.println(userDao.selectOne(userQuery));
		System.out.println();
	}

	@Test
	public void selectSome() {
		UserQuery userQuery = new UserQuery();
		userQuery.setUserName("huaji2");
		System.out.println();
		for (UserPo userPo: userDao.selectSome(userQuery)) {
			System.out.println(userPo);
		}
		System.out.println();
	}

	@Test
	public void update() {
		UserPo userPo = new UserPo();
		userPo.setUserName("huaji");
		userPo.setUserPassword("zzz");
		assertEquals(1, userDao.update(userPo));
	}
}