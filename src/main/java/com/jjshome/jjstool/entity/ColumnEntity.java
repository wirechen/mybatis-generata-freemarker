package com.jjshome.jjstool.entity;

import java.io.Serializable;

import com.jjshome.jjstool.util.GenerateUtil;

/**
 * 字段实体
 * 
 * @ClassName: ColumnEntity
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author za
 * @date 2014年3月12日 下午10:28:59
 */
public class ColumnEntity implements Serializable {
	/**
	 * @Fields serialVersionUID:序列化ID
	 */
	private static final long serialVersionUID = 1L;

	//表名
	private String tableName;
    //列名
	private String columnName;
    //数据类型
	private String dataType;
    //java字段名
	private String fieldName;
	//字符串长度
	private Integer characterMaximumLength;
	//数字长度
	private Integer numericPrecision;
	//小数点后长度
	private Integer numericScale;
	//是否属于为空（NO:非空;YES:为空）
	private String isNullable;
	//是否是自增字段(0:否,1:是)
	private Integer isAutoIncrement;
	//字段默认值
	private String columnDefault;
	//字段排序序号
	private Integer ordinalPosition;
	//主键标识（PK:主键;MUL:索引）
	private String columnKey;
	//字段注释
	private String columnComment;
	
	private String varName;
	/**
	 * 是否日期字段
	 */
	private Boolean isDateField;
	
	public Boolean getIsDateField() {
		if (dataType == null) {
			return false;
		}
		String temp = dataType.toLowerCase();
		if (temp.equals("timestamp") || temp.equals("date")
				|| temp.equals("datetime")) {
			return true;
		}
		return false;
	}
	
	public boolean getKeyFlg() {
		if ("PK".equals(this.getColumnKey()) || "PRI".equals(this.getColumnKey())) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "ColumnEntity [tableName=" + tableName + ", columnName="
				+ columnName + ", dataType=" + dataType + ", fieldName="
				+ fieldName + ", characterMaximumLength="
				+ characterMaximumLength + ", numericPrecision="
				+ numericPrecision + ", numericScale=" + numericScale
				+ ", isNullable=" + isNullable + ", isAutoIncrement="
				+ isAutoIncrement + ", columnDefault=" + columnDefault
				+ ", ordinalPosition=" + ordinalPosition + ", columnKey="
				+ columnKey + ", isDateField=" + isDateField
				+ ", columnComment=" + columnComment + ", varName=" + varName
				+ "]";
	}

	

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		if (dataType.toUpperCase().equals("INT")) {
			return "INTEGER";
		}
		if (dataType.toUpperCase().equals("DATETIME")) {
			return "DATE";
		}
		if (dataType.toUpperCase().equals("TEXT") || dataType.toUpperCase().equals("LONGTEXT")) {
			return "VARCHAR";
		}
		return dataType.toUpperCase();
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	/**
	 * 字段命名规范 Start_End_ 形成 startEnd 首单词字母小写 其它单词大写
	 * 
	 * @Title: getFieldName
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @throws
	 */
	public String getFieldName() {
		//如果没有下划线 直接返回字段名
		/*if(this.getColumnName().indexOf("_")<0)
		{
			return this.getColumnName();
		}*/
		String[] strs = this.getColumnName().split("_");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strs.length; i++) {
			if (i == 0) {
				sb.append(strs[i].toLowerCase());
				continue;
			}
			sb.append(GenerateUtil.toUpperCaseFirstOne(strs[i].toLowerCase()));
		}
		this.fieldName = sb.toString();
		return fieldName;
	}

	/**
	 * 获得封装的名称 用于get set 每个单词首字母都要大写
	 * @Title: getFzName
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @return String
	 * @throws
	 */
	public String getFzName() {
		String[] strs = this.getColumnName().split("_");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < strs.length; i++) {
			sb.append(GenerateUtil.toUpperCaseFirstOne(strs[i].toLowerCase()));
		}
		return sb.toString();
	}

	public String getVarName() {
		this.varName = this.getFieldName().toLowerCase();
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public Integer getCharacterMaximumLength() {
		return characterMaximumLength;
	}

	public void setCharacterMaximumLength(Integer characterMaximumLength) {
		this.characterMaximumLength = characterMaximumLength;
	}

	public Integer getNumericPrecision() {
		return numericPrecision;
	}

	public void setNumericPrecision(Integer numericPrecision) {
		this.numericPrecision = numericPrecision;
	}

	public Integer getNumericScale() {
		return numericScale;
	}

	public void setNumericScale(Integer numericScale) {
		this.numericScale = numericScale;
	}

	public String getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}

	public Integer getIsAutoIncrement() {
		return isAutoIncrement;
	}

	public void setIsAutoIncrement(Integer isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}

	public String getColumnDefault() {
		return columnDefault;
	}

	public void setColumnDefault(String columnDefault) {
		this.columnDefault = columnDefault;
	}

	public Integer getOrdinalPosition() {
		return ordinalPosition;
	}

	public void setOrdinalPosition(Integer ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public void setIsDateField(Boolean isDateField) {
		this.isDateField = isDateField;
	}
	
	public String getFileTypeByDataType() {
		String fileDataType = this.dataType.toLowerCase();
		if (fileDataType.equals("varchar")) {
			return "String";
		} else if (fileDataType.equals("timestamp")
				|| fileDataType.equals("datetime")
				|| fileDataType.equals("date")) {
			return "Date";
		} else if (fileDataType.indexOf("bigint") >= 0) {
			return "Long";
		} else if (fileDataType.indexOf("int") >= 0
				|| fileDataType.indexOf("integer") >= 0) {
			return "Integer";
		} else if (fileDataType.indexOf("float") >= 0) {
			return "Float";
		} else if (fileDataType.indexOf("double") >= 0) {
			return "Double";
		} else if (fileDataType.indexOf("decimal") >= 0) {
			return "BigDecimal";
		} else {
			return "String";
		}
	}
	
	public String getMybatisCondition(){
		return this.getColumnName()+" = " + "#{"+this.getFieldName()+",jdbcType="+this.getDataType()+"}";
	}
	
	public String getMybatisAssign(){
		return "#{"+this.getFieldName()+",jdbcType="+this.getDataType()+"}";
	}
}
