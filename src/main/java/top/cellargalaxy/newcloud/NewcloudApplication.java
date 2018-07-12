package top.cellargalaxy.newcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching // 启动缓存
@SpringBootApplication
public class NewcloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewcloudApplication.class, args);
	}
}
