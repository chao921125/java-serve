package com.cc.net.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author cc
 * @since 2023-47-13 15:10:981
 */
@Getter
@Setter
@TableName("sys_role")
@Schema(description = "SysRole对象")
public class SysRole implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "名称")
    @TableField("name")
    private String name;

    @Schema(description = "角色key")
    @TableField("key")
    private String key;

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
}
