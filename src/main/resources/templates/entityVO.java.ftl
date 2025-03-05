package ${package.Entity};

import java.io.Serializable;
import java.util.Date;
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
</#if>
@Schema(name = "${entity}VO对象", description = "${table.comment!}")
public class ${entity}VO implements Serializable {

private static final long serialVersionUID = 1L;

<#list table.fields as field>

    <#if field.keyFlag>
        <#assign keyPropertyName="${field.propertyName}"/>
    </#if>
    <#if field.keyFlag>
        <#if field.comment!?length gt 0>
            @Schema(description = "${field.comment}")
        <#else>
            @Schema(description = "主键")
        </#if>
    <#-- 普通字段 -->
    <#elseif field.fill??>
        <#if field.comment!?length gt 0>
            @Schema(description = "${field.comment}")
        <#else>
            @Schema(description = "-")
        </#if>
    <#elseif field.convert>
        <#if field.comment!?length gt 0>
            @Schema(description = "${field.comment}")
        <#else>
            @Schema(description = "-")
        </#if>
    </#if>
    private ${field.propertyType} ${field.propertyName};
</#list>

<#------------  END 字段循环遍历  ---------->
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
<#if !entityLombokModel>

    @Override
    public String toString() {
    return "${entity}VO{" +
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