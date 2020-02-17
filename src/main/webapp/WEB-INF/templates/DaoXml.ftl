<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${namespace}">
	<resultMap id="${className}" type="${packageClassName}">
<#list columnList as column>
	<#if column.keyFlg>
		<id column="${column.columnName}" property="${column.fieldName}" jdbcType="${column.dataType}"/>
	<#else>
		<result column="${column.columnName}" property="${column.fieldName}" jdbcType="${column.dataType}"/>
	</#if>
</#list>
	</resultMap>
	
	<sql id="Base_Column_List">
<#list columnList as column>
	<#if (column_has_next)>
		${column.columnName},  
	<#else>
		${column.columnName}
	</#if>
</#list>
	</sql>
	
	<!-- 根据主键查询 -->
	<select id="selectById" resultMap="${className}" parameterType="${keyJavaType}">
		SELECT
			<include refid="Base_Column_List" />
		FROM
			${tableName}
		WHERE
			${pkColumn.mybatisCondition}
	</select>
	
	<!-- 根据主键删除 -->
	<delete id="deleteById" parameterType="${keyJavaType}">
		DELETE FROM
			${tableName}
		WHERE
			${pkColumn.mybatisCondition}
	</delete>
	
	<!-- 保存 -->
	<insert id="insert" parameterType="${packageClassName}" <#if pkAutoField> useGeneratedKeys="true"
			keyProperty="${pkColumn.fieldName}" </#if>>
		INSERT INTO ${tableName}
	<#list columnNoAutoList as column>
		<#if column_index==0>
			(${column.columnName},
		<#elseif column_has_next>
			${column.columnName},
		<#else>
			${column.columnName})
		</#if>
	</#list>
		VALUES
	<#list columnNoAutoList as column>
		<#if column_index==0>
			(${column.mybatisAssign},
		<#elseif column_has_next>
			${column.mybatisAssign},
		<#else>
			${column.mybatisAssign})
		</#if>
	</#list>
	</insert>
	
	<!-- 选择性保存,非空的不在保存范围 -->
	<insert id="insertSelective" parameterType="${packageClassName}" <#if pkAutoField> useGeneratedKeys="true"
			keyProperty="${pkColumn.fieldName}" </#if>>
		INSERT INTO ${tableName}
		<trim prefix="(" suffix=")" suffixOverrides=",">
		<#list columnNoAutoList as column>
			<if test="${column.fieldName} != null">
				${column.columnName},
			</if>
		</#list>
		</trim>
		<trim prefix="VALUES (" suffix=")" suffixOverrides=",">
	<#list columnNoAutoList as column>
		<if test="${column.fieldName} != null">
			${column.mybatisAssign},
		</if>
	</#list>
		</trim>
	</insert>
	
	<!-- 选择性修改,非空的不在保存范围 -->
	<update id="updateSelective" parameterType="${packageClassName}">
		UPDATE ${tableName}
		<set>
	<#list columnNoAutoList as column>
		<if test="${column.fieldName} != null">
			${column.mybatisCondition},
		</if>
	</#list>
		</set>
		WHERE
			${pkColumn.mybatisCondition}
	</update>
	
	<!-- 根据主键修改 -->
	<update id="updateById" parameterType="${packageClassName}">
		UPDATE 
			${tableName}
		SET
	<#list columnNoAutoList as column>
			${column.mybatisCondition} ,
	</#list>
		WHERE
			${pkColumn.mybatisCondition}
	</update>
	
	<select id="selectList" parameterType="${packageClassName}" resultMap="${className}">
		SELECT
			<include refid="Base_Column_List" />
		FROM
			${tableName}
		WHERE
			1=1
	</select>
	
</mapper>

