<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign classNameUppwer = className?cap_first>

package ${packagePrefix}.service${module}.impl;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ${packagePrefix}.dao${module}.I${classNameUppwer}DAO;
import ${packagePrefix}.entity${module}.${classNameUppwer};
import ${packagePrefix}.service${module}.I${classNameUppwer}Service;

@Service(value="${classNameLower}Service")
public class ${classNameUppwer}ServiceImpl implements I${classNameUppwer}Service{
	
	@Autowired
	private I${classNameUppwer}DAO ${classNameLower}DAO;
	
	@Override
	public int insert(${classNameUppwer} ${classNameLower}) {
		// TODO Auto-generated method stub
		return ${classNameLower}DAO.insert(${classNameLower});
	}

	@Override
	public int deleteById(${pkParam}) {
		// TODO Auto-generated method stub
		return ${classNameLower}DAO.deleteById(${pkParamOnly});
	}

	@Override
	public int update(${classNameUppwer} ${classNameLower}) {
		// TODO Auto-generated method stub
		return ${classNameLower}DAO.updateById(${classNameLower});
	}

	@Override
	public List<${classNameUppwer}> selectList(${classNameUppwer} ${classNameLower}) {
		// TODO Auto-generated method stub
		return ${classNameLower}DAO.selectList(${classNameLower});
	}
	

	@Override
	public ${classNameUppwer} selectById(${pkParam}) {
		// TODO Auto-generated method stub
		return ${classNameLower}DAO.selectById(${pkParamOnly});
	}

}
