package com.cc.server.controller;

import com.cc.core.entity.SysDepartment;
import com.cc.core.service.SysDepartmentService;
import com.cc.core.vo.DeptTreeVO;
import com.cc.framework.annotation.Log;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/sys/dept")
@RequiredArgsConstructor
public class SysDepartmentController {

    private final SysDepartmentService deptService;
    private final com.cc.server.service.impl.SysDepartmentServiceImpl deptServiceImpl;

    /**
     * 查询部门树
     */
    @GetMapping("/tree")
    @PreAuthorize("hasAuthority('sys:dept:list')")
    public R<List<DeptTreeVO>> tree() {
        return R.ok(deptServiceImpl.getDeptTreeVO());
    }

    /**
     * 查询所有部门（列表）
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:dept:list')")
    public R<List<SysDepartment>> list() {
        return R.ok(deptService.list());
    }

    /**
     * 查询部门详情
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:dept:query')")
    public R<SysDepartment> getInfo(@PathVariable Long id) {
        SysDepartment dept = deptService.getById(id);
        return dept != null ? R.ok(dept) : R.fail("部门不存在");
    }

    /**
     * 新增部门
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:dept:add')")
    @Log(title = "部门管理", businessType = com.cc.core.enums.BusinessType.INSERT)
    public R<Void> add(@RequestBody SysDepartment dept) {
        deptServiceImpl.addDept(dept);
        return R.ok();
    }

    /**
     * 修改部门
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:dept:edit')")
    @Log(title = "部门管理", businessType = com.cc.core.enums.BusinessType.UPDATE)
    public R<Void> edit(@RequestBody SysDepartment dept) {
        deptServiceImpl.updateDept(dept);
        return R.ok();
    }

    /**
     * 删除部门
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('sys:dept:remove')")
    @Log(title = "部门管理", businessType = com.cc.core.enums.BusinessType.DELETE)
    public R<Void> remove(@PathVariable Long id) {
        deptServiceImpl.deleteDept(id);
        return R.ok();
    }
}
