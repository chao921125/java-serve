package com.cc.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.SysMenu;
import com.cc.core.mapper.SysMenuMapper;
import com.cc.core.service.SysMenuService;
import com.cc.core.vo.MenuTreeVO;
import com.cc.framework.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单服务实现
 */
@Slf4j
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public List<SysMenu> getMenuTree() {
        List<SysMenu> all = baseMapper.selectAllOrdered();
        return buildTree(all);
    }

    @Override
    public List<SysMenu> getMenuTreeByUserId(Long userId) {
        List<SysMenu> menus = baseMapper.selectMenusByUserId(userId);
        return buildTree(menus);
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        return baseMapper.selectMenuIdsByRoleId(roleId);
    }

    /**
     * 获取菜单树 VO
     */
    public List<MenuTreeVO> getMenuTreeVO() {
        List<SysMenu> all = baseMapper.selectAllOrdered();
        List<SysMenu> tree = buildTree(all);
        return convertToVO(tree);
    }

    /**
     * 根据用户 ID 获取菜单树 VO
     */
    public List<MenuTreeVO> getMenuTreeVOByUserId(Long userId) {
        List<SysMenu> menus = baseMapper.selectMenusByUserId(userId);
        List<SysMenu> tree = buildTree(menus);
        return convertToVO(tree);
    }

    /**
     * 构建菜单树（只保留目录和菜单，不含按钮）
     */
    private List<SysMenu> buildTree(List<SysMenu> menus) {
        // 先按 type 过滤：目录和菜单保留，按钮不在树中展示
        List<SysMenu> filtered = menus.stream()
                .filter(m -> !"F".equals(m.getType()))
                .collect(Collectors.toList());

        List<SysMenu> parents = filtered.stream()
                .filter(m -> m.getParentId() == null || m.getParentId() == 0)
                .collect(Collectors.toList());

        for (SysMenu parent : parents) {
            parent.setChildren(getChildren(parent.getId(), filtered));
        }
        return parents;
    }

    private List<SysMenu> getChildren(Long parentId, List<SysMenu> all) {
        List<SysMenu> children = all.stream()
                .filter(m -> parentId.equals(m.getParentId()))
                .collect(Collectors.toList());
        for (SysMenu child : children) {
            child.setChildren(getChildren(child.getId(), all));
        }
        return children;
    }

    /**
     * 转换为 VO 树
     */
    private List<MenuTreeVO> convertToVO(List<SysMenu> menus) {
        if (menus == null || menus.isEmpty()) return new ArrayList<>();
        return menus.stream().map(m -> {
            MenuTreeVO vo = new MenuTreeVO();
            BeanUtils.copyProperties(m, vo);
            if (m.getChildren() != null && !m.getChildren().isEmpty()) {
                vo.setChildren(convertToVO(m.getChildren()));
            }
            return vo;
        }).collect(Collectors.toList());
    }

    /**
     * 新增菜单
     */
    @Transactional
    public boolean addMenu(SysMenu menu) {
        if (menu.getParentId() == null) {
            menu.setParentId(0L);
        }
        return save(menu);
    }

    /**
     * 修改菜单
     */
    @Transactional
    public boolean updateMenu(SysMenu menu) {
        if (menu.getId() == null) {
            throw ServiceException.badRequest("菜单ID不能为空");
        }
        return updateById(menu);
    }

    /**
     * 删除菜单（检查是否有子菜单）
     */
    @Transactional
    public boolean deleteMenu(Long menuId) {
        // 检查子菜单
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getParentId, menuId);
        long count = count(wrapper);
        if (count > 0) {
            throw ServiceException.badRequest("该菜单下还有子菜单，不允许删除");
        }
        return removeById(menuId);
    }
}
