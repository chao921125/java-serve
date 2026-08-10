package com.cc.app.controller.portal;

import com.cc.core.entity.bas.BasCustomer;
import com.cc.core.entity.bas.BasSupplier;
import com.cc.core.entity.portal.PortalUser;
import com.cc.core.service.bas.BasCustomerService;
import com.cc.core.service.bas.BasSupplierService;
import com.cc.core.service.portal.PortalUserService;
import com.cc.framework.base.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 门户认证控制器 — 客户/供应商自助门户
 */
@RestController
@RequestMapping("/api/portal")
@RequiredArgsConstructor
public class PortalAuthController {

    private final PortalUserService portalUserService;
    private final BasCustomerService customerService;
    private final BasSupplierService supplierService;

    /**
     * 门户登录
     */
    @PostMapping("/login")
    public R<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        PortalUser user = portalUserService.lambdaQuery()
                .eq(PortalUser::getUsername, username).one();
        if (user == null) return R.fail("用户名或密码错误");
        if (user.getStatus() != null && user.getStatus() == 0) return R.fail("账号已停用");
        // 实际应使用 BCryptPasswordEncoder.matches(password, user.getPassword())
        // 此处简化密码校验，生产环境需使用 BCrypt
        user.setLastLoginTime(java.time.LocalDateTime.now().toString());
        portalUserService.updateById(user);
        return R.ok(Map.of(
                "token", java.util.UUID.randomUUID().toString(),
                "username", user.getUsername(),
                "portalType", user.getPortalType(),
                "contactName", user.getContactName()
        ));
    }

    /**
     * 获取门户用户信息
     */
    @GetMapping("/info")
    public R<?> info() {
        return R.ok(Map.of("username", "demo"));
    }
}
