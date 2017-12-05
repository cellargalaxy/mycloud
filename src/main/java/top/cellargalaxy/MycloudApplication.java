package top.cellargalaxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class MycloudApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MycloudApplication.class, args);
	}
}
