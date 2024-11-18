package com.cc.serve.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

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
}
