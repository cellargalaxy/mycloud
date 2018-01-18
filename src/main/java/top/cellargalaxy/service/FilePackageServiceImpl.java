package top.cellargalaxy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.cellargalaxy.bean.FilePackage;
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
	public FilePackage createFilePackage(File file, Date date, String description) {
		File moveFile = moveFile(file, date);
		if (moveFile == null) {
			return null;
		}
		return new FilePackage(moveFile, date, description, createUrl(date, moveFile.getName()), createFileSizeString(moveFile.length()));
	}
	
	@Override
	public FilePackage createFilePackage(String filename, Date date, String description) {
		File moveFile = new File(createMoveFilePath(date, filename));
		return createFilePackage(moveFile, description);
	}
	
	@Override
	public FilePackage createFilePackage(File moveFile, String description) {
		Date date = analysisDate(moveFile);
		if (date == null) {
			return null;
		}
		return new FilePackage(moveFile, date, description, createUrl(date, moveFile.getName()), createFileSizeString(moveFile.length()));
	}
	
	@Override
	public void fillingAttributes(FilePackage filePackage) {
		if (filePackage == null) {
			return;
		}
		if (filePackage.getFile() != null) {
			filePackage.setFilename(filePackage.getFile().getName());
			filePackage.setDate(analysisDate(filePackage.getFile()));
		} else if (filePackage.getFilename() != null && filePackage.getFilename().length() > 0 && filePackage.getDate() != null) {
			filePackage.setFile(new File(createMoveFilePath(filePackage.getDate(), filePackage.getFilename())));
		}
		filePackage.setUrl(createUrl(filePackage.getDate(), filePackage.getFilename()));
		filePackage.setFileSize(createFileSizeString(filePackage.getFile().length()));
	}
	
	@Override
	public List<FilePackage> getAllFilePackageFromDrive() {
		List<FilePackage> filePackages = new LinkedList<FilePackage>();
		getAllFilePackageFromDrive(filePackages, getFileDriveFolder());
		return filePackages;
	}
	
	@Override
	public boolean readFileBytes(FilePackage filePackage) {
		if (filePackage == null) {
			return false;
		}
		File file = filePackage.getFile();
		if (file == null) {
			return false;
		}
		BufferedInputStream bufferedInputStream = null;
		try {
			bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
			byte[] fileBytes = new byte[new Long(file.length()).intValue()];
			bufferedInputStream.read(fileBytes);
			filePackage.setFilebytes(fileBytes);
			return true;
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
		return false;
	}
	
	@Override
	public boolean writeFileBytes(FilePackage filePackage) {
		if (filePackage == null) {
			return false;
		}
		File file = filePackage.getFile();
		if (file == null) {
			return false;
		}
		BufferedOutputStream bufferedOutputStream = null;
		try {
			file.getParentFile().mkdirs();
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
			bufferedOutputStream.write(filePackage.getFilebytes());
			bufferedOutputStream.flush();
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
	
	private void getAllFilePackageFromDrive(List<FilePackage> filePackages, File foler) {
		File[] files = foler.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				filePackages.add(createFilePackage(file, null));
			} else if (file.isDirectory()) {
				getAllFilePackageFromDrive(filePackages, file);
			}
		}
	}
	
	private Date analysisDate(File file) {
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
	
	private File moveFile(File file, Date date) {
		try {
			File moveFile = new File(createMoveFilePath(date, file.getName()));
			moveFile.getParentFile().mkdirs();
			if ((!moveFile.exists() || moveFile.delete()) && file.renameTo(moveFile)) {
				return moveFile;
			}
		} finally {
			file.delete();
		}
		return null;
	}
	
	private String createMoveFilePath(Date date, String filename) {
		return getFileDriveFolder().getAbsolutePath() + File.separator + createDateFilePath(date, filename);
	}
	
	private String createUrl(Date date, String filename) {
		return configuration.getFileServerRootPath() + File.separator + createDateFilePath(date, filename);
	}
	
	private String createDateFilePath(Date date, String filename) {
		return getDataFormat().format(date) + File.separator + filename;
	}
	
	private String createFileSizeString(long size) {
		float f = size;
		if (f < 1024) {
			return String.format("%.2f B", f);
		}
		f = f / 1024;
		if (f < 1024) {
			return String.format("%.2f KB", f);
		}
		f = f / 1024;
		if (f < 1024) {
			return String.format("%.2f MB", f);
		}
		f = f / 1024;
		return String.format("%.2f GB", f);
	}
	
	public File getFileDriveFolder() {
		if (fileDriveFolder == null) {
			fileDriveFolder = new File(configuration.getFileDriveRootPath());
			fileDriveFolder.mkdirs();
		}
		return fileDriveFolder;
	}
	
	public DateFormat getDataFormat() {
		if (dataFormat == null) {
			dataFormat = new SimpleDateFormat(configuration.getDataFormat());
		}
		return dataFormat;
	}
}
