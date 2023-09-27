package com.cc.javaserve.domain.system;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author cc
 * @since 2023-58-27 13:09:219
 */
@Getter
@Setter
@TableName("sys_role")
@ApiModel(value = "SysRole对象", description = "角色表")
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("角色key")
    private String key;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("权限标识[C,R,U,D]")
    private String permissions;

    @ApiModelProperty("数据权限（1全部 2当前及以下 3当前）")
    private String dataScope;

    @ApiModelProperty("删除标识（0删除 1未删除）")
    private String delFlag;

    @ApiModelProperty("状态（1正常 0停用）")
    private String status;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("备注")
    private String remark;
}
