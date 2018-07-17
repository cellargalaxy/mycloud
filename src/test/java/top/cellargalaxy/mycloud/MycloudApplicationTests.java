package top.cellargalaxy.mycloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import top.cellargalaxy.mycloud.dao.*;
import top.cellargalaxy.mycloud.model.po.UserPo;
import top.cellargalaxy.mycloud.model.vo.ExceptionInfo;
import top.cellargalaxy.mycloud.util.FileBlocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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

	@Test
	public void test() throws Exception {
		new FileInfoDaoTest(fileInfoDao).insert();
	}
}
