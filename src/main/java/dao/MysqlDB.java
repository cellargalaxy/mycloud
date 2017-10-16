package dao;

import bean.FilePackage;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by cellargalaxy on 17-10-15.
 */
public final class MysqlDB extends DB {
	private static final String INIT_SQL = "CREATE TABLE IF NOT EXISTS FILE(FILE_NAME VARCHAR(255) NOT NULL, UPLOAD_DATE DATE NOT NULL, DESCRIPTION VARCHAR(255), FILE LONGBLOB NOT NULL, PRIMARY KEY(FILE_NAME,UPLOAD_DATE))";
	private static final String INSERT_SQL = "INSERT INTO FILE(FILE_NAME, UPLOAD_DATE, DESCRIPTION, FILE) VALUES(?,?,?,?)";
	private static final String SELECT_ALL_FILE_PACKAGE_SQL = "SELECT FILE_NAME, UPLOAD_DATE, DESCRIPTION FROM FILE";
	private static final String SELECT_FILE_PACKAGE_INFO_SQL = "SELECT DESCRIPTION FROM FILE WHERE FILE_NAME=? AND UPLOAD_DATE=?";
	private static final String SELECT_FILE_PACKAGE_BLOB_SQL = "SELECT FILE FROM FILE WHERE FILE_NAME=? AND UPLOAD_DATE=?";
	private static final String DELETE_FILE_PACKAGE_SQL = "DELETE FROM FILE WHERE FILE_NAME=? AND UPLOAD_DATE=?";
	
	public MysqlDB(File confFile) throws Exception {
		super(confFile);
		JDBCMethod.up(createConnection(), INIT_SQL);
	}
	
	public boolean insertFile(FilePackage filePackage) {
		if (selectFilePackageInfo(filePackage) != null) {
			deleteFilePackage(filePackage);
		}
		java.sql.Date sd = new java.sql.Date(filePackage.getUploadDate().getTime());
		Connection connection = createConnection();
		PreparedStatement preparedStatement = null;
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(INSERT_SQL);
			preparedStatement.setString(1, filePackage.getFileName());
			preparedStatement.setDate(2, sd);
			preparedStatement.setString(3, filePackage.getDescription());
			preparedStatement.setBlob(4, new BufferedInputStream(new FileInputStream(filePackage.getFile())));
			if (preparedStatement.executeUpdate() > 0) {
				connection.commit();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	public FilePackage[] selectAllFilePackage() {
		Connection connection = createConnection();
		List<Map<String, Object>> list = JDBCMethod.selectTableBySql(connection, SELECT_ALL_FILE_PACKAGE_SQL);
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (list == null) {
			return null;
		}
		FilePackage[] filePackages = new FilePackage[list.size()];
		int i = 0;
		for (Map<String, Object> map : list) {
			FilePackage filePackage = new FilePackage(map.get("FILE_NAME").toString(), (Date) map.get("UPLOAD_DATE"), map.get("DESCRIPTION").toString());
			filePackages[i] = filePackage;
			i++;
		}
		return filePackages;
	}
	
	public FilePackage selectFilePackageInfo(FilePackage filePackage) {
		java.sql.Date sd = new java.sql.Date(filePackage.getUploadDate().getTime());
		Connection connection = createConnection();
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(SELECT_FILE_PACKAGE_INFO_SQL);
			preparedStatement.setString(1, filePackage.getFileName());
			preparedStatement.setDate(2, sd);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				filePackage.setDescription(resultSet.getString(1));
				return filePackage;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public boolean selectFilePackageBlob(FilePackage filePackage) {
		java.sql.Date sd = new java.sql.Date(filePackage.getUploadDate().getTime());
		Connection connection = createConnection();
		PreparedStatement preparedStatement = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			preparedStatement = connection.prepareStatement(SELECT_FILE_PACKAGE_BLOB_SQL);
			preparedStatement.setString(1, filePackage.getFileName());
			preparedStatement.setDate(2, sd);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				Blob blob = resultSet.getBlob(1);
				inputStream = new BufferedInputStream(blob.getBinaryStream());
				filePackage.getFile().getParentFile().mkdirs();
				outputStream = new BufferedOutputStream(new FileOutputStream(filePackage.getFile()));
				byte[] bytes = new byte[1024];
				int len;
				while ((len = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, len);
				}
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	public boolean deleteFilePackage(FilePackage filePackage) {
		java.sql.Date sd = new java.sql.Date(filePackage.getUploadDate().getTime());
		Connection connection = createConnection();
		PreparedStatement preparedStatement = null;
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(DELETE_FILE_PACKAGE_SQL);
			preparedStatement.setString(1, filePackage.getFileName());
			preparedStatement.setDate(2, sd);
			if (preparedStatement.executeUpdate() > -1) {
				connection.commit();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} finally {
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
}
