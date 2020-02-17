<#include "/custom.include">
<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign classNameFirstLower = className?uncap_first> 

<#list table.columnList as column>
	<tr>	
		<td class="tdLabel">
			<span class="required">*</span>${column.columnComment}:
		</td>		
		<td>
		<input  id="${column.fieldName}" name="${column.fieldName}" value="<#if column.isDateField>${dkhHead}(${classNameFirstLower}.${column.fieldName}?date)!${dkhEnd}<#else>${dkhHead}(${classNameFirstLower}.${column.fieldName})!${dkhEnd}</#if>"/>
	</td>
	</tr>
</#list>