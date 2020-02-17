<#include "/custom.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case> 
<html>
<head>
<title>${table.tableComment}信息</title>
</head>
<body>
		<table class="formTable">
		<#list table.columnList as column>
			<tr>	
				<td class="tdLabel">
					<span class="required">*</span>${column.columnComment}:
				</td>		
				<td>
				<#if column.isDateField>${dkhHead}(${classNameLower}.${column.fieldName}?date)!${dkhEnd}<#else>${dkhHead}(${classNameLower}.${column.fieldName})!${dkhEnd}</#if>
			   </td>
			</tr>
		</#list>
		</table>
		<a href="javascript:history.back();">返回</a>
</body>
</html>