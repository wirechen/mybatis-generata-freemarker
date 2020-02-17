package com.jjshome.jjstool.service;

import com.jjshome.jjstool.dao.IGenerateCodeDAO;
import com.jjshome.jjstool.entity.ColumnEntity;
import com.jjshome.jjstool.entity.TableEntity;
import com.jjshome.jjstool.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("codeService")
public class GenerateClassServiceImpl implements IGenerateClassService {
	@Autowired
	private IGenerateCodeDAO codeDAO;
	@Value("${jdbc.database}")
	private String tableSchema;

	public void generate(String tableName, String tableSchema) {
		try {
			// 查询表实体
			TableEntity tableEntity = this.codeDAO.selectTableByName(tableName, tableSchema);
			// 查询表的所有列
			List<ColumnEntity> columnList = this.codeDAO.selectColumnByTable(tableName, tableSchema);
			if (tableEntity != null) {
				tableEntity.setColumnList(columnList);
			}
			if (GenerateUtil.TRUE_STR.equals(GenerateUtil.isGenrateDao)) {
				generateEntity(tableEntity, columnList);
				generateDao(tableEntity, columnList);
				generateDaoXml(tableEntity, columnList);
			}
			if (GenerateUtil.TRUE_STR.equals(GenerateUtil.isGenrateService)) {
				generateService(tableEntity, columnList);
			}
			if (GenerateUtil.TRUE_STR.equals(GenerateUtil.isGenrateController)) {
				generateController(tableEntity, columnList);
			}
			if (GenerateUtil.TRUE_STR.equals(GenerateUtil.isGenratePages)) {
				generatePages(tableEntity, columnList);
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 生成service
	 *
	 * @param tableEntity
	 * @param columnList
	 */
	private static void generateService(TableEntity tableEntity, List<ColumnEntity> columnList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table", tableEntity);
		map.put("packagePrefix", GenerateUtil.packagePrefix);
		if (StringUtil.isNotEmpty(GenerateUtil.module)) {
			map.put("module", "." + GenerateUtil.module);
		} else {
			map.put("module", "");
		}
		String[] pkParam = GenrateColumnUtil.getPKPram(columnList);
		map.put("pkParam", pkParam[0]);
		map.put("pkParamOnly", pkParam[1]);
		map.put("containsClassName", GenerateUtil.methodNameContainsClassName);
		String path = GenerateUtil.projectPath + GenerateUtil.javaPath + GenerateUtil.packagePath + "/service/";
		if (StringUtil.isNotEmpty(GenerateUtil.module)) {
			path = path + GenerateUtil.module + "/";
		}
		FileUtil.createNewFolder(path);
		FreemarkerUtils.createFile("Service.ftl", map, path + "I" + tableEntity.getClassName() + "Service.java");
		path = path + "/impl/";
		FileUtil.createNewFolder(path);
		FreemarkerUtils.createFile("ServiceImpl.ftl", map, path + tableEntity.getClassName() + "ServiceImpl.java");
	}

	/**
	 * 生成controller
	 *
	 * @param tableEntity
	 * @param columnList
	 */
	private static void generateController(TableEntity tableEntity, List<ColumnEntity> columnList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("projectName", GenerateUtil.projectName);
		map.put("table", tableEntity);
		map.put("packagePrefix", GenerateUtil.packagePrefix);
		if (StringUtil.isNotEmpty(GenerateUtil.module)) {
			map.put("module", "." + GenerateUtil.module);
			map.put("modulePath", "/" + GenerateUtil.module);
		} else {
			map.put("module", "");
			map.put("modulePath", "");
		}
		String[] pkParam = GenrateColumnUtil.getPKPram(columnList);
		map.put("pkParam", pkParam[0]);
		map.put("pkParamOnly", pkParam[1]);
		String path = GenerateUtil.projectPath + GenerateUtil.javaPath + GenerateUtil.packagePath + "/web/controller/";
		if (StringUtil.isNotEmpty(GenerateUtil.module)) {
			path = path + GenerateUtil.module + "/";
		}
		FileUtil.createNewFolder(path);
		FreemarkerUtils.createFile("Controller.ftl", map, path + tableEntity.getClassName() + "Controller.java");
	}

	/**
	 * 生成页面
	 *
	 * @param tableEntity
	 * @param columnList
	 */
	private static void generatePages(TableEntity tableEntity, List<ColumnEntity> columnList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table", tableEntity);
		map.put("packagePrefix", GenerateUtil.packagePrefix);
		String pkLink = getPkLink(columnList);
		map.put("pkLink", pkLink);
		map.put("pageSuffix", GenerateUtil.FILE_SUFFIX);
		String fold = GenerateUtil.toLowerCaseFirstOne(tableEntity.getClassName());
		String path = GenerateUtil.projectPath + GenerateUtil.pagesPath + "/";
		if (StringUtil.isNotEmpty(GenerateUtil.module)) {
			path = path + GenerateUtil.module + "/";
			FileUtil.createNewFolder(path);
		}
		path = path + fold + "/";
		FileUtil.createNewFolder(path);
		FreemarkerUtils.createFile("Page_of_form.ftl", map, path + GenerateUtil.getFileName(tableEntity.getVarName(), GenerateUtil.FORM_FILE));
		FreemarkerUtils.createFile("Page_of_insert.ftl", map, path + GenerateUtil.getFileName(tableEntity.getVarName(), GenerateUtil.INSERT_FILE));
		FreemarkerUtils.createFile("Page_of_list.ftl", map, path + GenerateUtil.getFileName(tableEntity.getVarName(), GenerateUtil.LIST_FILE));
		FreemarkerUtils.createFile("Page_of_update.ftl", map, path + GenerateUtil.getFileName(tableEntity.getVarName(), GenerateUtil.UPDATE_FILE));
		FreemarkerUtils.createFile("Page_of_show.ftl", map, path + GenerateUtil.getFileName(tableEntity.getVarName(), GenerateUtil.SHOW_FILE));
	}

	/**
	 * 生成实体对象
	 *
	 * @param tableEntity
	 * @param columnList
	 */
	private static void generateEntity(TableEntity tableEntity, List<ColumnEntity> columnList) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("package", GenerateUtil.getPackagePath("entity"));
			map.put("hasDate", GenerateUtil.isWithDate(columnList));
			map.put("hasDouble", GenerateUtil.isWithDecimal(columnList));
			map.put("className", tableEntity.getClassName());
			map.put("columnList", columnList);
			// 生成DAO接口
			String path = GenerateUtil.projectPath + GenerateUtil.javaPath + GenerateUtil.packagePath + "/entity/";
			if (StringUtil.isNotEmpty(GenerateUtil.module)) {
				path = path + GenerateUtil.module + "/";
				FileUtil.createNewFolder(path);
			}
			FreemarkerUtils.createFile("Entity.ftl", map, path + tableEntity.getClassName() + ".java");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getPkLink(List<ColumnEntity> columnList) {
		StringBuffer sb = new StringBuffer();
		List<ColumnEntity> list = GenrateColumnUtil.getPkColumnList(columnList);
		for (int i = 0; i < list.size(); i++) {
			ColumnEntity a = columnList.get(i);
			if (i > 0) {
				sb.append("&&");
			}
			if (a.getIsDateField()) {
				sb.append(a.getFieldName() + "=${(row." + a.getFieldName() + "?date)!}");
			} else {
				sb.append(a.getFieldName() + "=${(row." + a.getFieldName() + ")!}");
			}
		}
		return sb.toString();
	}

	/**
	 * 生成DAO
	 *
	 * @param tableEntity
	 * @param columnList
	 */
	public static void generateDao(TableEntity tableEntity, List<ColumnEntity> columnList) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table", tableEntity);
		map.put("packagePrefix", GenerateUtil.packagePrefix);
		if (StringUtil.isNotEmpty(GenerateUtil.module)) {
			map.put("module", "." + GenerateUtil.module);
		} else {
			map.put("module", "");
		}
		String[] pkParam = GenrateColumnUtil.getPKPram(columnList);
		map.put("pkParam", pkParam[0]);
		map.put("containsClassName", GenerateUtil.methodNameContainsClassName);

		// 生成DAO接口
		String path = GenerateUtil.projectPath + GenerateUtil.javaPath + GenerateUtil.packagePath + "/dao/";
		if (StringUtil.isNotEmpty(GenerateUtil.module)) {
			path = path + GenerateUtil.module + "/";
			FileUtil.createNewFolder(path);
		}
		FreemarkerUtils.createFile("Dao.ftl", map, path + "I" + tableEntity.getClassName() + "DAO.java");
	}

	public static void generateDaoXml(TableEntity tableEntity, List<ColumnEntity> columnList) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("namespace", GenerateUtil.getPackagePath("dao") + ".I" + tableEntity.getClassName() + "DAO");
			map.put("className", tableEntity.getClassName());
			map.put("columnList", columnList);
			boolean pkAutoField = false;
			List<ColumnEntity> autoList = GenrateColumnUtil.getAutoColumnList(columnList);
			List<ColumnEntity> pkAutoColumnList = GenrateColumnUtil.getPkColumn(autoList);
			if (pkAutoColumnList != null && pkAutoColumnList.size() > 0) {
				pkAutoField = true;
			}
			map.put("pkAutoField", pkAutoField);
			List<ColumnEntity> columnNoAutoList = RemoveGenerateServiceImpl.removeAutoEntity(autoList, columnList);
			map.put("columnNoAutoList", columnNoAutoList);
			List<ColumnEntity> pkColumnList = GenrateColumnUtil.getPkColumn(columnList);
			map.put("keyJavaType", GenrateColumnUtil.getJavaTypeByDataType(pkColumnList.get(0).getDataType()));
			map.put("pkColumn", pkColumnList.get(0));
			map.put("tableName", tableEntity.getTableName());
			String[] pkParam = GenrateColumnUtil.getPKPram(columnList);
			map.put("pkParam", pkParam[0]);
			map.put("containsClassName", GenerateUtil.methodNameContainsClassName);
			map.put("packageClassName", GenerateUtil.getPackagePath("entity") + "." + tableEntity.getClassName());
			// 数据访问层的配置文件
			String path = GenerateUtil.projectPath + GenerateUtil.javaPath + GenerateUtil.packagePath + "/dao/";
			if (StringUtil.isNotEmpty(GenerateUtil.module)) {
				path = path + GenerateUtil.module + "/mapper/";
			} else {
				path = path + "mapper/";
			}
			FileUtil.createNewFolder(path);
			FreemarkerUtils.createFile("DaoXml.ftl", map, path + "I" + tableEntity.getClassName() + "DAO.xml");
			FileUtil.createNewFolder(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<TableEntity> getTableList(String tableSchema) {
		return codeDAO.selectTableListBySchema(tableSchema);
	}

	public void batchGenerate(List<String> tableList, String tableSchema) {
		if (StringUtil.isEmpty(tableSchema)) {
			System.out.println("数据库名不能为空");
			return;
		}
		if (tableList == null || tableList.size() == 0) {
			System.out.println("表列表不能为空");
			return;
		}
		for (String table : tableList) {
			if (StringUtil.isEmpty(table)) {
				continue;
			}
			generate(table.trim(), tableSchema);
		}
	}
}
