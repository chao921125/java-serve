package com.cc.core.constants;

/**
 * 系统常量
 */
public final class Constants {

    private Constants() {}

    /** UTF-8 编码 */
    public static final String UTF8 = "UTF-8";

    /** 通用成功标识 */
    public static final String SUCCESS = "0";

    /** 通用失败标识 */
    public static final String FAIL = "1";

    /** 登录成功 */
    public static final String LOGIN_SUCCESS = "Success";

    /** 注销 */
    public static final String LOGOUT = "Logout";

    /** 注册 */
    public static final String REGISTER = "Register";

    /** 登录失败 */
    public static final String LOGIN_FAIL = "Error";

    /** 管理员角色标识 */
    public static final String ROLE_ADMIN = "admin";

    /** 系统用户 */
    public static final String USER_TYPE_SYSTEM = "00";

    /** 验证码有效期（分钟） */
    public static final Long CAPTCHA_EXPIRATION = 2L;

    /** 令牌前缀 */
    public static final String TOKEN_PREFIX = "Bearer ";

    /** 令牌请求头 */
    public static final String HEADER_AUTHORIZATION = "Authorization";

    /** 登录用户 key（Redis） */
    public static final String LOGIN_USER_KEY = "login_user:";

    /** 登录用户 ID key（Security Context） */
    public static final String LOGIN_USER_ID = "login_user_id";

    /** 部门树顶级 parentId */
    public static final Long DEPT_TOP_PARENT_ID = 0L;

    /** 菜单树顶级 parentId */
    public static final Long MENU_TOP_PARENT_ID = 0L;
}
