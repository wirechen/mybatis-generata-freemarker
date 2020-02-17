package com.jjshome.jjstool;

import java.io.IOException;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jjshome.jjstool.entity.TableEntity;
import com.jjshome.jjstool.service.IGenerateClassService;
import com.jjshome.jjstool.util.FileUtil;
import com.jjshome.jjstool.util.GenerateUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
public class GenerateCodeServiceTest {
	@Autowired
	private IGenerateClassService codeService;
	
	@Value("${jdbc.database}")
	private String tableSchema;
	
	@Test
	public void generateCode() throws IOException {
		List<String> tableList = GenerateUtil.getTables();
		codeService.batchGenerate(tableList, tableSchema);
		Runtime.getRuntime().exec(
				"cmd.exe /c start " + GenerateUtil.projectPath);
	}

	
	/**
	 * 获取所有表
	 * @throws IOException
	 */
	@Test
	public void testGetAllTable() throws IOException {
		List<TableEntity> list = codeService.getTableList(GenerateUtil
				.getDataBase());
		StringBuffer sb = new StringBuffer();
		for (TableEntity table : list) {
			sb.append(table.getTableName()).append("\r\n");
			System.out.println(table.toString());
		}
		
		FileUtil.createFile(GenerateUtil.exportAllTablesSaveFile);
		FileUtil.writeContent(GenerateUtil.exportAllTablesSaveFile, sb.toString());
		Runtime.getRuntime().exec(
				"cmd.exe /c start " + GenerateUtil.exportAllTablesSaveFile);
	}

}
