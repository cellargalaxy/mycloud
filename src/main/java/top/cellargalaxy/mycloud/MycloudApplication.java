package top.cellargalaxy.mycloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

//事务，由于UploadFileTaskExecute类要被注入，又有事务，还实现了个接口。但不知为何spring会报错，说
//The bean 'uploadFileTaskExecute' could not be injected as a 'top.cellargalaxy.mycloud.service.schedule.UploadFileTaskExecute' because it is a JDK dynamic proxy that implements:top.cellargalaxy.mycloud.service.schedule.TaskExecute
//所以不得不用cglib
//@EnableTransactionManagement(proxyTargetClass = true)
//@EnableScheduling//定时任务
//@EnableCaching//缓存
@SpringBootApplication
public class MycloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(MycloudApplication.class, args);
    }
}
