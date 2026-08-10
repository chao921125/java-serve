package com.cc.framework.aop;

import com.cc.core.entity.SysRole;
import com.cc.core.mapper.SysRoleDepartmentMapper;
import com.cc.core.mapper.SysRoleMapper;
import com.cc.framework.annotation.DataScope;
import com.cc.framework.config.security.LoginUser;
import com.cc.framework.config.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 数据权限 AOP 切面
 * 在 SQL 执行前，根据用户的数据权限范围，动态设置部门过滤条件
 *
 * 五种数据范围：
 * 1. 全部数据权限 -- 不添加过滤
 * 2. 自定义数据权限 -- 根据角色关联的部门过滤
 * 3. 本部门数据权限 -- 只查本部门
 * 4. 本部门及以下 -- 查本部门及所有子部门
 * 5. 仅本人 -- 只查自己的数据
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class DataScopeAspect {

    /** 数据权限过滤关键字 */
    public static final String DATA_SCOPE = "dataScope";

    private final SysRoleMapper roleMapper;
    private final SysRoleDepartmentMapper roleDeptMapper;

    @Before("@annotation(dataScope)")
    public void doBefore(JoinPoint joinPoint, DataScope dataScope) {
        LoginUser loginUser = SecurityUtil.getLoginUser();
        if (loginUser == null) {
            log.warn("数据权限: 当前用户未登录, 跳过过滤");
            return;
        }

        // 管理员无需过滤
        if (loginUser.getRoles().contains("admin")) {
            log.debug("数据权限: 管理员, 跳过过滤");
            return;
        }

        // 获取角色数据范围（取最小范围 = 最大值）
        Integer scopeType = getCurrentUserDataScope(loginUser);
        if (scopeType == null || scopeType == 1) {
            return; // 全部数据权限，不添加过滤
        }

        // 构建 SQL 过滤条件
        String scopeSql = buildScopeSql(scopeType, loginUser, dataScope);

        // 将过滤条件注入参数的 dataScope 字段
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            Object param = args[0];
            if (param != null) {
                try {
                    var setter = param.getClass().getMethod("setDataScope", String.class);
                    setter.invoke(param, scopeSql);
                    log.debug("数据权限: 注入过滤 SQL -> {}", scopeSql);
                } catch (Exception e) {
                    // 尝试设置 params Map
                    try {
                        var getParams = param.getClass().getMethod("getParams");
                        @SuppressWarnings("unchecked")
                        var params = (java.util.Map<String, Object>) getParams.invoke(param);
                        if (params == null) {
                            params = new java.util.HashMap<>();
                            var setParams = param.getClass().getMethod("setParams", java.util.Map.class);
                            setParams.invoke(param, params);
                        }
                        params.put(DATA_SCOPE, scopeSql);
                        log.debug("数据权限: 注入过滤 SQL via params -> {}", scopeSql);
                    } catch (Exception e2) {
                        log.warn("数据权限: 参数对象不支持 dataScope 注入, 跳过");
                    }
                }
            }
        }
    }

    /**
     * 构建数据范围 SQL
     * 使用参数化查询防止 SQL 注入
     */
    private String buildScopeSql(Integer scopeType, LoginUser loginUser, DataScope dataScope) {
        String deptAlias = dataScope.deptAlias();
        String userAlias = dataScope.userAlias();
        Long deptId = loginUser.getDeptId();
        String username = loginUser.getUsername().replace("'", "''");

        return switch (scopeType) {
            case 2 -> // 自定义数据权限
                buildCustomScope(deptAlias, loginUser);
            case 3 -> // 本部门
                deptAlias + ".id = " + deptId;
            case 4 -> // 本部门及以下
                "(" + deptAlias + ".id = " + deptId
                    + " OR " + deptAlias + ".ancestors LIKE '%," + deptId + ",%')";
            case 5 -> // 仅本人
                userAlias + ".create_by = '" + username + "'";
            default -> "";
        };
    }

    /**
     * 自定义数据权限：根据用户角色关联的部门列表过滤
     */
    private String buildCustomScope(String deptAlias, LoginUser loginUser) {
        // 查询用户所有角色的自定义部门
        List<SysRole> roles = roleMapper.selectRolesByUserId(loginUser.getUserId());
        Set<Long> deptIds = new java.util.HashSet<>();
        for (SysRole role : roles) {
            if (role.getDataScope() != null && role.getDataScope() == 2) {
                List<Long> ids = roleDeptMapper.selectDeptIdsByRoleId(role.getId());
                deptIds.addAll(ids);
            }
        }
        if (deptIds.isEmpty()) {
            return deptAlias + ".id = 0"; // 无权限
        }
        return deptAlias + ".id IN (" + deptIds.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")) + ")";
    }

    /**
     * 获取当前用户的数据权限范围
     * 取所有角色中范围最小的（值最大的 = 最严格的）
     */
    private Integer getCurrentUserDataScope(LoginUser loginUser) {
        List<SysRole> roles = roleMapper.selectRolesByUserId(loginUser.getUserId());
        if (roles == null || roles.isEmpty()) {
            return 5; // 无角色默认仅本人
        }

        int maxScope = 0;
        for (SysRole role : roles) {
            if (role.getStatus() != 0) continue; // 跳过停用角色
            Integer scope = role.getDataScope();
            if (scope != null && scope > maxScope) {
                maxScope = scope;
            }
        }
        return maxScope == 0 ? 5 : maxScope;
    }
}
