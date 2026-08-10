package com.cc.app.controller.inv;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.inv.InvWarningConfig;
import com.cc.core.service.inv.InvWarningConfigService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 库存预警配置控制器
 */
@RestController
@RequestMapping("/api/v1/warning-configs")
@RequiredArgsConstructor
public class InvWarningConfigController {

    private final InvWarningConfigService warningConfigService;

    /**
     * 分页查询
     */
    @GetMapping
    public R<Page<InvWarningConfig>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long warehouseId) {
        Page<InvWarningConfig> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<InvWarningConfig> wrapper = new LambdaQueryWrapper<>();
        if (productId != null) {
            wrapper.eq(InvWarningConfig::getProductId, productId);
        }
        if (warehouseId != null) {
            wrapper.eq(InvWarningConfig::getWarehouseId, warehouseId);
        }
        wrapper.orderByDesc(InvWarningConfig::getCreateTime);
        return R.ok(warningConfigService.page(p, wrapper));
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@RequestBody InvWarningConfig config) {
        config.setStatus(0);
        warningConfigService.save(config);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody InvWarningConfig config) {
        config.setId(id);
        warningConfigService.updateById(config);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return warningConfigService.removeById(id) ? R.ok() : R.fail("删除失败");
    }
}
