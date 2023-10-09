package ${package}.${moduleName}.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ${package}.${moduleName}.service.${entityName}Service;
import ${package}.${moduleName}.entity.${entityName};

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import java.util.List;

/**
* ${tableComment} 前端控制器
*
* @author ${author}
* @date ${datetime}
*/
@RestController
@RequestMapping("/${moduleName}/${entityName}")
public class ${entityName}Controller {

@Autowired
private ${entityName}Service ${entityNameLower}Service;

// 控制器方法
}