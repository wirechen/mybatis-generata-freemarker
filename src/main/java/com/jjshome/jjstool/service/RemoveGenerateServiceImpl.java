package com.jjshome.jjstool.service;

import com.jjshome.jjstool.entity.ColumnEntity;
import com.jjshome.jjstool.entity.TableEntity;
import com.jjshome.jjstool.util.FileUtil;
import com.jjshome.jjstool.util.GenerateUtil;
import com.jjshome.jjstool.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("removeGenerateService")
public class RemoveGenerateServiceImpl implements IRemoveGenerateService {

	@Override
	public void batchRemoveGenerate(List<String> tableList) {
		if (tableList == null || tableList.size() == 0) {
			System.out.println("表列表不能为空");
			return;
		}
		for (String table : tableList) {
			if (StringUtil.isEmpty(table)) {
				continue;
			}
			removeGenerate(table.trim());
		}
	}

	@Override
	public void removeGenerate(String tableName) {
		TableEntity tableEntity = new TableEntity();
		tableEntity.setTableName(tableName);
		if (GenerateUtil.TRUE_STR.equals(GenerateUtil.isRemoveDao)) {
			removeEntity(tableEntity);
			removeDao(tableEntity);
			removeDaoXml(tableEntity);
		}
		if (GenerateUtil.TRUE_STR.equals(GenerateUtil.isRemoveService)) {
			removeService(tableEntity);
		}
		if (GenerateUtil.TRUE_STR.equals(GenerateUtil.isRemoveController)) {
			removeController(tableEntity);
		}
		if (GenerateUtil.TRUE_STR.equals(GenerateUtil.isRemovePages)) {
			removePages(tableEntity);
		}
	}

	private void removeEntity(TableEntity tableEntity) {
		String path = GenerateUtil.projectPath + GenerateUtil.javaPath + GenerateUtil.packagePath + "/entity/";
		if (StringUtil.isNotEmpty(GenerateUtil.module)) {
			path = path + GenerateUtil.module + "/";
		}
		String writeFilePath = path + tableEntity.getClassName() + ".java";
		FileUtil.delDirectories(writeFilePath, "entity");
	}

	private void removeDao(TableEntity tableEntity) {
		// 生成DAO接口
		String path = GenerateUtil.projectPath + GenerateUtil.javaPath + GenerateUtil.packagePath + "/dao/";
		if (StringUtil.isNotEmpty(GenerateUtil.module)) {
			path = path + GenerateUtil.module + "/";
		}
		String filePath = path + tableEntity.getClassName() + "DAO.java";
		FileUtil.delDirectories(filePath, "dao");

		String configFilePath = GenerateUtil.daoConfigPath;
		//FileUtil.bankForFile(configFilePath);
		String beanId = StringUtil.toLowerForFirstChar(tableEntity.getClassName()) + "DAO";
		// 配置是否已经存在
		boolean isExsit = FileUtil.isBeanExist(beanId, configFilePath);
		if (isExsit) {
			FileUtil.removeBeanFromFile(configFilePath, beanId, "</bean>");
		}
	}

	private void removeDaoXml(TableEntity tableEntity) {
		// 数据访问层的配置文件
		String path = GenerateUtil.projectPath + GenerateUtil.javaPath + GenerateUtil.packagePath + "/dao/";
		if (StringUtil.isNotEmpty(GenerateUtil.module)) {
			path = path + GenerateUtil.module + "/xml/";
		} else {
			path = path + "xml/";
		}
		String writeFilePath = path + tableEntity.getClassName() + "DAO.xml";
		FileUtil.delDirectories(writeFilePath, "dao");
	}

	private void removeService(TableEntity tableEntity) {
		String path = GenerateUtil.projectPath + GenerateUtil.javaPath + GenerateUtil.packagePath + "/service/";
		if (StringUtil.isNotEmpty(GenerateUtil.module)) {
			path = path + GenerateUtil.module + "/";
		}
		FileUtil.delDirectories(path + tableEntity.getClassName() + "Service.java", "service");
		path = path + "/impl/";
		FileUtil.delDirectories(path + tableEntity.getClassName() + "ServiceImpl.java", "service");
	}

	private void removeController(TableEntity tableEntity) {
		String path = GenerateUtil.projectPath + GenerateUtil.javaPath + GenerateUtil.packagePath + "/web/controller/";
		if (StringUtil.isNotEmpty(GenerateUtil.module)) {
			path = path + GenerateUtil.module + "/";
		}
		FileUtil.delDirectories(path + tableEntity.getClassName() + "Controller.java", "controller");
	}

	private void removePages(TableEntity tableEntity) {
		String fold = GenerateUtil.toLowerCaseFirstOne(tableEntity.getClassName());
		String path = GenerateUtil.projectPath + GenerateUtil.pagesPath + "/";

		if (StringUtil.isNotEmpty(GenerateUtil.module)) {
			path = path + GenerateUtil.module + "/";
		}

		path = path + fold + "/";

		FileUtil.delDirectories(path + GenerateUtil.getFileName(tableEntity.getVarName(), GenerateUtil.INSERT_FILE), "view");
		FileUtil.delDirectories(path + GenerateUtil.getFileName(tableEntity.getVarName(), GenerateUtil.UPDATE_FILE), "view");
		FileUtil.delDirectories(path + GenerateUtil.getFileName(tableEntity.getVarName(), GenerateUtil.LIST_FILE), "view");
		FileUtil.delDirectories(path + GenerateUtil.getFileName(tableEntity.getVarName(), GenerateUtil.FORM_FILE), "view");
		FileUtil.delDirectories(path + GenerateUtil.getFileName(tableEntity.getVarName(), GenerateUtil.SHOW_FILE), "view");
	}

	public static List<ColumnEntity> removeAutoEntity(List<ColumnEntity> autoList, List<ColumnEntity> columnList) {
		List<ColumnEntity> resultList = new ArrayList<ColumnEntity>();
		if (autoList == null || autoList.size() == 0) {
			return columnList;
		}
		for (ColumnEntity a : autoList) {
			for (ColumnEntity c : columnList) {
				if (a.getColumnName().equals(c.getColumnName())) {
					continue;
				}
				resultList.add(c);
			}
		}
		return resultList;
	}
}
