package com.jjshome.jjstool.util;

import java.util.ArrayList;
import java.util.List;
import com.jjshome.jjstool.entity.ColumnEntity;

/**
 * 字段生成版主类 
 * @author 曾安
 */
public class GenrateColumnUtil 
{
	/**
	 * 获取主键字段列表存在多个的
	 * @param columnList
	 * @return
	 */
	public static List<ColumnEntity> getPkColumnList(
			List<ColumnEntity> columnList) {
		if (columnList == null || columnList.size() == 0) {
			return columnList;
		}
		List<ColumnEntity> list = getPkColumn(columnList);
		if (list.size() > 0) {
			return list;
		}
		// 找不到，第一个字段
		for (ColumnEntity o : columnList) {
			if (o.getOrdinalPosition() == 1) {
				list.add(o);
				break;
			}
		}
		return list;
	}

	public static String[] getPKPram(List<ColumnEntity> columnList) {
		String[] strArray = new String[] { "", "" };
		String str = "";
		String str1 = "";
		List<ColumnEntity> list = getPkColumnList(columnList);
		for (int i = 0; i < list.size(); i++) {
			ColumnEntity a = columnList.get(i);
			if (i > 0) {
				str = str + ",";
				str1 = str1 + ",";
			}
			str = str + GenerateUtil.getFileTypeByDataType(a.getDataType())
					+ " " + a.getFieldName();
			str1 = str1 + a.getFieldName();
		}
		strArray[0] = str;
		strArray[1] = str1;
		return strArray;
	}
	
	/**
	 * 获取主键字段列表
	 * @param columnList
	 * @return
	 */
	public static List<ColumnEntity> getPkColumn(List<ColumnEntity> columnList) {
		List<ColumnEntity> list = new ArrayList<ColumnEntity>();
		// 找主键
		for (ColumnEntity o : columnList) {
			if ("PK".equals(o.getColumnKey()) || "PRI".equals(o.getColumnKey())) {
				list.add(o);
			}
		}
		return list;
	}
	
	/**
	 * 获取自增字段列表
	 * @param columnList
	 * @return
	 */
	public static List<ColumnEntity> getAutoColumnList(
			List<ColumnEntity> columnList) {
		List<ColumnEntity> list = new ArrayList<ColumnEntity>();
		for (ColumnEntity o : columnList) {
			if (o.getIsAutoIncrement() != null && o.getIsAutoIncrement() == 1) {
				list.add(o);
			}
		}
		return list;
	}
	
	//判断是否为主键
	public static boolean isPk(ColumnEntity entity,
			List<ColumnEntity> pkColumnList) {
		boolean isPk = false;
		for (ColumnEntity a : pkColumnList) {
			if (entity.getFieldName().equals(a.getFieldName())) {
				isPk = true;
				break;
			}
		}
		return isPk;
	}
	
	public static String getPkSql(List<ColumnEntity> columnList) {
		StringBuffer sb = new StringBuffer();
		sb.append("\t\t\t");
		List<ColumnEntity> list = GenrateColumnUtil.getPkColumnList(columnList);
		for (int i = 0; i < list.size(); i++) {
			ColumnEntity a = columnList.get(i);
			if (i > 0) {
				sb.append(" AND ");
			}
			sb.append(a.getColumnName() + " = #{" + a.getFieldName()
					+ ",jdbcType=" + a.getDataType().toUpperCase() + "}");
		}
		// sb.append("\n");
		return sb.toString();
	}
	
	public static String getJavaTypeByDataType(String fileDataType) {
		fileDataType = fileDataType.toLowerCase();
		if (fileDataType.equals("varchar")) {
			return "java.lang.String";
		} else if (fileDataType.equals("timestamp")
				|| fileDataType.equals("datetime")
				|| fileDataType.equals("date")) {
			return "java.util.Date";
		} else if (fileDataType.indexOf("bigint") >= 0) {
			return "java.lang.Long";
		} else if (fileDataType.indexOf("int") >= 0
				|| fileDataType.indexOf("integer") >= 0) {
			return "java.lang.Integer";
		} else if (fileDataType.indexOf("float") >= 0) {
			return "java.lang.Float";
		} else if (fileDataType.indexOf("double") >= 0) {
			return "java.lang.Double";
		} else if (fileDataType.indexOf("decimal") >= 0) {
			return "java.math.BigDecimal";
		} else {
			return "java.lang.String";
		}
	}
}
