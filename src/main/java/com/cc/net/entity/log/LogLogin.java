package com.cc.net.entity.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author cc
 * @since 2023-42-08 14:10:202
 */
@Getter
@Setter
@TableName("log_login")
@Data
@Schema(description = "LogLogin对象")
public class LogLogin implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "用户 id")
    @TableField("user_id")
    private String userId;

    @Schema(description = "用户名")
    @TableField("user_name")
    private String userName;

    @Schema(description = "登录 IP")
    @TableField("ip")
    private String ip;

    @Schema(description = "真实IP")
    @TableField("ip_real")
    private String ipReal;

    @Schema(description = "登录时间")
    @TableField("login_time")
    private Date loginTime;

    @Schema(description = "地址")
    @TableField("address")
    private String address;

    @Schema(description = "设备信息")
    @TableField("system")
    private String system;

    @Schema(description = "登录状态（0 失败，1 成功）")
    @TableField("status")
    private String status;
}
