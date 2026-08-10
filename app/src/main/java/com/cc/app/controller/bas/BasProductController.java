package com.cc.app.controller.bas;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cc.core.dto.bas.ProductQueryDTO;
import com.cc.core.dto.bas.ProductSaveDTO;
import com.cc.core.entity.bas.BasProduct;
import com.cc.core.service.bas.BasProductService;
import com.cc.framework.base.R;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 商品控制器
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class BasProductController {

    private final BasProductService productService;

    /**
     * 分页查询
     */
    @GetMapping
    public R<IPage<BasProduct>> list(ProductQueryDTO query) {
        return R.ok(productService.page(query));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    public R<BasProduct> detail(@PathVariable Long id) {
        return R.ok(productService.getById(id));
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@Valid @RequestBody ProductSaveDTO dto) {
        BasProduct entity = new BasProduct();
        BeanUtils.copyProperties(dto, entity);
        productService.save(entity);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Valid @RequestBody ProductSaveDTO dto) {
        BasProduct entity = new BasProduct();
        BeanUtils.copyProperties(dto, entity);
        entity.setId(id);
        productService.updateById(entity);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return productService.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    /**
     * 切换状态
     */
    @PatchMapping("/{id}/status")
    public R<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        productService.updateStatus(id, status);
        return R.ok();
    }
}
