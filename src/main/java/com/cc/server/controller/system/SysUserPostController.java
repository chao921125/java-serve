package com.cc.server.controller.system;

import com.cc.server.entity.system.SysUserPost;
import com.cc.server.service.system.SysUserPostService;
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
 * 用户岗位 用户1-N岗位 前端控制器
 * </p>
 *
 * @author cc
 * @since 2024-12-05 10:57:08
 */
@Tag(name = "用户岗位管理", description = "用户岗位管理接口")
@RestController
@RequestMapping("/api-admin/sys-user-post")
public class SysUserPostController {
    @Resource
    private SysUserPostService userPostService;

    @Operation(summary = "分页查询用户岗位")
    @GetMapping("/list")
    public ApiResponse<PageResult<SysUserPost>> list(@RequestParam(defaultValue = "1") int pageNum, 
                                       @RequestParam(defaultValue = "10") int pageSize) {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(pageNum);
        pageRequest.setPageSize(pageSize);
        return ApiResponse.success(userPostService.pageSysUserPost(pageRequest));
    }

    @Operation(summary = "根据ID查询用户岗位")
    @GetMapping("/{id}")
    public ApiResponse<SysUserPost> getById(@PathVariable Long id) {
        SysUserPost result = userPostService.selectSysUserPostById(id);
        if (result == null) {
            return ApiResponse.success("未查询到数据", null);
        }
        return ApiResponse.success(result);
    }

    @Operation(summary = "新增用户岗位")
    @PostMapping("/add")
    public ApiResponse<String> add(@RequestBody SysUserPost userPost) {
        userPostService.insertSysUserPost(userPost);
        return ApiResponse.success("新增成功", null);
    }

    @Operation(summary = "修改用户岗位")
    @PostMapping("/update")
    public ApiResponse<String> update(@RequestBody SysUserPost userPost) {
        userPostService.updateSysUserPostById(userPost);
        return ApiResponse.success("修改成功", null);
    }

    @Operation(summary = "删除用户岗位")
    @PostMapping("/delete/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        userPostService.deleteSysUserPostById(id);
        return ApiResponse.success("删除成功", null);
    }
}
