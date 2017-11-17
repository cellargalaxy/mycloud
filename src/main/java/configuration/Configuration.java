package configuration;

import bean.FilePackage;
import dao.DBFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by cellargalaxy on 17-11-17.
 */
public class Configuration {
	private static DateFormat drivePathDateFormat;
	private static File driveRootFile;
	private static String driveUrl;
	private static Map<File, String> dbConfigurations;
	private static DateFormat requestDateFormat;
	private static long maxUploadFileLength;
	
	static {
		dbConfigurations = new HashMap<File, String>();
		requestDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		maxUploadFileLength=-1;
		
		String path = new File(Configuration.class.getResource("").getPath()).getParentFile().getAbsolutePath() + File.separator;
		Properties properties = new Properties();
		try {
			properties.load(new InputStreamReader(new FileInputStream(path + "mycloud.properties")));
			
			String drivePathDateFormatString = properties.getProperty("drivePathDateFormat");
			if (drivePathDateFormatString != null) {
				drivePathDateFormat = new SimpleDateFormat(drivePathDateFormatString);
			} else {
				drivePathDateFormat = new SimpleDateFormat("yyyyMM/dd");
			}
			
			String driveRootFileString = properties.getProperty("driveRootFile");
			driveUrl = properties.getProperty("driveUrl");
			if (driveRootFileString != null && driveUrl != null) {
				driveRootFile = new File(driveRootFileString);
			} else {
				driveRootFile = new File(new File(Configuration.class.getResource("").getPath()).getParentFile().getParentFile().getParentFile().getAbsolutePath() + File.separator + "drive");
				driveUrl = "/mycloud/drive";
			}
			
			String maxUploadFileLengthString = properties.getProperty("maxUploadFileLength");
			if (maxUploadFileLength==-1) {
				maxUploadFileLength=16777216;
			}
			
			for (int i = 1; i < Integer.MAX_VALUE; i++) {
				String dbConfFilePath = properties.getProperty("dbConfFilePath" + i);
				String dbType = properties.getProperty("dbType" + i);
				if (dbConfFilePath == null || dbConfFilePath.length() == 0 || dbType == null || dbType.length() == 0) {
					break;
				}
				dbConfigurations.put(new File(path + dbConfFilePath), dbType);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static DateFormat getDrivePathDateFormat() {
		return drivePathDateFormat;
	}
	
	public static void setDrivePathDateFormat(DateFormat drivePathDateFormat) {
		Configuration.drivePathDateFormat = drivePathDateFormat;
	}
	
	public static File getDriveRootFile() {
		return driveRootFile;
	}
	
	public static void setDriveRootFile(File driveRootFile) {
		Configuration.driveRootFile = driveRootFile;
	}
	
	public static String getDriveUrl() {
		return driveUrl;
	}
	
	public static void setDriveUrl(String driveUrl) {
		Configuration.driveUrl = driveUrl;
	}
	
	public static Map<File, String> getDbConfigurations() {
		return dbConfigurations;
	}
	
	public static void setDbConfigurations(Map<File, String> dbConfigurations) {
		Configuration.dbConfigurations = dbConfigurations;
	}
	
	public static DateFormat getRequestDateFormat() {
		return requestDateFormat;
	}
	
	public static void setRequestDateFormat(DateFormat requestDateFormat) {
		Configuration.requestDateFormat = requestDateFormat;
	}
	
	public static long getMaxUploadFileLength() {
		return maxUploadFileLength;
	}
	
	public static void setMaxUploadFileLength(long maxUploadFileLength) {
		Configuration.maxUploadFileLength = maxUploadFileLength;
	}
}
