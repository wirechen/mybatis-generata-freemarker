<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign classNameUppwer = className?cap_first>
package ${packagePrefix}.service${module};
import java.util.List;
import ${packagePrefix}.entity${module}.${classNameUppwer};

public interface I${classNameUppwer}Service {

	public int insert(${classNameUppwer} ${classNameLower});
	
	public int deleteById(${pkParam});
	
	public int update(${classNameUppwer} ${classNameLower});
	
	public List<${classNameUppwer}> selectList(${classNameUppwer} ${classNameLower});
	
	public ${classNameUppwer} selectById(${pkParam});
}