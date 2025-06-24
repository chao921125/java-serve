package entity;

import java.io.Serializable;
import java.util.Date;
    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableField;
    import com.baomidou.mybatisplus.annotation.TableId;
    import com.baomidou.mybatisplus.annotation.TableName;
    import java.io.Serializable;
    import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;

/**
* <p>
    * sys_menu 菜单表
    * </p>
* 
* @author cc
* @since 2025-06-23 18:27:36
*/
    @TableName("sys_menu")
public class SysMenu implements Serializable {

private static final long serialVersionUID = 1L;


            @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        @TableField("parent_id")
    private Long parentId;

        @TableField("name")
    private String name;

        @TableField("sort")
    private Integer sort;

        @TableField("path")
    private String path;

        @TableField("component")
    private String component;

        @TableField("icon")
    private String icon;

        @TableField("title")
    private String title;

        @TableField("type")
    private String type;

        @TableField("auth")
    private String auth;

        @TableField("isLink")
    private String isLink;

        @TableField("isIframe")
    private String isIframe;

        @TableField("address")
    private String address;

        @TableField("isHide")
    private String isHide;

        @TableField("isHideSubMenu")
    private String isHideSubMenu;

        @TableField("isMobile")
    private String isMobile;

        @TableField("create_by")
    private String createBy;

        @TableField("create_time")
    private Date createTime;

        @TableField("update_by")
    private String updateBy;

        @TableField("update_time")
    private Date updateTime;

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



public SysMenu(Long id,Long parentId,String name,Integer sort,String path,String component,String icon,String title,String type,String auth,String isLink,String isIframe,String address,String isHide,String isHideSubMenu,String isMobile,String createBy,Date createTime,String updateBy,Date updateTime,String remark){
    this.id = id;
    this.parentId = parentId;
    this.name = name;
    this.sort = sort;
    this.path = path;
    this.component = component;
    this.icon = icon;
    this.title = title;
    this.type = type;
    this.auth = auth;
    this.isLink = isLink;
    this.isIframe = isIframe;
    this.address = address;
    this.isHide = isHide;
    this.isHideSubMenu = isHideSubMenu;
    this.isMobile = isMobile;
    this.createBy = createBy;
    this.createTime = createTime;
    this.updateBy = updateBy;
    this.updateTime = updateTime;
    this.remark = remark;
}

public SysMenu(){
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