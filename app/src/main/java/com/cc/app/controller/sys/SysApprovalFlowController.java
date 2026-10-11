// AI生成
package com.cc.app.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.sys.SysApprovalFlow;
import com.cc.core.entity.sys.SysApprovalNode;
import com.cc.core.service.sys.SysApprovalFlowService;
import com.cc.core.service.sys.SysApprovalNodeService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 审批流管理控制器
 */
@RestController
@RequestMapping("/api/sys/approval-flows")
@RequiredArgsConstructor
public class SysApprovalFlowController {

    private final SysApprovalFlowService service;
    private final SysApprovalNodeService nodeService;

    @GetMapping
    public R<Page<SysApprovalFlow>> list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        Page<SysApprovalFlow> p = new Page<>(page, pageSize);
        LambdaQueryWrapper<SysApprovalFlow> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SysApprovalFlow::getCreateTime);
        return R.ok(service.page(p, wrapper));
    }

    @GetMapping("/{id}")
    public R<SysApprovalFlow> detail(@PathVariable Long id) {
        return R.ok(service.getById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody SysApprovalFlow entity) {
        service.save(entity);
        return R.ok();
    }

    @PutMapping("/{id}")
    public R<Void> update(@PathVariable Long id, @RequestBody SysApprovalFlow entity) {
        entity.setId(id);
        service.updateById(entity);
        return R.ok();
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        return service.removeById(id) ? R.ok() : R.fail("删除失败");
    }

    @PutMapping("/{id}/toggle")
    public R<Void> toggleEnabled(@PathVariable Long id) {
        SysApprovalFlow flow = service.getById(id);
        if (flow != null) {
            flow.setIsEnabled(flow.getIsEnabled() == 1 ? 0 : 1);
            service.updateById(flow);
        }
        return R.ok();
    }

    @GetMapping("/{id}/nodes")
    public R<java.util.List<SysApprovalNode>> nodes(@PathVariable Long id) {
        return R.ok(service.getNodesByFlowId(id));
    }

    @PostMapping("/{id}/nodes")
    public R<Void> addNode(@PathVariable Long id, @RequestBody SysApprovalNode node) {
        node.setFlowId(id);
        service.addNode(node);
        return R.ok();
    }

    @DeleteMapping("/nodes/{nodeId}")
    public R<Void> deleteNode(@PathVariable Long nodeId) {
        return nodeService.removeById(nodeId) ? R.ok() : R.fail("节点不存在");
    }
}