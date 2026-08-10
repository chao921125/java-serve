package com.cc.app.controller.bas;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cc.core.entity.bas.BasAttribute;
import com.cc.core.entity.bas.BasAttributeValue;
import com.cc.core.entity.bas.BasProductAttribute;
import com.cc.core.dto.bas.ProductAttributeSaveDTO;
import com.cc.core.service.bas.BasAttributeService;
import com.cc.core.service.bas.BasAttributeValueService;
import com.cc.core.service.bas.BasProductAttributeService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 属性定义控制器
 */
@RestController
@RequestMapping("/api/v1/attributes")
@RequiredArgsConstructor
public class BasAttributeController {

    private final BasAttributeService attributeService;
    private final BasAttributeValueService valueService;
    private final BasProductAttributeService productAttributeService;

    // ========== 属性定义 ==========

    /**
     * 按模板查询属性列表
     */
    @GetMapping
    public R<List<BasAttribute>> list(@RequestParam Long templateId) {
        LambdaQueryWrapper<BasAttribute> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BasAttribute::getTemplateId, templateId);
        wrapper.orderByAsc(BasAttribute::getSortOrder);
        return R.ok(attributeService.list(wrapper));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    public R<BasAttribute> detail(@PathVariable Long id) {
        return R.ok(attributeService.getById(id));
    }

    /**
     * 新增属性
     */
    @PostMapping
    public R<Void> create(@RequestBody BasAttribute entity) {
        attributeService.save(entity);
        return R.ok();
    }

    /**
     * 修改属性
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody BasAttribute entity) {
        entity.setId(id);
        attributeService.updateById(entity);
        return R.ok();
    }

    /**
     * 删除属性（同时删除预设值）
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        // 删除该属性下的所有预设值
        LambdaQueryWrapper<BasAttributeValue> vw = new LambdaQueryWrapper<>();
        vw.eq(BasAttributeValue::getAttributeId, id);
        valueService.remove(vw);
        return attributeService.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    // ========== 属性预设值 ==========

    /**
     * 查询属性的预设值列表
     */
    @GetMapping("/{attributeId}/values")
    public R<List<BasAttributeValue>> listValues(@PathVariable Long attributeId) {
        LambdaQueryWrapper<BasAttributeValue> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BasAttributeValue::getAttributeId, attributeId);
        wrapper.orderByAsc(BasAttributeValue::getSortOrder);
        return R.ok(valueService.list(wrapper));
    }

    /**
     * 新增预设值
     */
    @PostMapping("/{attributeId}/values")
    public R<Void> createValue(@PathVariable Long attributeId, @RequestBody BasAttributeValue entity) {
        entity.setAttributeId(attributeId);
        valueService.save(entity);
        return R.ok();
    }

    /**
     * 修改预设值
     */
    @PutMapping("/values/{id}")
    public R<Void> updateValue(@PathVariable Long id, @RequestBody BasAttributeValue entity) {
        entity.setId(id);
        valueService.updateById(entity);
        return R.ok();
    }

    /**
     * 删除预设值
     */
    @DeleteMapping("/values/{id}")
    public R<Void> deleteValue(@PathVariable Long id) {
        return valueService.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    // ========== 商品属性关联 ==========

    /**
     * 查询商品的属性值
     */
    @GetMapping("/products/{productId}")
    public R<List<BasProductAttribute>> getProductAttributes(@PathVariable Long productId) {
        return R.ok(productAttributeService.getByProductId(productId));
    }

    /**
     * 批量保存商品属性值
     */
    @PostMapping("/products/batch")
    public R<Void> batchSaveProductAttributes(@RequestBody ProductAttributeSaveDTO dto) {
        List<BasProductAttribute> attributes = dto.getAttributes().stream().map(item -> {
            BasProductAttribute a = new BasProductAttribute();
            a.setProductId(dto.getProductId());
            a.setAttributeId(item.getAttributeId());
            a.setAttributeValueId(item.getAttributeValueId());
            a.setManualValue(item.getManualValue());
            return a;
        }).toList();
        productAttributeService.batchSave(dto.getProductId(), attributes);
        return R.ok();
    }
}
