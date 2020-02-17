<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign classNameUppwer = className?cap_first>
<#assign insertView = table.insertView>  
<#assign updateView = table.updateView>  
<#assign listView = table.listView>  
<#assign formView = table.formView>  
<#assign showView = table.showView>

package ${packagePrefix}.web.controller${module};
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import ${packagePrefix}.entity${module}.${classNameUppwer};
import ${packagePrefix}.service${module}.I${classNameUppwer}Service;

@Controller
@RequestMapping("${modulePath}/${classNameLower}")
public class ${classNameUppwer}Controller{
	@Autowired
	private I${classNameUppwer}Service ${classNameLower}Service;

	private final String LIST_ACTION = "redirect:${modulePath}/${classNameLower}/list";
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	      binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));  
	}
	
	/** 列表 */
	@RequestMapping(value="/list")
	public ModelAndView list(${classNameUppwer} query,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		List<${classNameUppwer}> ${classNameLower}List = ${classNameLower}Service.select${classNameUppwer}List(query);
		model.addObject("${classNameLower}List", ${classNameLower}List);
		model.setViewName("${modulePath}/${classNameLower}/${listView}");
		return model;
	}
	
	/** 显示 */
	@RequestMapping(value="/show")
	public ModelAndView show(${pkParam}) throws Exception {
		ModelAndView model = new ModelAndView();
		${classNameUppwer} ${classNameLower} = ${classNameLower}Service.select${classNameUppwer}ById(${pkParamOnly});
		model.addObject("${classNameLower}", ${classNameLower});
		model.setViewName("${modulePath}/${classNameLower}/${showView}");
		return model;
	}

	/** 进入新增 */
	@RequestMapping(value="/toInsert")
	public ModelAndView toInsert(${classNameUppwer} ${classNameLower},HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView model = new ModelAndView();
		model.addObject("${classNameLower}", ${classNameLower});
		model.setViewName("${modulePath}/${classNameLower}/${insertView}");
		return model;
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insert(@Validated ${classNameUppwer} ${classNameLower},BindingResult errors,HttpServletRequest request,HttpServletResponse response) throws Exception {
		if(errors.hasErrors()) {
			return  "${modulePath}/${classNameLower}/${insertView}";
		}
		${classNameLower}Service.insert${classNameUppwer}(${classNameLower});
		return LIST_ACTION;
	}
	
	@RequestMapping(value="/toUpdate")
	public ModelAndView toUpdate(${pkParam}) throws Exception {
		ModelAndView model = new ModelAndView();
		${classNameUppwer} ${classNameLower} = ${classNameLower}Service.select${classNameUppwer}ById(${pkParamOnly});
		model.addObject("${classNameLower}", ${classNameLower});
		model.setViewName("${modulePath}/${classNameLower}/${updateView}");
		return model;
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(@Validated ${classNameUppwer} ${classNameLower},BindingResult errors,HttpServletRequest request,HttpServletResponse response) throws Exception {
		if(errors.hasErrors()) {
			return "${modulePath}/${classNameLower}/${updateView}";
		}
		${classNameLower}Service.update${classNameUppwer}(${classNameLower});
		return LIST_ACTION;
	}
	
	@RequestMapping(value="/delete")
	public String delete(${pkParam}) {
		${classNameLower}Service.delete${classNameUppwer}ById(${pkParamOnly});
		return LIST_ACTION;
	}
}

