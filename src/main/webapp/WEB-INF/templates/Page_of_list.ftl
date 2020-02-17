<#include "/custom.include">
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case> 
<html>
<head>
<title>${table.tableComment}维护</title>
</head>
<body>

<form id="queryForm" name="queryForm" action="list" method="post">
	<div class="queryPanel">
		<fieldset>
			<legend>搜索</legend>
			<table>
				<#list table.columnList?chunk(4) as row>
				<tr>	
					<#list row as column>
					<td class="tdLabel">${column.columnComment}</td>		
					<td>
						<input value="" id="${column.fieldName}" name="${column.fieldName}"/>
					</td>
					</#list>
				</tr>	
				</#list>			
			</table>
			<input type="submit" class="stdButton" style="width:80px" value="查询"/>
			<input type="button" class="stdButton" style="width:80px" value="新增" onclick="window.location = 'toInsert'"/>
		</fieldset>
	</div>
</form>
	
	<div class="gridTable">
		<table width="100%"  border="0" cellspacing="0" class="gridBody">
			  <tr>
				<#list table.columnList as column>
				<th>${column.columnComment}</th>
				</#list>
				<th>操作</th>
			  </tr>
			  
		       ${listHead} ${classNameFirstLower}List as row${jkhEnd}
			  <tr>
				<#list table.columnList as column>
				<td>
					<#if column.isDateField>
                      ${dkhHead}(row.${column.fieldName}${dateTag})!${dkhEnd}
					<#else>
					  ${dkhHead}(row.${column.fieldName})!${dkhEnd}
					</#if>
					&nbsp;
				</td>
				</#list>
				<td>
				<a href="show?${pkLink}">查看</a>&nbsp;
				<a href="toUpdate?${pkLink}">修改</a>&nbsp;
				<a href="delete?${pkLink}">删除</a>&nbsp;
				</td>
			  </tr>
			   ${listEnd}
		</table>
	</div>
	<script>
	</script>
</body>
</html>
