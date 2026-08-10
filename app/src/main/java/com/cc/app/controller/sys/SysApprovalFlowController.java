package com.cc.app.controller.sys;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cc.core.entity.sys.SysApprovalFlow;
import com.cc.core.service.sys.SysApprovalFlowService;
import com.cc.core.service.sys.SysApprovalInstanceService;
import com.cc.core.service.sys.SysApprovalRecordService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * SysApprovalFlow 控制器
 */
@RestController
@RequestMapping("/api/sys/approval-flows")
@RequiredArgsConstructor
public class SysApprovalFlowController {

    private final SysApprovalFlowService service;
    private final SysApprovalInstanceService instanceService;
    private final SysApprovalRecordService recordService;

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

    // ==== 审批流管理 ====

    @PutMapping("/{id}/toggle")
    public R<Void> toggleEnabled(@PathVariable Long id) {
        SysApprovalFlow flow = service.getById(id);
        if (flow != null) {
            flow.setIsEnabled(flow.getIsEnabled() == 1 ? 0 : 1);
            service.updateById(flow);
        }
        return R.ok();
    }

    // ==== 审批节点管理 ====

    @GetMapping("/{id}/nodes")
    public R<java.util.List<com.cc.core.entity.sys.SysApprovalNode>> nodes(@PathVariable Long id) {
        // 读取审批节点需要通过节点服务，这里简化处理，返回流程下节点
        return R.ok(service.getNodesByFlowId(id));
    }

    @PostMapping("/{id}/nodes")
    public R<Void> addNode(@PathVariable Long id, @RequestBody com.cc.core.entity.sys.SysApprovalNode node) {
        node.setFlowId(id);
        service.addNode(node);
        return R.ok();
    }

    // ==== 审批流程查询 ====

    @GetMapping("/my-apply")
    public R<java.util.List<com.cc.core.entity.sys.SysApprovalInstance>> myApply(@RequestParam Long userId) {
        return R.ok(instanceService.getMyApply(userId));
    }

    @GetMapping("/my-approval")
    public R<java.util.List<com.cc.core.entity.sys.SysApprovalInstance>> myApproval(@RequestParam Long userId) {
        return R.ok(instanceService.getMyApproval(userId));
    }

    @GetMapping("/my-done")
    public R<java.util.List<com.cc.core.entity.sys.SysApprovalInstance>> myDone(@RequestParam Long userId) {
        return R.ok(instanceService.getMyDone(userId));
    }

    // ==== 审批实例操作 ====

    @PostMapping("/submit")
    public R<Void> submitApproval(@RequestParam String businessType,
            @RequestParam Long businessId, @RequestParam String businessNo,
            @RequestParam Long applicantId, @RequestParam String applicantName) {
        instanceService.submit(businessType, businessId, businessNo, applicantId, applicantName);
        return R.ok();
    }

    @PostMapping("/instances/{instanceId}/approve")
    public R<Void> approve(@PathVariable Long instanceId,
            @RequestParam Long approverId, @RequestParam String approverName,
            @RequestParam(defaultValue = "") String comment) {
        recordService.approve(instanceId, approverId, approverName, comment);
        return R.ok();
    }

    @PostMapping("/instances/{instanceId}/reject")
    public R<Void> reject(@PathVariable Long instanceId,
            @RequestParam Long approverId, @RequestParam String approverName,
            @RequestParam(defaultValue = "") String comment) {
        recordService.reject(instanceId, approverId, approverName, comment);
        return R.ok();
    }

    @PostMapping("/instances/{instanceId}/delegate")
    public R<Void> delegate(@PathVariable Long instanceId,
            @RequestParam Long approverId, @RequestParam String approverName,
            @RequestParam Long delegateToId, @RequestParam(defaultValue = "") String comment) {
        recordService.delegate(instanceId, approverId, approverName, delegateToId, comment);
        return R.ok();
    }

    @PostMapping("/instances/{instanceId}/withdraw")
    public R<Void> withdraw(@PathVariable Long instanceId) {
        recordService.withdraw(instanceId);
        return R.ok();
    }
}
