// AI生成
package com.cc.app.controller.sys;

import com.cc.core.entity.sal.SalSalesOrder;
import com.cc.core.entity.pur.PurPurchaseOrder;
import com.cc.core.entity.sys.SysApprovalInstance;
import com.cc.core.entity.sys.SysNotification;
import com.cc.core.entity.sys.SysNotificationRead;
import com.cc.core.service.sal.SalSalesOrderService;
import com.cc.core.service.pur.PurPurchaseOrderService;
import com.cc.core.service.sys.SysApprovalInstanceService;
import com.cc.core.service.sys.SysNotificationReadService;
import com.cc.core.service.sys.SysNotificationService;
import com.cc.framework.base.R;
import com.cc.framework.config.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 我的待办控制器
 * 聚合当前登录用户的所有待办任务：待审批、待处理订单、未读通知等
 */
@RestController
@RequestMapping("/api/v1/my-tasks")
@RequiredArgsConstructor
public class MyTaskController {

    private final SysApprovalInstanceService approvalInstanceService;
    private final SalSalesOrderService salesOrderService;
    private final PurPurchaseOrderService purchaseOrderService;
    private final SysNotificationService notificationService;
    private final SysNotificationReadService notificationReadService;

    /**
     * 我的待办总览 — 返回各类待办数量及列表
     */
    @GetMapping("/summary")
    public R<Map<String, Object>> summary() {
        Long userId = SecurityUtil.getUserId();
        if (userId == null) {
            return R.fail("未登录");
        }

        Map<String, Object> result = new LinkedHashMap<>();

        // 1. 待我审批
        List<SysApprovalInstance> pendingApprovals = approvalInstanceService.getMyApproval(userId);
        List<Map<String, Object>> approvalTasks = pendingApprovals.stream()
                .map(this::toApprovalTask)
                .collect(Collectors.toList());
        result.put("pendingApprovals", approvalTasks);
        result.put("pendingApprovalCount", approvalTasks.size());

        // 2. 我提交的审批中申请
        List<SysApprovalInstance> myApplying = approvalInstanceService.getMyApply(userId).stream()
                .filter(i -> i.getStatus() != null && i.getStatus() == 0)
                .collect(Collectors.toList());
        result.put("myApplyingCount", myApplying.size());

        // 3. 待我处理的销售订单（status=1 待审核，且我创建的）
        List<SalSalesOrder> pendingSalesOrders = salesOrderService.lambdaQuery()
                .eq(SalSalesOrder::getStatus, 1)
                .eq(SalSalesOrder::getCreateBy, SecurityUtil.getUsername())
                .orderByDesc(SalSalesOrder::getCreateTime)
                .last("LIMIT 20")
                .list();
        List<Map<String, Object>> salesOrderTasks = pendingSalesOrders.stream()
                .map(this::toSalesOrderTask)
                .collect(Collectors.toList());
        result.put("pendingSalesOrders", salesOrderTasks);
        result.put("pendingSalesOrderCount", salesOrderTasks.size());

        // 4. 待我处理的采购订单（status=1 待审核，且我创建的）
        List<PurPurchaseOrder> pendingPurchaseOrders = purchaseOrderService.lambdaQuery()
                .eq(PurPurchaseOrder::getStatus, 1)
                .eq(PurPurchaseOrder::getCreateBy, SecurityUtil.getUsername())
                .orderByDesc(PurPurchaseOrder::getCreateTime)
                .last("LIMIT 20")
                .list();
        List<Map<String, Object>> purchaseOrderTasks = pendingPurchaseOrders.stream()
                .map(this::toPurchaseOrderTask)
                .collect(Collectors.toList());
        result.put("pendingPurchaseOrders", purchaseOrderTasks);
        result.put("pendingPurchaseOrderCount", purchaseOrderTasks.size());

        // 5. 未读通知
        Long unreadCount = notificationService.getUnreadCount(userId);
        result.put("unreadNotificationCount", unreadCount != null ? unreadCount : 0);

        // 6. 总待办数
        int totalPending = approvalTasks.size() + salesOrderTasks.size() + purchaseOrderTasks.size()
                + (unreadCount != null ? unreadCount.intValue() : 0);
        result.put("totalPendingCount", totalPending);

        return R.ok(result);
    }

    /**
     * 我的待审批列表
     */
    @GetMapping("/pending-approvals")
    public R<List<Map<String, Object>>> pendingApprovals() {
        Long userId = SecurityUtil.getUserId();
        if (userId == null) {
            return R.fail("未登录");
        }
        List<SysApprovalInstance> list = approvalInstanceService.getMyApproval(userId);
        return R.ok(list.stream().map(this::toApprovalTask).collect(Collectors.toList()));
    }

    /**
     * 我提交的申请列表
     */
    @GetMapping("/my-applied")
    public R<List<Map<String, Object>>> myApplied() {
        Long userId = SecurityUtil.getUserId();
        if (userId == null) {
            return R.fail("未登录");
        }
        List<SysApprovalInstance> list = approvalInstanceService.getMyApply(userId);
        return R.ok(list.stream().map(this::toApprovalTask).collect(Collectors.toList()));
    }

    /**
     * 我的待处理销售订单
     */
    @GetMapping("/pending-sales-orders")
    public R<List<Map<String, Object>>> pendingSalesOrders() {
        Long userId = SecurityUtil.getUserId();
        if (userId == null) {
            return R.fail("未登录");
        }
        List<SalSalesOrder> list = salesOrderService.lambdaQuery()
                .eq(SalSalesOrder::getStatus, 1)
                .eq(SalSalesOrder::getCreateBy, SecurityUtil.getUsername())
                .orderByDesc(SalSalesOrder::getCreateTime)
                .list();
        return R.ok(list.stream().map(this::toSalesOrderTask).collect(Collectors.toList()));
    }

    /**
     * 我的待处理采购订单
     */
    @GetMapping("/pending-purchase-orders")
    public R<List<Map<String, Object>>> pendingPurchaseOrders() {
        Long userId = SecurityUtil.getUserId();
        if (userId == null) {
            return R.fail("未登录");
        }
        List<PurPurchaseOrder> list = purchaseOrderService.lambdaQuery()
                .eq(PurPurchaseOrder::getStatus, 1)
                .eq(PurPurchaseOrder::getCreateBy, SecurityUtil.getUsername())
                .orderByDesc(PurPurchaseOrder::getCreateTime)
                .list();
        return R.ok(list.stream().map(this::toPurchaseOrderTask).collect(Collectors.toList()));
    }

    /**
     * 我的未读通知列表
     */
    @GetMapping("/unread-notifications")
    public R<List<Map<String, Object>>> unreadNotifications() {
        Long userId = SecurityUtil.getUserId();
        if (userId == null) {
            return R.fail("未登录");
        }
        // 查询所有通知，排除已读的
        List<SysNotification> allNotifications = notificationService.lambdaQuery()
                .orderByDesc(SysNotification::getCreateTime)
                .last("LIMIT 50")
                .list();
        Set<Long> readIds = notificationReadService.lambdaQuery()
                .eq(SysNotificationRead::getUserId, userId)
                .list().stream()
                .map(SysNotificationRead::getNotificationId)
                .collect(Collectors.toSet());

        List<Map<String, Object>> result = allNotifications.stream()
                .filter(n -> !readIds.contains(n.getId()))
                .map(n -> {
                    Map<String, Object> m = new LinkedHashMap<>();
                    m.put("id", n.getId());
                    m.put("title", n.getTitle());
                    m.put("content", n.getContent());
                    m.put("type", n.getType());
                    m.put("level", n.getLevel());
                    m.put("businessType", n.getBusinessType());
                    m.put("businessId", n.getBusinessId());
                    m.put("createTime", n.getCreateTime());
                    return m;
                })
                .collect(Collectors.toList());
        return R.ok(result);
    }

    private Map<String, Object> toApprovalTask(SysApprovalInstance instance) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("id", instance.getId());
        m.put("businessType", instance.getBusinessType());
        m.put("businessId", instance.getBusinessId());
        m.put("businessNo", instance.getBusinessNo());
        m.put("status", instance.getStatus());
        m.put("applicantName", instance.getApplicantName());
        m.put("appliedTime", instance.getAppliedTime());
        m.put("currentNodeOrder", instance.getCurrentNodeOrder());
        m.put("totalNodes", instance.getTotalNodes());
        m.put("taskType", "APPROVAL");
        m.put("taskTypeName", "待审批");
        return m;
    }

    private Map<String, Object> toSalesOrderTask(SalSalesOrder order) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("id", order.getId());
        m.put("orderNo", order.getOrderNo());
        m.put("customerId", order.getCustomerId());
        m.put("orderDate", order.getOrderDate());
        m.put("totalAmount", order.getTotalAmount());
        m.put("status", order.getStatus());
        m.put("createTime", order.getCreateTime());
        m.put("taskType", "SALES_ORDER");
        m.put("taskTypeName", "销售订单待审核");
        return m;
    }

    private Map<String, Object> toPurchaseOrderTask(PurPurchaseOrder order) {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("id", order.getId());
        m.put("orderNo", order.getOrderNo());
        m.put("supplierId", order.getSupplierId());
        m.put("orderDate", order.getOrderDate());
        m.put("totalAmount", order.getTotalAmount());
        m.put("status", order.getStatus());
        m.put("createTime", order.getCreateTime());
        m.put("taskType", "PURCHASE_ORDER");
        m.put("taskTypeName", "采购订单待审核");
        return m;
    }
}
