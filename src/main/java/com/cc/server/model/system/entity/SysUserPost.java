package com.cc.server.model.system.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * sys_user_post 用户岗位 用户1-N岗位
 * </p>
 * 
 * @author cc
 * @since 2024-12-05 10:57:08
 */

@TableName("sys_user_post")
public class SysUserPost implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

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