package bean;


/**
 * Created by cellargalaxy on 17-10-15.
 */
public class DBPackage {
	private final String dbName;
	private final FilePackage[] filePackages;
	
	public DBPackage(String dbName, FilePackage[] filePackages) {
		this.dbName = dbName;
		this.filePackages = filePackages;
	}
	
	public String getDbName() {
		return dbName;
	}
	
	public FilePackage[] getFilePackages() {
		return filePackages;
	}
}
