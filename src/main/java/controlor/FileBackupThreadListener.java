package controlor;

import dao.DBFactory;
import service.FileBackupThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by cellargalaxy on 17-10-15.
 */
public class FileBackupThreadListener implements ServletContextListener {
	public static final FileBackupThread FILE_BACKUP_THREAD = new FileBackupThread(DBFactory.getDbs());
	
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		FILE_BACKUP_THREAD.start();
	}
	
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		FILE_BACKUP_THREAD.interrupt();
	}
	
	
}
