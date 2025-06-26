package com.cc.generator;

public class CodeTemplate {
    public static String entity(String packageName, String className, String fields, String tableName, String author, String date) {
        return "package " + packageName + ".entity;\n" +
                "\n" +
                "import com.baomidou.mybatisplus.annotation.TableId;\n" +
                "import com.baomidou.mybatisplus.annotation.TableName;\n" +
                "import com.baomidou.mybatisplus.annotation.TableField;\n" +
                "import java.io.Serializable;\n" +
                "\n" +
                "/**\n" +
                " * " + tableName + " 实体类\n" +
                " * @author " + author + "\n" +
                " * @date " + date + "\n" +
                " */\n" +
                "@TableName(\"" + tableName + "\")\n" +
                "public class " + className + " implements Serializable {\n" +
                "    private static final long serialVersionUID = 1L;\n" +
                fields +
                "}\n";
    }

    public static String mapper(String packageName, String className) {
        return "package " + packageName + ".mapper;\n" +
                "\n" +
                "import com.baomidou.mybatisplus.core.mapper.BaseMapper;\n" +
                "import " + packageName + ".entity." + className + ";\n" +
                "import org.apache.ibatis.annotations.Mapper;\n" +
                "\n" +
                "@Mapper\n" +
                "public interface " + className + "Mapper extends BaseMapper<" + className + "> {\n" +
                "}\n";
    }

    public static String service(String packageName, String className) {
        return "package " + packageName + ".service;\n" +
                "\n" +
                "import com.baomidou.mybatisplus.extension.service.IService;\n" +
                "import " + packageName + ".entity." + className + ";\n" +
                "\n" +
                "public interface " + className + "Service extends IService<" + className + "> {\n" +
                "}\n";
    }

    public static String serviceImpl(String packageName, String className) {
        return "package " + packageName + ".service.impl;\n" +
                "\n" +
                "import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;\n" +
                "import " + packageName + ".entity." + className + ";\n" +
                "import " + packageName + ".mapper." + className + "Mapper;\n" +
                "import " + packageName + ".service." + className + "Service;\n" +
                "import org.springframework.stereotype.Service;\n" +
                "\n" +
                "@Service\n" +
                "public class " + className + "ServiceImpl extends ServiceImpl<" + className + "Mapper, " + className + "> implements " + className + "Service {\n" +
                "}\n";
    }

    public static String controller(String packageName, String className, String voClass, String author, String date) {
        return "package " + packageName + ".controller;\n" +
                "\n" +
                "import " + packageName + ".service." + className + "Service;\n" +
                "import " + packageName + ".vo." + voClass + ";\n" +
                "import org.springframework.web.bind.annotation.*;\n" +
                "import io.swagger.v3.oas.annotations.tags.Tag;\n" +
                "import io.swagger.v3.oas.annotations.Operation;\n" +
                "import jakarta.annotation.Resource;\n" +
                "import java.util.List;\n" +
                "\n" +
                "@Tag(name = \"" + className + "管理\", description = \"" + className + "相关接口\")\n" +
                "@RestController\n" +
                "@RequestMapping(\"/" + className.toLowerCase() + "\")\n" +
                "public class " + className + "Controller {\n" +
                "    @Resource\n" +
                "    private " + className + "Service service;\n" +
                "\n" +
                "    @Operation(summary = \"列表\")\n" +
                "    @GetMapping(\"/list\")\n" +
                "    public List<" + voClass + "> list() {\n" +
                "        // TODO: 实现分页和VO转换\n" +
                "        return null;\n" +
                "    }\n" +
                "    // TODO: 其他接口\n" +
                "}\n";
    }

    public static String vo(String packageName, String voClass, String fields, String author, String date) {
        return "package " + packageName + ".vo;\n" +
                "\n" +
                "/**\n" +
                " * " + voClass + " VO\n" +
                " * @author " + author + "\n" +
                " * @date " + date + "\n" +
                " */\n" +
                "public class " + voClass + " {\n" +
                fields +
                "}\n";
    }

    public static String converter(String packageName, String className, String voClass) {
        return "package " + packageName + ".vo;\n" +
                "\n" +
                "import " + packageName + ".entity." + className + ";\n" +
                "import java.util.List;\n" +
                "import java.util.stream.Collectors;\n" +
                "\n" +
                "public class " + className + "Converter {\n" +
                "    public static " + voClass + " toVO(" + className + " entity) {\n" +
                "        // TODO: 实现属性拷贝\n" +
                "        return null;\n" +
                "    }\n" +
                "    public static List<" + voClass + "> toVOList(List<" + className + "> list) {\n" +
                "        return list.stream().map(" + className + "Converter::toVO).collect(Collectors.toList());\n" +
                "    }\n" +
                "}\n";
    }

    public static String mapperXml(String packageName, String className, String tableName) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
                "<mapper namespace=\"" + packageName + ".mapper." + className + "Mapper\">\n" +
                "    <!-- TODO: 自定义SQL -->\n" +
                "</mapper>\n";
    }
} 