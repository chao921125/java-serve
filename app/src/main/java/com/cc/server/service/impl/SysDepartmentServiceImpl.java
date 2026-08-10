package com.cc.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.core.entity.SysDepartment;
import com.cc.core.mapper.SysDepartmentMapper;
import com.cc.core.service.SysDepartmentService;
import com.cc.core.vo.DeptTreeVO;
import com.cc.framework.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门服务实现
 */
@Slf4j
@Service
public class SysDepartmentServiceImpl extends ServiceImpl<SysDepartmentMapper, SysDepartment> implements SysDepartmentService {

    @Override
    public List<SysDepartment> getDeptTree() {
        List<SysDepartment> all = baseMapper.selectAllOrdered();
        return buildTree(all);
    }

    @Override
    public List<Long> getChildDeptIds(Long deptId) {
        List<SysDepartment> all = baseMapper.selectAllOrdered();
        List<Long> result = new ArrayList<>();
        result.add(deptId);
        collectChildIds(deptId, all, result);
        return result;
    }

    /**
     * 获取部门树 VO
     */
    public List<DeptTreeVO> getDeptTreeVO() {
        List<SysDepartment> all = baseMapper.selectAllOrdered();
        List<SysDepartment> tree = buildTree(all);
        return convertToVO(tree);
    }

    /**
     * 新增部门
     */
    @Transactional
    public boolean addDept(SysDepartment dept) {
        if (dept.getParentId() == null) {
            dept.setParentId(0L);
        }
        // 构建 ancestors
        if (dept.getParentId() == 0) {
            dept.setAncestors("0");
        } else {
            SysDepartment parent = getById(dept.getParentId());
            if (parent == null) {
                throw ServiceException.badRequest("父部门不存在");
            }
            dept.setAncestors(parent.getAncestors() + "," + parent.getId());
        }
        return save(dept);
    }

    /**
     * 修改部门
     */
    @Transactional
    public boolean updateDept(SysDepartment dept) {
        if (dept.getId() == null) {
            throw ServiceException.badRequest("部门ID不能为空");
        }
        SysDepartment existing = getById(dept.getId());
        if (existing == null) {
            throw ServiceException.notFound("部门不存在");
        }
        // 如果修改了父部门，需要更新 ancestors
        if (dept.getParentId() != null && !dept.getParentId().equals(existing.getParentId())) {
            if (dept.getParentId() == 0) {
                dept.setAncestors("0");
            } else {
                SysDepartment parent = getById(dept.getParentId());
                if (parent == null) {
                    throw ServiceException.badRequest("父部门不存在");
                }
                // 不允许将自己设为子部门的父级
                if (dept.getParentId().equals(dept.getId())) {
                    throw ServiceException.badRequest("不能将自身设为父部门");
                }
                dept.setAncestors(parent.getAncestors() + "," + parent.getId());
            }
            // 更新所有子部门的 ancestors
            updateChildAncestors(dept.getId(), dept.getAncestors());
        }
        return updateById(dept);
    }

    /**
     * 删除部门（检查是否有子部门）
     */
    @Transactional
    public boolean deleteDept(Long deptId) {
        LambdaQueryWrapper<SysDepartment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDepartment::getParentId, deptId);
        long count = count(wrapper);
        if (count > 0) {
            throw ServiceException.badRequest("该部门下还有子部门，不允许删除");
        }
        return removeById(deptId);
    }

    // ---- private ----

    private List<SysDepartment> buildTree(List<SysDepartment> all) {
        List<SysDepartment> parents = all.stream()
                .filter(d -> d.getParentId() == null || d.getParentId() == 0)
                .collect(Collectors.toList());
        for (SysDepartment parent : parents) {
            parent.setChildren(getChildren(parent.getId(), all));
        }
        return parents;
    }

    private List<SysDepartment> getChildren(Long parentId, List<SysDepartment> all) {
        List<SysDepartment> children = all.stream()
                .filter(d -> parentId.equals(d.getParentId()))
                .collect(Collectors.toList());
        for (SysDepartment child : children) {
            child.setChildren(getChildren(child.getId(), all));
        }
        return children;
    }

    private void collectChildIds(Long parentId, List<SysDepartment> all, List<Long> result) {
        for (SysDepartment dept : all) {
            if (parentId.equals(dept.getParentId()) && !result.contains(dept.getId())) {
                result.add(dept.getId());
                collectChildIds(dept.getId(), all, result);
            }
        }
    }

    private void updateChildAncestors(Long deptId, String ancestors) {
        LambdaQueryWrapper<SysDepartment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysDepartment::getParentId, deptId);
        List<SysDepartment> children = list(wrapper);
        for (SysDepartment child : children) {
            child.setAncestors(ancestors + "," + deptId);
            updateById(child);
            updateChildAncestors(child.getId(), child.getAncestors());
        }
    }

    private List<DeptTreeVO> convertToVO(List<SysDepartment> depts) {
        if (depts == null || depts.isEmpty()) return new ArrayList<>();
        return depts.stream().map(d -> {
            DeptTreeVO vo = new DeptTreeVO();
            BeanUtils.copyProperties(d, vo);
            if (d.getChildren() != null && !d.getChildren().isEmpty()) {
                vo.setChildren(convertToVO(d.getChildren()));
            }
            return vo;
        }).collect(Collectors.toList());
    }
}
