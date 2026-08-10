package com.cc.app.controller.inv;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.inv.InvAssembly;
import com.cc.core.entity.inv.InvAssemblyItem;
import com.cc.core.service.inv.InvAssemblyService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组装拆卸单控制器
 */
@RestController
@RequestMapping("/api/v1/assemblies")
@RequiredArgsConstructor
public class InvAssemblyController {

    private final InvAssemblyService assemblyService;

    /**
     * 分页查询
     */
    @GetMapping
    public R<IPage<InvAssembly>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) Integer status) {
        return R.ok(assemblyService.page(new Page<>(page, pageSize), type, status));
    }

    /**
     * 详情（含明细）
     */
    @GetMapping("/{id}")
    public R<Map<String, Object>> detail(@PathVariable Long id) {
        InvAssembly assembly = assemblyService.getById(id);
        if (assembly == null) {
            return R.fail("组装拆卸单不存在");
        }
        List<InvAssemblyItem> items = assemblyService.getItems(id);
        Map<String, Object> result = new HashMap<>();
        result.put("assembly", assembly);
        result.put("items", items);
        return R.ok(result);
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@RequestBody InvAssembly assembly) {
        assembly.setStatus(0);
        assemblyService.save(assembly);
        return R.ok();
    }

    /**
     * 删除（仅草稿状态）
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        InvAssembly assembly = assemblyService.getById(id);
        if (assembly == null) {
            return R.fail("组装拆卸单不存在");
        }
        if (assembly.getStatus() != 0) {
            return R.fail("只有草稿状态的组装拆卸单才能删除");
        }
        return assemblyService.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    /**
     * 审核
     */
    @PostMapping("/{id}/approve")
    public R<Void> approve(@PathVariable Long id) {
        assemblyService.approve(id);
        return R.ok();
    }
}
