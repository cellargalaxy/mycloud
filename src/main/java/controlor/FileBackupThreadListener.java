package controlor;

import dao.DBFactory;
import service.FileServiceThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by cellargalaxy on 17-10-15.
 */
public class FileBackupThreadListener implements ServletContextListener {
	public static final FileServiceThread FILE_BACKUP_THREAD = new FileServiceThread(DBFactory.getDbs());
	
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		FILE_BACKUP_THREAD.start();
	}
	
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		FILE_BACKUP_THREAD.interrupt();
	}
	
	
}
