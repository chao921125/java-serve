package com.cc.app.controller.pur;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.core.dto.pur.SupplierPriceQueryDTO;
import com.cc.core.entity.pur.PurSupplierPrice;
import com.cc.core.service.pur.PurSupplierPriceService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 供应商价格控制器
 */
@RestController
@RequestMapping("/api/v1/supplier-prices")
@RequiredArgsConstructor
public class PurSupplierPriceController {

    private final PurSupplierPriceService supplierPriceService;

    /**
     * 分页查询
     */
    @GetMapping
    public R<IPage<PurSupplierPrice>> list(SupplierPriceQueryDTO query) {
        return R.ok(supplierPriceService.page(query));
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@RequestBody PurSupplierPrice supplierPrice) {
        supplierPriceService.save(supplierPrice);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody PurSupplierPrice supplierPrice) {
        supplierPrice.setId(id);
        supplierPriceService.updateById(supplierPrice);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return supplierPriceService.removeById(id) ? R.ok() : R.fail("删除失败");
    }
}
