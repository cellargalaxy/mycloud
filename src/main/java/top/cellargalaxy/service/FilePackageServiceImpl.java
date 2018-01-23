package top.cellargalaxy.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.cellargalaxy.bean.daoBean.FilePackage;
import top.cellargalaxy.configuration.MycloudConfiguration;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by cellargalaxy on 17-12-5.
 */
@Component
public class FilePackageServiceImpl implements FilePackageService {
	@Autowired
	private MycloudConfiguration configuration;
	
	private File fileDriveFolder;
	private DateFormat dataFormat;
	
	@Override
	public File moveFileToDrive(File tmpFile, Date date) {
		if (tmpFile == null || date == null) {
			return null;
		}
		File driveFile = new File(createDriveDateFilePath(date, tmpFile.getName()));
		driveFile.getParentFile().mkdirs();
		if (tmpFile.renameTo(driveFile)) {
			return driveFile;
		}
		return null;
	}
	
	@Override
	public FilePackage fillingFilePackageInfoAttributes(FilePackage filePackage) {
		if (filePackage == null) {
			return null;
		}
		if ((filePackage.getFilename() == null || filePackage.getFilename().length() == 0 || filePackage.getDate() == null) && filePackage.getFile() == null) {
			return null;
		}
		if (filePackage.getFilename() != null && filePackage.getDate() != null && filePackage.getFile() == null) {
			filePackage.setFile(new File(createDriveDateFilePath(filePackage.getDate(), filePackage.getFilename())));
		}
		if ((filePackage.getFilename() == null || filePackage.getDate() == null) && filePackage.getFile() != null) {
			filePackage.setFilename(filePackage.getFile().getName());
			filePackage.setDate(analysisDate(filePackage.getFile()));
		}
		if (filePackage.getFileLength() <= 0) {
			filePackage.setFileLength(filePackage.getFile().length());
		}
		filePackage.setUrl(createUrl(filePackage.getDate(), filePackage.getFilename()));
		return filePackage;
	}
	
	@Override
	public FilePackage fillingFilePackageAllAttributes(FilePackage filePackage) {
		filePackage = fillingFilePackageInfoAttributes(filePackage);
		if (filePackage.getFileLength() > 0) {
			filePackage.setFileBytes(readFileBytes(filePackage.getFile()));
		}
		if (filePackage.getFileBytes() != null && filePackage.getFileSha256() == null) {
			filePackage.setFileSha256(DigestUtils.sha256Hex(filePackage.getFileBytes()));
		}
		return filePackage;
	}
	
	@Override
	public boolean writeFileBytes(FilePackage filePackage) {
		if (filePackage == null || filePackage.getFile() == null || filePackage.getFileBytes() == null) {
			return false;
		}
		return writeFileBytes(filePackage.getFile(), filePackage.getFileBytes());
	}
	
	@Override
	public LinkedList<FilePackage> getAllFilePackageFromDrive() {
		LinkedList<FilePackage> filePackages = new LinkedList<FilePackage>();
		getAllFilePackageFromDrive(filePackages, getFileDriveFolder());
		for (FilePackage filePackage : filePackages) {
			fillingFilePackageInfoAttributes(filePackage);
		}
		return filePackages;
	}
	
	
	private final byte[] readFileBytes(File driveFile) {
		BufferedInputStream bufferedInputStream = null;
		try {
			bufferedInputStream = new BufferedInputStream(new FileInputStream(driveFile));
			byte[] fileBytes = new byte[new Long(driveFile.length()).intValue()];
			bufferedInputStream.read(fileBytes);
			return fileBytes;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedInputStream != null) {
				try {
					bufferedInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	private final boolean writeFileBytes(File driveFile, byte[] fileBytes) {
		BufferedOutputStream bufferedOutputStream = null;
		try {
			driveFile.getParentFile().mkdirs();
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(driveFile));
			bufferedOutputStream.write(fileBytes);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bufferedOutputStream != null) {
				try {
					bufferedOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	
	private final void getAllFilePackageFromDrive(List<FilePackage> filePackages, File folder) {
		File[] files = folder.listFiles();
		if (files == null) {
			return;
		}
		for (File file : files) {
			if (file.isFile()) {
				FilePackage filePackage = new FilePackage();
				filePackage.setFile(file);
				filePackages.add(filePackage);
			} else if (file.isDirectory()) {
				getAllFilePackageFromDrive(filePackages, file);
			}
		}
	}
	
	
	private final Date analysisDate(File file) {
		try {
			if (file == null) {
				return null;
			}
			String dateString = file.getAbsolutePath().
					replaceAll(getFileDriveFolder().getAbsolutePath() + File.separator, "").
					replaceAll(File.separator + file.getName(), "");
			return getDataFormat().parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private final String createDriveDateFilePath(Date date, String filename) {
		return getFileDriveFolder().getAbsolutePath() + File.separator + createDateFilePath(date, filename);
	}
	
	private final String createUrl(Date date, String filename) {
		return configuration.getFileServerRootPath() + File.separator + createDateFilePath(date, filename);
	}
	
	private final String createDateFilePath(Date date, String filename) {
		return getDataFormat().format(date) + File.separator + filename;
	}
	
	public final File getFileDriveFolder() {
		if (fileDriveFolder == null) {
			fileDriveFolder = new File(configuration.getFileDriveRootPath());
		}
		return fileDriveFolder;
	}
	
	public final DateFormat getDataFormat() {
		if (dataFormat == null) {
			dataFormat = new SimpleDateFormat(configuration.getDataFormat());
		}
		return dataFormat;
	}
}
