package com.jjshome.jjstool.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jjshome.jjstool.util.GenerateUtil;
import com.jjshome.jjstool.util.StringUtil;

/**
 * @ClassName:TableEntity
 * @Description:对应数据库表信息
 * @author za
 * @date 2014年1月22日 上午10:37:33
 */
public class TableEntity implements Serializable {
	/**
	 * @Fields serialVersionUID:序列化ID
	 */
	private static final long serialVersionUID = 1L;
	//数据库名
	private String tableSchema;
	//表名
	private String tableName;
	//表注释
	private String tableComment;
	//表字段列表
	private List<ColumnEntity> columnList;

	public String getInsertView() {
		return GenerateUtil.getViewName(getVarName(), GenerateUtil.INSERT_FILE);
	}

	public String getUpdateView() {
		return GenerateUtil.getViewName(getVarName(), GenerateUtil.UPDATE_FILE);
	}

	public String getListView() {
		return GenerateUtil.getViewName(getVarName(), GenerateUtil.LIST_FILE);
	}

	public String getFormView() {
		return GenerateUtil.getViewName(getVarName(), GenerateUtil.FORM_FILE);
	}

	public String getShowView() {
		return GenerateUtil.getViewName(getVarName(), GenerateUtil.SHOW_FILE);
	}

	public List<ColumnEntity> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<ColumnEntity> columnList) {
		this.columnList = columnList;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

	public String getTableSchema() {
		return tableSchema;
	}

	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}

	/**
	 * 获得实体名
	 * @Title: getClassName
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public String getClassName() {
		String tableName = getRemoveTablePrefix();
		//如果没有下划线 就直接返回表名
		if(this.tableName.indexOf("_")<0)
		{
			return tableName;
		}
		String[] strs = tableName.split("_");
		List<String> list = getTrimList(strs);
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(GenerateUtil.toUpperCaseFirstOne(list.get(i).toLowerCase()));
		}
		return sb.toString();
	}
	
	
	/**
	 * 获取删除表前缀后的名称
	 * @return
	 */
	private String  getRemoveTablePrefix(){
		String removeTablePrefix = GenerateUtil.removeTablePrefix;
		String tableName = this.getTableName();
		if(StringUtil.isEmpty(removeTablePrefix)){
			return tableName;
		}
		int length = removeTablePrefix.length();
		String tablePrefix = tableName.substring(0, length);
		if(removeTablePrefix.equalsIgnoreCase(tablePrefix)){
			tableName = tableName.substring(length);
		}
		return tableName;
	}
	
	

	/**
	 * 获得变量名 实例对象的时候 规则同上 只是首字母也需小写
	 * 
	 * @Title: getVarName
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return
	 * @throws
	 */
	public String getVarName() {
		String tableName = getRemoveTablePrefix();
		String[] strs = tableName.split("_");
		List<String> list = getTrimList(strs);
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				sb.append(list.get(i).toLowerCase());
			}else{
				sb.append(GenerateUtil.toUpperCaseFirstOne(list.get(i).toLowerCase()));
			}
		}
		return sb.toString();
	}
	
	private List<String> getTrimList(String[] strs){
		List<String> list = new ArrayList<String>();
		if(strs == null){
			return list;
		}
		for(int i=0; i<strs.length; i++){
			if(StringUtil.isNotEmpty(strs[i])){
				list.add(strs[i]);
			}
		}
		return list;
	}
	
	public String getFullClassName() {
		String[] strs = this.getTableName().split("_");
		List<String> list = getTrimList(strs);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(GenerateUtil.toUpperCaseFirstOne(list.get(i).toLowerCase()));
		}
		return sb.toString();
	}

	public String getFullVarName() {
		String[] strs = this.getTableName().split("_");
		List<String> list = getTrimList(strs);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			if (i == 0) {
				sb.append(list.get(i).toLowerCase());
			}else{
				sb.append(GenerateUtil.toUpperCaseFirstOne(list.get(i).toLowerCase()));
			}
		}
		return sb.toString();
	}


	public static void main(String[] args) {

	}

	@Override
	public String toString() {
		return "TableEntity [tableSchema=" + tableSchema + ", tableName="
				+ tableName + ", tableComment=" + tableComment
				+ ", columnList=" + columnList + "]";
	}

}
