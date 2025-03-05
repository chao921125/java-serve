package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import java.util.List;
import ${package.Mapper}.${table.mapperName};
<#if generateService>
    import ${package.Service}.${table.serviceName};
</#if>
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;

/**
* <p>
    * ${table.comment!} 服务实现类
    * </p>
*
* @author ${author}
* @since ${date}
*/
@Service
<#if kotlin>
    open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>()<#if generateService>, ${table.serviceName}</#if> {

    }
<#else>
    public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}><#if generateService> implements ${table.serviceName}</#if> {
    @Resource
    private ${table.mapperName} ${table.entityPath}Mapper;

    /**
    *  查询表${table.name}所有信息
    */
    @Override
    public List<${entity}> selectAll${entity}() { return ${table.entityPath}Mapper.selectAll${entity}();}

    <#list table.fields as field>
        <#if field.keyFlag>
            /**
            *  根据主键${field.propertyName}查询表${table.name}信息
            *  @param ${field.propertyName}
            */
            @Override
            public ${entity} select${entity}By${field.propertyName}(@Param("${field.propertyName}") ${field.propertyType} ${field.propertyName}) { return ${table.entityPath}Mapper.select${entity}By${field.propertyName}(${field.propertyName});}
        </#if>
    </#list>

    /**
    *  根据条件查询表${table.name}信息
    *  @param ${table.entityPath}
    */
    @Override
    public List<${entity}> select${entity}ByCondition(${entity} ${table.entityPath}) { return ${table.entityPath}Mapper.select${entity}ByCondition(${table.entityPath});}

    <#list table.fields as field>
        <#if field.keyFlag>
            /**
            *  根据主键${field.propertyName}查询表${table.name}信息
            *  @param ${field.propertyName}
            */
            @Override
            public Integer delete${entity}By${field.propertyName}(@Param("${field.propertyName}") ${field.propertyType} ${field.propertyName}) { return ${table.entityPath}Mapper.delete${entity}By${field.propertyName}(${field.propertyName});}
        </#if>
    </#list>

    <#list table.fields as field>
        <#if field.keyFlag>
            /**
            *  根据主键${field.propertyName}更新表${table.name}信息
            *  @param ${table.entityPath}
            */
            @Override
            public Integer update${entity}By${field.propertyName}(${entity} ${table.entityPath}) { return ${table.entityPath}Mapper.update${entity}By${field.propertyName}(${table.entityPath});}
        </#if>
    </#list>

    <#list table.fields as field>
        <#if field.keyFlag>
            /**
            *  新增表${table.name}信息
            *  @param ${table.entityPath}
            */
            @Override
            public Integer insert${entity}(${entity} ${table.entityPath}) { return ${table.entityPath}Mapper.insert${entity}(${table.entityPath});}
        </#if>
    </#list>
    }
</#if>
