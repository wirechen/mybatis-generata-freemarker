package com.jjshome.jjstool.service;

import java.util.List;

public interface IRemoveGenerateService 
{
	/**
	 * 批量清理生成
	 * @param tableList
	 */
	public void batchRemoveGenerate(List<String> tableList);
	
	/**
	 * 清理生成
	 * @param tableName
	 */
	public void removeGenerate(String tableName);
}
