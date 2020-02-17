package ${package};

<#if hasDate>
import java.util.Date;
</#if>
<#if hasDouble>
import java.math.BigDecimal;
</#if>
import java.io.Serializable;

/**
 * @Auther: wirechen
 * @Date: ${.now?string["yyyy/MM/dd HH:mm"]}
 * @Description:
 */
public class ${className} implements Serializable {

	private static final long serialVersionUID = 1L;
<#list columnList as column>
	/** ${column.columnComment} */
	private ${column.fileTypeByDataType} ${column.fieldName};
</#list>
<#list columnList as column>

	public ${column.fileTypeByDataType} get${column.fzName}() {
		return ${column.fieldName};
	}
	public void set${column.fzName}(${column.fileTypeByDataType} ${column.fieldName}) {
		this.${column.fieldName} = ${column.fieldName};
	}
</#list>
}
