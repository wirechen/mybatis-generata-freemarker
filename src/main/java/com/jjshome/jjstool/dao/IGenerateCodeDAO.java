package com.jjshome.jjstool.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jjshome.jjstool.entity.ColumnEntity;
import com.jjshome.jjstool.entity.TableEntity;

public interface IGenerateCodeDAO {
	
	/**
	 * @Title: selectTableByName
	 * @Description: 查询表信息根据表名和表概要
	 * @param tableName
	 * @param tableSchema
	 * @return TableEntity
	 * @throws
	 */
	public TableEntity selectTableByName(@Param("tableName") String tableName,
			@Param("tableSchema") String tableSchema);

	/**
	 * 查询表列表
	 * @param tableSchema
	 * @return
	 */
	public List<TableEntity> selectTableListBySchema(
			@Param("tableSchema") String tableSchema);

	/**
	 * @Title: selectColumnByTable
	 * @Description: 根据表明和数据库 查询所有字段
	 * @param @param tableName
	 * @param @param tableSchema
	 * @return List<ColumnEntity> 返回类型
	 * @throws
	 */
	public List<ColumnEntity> selectColumnByTable(
			@Param("tableName") String tableName,
			@Param("tableSchema") String tableSchema);
}
