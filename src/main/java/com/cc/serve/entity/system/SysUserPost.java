package com.cc.serve.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户岗位
 * </p>
 *
 * @author cc
 * @since 2024-09-28 15:03:232
 */
@TableName("sys_user_post")
@Schema(name = "SysUserPost对象", description = "用户岗位")
public class SysUserPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(name = "用户 id")
    @TableField("user_id")
    private Long userId;

    @Schema(name = "岗位 id")
    @TableField("post_id")
    private Long postId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "SysUserPost{" +
            "id = " + id +
            ", userId = " + userId +
            ", postId = " + postId +
            "}";
    }

    /**
     * <p>
     * sys_department 部门表
     * </p>
     *
     * @author cc
     * @since 2024-11-20 23:04:58
     */

    @TableName("sys_department")
    @Schema(name = "SysDepartment对象", description = "部门表")
    public static class SysDepartment implements Serializable {

        private static final long serialVersionUID = 1L;


        @Schema(description = "部门 id")
        @TableId(value = "id", type = IdType.AUTO)
        private Long id;

        @Schema(description = "父级id")
        @TableField("parent_id")
        private Long parentId;

        @Schema(description = "祖级，树结构")
        @TableField("ancestors")
        private String ancestors;

        @Schema(description = "部门名称")
        @TableField("name")
        private String name;

        @Schema(description = "排序")
        @TableField("sort")
        private Integer sort;

        @Schema(description = "负责人")
        @TableField("leader")
        private String leader;

        @Schema(description = "手机号")
        @TableField("phone")
        private String phone;

        @Schema(description = "邮箱")
        @TableField("email")
        private String email;

        @Schema(description = "状态（1正常 0停用 9删除）")
        @TableField("status")
        private String status;

        @Schema(description = "创建人")
        @TableField("create_by")
        private String createBy;

        @Schema(description = "创建时间")
        @TableField("create_time")
        private Date createTime;

        @Schema(description = "修改人")
        @TableField("update_by")
        private String updateBy;

        @Schema(description = "修改时间")
        @TableField("update_time")
        private Date updateTime;

        @Schema(description = "备注")
        @TableField("remark")
        private String remark;


        public Long getId() {
        return id;
        }

        public void setId(Long id) {
        this.id = id;
        }

        public Long getParentId() {
        return parentId;
        }

        public void setParentId(Long parentId) {
        this.parentId = parentId;
        }

        public String getAncestors() {
        return ancestors;
        }

        public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
        }

        public String getName() {
        return name;
        }

        public void setName(String name) {
        this.name = name;
        }

        public Integer getSort() {
        return sort;
        }

        public void setSort(Integer sort) {
        this.sort = sort;
        }

        public String getLeader() {
        return leader;
        }

        public void setLeader(String leader) {
        this.leader = leader;
        }

        public String getPhone() {
        return phone;
        }

        public void setPhone(String phone) {
        this.phone = phone;
        }

        public String getEmail() {
        return email;
        }

        public void setEmail(String email) {
        this.email = email;
        }

        public String getStatus() {
        return status;
        }

        public void setStatus(String status) {
        this.status = status;
        }

        public String getCreateBy() {
        return createBy;
        }

        public void setCreateBy(String createBy) {
        this.createBy = createBy;
        }

        public Date getCreateTime() {
        return createTime;
        }

        public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        }

        public String getUpdateBy() {
        return updateBy;
        }

        public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        }

        public Date getUpdateTime() {
        return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        }

        public String getRemark() {
        return remark;
        }

        public void setRemark(String remark) {
        this.remark = remark;
        }

        @Override
        public String toString() {
        return "SysDepartment{" +
                "id = " + id +
                ", parentId = " + parentId +
                ", ancestors = " + ancestors +
                ", name = " + name +
                ", sort = " + sort +
                ", leader = " + leader +
                ", phone = " + phone +
                ", email = " + email +
                ", status = " + status +
                ", createBy = " + createBy +
                ", createTime = " + createTime +
                ", updateBy = " + updateBy +
                ", updateTime = " + updateTime +
                ", remark = " + remark +
        "}";
        }
    }

    /**
     * <p>
     * sys_dictionary
     * </p>
     *
     * @author cc
     * @since 2024-11-20 23:04:58
     */

    @TableName("sys_dictionary")
    @Schema(name = "SysDictionary对象", description = "")
    public static class SysDictionary implements Serializable {

        private static final long serialVersionUID = 1L;


        @Schema(description = "主键")
        @TableId("id")
        private Integer id;

        @Schema(description = "字典名称")
        @TableField("name")
        private String name;

        @Schema(description = "字典值")
        @TableField("value")
        private String value;

        @Schema(description = "状态（0 停用 1 正常）")
        @TableField("status")
        private String status;

        @Schema(description = "创建人")
        @TableField("create_by")
        private String createBy;

        @Schema(description = "创建时间")
        @TableField("create_time")
        private Date createTime;

        @Schema(description = "修改人")
        @TableField("update_by")
        private String updateBy;

        @Schema(description = "修改时间")
        @TableField("update_time")
        private Date updateTime;

        @Schema(description = "备注")
        @TableField("remark")
        private String remark;


        public Integer getId() {
        return id;
        }

        public void setId(Integer id) {
        this.id = id;
        }

        public String getName() {
        return name;
        }

        public void setName(String name) {
        this.name = name;
        }

        public String getValue() {
        return value;
        }

        public void setValue(String value) {
        this.value = value;
        }

        public String getStatus() {
        return status;
        }

        public void setStatus(String status) {
        this.status = status;
        }

        public String getCreateBy() {
        return createBy;
        }

        public void setCreateBy(String createBy) {
        this.createBy = createBy;
        }

        public Date getCreateTime() {
        return createTime;
        }

        public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        }

        public String getUpdateBy() {
        return updateBy;
        }

        public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        }

        public Date getUpdateTime() {
        return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        }

        public String getRemark() {
        return remark;
        }

        public void setRemark(String remark) {
        this.remark = remark;
        }

        @Override
        public String toString() {
        return "SysDictionary{" +
                "id = " + id +
                ", name = " + name +
                ", value = " + value +
                ", status = " + status +
                ", createBy = " + createBy +
                ", createTime = " + createTime +
                ", updateBy = " + updateBy +
                ", updateTime = " + updateTime +
                ", remark = " + remark +
        "}";
        }
    }

    /**
     * <p>
     * sys_menu 菜单表
     * </p>
     *
     * @author cc
     * @since 2024-11-20 23:04:58
     */

    @TableName("sys_menu")
    @Schema(name = "SysMenu对象", description = "菜单表")
    public static class SysMenu implements Serializable {

        private static final long serialVersionUID = 1L;


        @Schema(description = "主键")
        @TableId(value = "id", type = IdType.AUTO)
        private Long id;

        @Schema(description = "父级id")
        @TableField("parent_id")
        private Long parentId;

        @Schema(description = "菜单名称（国际化直接自动转化为对应的key）")
        @TableField("name")
        private String name;

        @Schema(description = "排序")
        @TableField("sort")
        private Integer sort;

        @Schema(description = "请求路径")
        @TableField("path")
        private String path;

        @Schema(description = "组件地址，默认在views/目录下，但不用填写views/")
        @TableField("component")
        private String component;

        @Schema(description = "图标")
        @TableField("icon")
        private String icon;

        @Schema(description = "标题")
        @TableField("title")
        private String title;

        @Schema(description = "菜单类型（0 菜单 1 目录）")
        @TableField("type")
        private String type;

        @Schema(description = "是否登录访问（默认此项不填 0否 1是）")
        @TableField("auth")
        private String auth;

        @Schema(description = "是否是连接（0否 1是）与iframe互斥")
        @TableField("isLink")
        private String isLink;

        @Schema(description = "是否是内嵌（0否 1是）与link互斥")
        @TableField("isIframe")
        private String isIframe;

        @Schema(description = "link或者iframe时，访问地址")
        @TableField("address")
        private String address;

        @Schema(description = "是否隐藏路由（0否 1是）")
        @TableField("isHide")
        private String isHide;

        @Schema(description = "是否隐藏子路由（0否 1是）")
        @TableField("isHideSubMenu")
        private String isHideSubMenu;

        @Schema(description = "是否为手机端（0否 1是）")
        @TableField("isMobile")
        private String isMobile;

        @Schema(description = "创建人")
        @TableField("create_by")
        private String createBy;

        @Schema(description = "创建时间")
        @TableField("create_time")
        private Date createTime;

        @Schema(description = "修改人")
        @TableField("update_by")
        private String updateBy;

        @Schema(description = "修改时间")
        @TableField("update_time")
        private Date updateTime;

        @Schema(description = "备注")
        @TableField("remark")
        private String remark;


        public Long getId() {
        return id;
        }

        public void setId(Long id) {
        this.id = id;
        }

        public Long getParentId() {
        return parentId;
        }

        public void setParentId(Long parentId) {
        this.parentId = parentId;
        }

        public String getName() {
        return name;
        }

        public void setName(String name) {
        this.name = name;
        }

        public Integer getSort() {
        return sort;
        }

        public void setSort(Integer sort) {
        this.sort = sort;
        }

        public String getPath() {
        return path;
        }

        public void setPath(String path) {
        this.path = path;
        }

        public String getComponent() {
        return component;
        }

        public void setComponent(String component) {
        this.component = component;
        }

        public String getIcon() {
        return icon;
        }

        public void setIcon(String icon) {
        this.icon = icon;
        }

        public String getTitle() {
        return title;
        }

        public void setTitle(String title) {
        this.title = title;
        }

        public String getType() {
        return type;
        }

        public void setType(String type) {
        this.type = type;
        }

        public String getAuth() {
        return auth;
        }

        public void setAuth(String auth) {
        this.auth = auth;
        }

        public String getIsLink() {
        return isLink;
        }

        public void setIsLink(String isLink) {
        this.isLink = isLink;
        }

        public String getIsIframe() {
        return isIframe;
        }

        public void setIsIframe(String isIframe) {
        this.isIframe = isIframe;
        }

        public String getAddress() {
        return address;
        }

        public void setAddress(String address) {
        this.address = address;
        }

        public String getIsHide() {
        return isHide;
        }

        public void setIsHide(String isHide) {
        this.isHide = isHide;
        }

        public String getIsHideSubMenu() {
        return isHideSubMenu;
        }

        public void setIsHideSubMenu(String isHideSubMenu) {
        this.isHideSubMenu = isHideSubMenu;
        }

        public String getIsMobile() {
        return isMobile;
        }

        public void setIsMobile(String isMobile) {
        this.isMobile = isMobile;
        }

        public String getCreateBy() {
        return createBy;
        }

        public void setCreateBy(String createBy) {
        this.createBy = createBy;
        }

        public Date getCreateTime() {
        return createTime;
        }

        public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        }

        public String getUpdateBy() {
        return updateBy;
        }

        public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        }

        public Date getUpdateTime() {
        return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        }

        public String getRemark() {
        return remark;
        }

        public void setRemark(String remark) {
        this.remark = remark;
        }

        @Override
        public String toString() {
        return "SysMenu{" +
                "id = " + id +
                ", parentId = " + parentId +
                ", name = " + name +
                ", sort = " + sort +
                ", path = " + path +
                ", component = " + component +
                ", icon = " + icon +
                ", title = " + title +
                ", type = " + type +
                ", auth = " + auth +
                ", isLink = " + isLink +
                ", isIframe = " + isIframe +
                ", address = " + address +
                ", isHide = " + isHide +
                ", isHideSubMenu = " + isHideSubMenu +
                ", isMobile = " + isMobile +
                ", createBy = " + createBy +
                ", createTime = " + createTime +
                ", updateBy = " + updateBy +
                ", updateTime = " + updateTime +
                ", remark = " + remark +
        "}";
        }
    }

    /**
     * <p>
     * sys_post 岗位表
     * </p>
     *
     * @author cc
     * @since 2024-11-20 23:04:58
     */

    @TableName("sys_post")
    @Schema(name = "SysPost对象", description = "岗位表")
    public static class SysPost implements Serializable {

        private static final long serialVersionUID = 1L;


        @Schema(description = "岗位 id")
        @TableId(value = "id", type = IdType.AUTO)
        private Long id;

        @Schema(description = "key")
        @TableField("code")
        private String code;

        @Schema(description = "名称")
        @TableField("name")
        private String name;

        @Schema(description = "排序")
        @TableField("sort")
        private Integer sort;

        @Schema(description = "状态（1正常 0停用 9删除）")
        @TableField("status")
        private String status;

        @Schema(description = "创建人")
        @TableField("create_by")
        private String createBy;

        @Schema(description = "创建时间")
        @TableField("create_time")
        private Date createTime;

        @Schema(description = "修改人")
        @TableField("update_by")
        private String updateBy;

        @Schema(description = "修改时间")
        @TableField("update_time")
        private Date updateTime;

        @Schema(description = "备注")
        @TableField("remark")
        private String remark;


        public Long getId() {
        return id;
        }

        public void setId(Long id) {
        this.id = id;
        }

        public String getCode() {
        return code;
        }

        public void setCode(String code) {
        this.code = code;
        }

        public String getName() {
        return name;
        }

        public void setName(String name) {
        this.name = name;
        }

        public Integer getSort() {
        return sort;
        }

        public void setSort(Integer sort) {
        this.sort = sort;
        }

        public String getStatus() {
        return status;
        }

        public void setStatus(String status) {
        this.status = status;
        }

        public String getCreateBy() {
        return createBy;
        }

        public void setCreateBy(String createBy) {
        this.createBy = createBy;
        }

        public Date getCreateTime() {
        return createTime;
        }

        public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        }

        public String getUpdateBy() {
        return updateBy;
        }

        public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        }

        public Date getUpdateTime() {
        return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        }

        public String getRemark() {
        return remark;
        }

        public void setRemark(String remark) {
        this.remark = remark;
        }

        @Override
        public String toString() {
        return "SysPost{" +
                "id = " + id +
                ", code = " + code +
                ", name = " + name +
                ", sort = " + sort +
                ", status = " + status +
                ", createBy = " + createBy +
                ", createTime = " + createTime +
                ", updateBy = " + updateBy +
                ", updateTime = " + updateTime +
                ", remark = " + remark +
        "}";
        }
    }

    /**
     * <p>
     * sys_role 角色表
     * </p>
     *
     * @author cc
     * @since 2024-11-20 23:04:58
     */

    @TableName("sys_role")
    @Schema(name = "SysRole对象", description = "角色表")
    public static class SysRole implements Serializable {

        private static final long serialVersionUID = 1L;


        @Schema(description = "角色 id")
        @TableId(value = "id", type = IdType.AUTO)
        private Long id;

        @Schema(description = "名称")
        @TableField("name")
        private String name;

        @Schema(description = "角色key")
        @TableField("code")
        private String code;

        @Schema(description = "排序")
        @TableField("sort")
        private Integer sort;

        @Schema(description = "权限标识[C,R,U,D]")
        @TableField("permissions")
        private String permissions;

        @Schema(description = "数据权限（1全部 2当前及以下 3当前）")
        @TableField("data_scope")
        private String dataScope;

        @Schema(description = "删除标识（0删除 1未删除）")
        @TableField("del_flag")
        private String delFlag;

        @Schema(description = "状态（1正常 0停用）")
        @TableField("status")
        private String status;

        @Schema(description = "创建人")
        @TableField("create_by")
        private String createBy;

        @Schema(description = "创建时间")
        @TableField("create_time")
        private Date createTime;

        @Schema(description = "修改人")
        @TableField("update_by")
        private String updateBy;

        @Schema(description = "修改时间")
        @TableField("update_time")
        private Date updateTime;

        @Schema(description = "备注")
        @TableField("remark")
        private String remark;


        public Long getId() {
        return id;
        }

        public void setId(Long id) {
        this.id = id;
        }

        public String getName() {
        return name;
        }

        public void setName(String name) {
        this.name = name;
        }

        public String getCode() {
        return code;
        }

        public void setCode(String code) {
        this.code = code;
        }

        public Integer getSort() {
        return sort;
        }

        public void setSort(Integer sort) {
        this.sort = sort;
        }

        public String getPermissions() {
        return permissions;
        }

        public void setPermissions(String permissions) {
        this.permissions = permissions;
        }

        public String getDataScope() {
        return dataScope;
        }

        public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
        }

        public String getDelFlag() {
        return delFlag;
        }

        public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
        }

        public String getStatus() {
        return status;
        }

        public void setStatus(String status) {
        this.status = status;
        }

        public String getCreateBy() {
        return createBy;
        }

        public void setCreateBy(String createBy) {
        this.createBy = createBy;
        }

        public Date getCreateTime() {
        return createTime;
        }

        public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        }

        public String getUpdateBy() {
        return updateBy;
        }

        public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        }

        public Date getUpdateTime() {
        return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        }

        public String getRemark() {
        return remark;
        }

        public void setRemark(String remark) {
        this.remark = remark;
        }

        @Override
        public String toString() {
        return "SysRole{" +
                "id = " + id +
                ", name = " + name +
                ", code = " + code +
                ", sort = " + sort +
                ", permissions = " + permissions +
                ", dataScope = " + dataScope +
                ", delFlag = " + delFlag +
                ", status = " + status +
                ", createBy = " + createBy +
                ", createTime = " + createTime +
                ", updateBy = " + updateBy +
                ", updateTime = " + updateTime +
                ", remark = " + remark +
        "}";
        }
    }

    /**
     * <p>
     * sys_role_menu 角色菜单
     * </p>
     *
     * @author cc
     * @since 2024-11-20 23:04:58
     */

    @TableName("sys_role_menu")
    @Schema(name = "SysRoleMenu对象", description = "角色菜单")
    public static class SysRoleMenu implements Serializable {

        private static final long serialVersionUID = 1L;


        @Schema(description = "主键")
        @TableId("id")
        private Long id;

        @Schema(description = "主键")
        @TableId("role_id")
        private Long roleId;

        @Schema(description = "主键")
        @TableId("menu_id")
        private Long menuId;


        public Long getId() {
        return id;
        }

        public void setId(Long id) {
        this.id = id;
        }

        public Long getRoleId() {
        return roleId;
        }

        public void setRoleId(Long roleId) {
        this.roleId = roleId;
        }

        public Long getMenuId() {
        return menuId;
        }

        public void setMenuId(Long menuId) {
        this.menuId = menuId;
        }

        @Override
        public String toString() {
        return "SysRoleMenu{" +
                "id = " + id +
                ", roleId = " + roleId +
                ", menuId = " + menuId +
        "}";
        }
    }

    /**
     * <p>
     * sys_user 用户表
     * </p>
     *
     * @author cc
     * @since 2024-11-20 23:04:58
     */

    @TableName("sys_user")
    @Schema(name = "SysUser对象", description = "用户表")
    public static class SysUser implements Serializable {

        private static final long serialVersionUID = 1L;


        @Schema(description = "用户 id")
        @TableId(value = "id", type = IdType.AUTO)
        private Long id;

        @Schema(description = "用户登陆名")
        @TableField("user_name")
        private String userName;

        @Schema(description = "登陆密码")
        @TableField("password")
        private String password;

        @Schema(description = "昵称")
        @TableField("nick_name")
        private String nickName;

        @Schema(description = "真实姓名")
        @TableField("real_name")
        private String realName;

        @Schema(description = "头像")
        @TableField("avatar")
        private String avatar;

        @Schema(description = "邮箱")
        @TableField("email")
        private String email;

        @Schema(description = "手机号")
        @TableField("phone")
        private String phone;

        @Schema(description = "性别（0女 1男 2未知）")
        @TableField("sex")
        private String sex;

        @Schema(description = "年龄")
        @TableField("age")
        private String age;

        @Schema(description = "地址")
        @TableField("address")
        private String address;

        @Schema(description = "状态（1正常 0停用 9删除）")
        @TableField("status")
        private String status;

        @Schema(description = "最后登陆ip")
        @TableField("login_ip")
        private String loginIp;

        @Schema(description = "最后登陆地址")
        @TableField("login_address")
        private String loginAddress;

        @Schema(description = "最后登陆设备")
        @TableField("login_info")
        private String loginInfo;

        @Schema(description = "最后登陆时间")
        @TableField("login_time")
        private Date loginTime;

        @Schema(description = "密码最后修改时间")
        @TableField("pwd_update_time")
        private Date pwdUpdateTime;

        @Schema(description = "创建人")
        @TableField("create_by")
        private String createBy;

        @Schema(description = "创建时间")
        @TableField("create_time")
        private Date createTime;

        @Schema(description = "修改人")
        @TableField("update_by")
        private String updateBy;

        @Schema(description = "修改时间")
        @TableField("update_time")
        private Date updateTime;

        @Schema(description = "备注")
        @TableField("remark")
        private String remark;


        public Long getId() {
        return id;
        }

        public void setId(Long id) {
        this.id = id;
        }

        public String getUserName() {
        return userName;
        }

        public void setUserName(String userName) {
        this.userName = userName;
        }

        public String getPassword() {
        return password;
        }

        public void setPassword(String password) {
        this.password = password;
        }

        public String getNickName() {
        return nickName;
        }

        public void setNickName(String nickName) {
        this.nickName = nickName;
        }

        public String getRealName() {
        return realName;
        }

        public void setRealName(String realName) {
        this.realName = realName;
        }

        public String getAvatar() {
        return avatar;
        }

        public void setAvatar(String avatar) {
        this.avatar = avatar;
        }

        public String getEmail() {
        return email;
        }

        public void setEmail(String email) {
        this.email = email;
        }

        public String getPhone() {
        return phone;
        }

        public void setPhone(String phone) {
        this.phone = phone;
        }

        public String getSex() {
        return sex;
        }

        public void setSex(String sex) {
        this.sex = sex;
        }

        public String getAge() {
        return age;
        }

        public void setAge(String age) {
        this.age = age;
        }

        public String getAddress() {
        return address;
        }

        public void setAddress(String address) {
        this.address = address;
        }

        public String getStatus() {
        return status;
        }

        public void setStatus(String status) {
        this.status = status;
        }

        public String getLoginIp() {
        return loginIp;
        }

        public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
        }

        public String getLoginAddress() {
        return loginAddress;
        }

        public void setLoginAddress(String loginAddress) {
        this.loginAddress = loginAddress;
        }

        public String getLoginInfo() {
        return loginInfo;
        }

        public void setLoginInfo(String loginInfo) {
        this.loginInfo = loginInfo;
        }

        public Date getLoginTime() {
        return loginTime;
        }

        public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
        }

        public Date getPwdUpdateTime() {
        return pwdUpdateTime;
        }

        public void setPwdUpdateTime(Date pwdUpdateTime) {
        this.pwdUpdateTime = pwdUpdateTime;
        }

        public String getCreateBy() {
        return createBy;
        }

        public void setCreateBy(String createBy) {
        this.createBy = createBy;
        }

        public Date getCreateTime() {
        return createTime;
        }

        public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        }

        public String getUpdateBy() {
        return updateBy;
        }

        public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
        }

        public Date getUpdateTime() {
        return updateTime;
        }

        public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        }

        public String getRemark() {
        return remark;
        }

        public void setRemark(String remark) {
        this.remark = remark;
        }

        @Override
        public String toString() {
        return "SysUser{" +
                "id = " + id +
                ", userName = " + userName +
                ", password = " + password +
                ", nickName = " + nickName +
                ", realName = " + realName +
                ", avatar = " + avatar +
                ", email = " + email +
                ", phone = " + phone +
                ", sex = " + sex +
                ", age = " + age +
                ", address = " + address +
                ", status = " + status +
                ", loginIp = " + loginIp +
                ", loginAddress = " + loginAddress +
                ", loginInfo = " + loginInfo +
                ", loginTime = " + loginTime +
                ", pwdUpdateTime = " + pwdUpdateTime +
                ", createBy = " + createBy +
                ", createTime = " + createTime +
                ", updateBy = " + updateBy +
                ", updateTime = " + updateTime +
                ", remark = " + remark +
        "}";
        }
    }
}
