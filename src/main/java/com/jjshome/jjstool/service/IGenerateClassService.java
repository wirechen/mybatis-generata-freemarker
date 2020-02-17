package com.jjshome.jjstool.service;

import java.util.List;

import com.jjshome.jjstool.entity.TableEntity;


public interface IGenerateClassService 
{
	/**
	 * @param packagePath TODO
	* @Title: generateClass
	* @Description: 生成java类
	* @param tableName 表名
	* @param tableSchema 
	* @return void
	* @throws
	 */
	public void generate(String tableName, String tableSchema);
	
	
	/**
	 * 批量生成
	 * @param tableList
	 * @param tableSchema
	 */
	void batchGenerate(List<String> tableList, String tableSchema);
	
	/**
	 * 获取table列表
	 * @param tableSchema
	 * @return
	 */
	List<TableEntity> getTableList(String tableSchema);
	
}
