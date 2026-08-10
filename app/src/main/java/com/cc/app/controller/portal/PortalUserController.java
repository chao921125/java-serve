package com.cc.app.controller.portal;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.portal.PortalUser;
import com.cc.core.service.portal.PortalUserService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * PortalUser 控制器
 */
@RestController
@RequestMapping("/api/portal/users")
@RequiredArgsConstructor
public class PortalUserController {

    private final PortalUserService service;

    @GetMapping
    public R<Page<PortalUser>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<PortalUser> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<PortalUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(PortalUser::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<PortalUser> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody PortalUser entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody PortalUser entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }
}
