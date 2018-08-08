package top.cellargalaxy.mycloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.cellargalaxy.mycloud.dao.*;
import top.cellargalaxy.mycloud.service.UserService;
import top.cellargalaxy.mycloud.service.impl.UserServiceImplTest;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MycloudApplicationTests {

	@Autowired
	private PermissionDao permissionDao;
	@Autowired
	private AuthorizationDao authorizationDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private FileInfoDao fileInfoDao;
	@Autowired
	private OwnDao ownDao;
	@Autowired
	private UserService userService;
	@Autowired
	private BlockDao blockDao;

	@Test
	public void test() throws Exception {
		new BlockDaoTest(blockDao).insert();
	}
}
