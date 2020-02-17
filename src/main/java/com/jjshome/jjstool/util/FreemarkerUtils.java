package com.jjshome.jjstool.util;

import freemarker.template.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FreemarkerUtils {

	private static Configuration cfg = null;

	/**
	 * 获取freemarker的配置 freemarker本身支持classpath,目录和从ServletContext获取.
	 * @return 返回Configuration对象
	 * @throws IOException 
	 */
	private static Configuration getConfiguration() throws IOException {
		if (null == cfg) {
			cfg = new Configuration();
			//2.设置模板文件目录
			//（1）src目录下的目录（template在src下）
			cfg.setDirectoryForTemplateLoading(new File("src/templates"));
			//（2）完整路径（template在src下）
			//cfg.setDirectoryForTemplateLoading(new File(
			//      "D:/cpic-env/workspace/javaFreemarker/src/template"));
			//cfg.setDirectoryForTemplateLoading(new File("src/template"));
			//（3）工程目录下的目录（template/main在工程下）--推荐
			// cfg.setDirectoryForTemplateLoading(new File("template/main"));
			// setEncoding这个方法一定要设置国家及其编码，不然在flt中的中文在生成html后会变成乱码
			cfg.setEncoding(Locale.getDefault(), "UTF-8");
			// 设置对象的包装器
			cfg.setObjectWrapper(new DefaultObjectWrapper());
			// 设置异常处理器//这样的话就可以${a.b.c.d}即使没有属性也不会出错
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		}

		return cfg;
	}

	/**
	 * 
	 * @param ftl 模板文件名,相对上面的模版根目录templates路径,例如/module/view.ftl templates/module/view.ftl
	 * @param data 填充数据
	 * @param targetFile 要生成的静态文件的路径,相对设置中的根路径,例如 "jsp/user/1.html"
	 * @return
	 */
	public static boolean createFile(String ftl, Map<String, Object> data, String targetFile) {

		String fileName = targetFile.substring(targetFile.lastIndexOf("/") + 1);
		String path = targetFile.replace(fileName, "");
		FileUtil.createNewFolder(path);
		try {
			// 创建Template对象
			Configuration cfg = FreemarkerUtils.getConfiguration();
			Template template = cfg.getTemplate(ftl);
			template.setEncoding("UTF-8");
			//生成静态页面
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile), "UTF-8"));
			template.process(data, out);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (TemplateException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		Map map = new HashMap();
		map.put("image", "ooo");// 背景图片
		map.put("categorylist", new ArrayList()); // 子项目list
		map.put("size", 0); // 子项目size
		map.put("url", "phone!categorySearch?id="); // 子项目url链接
		FreemarkerUtils.createFile("view.ftl", map, "D://test.java");
	}
}