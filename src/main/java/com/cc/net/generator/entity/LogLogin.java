package entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author cc
 * @since 2023-45-09 17:10:992
 */
@Getter
@Setter
@TableName("log_login")
@ApiModel(value = "LogLogin对象", description = "")
public class LogLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户 id")
    @TableField("user_id")
    private String userId;

    @ApiModelProperty("用户名")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty("登录 IP")
    @TableField("ip")
    private String ip;

    @ApiModelProperty("真实IP")
    @TableField("ip_real")
    private String ipReal;

    @ApiModelProperty("登录时间")
    @TableField("login_time")
    private Date loginTime;

    @ApiModelProperty("地址")
    @TableField("address")
    private String address;

    @ApiModelProperty("设备信息")
    @TableField("system")
    private String system;

    @ApiModelProperty("登录状态（0 失败，1 成功）")
    @TableField("status")
    private String status;
}
