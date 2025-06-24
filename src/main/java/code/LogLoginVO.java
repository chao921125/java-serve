package entity;

import java.io.Serializable;
import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;

/**
* <p>
    * log_login 
    * </p>
* 
* @author cc
* @since 2025-06-23 18:27:36
*/

@Schema(name = "LogLoginVO对象", description = "")
public class LogLoginVO implements Serializable {

private static final long serialVersionUID = 1L;


            @Schema(description = "主键")
    private Long id;

            @Schema(description = "用户 id")
    private String userId;

            @Schema(description = "用户名")
    private String userName;

            @Schema(description = "登录 IP")
    private String ip;

            @Schema(description = "真实IP")
    private String ipReal;

            @Schema(description = "登录时间")
    private Date loginTime;

            @Schema(description = "地址")
    private String address;

            @Schema(description = "设备信息")
    private String system;

            @Schema(description = "登录状态（0 失败，1 成功）")
    private String status;

            @Schema(description = "-")
    private String message;

            @Schema(description = "-")
    private String exceptionMsg;


        public Long getId() {
        return id;
        }

            public void setId(Long id) {
        this.id = id;
        }

        public String getUserId() {
        return userId;
        }

            public void setUserId(String userId) {
        this.userId = userId;
        }

        public String getUserName() {
        return userName;
        }

            public void setUserName(String userName) {
        this.userName = userName;
        }

        public String getIp() {
        return ip;
        }

            public void setIp(String ip) {
        this.ip = ip;
        }

        public String getIpReal() {
        return ipReal;
        }

            public void setIpReal(String ipReal) {
        this.ipReal = ipReal;
        }

        public Date getLoginTime() {
        return loginTime;
        }

            public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
        }

        public String getAddress() {
        return address;
        }

            public void setAddress(String address) {
        this.address = address;
        }

        public String getSystem() {
        return system;
        }

            public void setSystem(String system) {
        this.system = system;
        }

        public String getStatus() {
        return status;
        }

            public void setStatus(String status) {
        this.status = status;
        }

        public String getMessage() {
        return message;
        }

            public void setMessage(String message) {
        this.message = message;
        }

        public String getExceptionMsg() {
        return exceptionMsg;
        }

            public void setExceptionMsg(String exceptionMsg) {
        this.exceptionMsg = exceptionMsg;
        }

    @Override
    public String toString() {
    return "LogLoginVO{" +
            "id = " + id +
            ", userId = " + userId +
            ", userName = " + userName +
            ", ip = " + ip +
            ", ipReal = " + ipReal +
            ", loginTime = " + loginTime +
            ", address = " + address +
            ", system = " + system +
            ", status = " + status +
            ", message = " + message +
            ", exceptionMsg = " + exceptionMsg +
    "}";
    }
}