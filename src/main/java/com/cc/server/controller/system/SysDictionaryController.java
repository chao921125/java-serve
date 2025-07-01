package com.cc.server.controller.system;

import com.cc.server.entity.system.SysDictionary;
import com.cc.server.service.system.SysDictionaryService;
import com.cc.server.vo.PageRequest;
import com.cc.server.vo.PageResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author cc
 * @since 2024-12-05 10:57:08
 */
@Tag(name = "字典管理", description = "字典管理接口")
@RestController
@RequestMapping("/sys-dictionary")
public class SysDictionaryController {
    @Resource
    private SysDictionaryService dictionaryService;

    @Operation(summary = "分页查询字典")
    @GetMapping("/list")
    public PageResult<SysDictionary> list(@RequestParam(defaultValue = "1") int pageNum, 
                                         @RequestParam(defaultValue = "10") int pageSize) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(pageNum);
        pageRequest.setPageSize(pageSize);
        return dictionaryService.pageSysDictionary(pageRequest);
    }

    @Operation(summary = "根据ID查询字典")
    @GetMapping("/{id}")
    public SysDictionary getById(@PathVariable Integer id) {
        return dictionaryService.selectSysDictionaryById(id);
    }

    @Operation(summary = "新增字典")
    @PostMapping("/add")
    public String add(@RequestBody SysDictionary dictionary) {
        dictionaryService.insertSysDictionary(dictionary);
        return "success";
    }

    @Operation(summary = "修改字典")
    @PostMapping("/update")
    public String update(@RequestBody SysDictionary dictionary) {
        dictionaryService.updateSysDictionaryById(dictionary);
        return "success";
    }

    @Operation(summary = "删除字典")
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        dictionaryService.deleteSysDictionaryById(id);
        return "success";
    }
}
