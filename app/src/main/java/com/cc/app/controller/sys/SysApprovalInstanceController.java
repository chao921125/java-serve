// AI生成
package com.cc.app.controller.sys;

import com.cc.core.dto.sys.ApprovalActionDTO;
import com.cc.core.dto.sys.ApprovalDelegateDTO;
import com.cc.core.dto.sys.ApprovalSubmitDTO;
import com.cc.core.entity.sys.SysApprovalInstance;
import com.cc.core.entity.sys.SysApprovalRecord;
import com.cc.core.service.sys.SysApprovalInstanceService;
import com.cc.core.service.sys.SysApprovalRecordService;
import com.cc.framework.base.R;
import com.cc.framework.config.security.SecurityUtil;
import com.cc.framework.exception.ServiceException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 审批实例控制器
 */
@RestController
@RequestMapping("/api/sys/approvals")
@RequiredArgsConstructor
public class SysApprovalInstanceController {

    private final SysApprovalInstanceService instanceService;
    private final SysApprovalRecordService recordService;

    @PostMapping("/submit")
    public R<Void> submit(@Valid @RequestBody ApprovalSubmitDTO dto) {
        Long userId = SecurityUtil.getUserId();
        String userName = SecurityUtil.getNickName() != null ? SecurityUtil.getNickName() : SecurityUtil.getUsername();
        instanceService.submit(dto.getBusinessType(), dto.getBusinessId(), dto.getBusinessNo(), userId, userName, dto.getFormData());
        return R.ok();
    }

    @GetMapping("/my-apply")
    public R<List<SysApprovalInstance>> myApply() {
        return R.ok(instanceService.getMyApply(SecurityUtil.getUserId()));
    }

    @GetMapping("/my-approval")
    public R<List<SysApprovalInstance>> myApproval() {
        return R.ok(instanceService.getMyApproval(SecurityUtil.getUserId()));
    }

    @GetMapping("/my-done")
    public R<List<SysApprovalInstance>> myDone() {
        return R.ok(instanceService.getMyDone(SecurityUtil.getUserId()));
    }

    @GetMapping("/instances/{instanceId}")
    public R<SysApprovalInstance> detail(@PathVariable Long instanceId) {
        SysApprovalInstance instance = instanceService.getById(instanceId);
        if (instance == null) {
            throw ServiceException.notFound("审批实例不存在");
        }
        return R.ok(instance);
    }

    @GetMapping("/instances/{instanceId}/trace")
    public R<Map<String, Object>> trace(@PathVariable Long instanceId) {
        return R.ok(instanceService.getTrace(instanceId));
    }

    @GetMapping("/instances/{instanceId}/records")
    public R<List<SysApprovalRecord>> records(@PathVariable Long instanceId) {
        return R.ok(recordService.getByInstanceId(instanceId));
    }

    @PostMapping("/instances/{instanceId}/approve")
    public R<Void> approve(@PathVariable Long instanceId, @RequestBody(required = false) ApprovalActionDTO dto) {
        Long userId = SecurityUtil.getUserId();
        String userName = SecurityUtil.getNickName() != null ? SecurityUtil.getNickName() : SecurityUtil.getUsername();
        String comment = dto != null ? dto.getComment() : "";
        recordService.approve(instanceId, userId, userName, comment);
        return R.ok();
    }

    @PostMapping("/instances/{instanceId}/reject")
    public R<Void> reject(@PathVariable Long instanceId, @RequestBody(required = false) ApprovalActionDTO dto) {
        Long userId = SecurityUtil.getUserId();
        String userName = SecurityUtil.getNickName() != null ? SecurityUtil.getNickName() : SecurityUtil.getUsername();
        String comment = dto != null ? dto.getComment() : "";
        recordService.reject(instanceId, userId, userName, comment);
        return R.ok();
    }

    @PostMapping("/instances/{instanceId}/delegate")
    public R<Void> delegate(@PathVariable Long instanceId, @Valid @RequestBody ApprovalDelegateDTO dto) {
        Long userId = SecurityUtil.getUserId();
        String userName = SecurityUtil.getNickName() != null ? SecurityUtil.getNickName() : SecurityUtil.getUsername();
        recordService.delegate(instanceId, userId, userName, dto.getDelegateToId(), dto.getComment());
        return R.ok();
    }

    @PostMapping("/instances/{instanceId}/withdraw")
    public R<Void> withdraw(@PathVariable Long instanceId) {
        recordService.withdraw(instanceId, SecurityUtil.getUserId());
        return R.ok();
    }

    @PostMapping("/instances/{instanceId}/return")
    public R<Void> returnTo(@PathVariable Long instanceId, @RequestBody Map<String, Object> body) {
        Long userId = SecurityUtil.getUserId();
        String userName = SecurityUtil.getNickName() != null ? SecurityUtil.getNickName() : SecurityUtil.getUsername();
        Integer targetNodeOrder = Integer.valueOf(body.get("targetNodeOrder").toString());
        String comment = body.getOrDefault("comment", "").toString();
        recordService.returnTo(instanceId, userId, userName, targetNodeOrder, comment);
        return R.ok();
    }
}