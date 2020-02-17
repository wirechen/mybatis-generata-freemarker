package com.jjshome.jjstool;

import java.io.IOException;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.jjshome.jjstool.service.IRemoveGenerateService;
import com.jjshome.jjstool.util.GenerateUtil;

public class RemoveMainClass {

	private static ApplicationContext context;
	
	public static void main(String[] args) throws IOException {
		context = new ClassPathXmlApplicationContext(
				"classpath:applicationContext.xml");
		IRemoveGenerateService removeGenerateService = (IRemoveGenerateService) context
				.getBean("removeGenerateService");
		List<String> tableList = GenerateUtil.getTables();
		removeGenerateService.batchRemoveGenerate(tableList);
		Runtime.getRuntime().exec(
				"cmd.exe /c start " + GenerateUtil.projectPath);

	}
}
