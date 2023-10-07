package com.cc.net.entity.log;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author cc
 * @since 2023-54-07 14:10:812
 */
@Getter
@Setter
@TableName("log_login")
@ApiModel(value = "LogLogin对象", description = "")
public class LogLogin implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("用户 id")
    private String userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("用户昵称")
    private String userNickName;

    @ApiModelProperty("登录 IP")
    private String ip;

    @ApiModelProperty("真实IP")
    private String ipReal;

    @ApiModelProperty("登录时间")
    private LocalDateTime loginTime;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("设备信息")
    private String system;
}
