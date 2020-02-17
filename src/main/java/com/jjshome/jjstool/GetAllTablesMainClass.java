package com.jjshome.jjstool;

import java.io.IOException;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.jjshome.jjstool.entity.TableEntity;
import com.jjshome.jjstool.service.IGenerateClassService;
import com.jjshome.jjstool.util.FileUtil;
import com.jjshome.jjstool.util.GenerateUtil;

public class GetAllTablesMainClass {
	private static ApplicationContext context;
	
	public static void main(String[] args) throws IOException {
		context = new ClassPathXmlApplicationContext(
				"classpath:applicationContext.xml");

		IGenerateClassService codeService = (IGenerateClassService) context
				.getBean("codeService");
		
		List<TableEntity> list = codeService.getTableList(GenerateUtil
				.getDataBase());
		StringBuffer sb = new StringBuffer();
		for (TableEntity table : list) {
			sb.append(table.getTableName()).append("\r\n");
			System.out.println(table.toString());
		}
		FileUtil.deleteFile(GenerateUtil.exportAllTablesSaveFile);
		FileUtil.createFile(GenerateUtil.exportAllTablesSaveFile);
		FileUtil.writeContent(GenerateUtil.exportAllTablesSaveFile, sb.toString());
		Runtime.getRuntime().exec(
				"cmd.exe /c start " + GenerateUtil.exportAllTablesSaveFile);

	}
}
