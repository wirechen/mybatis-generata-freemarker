<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign classNameUppwer = className?cap_first>
package ${packagePrefix}.dao${module};
import java.util.List;
import ${packagePrefix}.entity${module}.${classNameUppwer};

public interface I${classNameUppwer}DAO {
	
	public ${classNameUppwer} selectById(${pkParam});
	
	public int deleteById(${pkParam});

	public int insert(${classNameUppwer} ${classNameLower});

	public int insertSelective(${classNameUppwer} ${classNameLower});

	public int updateSelective(${classNameUppwer} ${classNameLower});

	public int updateById(${classNameUppwer} ${classNameLower});

	public List<${classNameUppwer}> selectList(${classNameUppwer} ${classNameLower});
}