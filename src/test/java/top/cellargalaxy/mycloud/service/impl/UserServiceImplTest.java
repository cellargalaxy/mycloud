package top.cellargalaxy.mycloud.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.cellargalaxy.mycloud.model.query.UserQuery;
import top.cellargalaxy.mycloud.service.UserService;

import static org.junit.Assert.*;

public class UserServiceImplTest {
	private final UserService userService;

	public UserServiceImplTest(UserService userService) {
		this.userService = userService;
	}

	@Test
	public void pageUser() {
		UserQuery userQuery = new UserQuery();
		userQuery.setPageSize(2);
		userQuery.setPage(1);
//		userQuery.setUserId(13);
	}

	@Test
	public void pageUserAuthorization() {
		UserQuery userQuery = new UserQuery();
		userQuery.setPageSize(2);
		userQuery.setPage(1);
//		userQuery.setUserId(13);
	}
}