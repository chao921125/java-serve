package com.cc.core.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 用户岗位关联
 */
@Data
@EqualsAndHashCode
@TableName("sys_user_post")
public class SysUserPost implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户 ID */
    private Long userId;

    /** 岗位 ID */
    private Long postId;
}
