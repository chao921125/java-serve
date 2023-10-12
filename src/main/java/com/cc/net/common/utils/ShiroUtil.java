package com.cc.net.common.utils;

public class ShiroUtil extends AuthenticatingRealm {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();

        // 从JWT令牌中验证用户
        String jwtToken = usernamePasswordToken.getPassword().toString();
        if (!jwtUtil.validateToken(jwtToken)) {
            throw new AuthenticationException("Invalid token");
        }

        // 创建认证信息
        return new SimpleAuthenticationInfo(username, jwtToken, getName());
    }
}
