<#include "/custom.include">
<#assign className = table.className>   
<#assign classNameFirstLower = className?uncap_first> 
<#assign classNameLowerCase = className?lower_case> 
<#assign formPageName = table.formView + pageSuffix>
<html>
<head>
<title>${table.tableComment}修改</title>
</head>
<body>
	<form method="post" action="update" method="post">		
		<table class="formTable">
		${jkhHead}include "${formPageName}"${jkhEnd}
		</table>
		<input id="submitButton" name="submitButton" type="submit" value="提交" />
	</form>
	 <a href="javascript:history.back();">返回</a>
</body>
</html>

