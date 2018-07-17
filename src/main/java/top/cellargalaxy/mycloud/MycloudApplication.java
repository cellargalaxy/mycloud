package top.cellargalaxy.mycloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling//定时任务
@EnableCaching//缓存
@SpringBootApplication
public class MycloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(MycloudApplication.class, args);
	}
}
