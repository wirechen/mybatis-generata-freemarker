package com.jjshome.jjstool;

import com.jjshome.jjstool.service.IGenerateClassService;
import com.jjshome.jjstool.util.GenerateUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

public class GenrateMainClass {
	public static ApplicationContext context;

	public static void main(String[] args) throws IOException {
		context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		IGenerateClassService codeService = (IGenerateClassService) context.getBean("codeService");
		String dataBase = GenerateUtil.getDataBase();
		List<String> tableList = GenerateUtil.getTables();
		codeService.batchGenerate(tableList, dataBase);
		Runtime.getRuntime().exec("cmd.exe /c start " + GenerateUtil.projectPath);
	}
}
