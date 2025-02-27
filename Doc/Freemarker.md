# FreeMarker 模板对象属性和方法

| 对象/变量             | 属性/方法                    | 描述                                         |
|-------------------|--------------------------|--------------------------------------------|
| package           | package.Entity           | 实体类的包路径，如 com.example.entity               |
|                   | package.Mapper	          | Mapper 接口的包路径，如 com.example.mapper         |
|                   | package.Controller	      | Controller 类的包路径，如 com.example.controller  |
|                   | package.Service	         | Service 接口的包路径，如 com.example.service       |
|                   | package.ServiceImpl	     | Service 实现类的包路径，如 com.example.service.impl |
| table             | table.name               | 数据库表名，如 t_user                             |
|                   | table.comment	           | 表注释，如 用户表                                  |
|                   | table.entityName         | 实体类名，如 User                                |
|                   | table.mapperName         | Mapper 接口名，如 UserMapper                    |
|                   | table.controllerName	    | Controller 类名，如 UserController             |
|                   | table.serviceName        | Service 接口名，如 UserService                  |
|                   | table.serviceImplName    | Service 实现类名，如 UserServiceImpl             |
|                   | table.fields             | 表字段列表，包含字段信息的集合，配合 list 迭代                 |
|                   | table.commonFields       | 表字段列表，包含字段信息的集合，配合 fields + list 迭代        |
|                   | table.fieldNames         | 表字段列表，包含字段信息的集合，单独使用                       |
|                   | table.importPackages     | 获取导入的包                                     |
|                   | table.convert            | 转换字段	                                      |
| field             | field.propertyType       | 字段对应的 Java 数据类型，如 String                   |
|                   | field.propertyName       | 获取字段属性名	                                   |
|                   | field.name               | 获取字段名                                      |
|                   | field.columnName	        | 数据库中的列名，如 user_name                        |
|                   | field.convert            | 获取字段转换规则	                                  |
|                   | field.comment            | 字段注释，如 用户名                                 |
|                   | field.keyFlag            | 是否为主键（布尔值）                                 |
|                   | field.keyIdentityFlag    | 是否为主键（布尔值）                                 |
|                   | field.capitalName        | 获取字段首字母大写名	                                |
| superMapperClass	 | superMapperClass         | 获取 Mapper 父类	                              |
|                   | superMapperClassPackage	 | 获取 Mapper 父类包	                             |
| date              | date                     | 获取当前日期		                                   |
| author            | author                   | 获取作者		                                     |
# FreeMarker 模板控制语句

| 对象/变量    | 描述                   | 用法                                    |
|----------|----------------------|---------------------------------------|
| if       | 	判断某个条件是否为真          | <#if condition> ... </#if>            |
| else     | 	在 if 条件不成立时执行       | <#else> ... </#else>                  |
| elseif   | 	额外条件判断              | <#elseif condition> ... </#elseif>    |
| list     | 	遍历列表或集合             | <#list list as item> ... </#list>     |
| macro    | 	定义一个宏               | <#macro macroName> ... </#macro>      |
| include  | 	引入其他模板              | <#include "filename.ftl">             |
| assign   | 	赋值操作                | <#assign varName = value>             |
| noparse  | 	禁用 FreeMarker 的语法解析 | <#noparse> ... </#noparse>            |
| function | 	调用自定义函数             | <#function funcName> ... </#function> |
| break    | 	终止循环或条件判断           | <#break>                              |
| continue | 	跳过当前循环的剩余部分，继续下一轮   | <#continue>                           |
| case     | 	类似于 switch 语句的判断    | <#case condition> ... </#case>        |