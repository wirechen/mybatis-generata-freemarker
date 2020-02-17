<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign classNameUppwer = className?cap_first>
package ${packagePrefix}.dao${module};
import java.util.List;
import ${packagePrefix}.entity${module}.${classNameUppwer};

/**
 * @Auther: wirechen
 * @Date: ${.now?string["yyyy/MM/dd HH:mm"]}
 * @Description:
 */
public interface I${classNameUppwer}DAO {
	
	${classNameUppwer} selectById(${pkParam});
	
	int deleteById(${pkParam});

	int insert(${classNameUppwer} ${classNameLower});

	int insertSelective(${classNameUppwer} ${classNameLower});

	int updateSelective(${classNameUppwer} ${classNameLower});

	int updateById(${classNameUppwer} ${classNameLower});

	List<${classNameUppwer}> selectList(${classNameUppwer} ${classNameLower});
}