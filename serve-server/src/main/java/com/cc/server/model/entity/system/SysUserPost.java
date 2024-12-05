package com.cc.server.model.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>
 * sys_user_post 用户岗位 用户1-N岗位
 * </p>
 * 
 * @author cc
 * @since 2024-11-22 13:40:55
 */

@TableName("sys_user_post")
@Schema(name = "SysUserPost对象", description = "用户岗位 用户1-N岗位")
public class SysUserPost implements Serializable {

    private static final long serialVersionUID = 1L;


    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户 id")
    @TableField("user_id")
    private Long userId;

    @Schema(description = "岗位 id")
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
}