package ${package.Entity};

import java.io.Serializable;
import java.util.Date;
<#list table.importPackages as pkg>
    import ${pkg};
</#list>
<#if springdoc>
    import io.swagger.v3.oas.annotations.media.Schema;
<#elseif swagger>

</#if>
<#if entityLombokModel>
    import lombok.Getter;
    import lombok.Setter;
    <#if chainModel>
        import lombok.experimental.Accessors;
    </#if>
</#if>

import io.swagger.v3.oas.annotations.media.Schema;

/**
* <p>
    * ${table.name} ${table.comment!}
    * </p>
* ${schemaName}
* @author ${author}
* @since ${date}
*/
<#if table.convert>
    @TableName("${table.name}")
</#if>
public class ${entity} implements Serializable {

private static final long serialVersionUID = 1L;

<#list table.fields as field>

    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
    <#if field.keyFlag>
    <#-- 主键 -->
        <#if field.keyIdentityFlag>
            @TableId(value = "${field.annotationColumnName}", type = IdType.AUTO)
        <#elseif idType??>
            @TableId(value = "${field.annotationColumnName}", type = IdType.${idType})
        <#elseif field.convert>
            @TableId("${field.annotationColumnName}")
        </#if>
    <#-- 普通字段 -->
    <#elseif field.fill??>
    <#-- -----   存在字段填充设置   ----->
        <#if field.convert>
            @TableField(value = "${field.annotationColumnName}", fill = FieldFill.${field.fill})
        <#else>
            @TableField(fill = FieldFill.${field.fill})
        </#if>
    <#elseif field.convert>
        @TableField("${field.annotationColumnName}")
    </#if>
<#-- 乐观锁注解 -->
    <#if field.versionField>
        @Version
    </#if>
<#-- 逻辑删除注解 -->
    <#if field.logicDeleteField>
        @TableLogic
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>

<#------------  END 字段循环遍历  ---------->
<#-- get set -->
<#if !entityLombokModel>
    <#list table.fields as field>
        <#if field.propertyType == "boolean">
            <#assign getprefix="is"/>
        <#else>
            <#assign getprefix="get"/>
        </#if>

        public ${field.propertyType} ${getprefix}${field.capitalName}() {
        return ${field.propertyName};
        }

        <#if chainModel>
            public ${entity} set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
        <#else>
            public void set${field.capitalName}(${field.propertyType} ${field.propertyName}) {
        </#if>
        this.${field.propertyName} = ${field.propertyName};
        <#if chainModel>
            return this;
        </#if>
        }
    </#list>
</#if>

<#if entityColumnConstant>
    <#list table.fields as field>
        public static final String ${field.name?upper_case} = "${field.name}";
    </#list>
</#if>

<#-- Serializable -->
<#if activeRecord>
    @Override
    public Serializable pkVal() {
    <#if keyPropertyName??>
        return this.${keyPropertyName};
    <#else>
        return null;
    </#if>
    }
</#if>

<#-- 构造函数 -->
public ${entity}(<#list table.fields as field><#if field.propertyType == "LocalDateTime">Date ${field.propertyName}</#if><#if field.propertyType != "LocalDateTime">${field.propertyType} ${field.propertyName}</#if><#sep>,</#list>){
<#list table.fields as field>
    this.${field.propertyName} = ${field.propertyName};
</#list>
}

public ${entity}(){
}

<#-- toString -->
<#if !entityLombokModel>
    @Override
    public String toString() {
    return "${entity}{" +
    <#list table.fields as field>
        <#if field_index==0>
            "${field.propertyName} = " + ${field.propertyName} +
        <#else>
            ", ${field.propertyName} = " + ${field.propertyName} +
        </#if>
    </#list>
    "}";
    }
</#if>
}