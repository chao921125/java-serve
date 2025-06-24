package entity;

import java.io.Serializable;
import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;

/**
* <p>
    * sys_user_post 用户岗位 用户1-N岗位
    * </p>
* 
* @author cc
* @since 2025-06-23 18:27:36
*/

@Schema(name = "SysUserPostVO对象", description = "用户岗位 用户1-N岗位")
public class SysUserPostVO implements Serializable {

private static final long serialVersionUID = 1L;


            @Schema(description = "主键")
    private Long id;

            @Schema(description = "用户 id")
    private Long userId;

            @Schema(description = "岗位 id")
    private Long postId;


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

        public Long getPostId() {
        return postId;
        }

            public void setPostId(Long postId) {
        this.postId = postId;
        }

    @Override
    public String toString() {
    return "SysUserPostVO{" +
            "id = " + id +
            ", userId = " + userId +
            ", postId = " + postId +
    "}";
    }
}