package top.cellargalaxy.mycloud.dao;

import org.junit.Test;
import top.cellargalaxy.mycloud.model.po.UserPo;

import static org.junit.Assert.*;

public class UserDaoTest {
	private final UserDao userDao;

	public UserDaoTest(UserDao userDao) {
		this.userDao = userDao;
	}

	@Test
	public void insert() {
		UserPo userPo = new UserPo();
		userPo.setUserName("nickname");
		userPo.setUserPassword("654321");
		assertEquals(1, userDao.insert(userPo));
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
	public void update() {
	}
}