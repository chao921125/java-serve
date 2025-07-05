package com.cc.server.controller.system;

import com.cc.server.entity.system.SysUserPost;
import com.cc.server.service.system.SysUserPostService;
import com.cc.frame.core.PageRequest;
import com.cc.frame.core.PageResult;
import com.cc.frame.core.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 * 用户岗位 用户1-N岗位 前端控制器
 * </p>
 *
 * @author cc
 * @since 2024-12-05 10:57:08
 */
@Tag(name = "用户岗位管理", description = "用户岗位管理接口")
@RestController
@RequestMapping("/api-admin/sys-user-post")
public class SysUserPostController extends BaseController<SysUserPost, SysUserPostService> {
    @Resource
    private SysUserPostService userPostService;

    @Override
    protected SysUserPostService getService() {
        return userPostService;
    }

    @Override
    protected SysUserPost doGetById(Long id) {
        return userPostService.selectSysUserPostById(id);
    }

    @Override
    protected boolean doAdd(SysUserPost entity) {
        return userPostService.insertSysUserPost(entity) > 0;
    }

    @Override
    protected boolean doDelete(Long id) {
        return userPostService.deleteSysUserPostById(id) > 0;
    }

    @Override
    protected boolean doUpdate(SysUserPost entity) {
        return userPostService.updateSysUserPostById(entity) > 0;
    }

    @Override
    protected List<SysUserPost> doList() {
        return userPostService.selectAllSysUserPost();
    }

    @Override
    protected PageResult<SysUserPost> doPage(PageRequest pageRequest) {
        return userPostService.pageSysUserPost(pageRequest);
    }
}
