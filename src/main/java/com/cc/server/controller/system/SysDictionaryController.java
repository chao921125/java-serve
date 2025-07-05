package com.cc.server.controller.system;

import com.cc.server.entity.system.SysDictionary;
import com.cc.server.service.system.SysDictionaryService;
import com.cc.frame.core.PageRequest;
import com.cc.frame.core.PageResult;
import com.cc.frame.core.BaseController;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * <p>
 * 字典管理 前端控制器
 * </p>
 *
 * @author cc
 * @since 2024-12-05 10:57:08
 */
@Tag(name = "字典管理", description = "字典管理接口")
@RestController
@RequestMapping("/api-admin/sys-dictionary")
public class SysDictionaryController extends BaseController<SysDictionary, SysDictionaryService> {
    @Resource
    private SysDictionaryService dictionaryService;

    @Override
    protected SysDictionaryService getService() {
        return dictionaryService;
    }

    @Override
    protected SysDictionary doGetById(Long id) {
        // 字典主键为 Integer，需做类型转换
        return dictionaryService.selectSysDictionaryById(id.intValue());
    }

    @Override
    protected boolean doAdd(SysDictionary entity) {
        return dictionaryService.insertSysDictionary(entity) > 0;
    }

    @Override
    protected boolean doDelete(Long id) {
        return dictionaryService.deleteSysDictionaryById(id.intValue()) > 0;
    }

    @Override
    protected boolean doUpdate(SysDictionary entity) {
        return dictionaryService.updateSysDictionaryById(entity) > 0;
    }

    @Override
    protected List<SysDictionary> doList() {
        return dictionaryService.selectAllSysDictionary();
    }

    @Override
    protected PageResult<SysDictionary> doPage(PageRequest pageRequest) {
        return dictionaryService.pageSysDictionary(pageRequest);
    }
}
