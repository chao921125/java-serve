<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">
    <!-- 通用查询映射结果 -->
    <resultMap id="${entity}Map" type="${package.Entity}.${entity}">
        <#list table.fields as field>
            <#if field.keyFlag><#--生成主键排在第一位-->
                <id column="${field.name}" property="${field.propertyName}"/>
            </#if>
        </#list>
        <#list table.commonFields as field><#--生成公共字段 -->
            <result column="${field.name}" property="${field.propertyName}"/>
        </#list>
        <#list table.fields as field>
            <#if !field.keyFlag><#--生成普通字段 -->
                <result column="${field.name}" property="${field.propertyName}"/>
            </#if>
        </#list>
    </resultMap>

    <!-- 通用查询列 -->
    <sql id="${entity}List">
        <#list table.commonFields as field>
            ${field.name},
        </#list>
        ${table.fieldNames}
    </sql>

    <!-- 通用条件列 -->
    <sql id="${entity}By">
        <#list table.commonFields as field>
            <if test="${field.propertyName} != null and ${field.propertyName} != ''">
                AND ${field.name} = ${r"#{"}${field.propertyName}${r"}"}
            </if>
        </#list>
        <#list table.fields as field>
            <#if !field.keyFlag>
                <if test="${field.propertyName} != null and ${field.propertyName} != ''">
                    AND ${field.name} = ${r"#{"}${field.propertyName}${r"}"}
                </if>
            </#if>
        </#list>
    </sql>

    <!-- 通用设置列 -->
    <sql id="${entity}Set">
        <#list table.commonFields as field>
            <if test="${field.propertyName} != null and ${field.propertyName} != ''">
                ${field.name} = ${r"#{"}${field.propertyName}${r"}"}
            </if>
        </#list>
        <#list table.fields as field>
            <#if !field.keyFlag>
                <if test="${field.propertyName} != null and ${field.propertyName} != ''">
                    ${field.name} = ${r"#{"}${field.propertyName}${r"}"}
                </if>
            </#if>
        </#list>
    </sql>

    <!-- 通用查询 -->
    <sql id="select${entity}Vo">
        select<#list table.fields as field> ${field.name}<#if field_has_next>,</#if></#list> from ${table.name}
    </sql>

    <!-- 查询表${table.name}所有信息 -->
    <select id="selectAll${entity}" resultMap="${entity}Map">
        SELECT
        <include refid="${entity}List"/>
        FROM ${table.name}
    </select>

    <#list table.fields as field>
        <#if field.keyFlag>
            <!-- 根据主键${field.propertyName}查询表${table.name}信息 -->
            <select id="select${entity}By${field.propertyName}" resultMap="${entity}Map">
                SELECT
                <include refid="${entity}List"/>
                FROM ${table.name}
                WHERE ${field.name} = ${r"#{"}${field.propertyName}${r"}"}
            </select>
        </#if>
    </#list>

    <!-- 根据条件查询表${table.name}信息 -->
    <select id="select${entity}ByCondition" resultMap="${entity}Map">
        SELECT
        <include refid="${entity}List"/>
        FROM ${table.name}
        WHERE 1=1
        <include refid="${entity}By"/>
    </select>

    <#list table.fields as field>
        <#if field.keyFlag>
            <!-- 新增表${table.name}信息 -->
            <insert id="insert${entity}">
                INSERT INTO ${table.name} (
                <#list table.fields as field>
                    <#if field_index gt 0>,</#if>${field.name}
                </#list>
                ) VALUES (
                <#list table.fields as field>
                    <#if field_index gt 0>,</#if>${r"#{"}${field.propertyName}${r"}"}
                </#list>
                )
            </insert>
        </#if>
    </#list>

    <#list table.fields as field>
        <#if field.keyFlag>
            <!-- 根据主键${field.propertyName}更新表${table.name}信息 -->
            <update id="update${entity}By${field.propertyName}" parameterType="${package.Entity}.${entity}">
                UPDATE ${table.name}
                <set>
                    <include refid="${entity}Set"/>
                </set>
                WHERE
                <#list table.fields as field><#if field.keyFlag>${field.name}=${r"#{"}${field.propertyName}${r"}"}</#if></#list>
            </update>
        </#if>
    </#list>

    <#list table.fields as field>
        <#if field.keyFlag>
            <!-- 根据主键${field.propertyName}删除表${table.name}信息：慎用！！！ -->
            <delete id="delete${entity}By${field.propertyName}">
                DELETE FROM
                ${table.name}
                WHERE ${field.name}=${r"#{"}${field.propertyName}${r"}"}
            </delete>
        </#if>
    </#list>
</mapper>
