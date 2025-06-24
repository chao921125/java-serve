package entity;

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

@Schema(name = "OnlineUserVO对象", description = "")
public class OnlineUserVO implements Serializable {

private static final long serialVersionUID = 1L;


            @Schema(description = "主键")
    private Long id;

            @Schema(description = "-")
    private Long userId;

            @Schema(description = "-")
    private String userName;

            @Schema(description = "-")
    private String token;

            @Schema(description = "-")
    private Date loginTime;

            @Schema(description = "-")
    private Date lastActiveTime;

            @Schema(description = "-")
    private String ip;

            @Schema(description = "-")
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

    @Override
    public String toString() {
    return "OnlineUserVO{" +
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