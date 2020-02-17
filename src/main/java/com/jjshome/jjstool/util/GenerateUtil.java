package com.jjshome.jjstool.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import com.jjshome.jjstool.entity.ColumnEntity;
public class GenerateUtil {
	//项目名称
	public static String projectName;
	// 项目路径
	public static String projectPath = "";
	// 包前缀
	public static String packagePrefix = "";
	// 包路径
	public static String packagePath = "";
	// 模块名称
	public static String module = "";
	// 要去除的表名前缀
	public static String removeTablePrefix = "";
	// DAO配置文件名
	public static String daoConfigFileName = "";
	// JAVA文件路径
	public static String javaPath = "/src/main/java";
	// 配置文件路径
	public static String resoucePath = "/src/main/resources/spring_conf";
	// 页面路径
	public static String pagesPath = "/src/main/webapp/WEB-INF/view";
	//导出数据库全部表名至文件的路径
	public static String exportAllTablesSaveFile = "D:/tool/alltables.txt";
	
	public static String dao;
	public static String service;
	public static String controller;
	public static String pages;
	public static String methodNameContainsClassName;
	
	public static String isGenrateDao;
	public static String isGenrateService;
	public static String isGenrateController;
	public static String isGenratePages;
	
	public static String isRemoveDao;
	public static String isRemoveService;
	public static String isRemoveController;
	public static String isRemovePages;
	
	public static String  TRUE_STR = "true";
	public static String projectBankPath="";
	
	public static final String SPACER = "_";
    public static final String FILE_SUFFIX=".html";
    public static final String FORM_FILE = "Form";
    public static final String INSERT_FILE = "Insert";
    public static final String UPDATE_FILE = "Update";
    public static final String LIST_FILE = "List";
    public static final String SHOW_FILE = "Show";
    

	public static String getViewName(String varName, String type) {
    	return varName + SPACER + type;
	}
	
    public static String getFileName(String varName, String type){
    	return getViewName(varName,type) + FILE_SUFFIX;
    }
    

	static {
		Properties prop = new Properties();
		InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("taget_project.properties");
		try {
			prop.load(in);
			projectName = prop.getProperty("projectName").trim();
			projectPath = prop.getProperty("projectPath").trim();
			packagePrefix = prop.getProperty("packagePrefix").trim();
			daoConfigFileName = getDaoConfigFileName(projectPath);
			packagePath = getPackagePaths(packagePrefix);
			methodNameContainsClassName = prop.getProperty("methodNameContainsClassName").trim();
			dao = prop.getProperty("dao").trim();
			service = prop.getProperty("service").trim();
			controller = prop.getProperty("controller").trim();
			pages = prop.getProperty("pages").trim();
			adapterConfig();
			module = prop.getProperty("module").trim();
			projectBankPath = getProjectBankPath(projectPath);
			removeTablePrefix = prop.getProperty("removeTablePrefix").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private  static void adapterConfig(){
		if(TRUE_STR.equals(pages)){
			isGenratePages = TRUE_STR;
			isGenrateController = TRUE_STR;
			isGenrateService = TRUE_STR;
			isGenrateDao = TRUE_STR;
			
			isRemovePages = TRUE_STR;
		}
		
		if(TRUE_STR.equals(controller)){
			isGenrateController = TRUE_STR;
			isGenrateService = TRUE_STR;
			isGenrateDao = TRUE_STR;
			
			isRemovePages = TRUE_STR;
			isRemoveController = TRUE_STR;
		}
		
		if(TRUE_STR.equals(service)){
			isGenrateService = TRUE_STR;
			isGenrateDao = TRUE_STR;
			
			isRemovePages = TRUE_STR;
			isRemoveController = TRUE_STR;
			isRemoveService = TRUE_STR;
		}
		
		if(TRUE_STR.equals(dao)){
			isGenrateDao = TRUE_STR;
			
			isRemovePages = TRUE_STR;
			isRemoveController = TRUE_STR;
			isRemoveService = TRUE_STR;
			isRemoveDao = TRUE_STR;
		}
		
	}
	

	public static String daoConfigPath = projectPath + resoucePath
			+ daoConfigFileName;
	

	private static String getDaoConfigFileName(String projectPath) {
		if (StringUtil.isEmpty(projectPath)) {
			return projectPath;
		}
		int len = projectPath.lastIndexOf("/") >= 0 ? projectPath
				.lastIndexOf("/") : projectPath.lastIndexOf("\\");
		return "/spring-" + projectPath.substring(len + 1) + ".xml";
	}

	private static String getPackagePaths(String packagePrefix) {
		if (StringUtil.isEmpty(packagePrefix)) {
			return packagePrefix;
		}
		return "/" + packagePrefix.replace(".", "/");
	}
	private static String getProjectBankPath(String projectPath){
		if(StringUtil.isEmpty(projectPath)){
			return projectPath;
		}
		return projectPath + "_bakForYou";
	}
	

	public static void main(String[] args) throws Exception {
		System.out.println(GenerateUtil.packagePrefix);
		System.out.println(GenerateUtil.projectPath);
		System.out.println(GenerateUtil.daoConfigFileName);
		System.out.println(GenerateUtil.packagePath);
		System.out.println(getTables());
	}

	// 首字母转大写
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toUpperCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}

	// 首字母转小写
	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder())
					.append(Character.toLowerCase(s.charAt(0)))
					.append(s.substring(1)).toString();
	}

	// 判断对象是否包含Date类型的字段 有则需要导入包
	public static boolean isWithDate(List<ColumnEntity> columnList) {
		for (ColumnEntity columnEntity : columnList) {
			if (columnEntity.getDataType().toLowerCase().equals("timestamp")
					|| columnEntity.getDataType().toLowerCase().equals("date")
					|| columnEntity.getDataType().toLowerCase()
							.equals("datetime")) {
				return true;
			}
		}
		return false;
	}

	// 判断对象是否包含Date类型的字段 有则需要导入包
	public static boolean isWithDecimal(List<ColumnEntity> columnList) {
		for (ColumnEntity columnEntity : columnList) {
			if (columnEntity.getDataType().toLowerCase().equals("decimal")) {
				return true;
			}
		}
		return false;
	}

	public static String getFileTypeByDataType(String fileDataType) {
		fileDataType = fileDataType.toLowerCase();
		if (fileDataType.equals("varchar")) {
			return "String";
		} else if (fileDataType.equals("timestamp")
				|| fileDataType.equals("datetime")
				|| fileDataType.equals("date")) {
			return "Date";
		} else if (fileDataType.indexOf("bigint") >= 0) {
			return "Long";
		} else if (fileDataType.indexOf("int") >= 0
				|| fileDataType.indexOf("integer") >= 0) {
			return "Integer";
		} else if (fileDataType.indexOf("float") >= 0) {
			return "Float";
		} else if (fileDataType.indexOf("double") >= 0) {
			return "Double";
		} else if (fileDataType.indexOf("decimal") >= 0) {
			return "BigDecimal";
		} else {
			return "String";
		}
	}

	public static String getPackagePath(String level) {
		if (StringUtil.isEmpty(module)) {
			return packagePrefix + "." + level;
		}
		return packagePrefix + "." + level + "." + module;
	}

	public static List<String> getTables() throws IOException {
		List<String> tableList = new ArrayList<String>();
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("tables_config.properties");
		Scanner scanner = new Scanner(in);
		while(scanner.hasNext()) {
			tableList.add(scanner.nextLine());
		}
		return tableList;
	}

	public static String getDataBase() {
		Properties prop = new Properties();
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("jdbc_conf.properties");
		String dataBase = "";
		try {
			prop.load(in);
			dataBase = prop.getProperty("jdbc.database").trim();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataBase;
	}
}
