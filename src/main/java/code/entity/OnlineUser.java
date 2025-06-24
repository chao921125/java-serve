package entity;

import java.io.Serializable;
import java.util.Date;
    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableField;
    import com.baomidou.mybatisplus.annotation.TableId;
    import com.baomidou.mybatisplus.annotation.TableName;
    import java.io.Serializable;
    import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;

/**
* <p>
    * online_user 
    * </p>
* 
* @author cc
* @since 2025-06-23 18:27:36
*/
    @TableName("online_user")
public class OnlineUser implements Serializable {

private static final long serialVersionUID = 1L;


            @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        @TableField("user_id")
    private Long userId;

        @TableField("user_name")
    private String userName;

        @TableField("token")
    private String token;

        @TableField("login_time")
    private Date loginTime;

        @TableField("last_active_time")
    private Date lastActiveTime;

        @TableField("ip")
    private String ip;

        @TableField("status")
    private String status;


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

        public String getUserName() {
        return userName;
        }

            public void setUserName(String userName) {
        this.userName = userName;
        }

        public String getToken() {
        return token;
        }

            public void setToken(String token) {
        this.token = token;
        }

        public Date getLoginTime() {
        return loginTime;
        }

            public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
        }

        public Date getLastActiveTime() {
        return lastActiveTime;
        }

            public void setLastActiveTime(Date lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
        }

        public String getIp() {
        return ip;
        }

            public void setIp(String ip) {
        this.ip = ip;
        }

        public String getStatus() {
        return status;
        }

            public void setStatus(String status) {
        this.status = status;
        }



public OnlineUser(Long id,Long userId,String userName,String token,Date loginTime,Date lastActiveTime,String ip,String status){
    this.id = id;
    this.userId = userId;
    this.userName = userName;
    this.token = token;
    this.loginTime = loginTime;
    this.lastActiveTime = lastActiveTime;
    this.ip = ip;
    this.status = status;
}

public OnlineUser(){
}

    @Override
    public String toString() {
    return "OnlineUser{" +
            "id = " + id +
            ", userId = " + userId +
            ", userName = " + userName +
            ", token = " + token +
            ", loginTime = " + loginTime +
            ", lastActiveTime = " + lastActiveTime +
            ", ip = " + ip +
            ", status = " + status +
    "}";
    }
}