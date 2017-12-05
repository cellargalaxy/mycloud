package top.cellargalaxy.controlor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import top.cellargalaxy.service.FilePackageBackup;
import top.cellargalaxy.service.FilePackageRestore;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by cellargalaxy on 17-12-5.
 */
@WebListener
public class ThreadListener implements ServletContextListener {
	@Autowired
	private FilePackageBackup filePackageBackup;
	@Autowired
	private FilePackageRestore filePackageRestore;
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		WebApplicationContextUtils.getRequiredWebApplicationContext(servletContextEvent.getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);
		((Thread) filePackageBackup).start();
		((Thread) filePackageRestore).start();
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		System.out.println("监听器摧毁");
	}
}
