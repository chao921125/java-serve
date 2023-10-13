package com.cc.net.entity.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author cc
 * @since 2023-47-13 15:10:800
 */
@Getter
@Setter
@TableName("sys_department")
@Data
@Schema(description = "SysDepartment对象")
public class SysDepartment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
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
