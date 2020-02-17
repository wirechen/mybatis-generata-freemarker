<#assign className = table.className>   
<#assign classNameLower = className?uncap_first>
<#assign classNameUppwer = className?cap_first>
	<bean id="${classNameLower}DAO" class="org.mybatis.spring.mapper.MapperFactoryBean">
		<property name="mapperInterface" value="${packagePrefix}.dao${module}.${classNameUppwer}DAO"/>
		<property name="sqlSessionFactory" ref="sqlSessionFactory" />
	</bean>