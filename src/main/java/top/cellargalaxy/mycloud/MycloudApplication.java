package top.cellargalaxy.mycloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching // 启动缓存
@SpringBootApplication
public class MycloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(MycloudApplication.class, args);
	}
}
