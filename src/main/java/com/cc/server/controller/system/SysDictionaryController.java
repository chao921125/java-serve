package com.cc.server.controller.system;

import com.cc.server.entity.system.SysDictionary;
import com.cc.server.service.system.SysDictionaryService;
import com.cc.frame.core.PageRequest;
import com.cc.frame.core.PageResult;
import com.cc.frame.core.ApiResponse;
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
@RequestMapping("/api-admin/sys-dictionary")
public class SysDictionaryController {
    @Resource
    private SysDictionaryService dictionaryService;

    @Operation(summary = "分页查询字典")
    @GetMapping("/list")
    public ApiResponse<PageResult<SysDictionary>> list(@RequestParam(defaultValue = "1") int pageNum, 
                                         @RequestParam(defaultValue = "10") int pageSize) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(pageNum);
        pageRequest.setPageSize(pageSize);
        return ApiResponse.success(dictionaryService.pageSysDictionary(pageRequest));
    }

    @Operation(summary = "根据ID查询字典")
    @GetMapping("/{id}")
    public ApiResponse<SysDictionary> getById(@PathVariable Integer id) {
        SysDictionary result = dictionaryService.selectSysDictionaryById(id);
        if (result == null) {
            return ApiResponse.success("未查询到数据", null);
        }
        return ApiResponse.success(result);
    }

    @Operation(summary = "新增字典")
    @PostMapping("/add")
    public ApiResponse<String> add(@RequestBody SysDictionary dictionary) {
        dictionaryService.insertSysDictionary(dictionary);
        return ApiResponse.success("新增成功", null);
    }

    @Operation(summary = "修改字典")
    @PostMapping("/update")
    public ApiResponse<String> update(@RequestBody SysDictionary dictionary) {
        dictionaryService.updateSysDictionaryById(dictionary);
        return ApiResponse.success("修改成功", null);
    }

    @Operation(summary = "删除字典")
    @PostMapping("/delete/{id}")
    public ApiResponse<String> delete(@PathVariable Integer id) {
        dictionaryService.deleteSysDictionaryById(id);
        return ApiResponse.success("删除成功", null);
    }
}
