package top.cellargalaxy.mycloud.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import top.cellargalaxy.mycloud.dao.UserDao;
import top.cellargalaxy.mycloud.model.bo.UserBo;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.query.UserQuery;
import top.cellargalaxy.mycloud.service.impl.UserServiceImpl;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {
	//@InjectMocks标注被测试的对象
	@InjectMocks
	private UserService userService = new UserServiceImpl();

	//@Mock标注需要被注入到被测试对象的依赖
	@Mock
	private UserDao userDao;

	@Before
	public void setUp() {
		//初始化测试用例类中由Mockito的注解标注的所有模拟对象
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void addUser() {
		//每调用userDao的insert方法时都返回1，表示插入成功
		when(userDao.insert(any(UserPo.class)))
				.thenReturn(1);

		//userService做了插入的时候，账号密码不得为空的检查
		assertEquals("用户名不得为空", userService.addUser(new UserPo()));

		//密码为空的测试
		assertEquals("用户密码不得为空", userService.addUser(new UserPo() {{
			setUsername("username");
		}}));

		//账号为空的测试
		assertEquals("用户名不得为空", userService.addUser(new UserPo() {{
			setUserPassword("password");
		}}));

		//账号密码都有，插入成功
		assertEquals(null, userService.addUser(new UserPo() {{
			setUsername("username");
			setUserPassword("password");
		}}));
	}

	@Test
	public void getUser() {
		//这里每调用userDao的selectOne方法时，都返回一个username为"create by mock"的对象
		when(userDao.selectOne(any(UserQuery.class)))
				.thenReturn(new UserBo() {{
					setUsername("create by mock");
				}});

		assertEquals("create by mock", userService.getUser(new UserQuery()).getUsername());
	}
}