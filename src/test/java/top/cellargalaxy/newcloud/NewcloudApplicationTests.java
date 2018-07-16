package top.cellargalaxy.newcloud;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import top.cellargalaxy.newcloud.model.po.UserPo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NewcloudApplicationTests {

	@Autowired
	private RedisTemplate redisTemplate;

	@Cacheable
	@Test
	public void test() throws Exception {

		UserPo userPo = new UserPo();
		userPo.setUserId(1);
		userPo.setUserName("aaa");
		userPo.setUserPassword("qqq");

		System.out.println();
		System.out.println("userPo.hashCode(): " + userPo.hashCode());
		redisTemplate.opsForValue().set(userPo.getUserName(), userPo);
		UserPo userPo1 = (UserPo) redisTemplate.opsForValue().get("aaa");
		System.out.println("userPo1.hashCode(): " + userPo1.hashCode());
		System.out.println(userPo);
		System.out.println(userPo1);
	}

}
