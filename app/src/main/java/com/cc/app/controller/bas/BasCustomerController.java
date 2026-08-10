package com.cc.app.controller.bas;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.dto.bas.CustomerSaveDTO;
import com.cc.core.entity.bas.BasCustomer;
import com.cc.core.service.bas.BasCustomerService;
import com.cc.framework.base.R;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 客户控制器
 */
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class BasCustomerController {

    private final BasCustomerService customerService;

    /**
     * 分页列表
     */
    @GetMapping
    public R<Page<BasCustomer>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer status) {
        Page<BasCustomer> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<BasCustomer> wrapper = new LambdaQueryWrapper<>();
        if (name != null && !name.isEmpty()) {
            wrapper.like(BasCustomer::getName, name);
        }
        if (status != null) {
            wrapper.eq(BasCustomer::getStatus, status);
        }
        wrapper.orderByDesc(BasCustomer::getCreateTime);
        return R.ok(customerService.page(p, wrapper));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    public R<BasCustomer> detail(@PathVariable Long id) {
        return R.ok(customerService.getById(id));
    }

    /**
     * 新增
     */
    @PostMapping
    public R<Void> create(@Valid @RequestBody CustomerSaveDTO dto) {
        BasCustomer entity = new BasCustomer();
        BeanUtils.copyProperties(dto, entity);
        customerService.save(entity);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @Valid @RequestBody CustomerSaveDTO dto) {
        BasCustomer entity = new BasCustomer();
        BeanUtils.copyProperties(dto, entity);
        entity.setId(id);
        customerService.updateById(entity);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return customerService.removeById(id) ? R.ok() : R.fail("删除失败");
    }
}
