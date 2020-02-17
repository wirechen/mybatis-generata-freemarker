package com.jjshome.jjstool.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import org.apache.log4j.Logger;

import com.jjshome.jjstool.service.GenerateClassServiceImpl;

public class FileUtil {
	// LOG4J 记录日志
	private static Logger log = Logger.getLogger(GenerateClassServiceImpl.class);

	public static boolean createFile(String destFileName) {
		File file = new File(destFileName);
		/*
		 * if (file.exists()) { log.info("创建单个文件" + destFileName +
		 * "失败，目标文件已存在！"); return false; }
		 */
		if (destFileName.endsWith(File.separator)) {
			log.info("创建单个文件" + destFileName + "失败，目标不能是目录！");
			return false;
		}
		if (!file.getParentFile().exists()) {
			log.info("目标文件所在路径不存在，准备创建。。。");
			if (!file.getParentFile().mkdirs()) {
				log.info("创建目录文件所在的目录失败！");
				return false;
			}
		}
		// 创建目标文件
		try {
			if (file.createNewFile()) {
				log.info("创建单个文件" + destFileName + "成功！");
				return true;
			} else {
				log.info("创建单个文件" + destFileName + "失败！");
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.info("创建单个文件" + destFileName + "失败！");
			return false;
		}
	}

	public static boolean isFileExist(String filePath) {
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 文件已存在备份到其他目录下
	 * 
	 * @param soureFile
	 * @param bankPath
	 */
	public static void bankForFile(String soureFile) {
		if (!isFileExist(soureFile)) {
			return;
		}
		String fileName = soureFile.substring(soureFile.lastIndexOf("/") + 1);
		fileName = fileName + DateUtil.getCurrentTimeStr() + "_" + RandomUtil.getFixLenthString(3);
		String bankPath = soureFile.replace(GenerateUtil.projectPath, GenerateUtil.projectBankPath);
		bankPath = bankPath.substring(0, bankPath.lastIndexOf("/") + 1);
		String bankFilePath = bankPath + fileName;
		createFile(bankFilePath);
		copyFile(soureFile, bankFilePath);
	}

	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
		}
	}

	// 正向递归删除路径下的文件
	public static void delFile(String path) {
		File file = new File(path);
		if (file.exists() && file.isFile()) {
			file.delete();
		} else if (file.exists() && file.isDirectory()) {
			File[] files = file.listFiles();
			if (files != null) {
				for (File f : files) {
					if (f != null) {
						delFile(f.getAbsolutePath());
					}
				}
			}
		}
	}

	// 逆向递归删除空目录
	public static void delEmptyPath(String path, String name) {
		File file = new File(path);
		if (file.exists() && file.isDirectory()) {
			File[] files = file.listFiles();
			if (files != null && files.length > 0 || name.equals(file.getName()))
				return;
			if (file.delete()) {
				delEmptyPath(file.getParent(), name);
			}
		}
	}

	public static void delDirectories(String path, String name) {
		File file = new File(path);
		bankForFile(path);
		delFile(file.getAbsolutePath());
		delEmptyPath(file.getParent(), name);
	}

	/**
	 * 创建文件夹
	 * 
	 * @param filePath
	 */
	public static void createNewFolder(String filePath) {
		File myFolderPath = new File(filePath);
		try {
			if (!myFolderPath.exists()) {
				myFolderPath.mkdirs();
			}
		} catch (Exception e) {
			System.out.println("新建目录操作出错");
			e.printStackTrace();
		}
	}

	public static void writeContent(String fileName, String content) {
		try {
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void deleteFile(String path) {
		File file = new File(path);
		if (!file.exists() || !file.isFile()) {
			return;
		}
		file.delete();
	}

	public static void removeLineFromFile(String file, String lineToRemove) {
		try {

			File inFile = new File(file);
			if (!inFile.isFile()) {
				System.out.println("Parameter is not an existing file");
				return;
			}

			File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
			BufferedReader br = new BufferedReader(new FileReader(file));
			PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (!line.trim().equals(lineToRemove)) {
					pw.println(line);
					pw.flush();
				}
			}
			pw.close();
			br.close();

			removeFile(file);
			copy(inFile.getAbsolutePath() + ".tmp", file);
			tempFile.delete();

			// if (!inFile.delete()) {
			// System.out.println("Could not delete file");
			// return;
			// }
			// // Rename the new file to the filename the original file had.
			// if (!tempFile.renameTo(inFile))
			// System.out.println("Could not rename file");

		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void removeBeanFromFile(String file, String beanId, String endTag) {
		try {
			File inFile = new File(file);
			if (!inFile.isFile()) {
				System.out.println("Parameter is not an existing file");
				return;
			}
			File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
			BufferedReader br = new BufferedReader(new FileReader(file));
			PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
			String line = null;
			boolean isWirte = true;
			while ((line = br.readLine()) != null) {
				if (line.trim().indexOf(beanId) > 0) {
					isWirte = false;
				}
				if (isWirte == false && line.trim().equals(endTag)) {
					isWirte = true;
					continue;
				}
				if (isWirte) {
					pw.println(line);
					pw.flush();
				}
			}
			pw.close();
			br.close();

			removeFile(file);
			copy(inFile.getAbsolutePath() + ".tmp", file);
			tempFile.delete();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void removeFile(String file) throws IOException {
		RandomAccessFile rf = new RandomAccessFile(file, "rw");
		FileChannel fc = rf.getChannel();
		fc.truncate(0);
		rf.close();
	}

	public static void copy(String path1, String path2) {
		try {
			FileInputStream fis = new FileInputStream(path1);
			int l = fis.available();
			byte[] c = new byte[l];
			fis.read(c);
			fis.close();
			int i = path2.lastIndexOf("\\") + 1;
			String path = path2.substring(0, i);
			File f = new File(path);
			f.mkdirs();//
			FileOutputStream fos = new FileOutputStream(path2);
			fos.write(c);
			fos.close();
		} catch (FileNotFoundException e) {
			System.out.println("文件复制失败");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readFileToString(String path) {
		StringBuffer str = new StringBuffer("");

		File file = new File(path);

		try {

			FileReader fr = new FileReader(file);

			int ch = 0;

			while ((ch = fr.read()) != -1)

			{
				str.append((char) ch);
			}

			fr.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("File reader出错");
		}
		return str.toString();
	}

	public static boolean isBeanExist(String beanid, String path) {
		File file = new File(path);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				line = reader.readLine();
				if (line != null && line.indexOf(beanid) != -1) {
					reader.close();
					return true;
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;

	}

	public static void main(String[] args) {
		boolean isExist = isBeanExist("id=\"wpUsersDAO\"", "E:\\workspace\\myfirstapp\\src\\main\\resources\\spring_conf\\spring-myfirstapp.xml");
		System.out.println(isExist);

		removeLineFromFile("E:\\workspace\\myfirstapp\\src\\main\\resources\\spring_conf\\spring-myfirstapp.xml", "</beans>");

		String result = readFileToString("E:\\workspace\\myfirstapp\\src\\main\\java\\com\\jjshome\\myfirstapp\\dao\\WpUsersConfig.xml");
		result = result + "\r\n</beans>";
		writeContent("E:\\workspace\\myfirstapp\\src\\main\\resources\\spring_conf\\spring-myfirstapp.xml", result);

	}

}
