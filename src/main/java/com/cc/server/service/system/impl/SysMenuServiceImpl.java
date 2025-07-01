package com.cc.server.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.server.entity.system.SysMenu;
import com.cc.server.mapper.system.SysMenuMapper;
import org.apache.ibatis.annotations.Param;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.cc.server.service.system.SysMenuService;
import com.cc.server.vo.PageRequest;
import com.cc.server.vo.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
	@Resource
	private SysMenuMapper sysMenuMapper;

	/**
	 * 查询表sys_menu所有信息
	 */
	@Override
	public List<SysMenu> selectAllSysMenu() {
		return sysMenuMapper.selectAllSysMenu();
	}

	/**
	 * 根据主键id查询表sys_menu信息
	 *
	 * @param id
	 */
	@Override
	public SysMenu selectSysMenuById(@Param("id") Long id) {
		return sysMenuMapper.selectSysMenuById(id);
	}

	/**
	 * 根据条件查询表sys_menu信息
	 *
	 * @param sysMenu
	 */
	@Override
	public List<SysMenu> selectSysMenuByCondition(SysMenu sysMenu) {
		return sysMenuMapper.selectSysMenuByCondition(sysMenu);
	}

	/**
	 * 根据主键id查询表sys_menu信息
	 *
	 * @param id
	 */
	@Override
	public Integer deleteSysMenuById(@Param("id") Long id) {
		return sysMenuMapper.deleteSysMenuById(id);
	}

	/**
	 * 根据主键id更新表sys_menu信息
	 *
	 * @param sysMenu
	 */
	@Override
	public Integer updateSysMenuById(SysMenu sysMenu) {
		return sysMenuMapper.updateSysMenuById(sysMenu);
	}

	/**
	 * 新增表sys_menu信息
	 *
	 * @param sysMenu
	 */
	@Override
	public Integer insertSysMenu(SysMenu sysMenu) {
		return sysMenuMapper.insertSysMenu(sysMenu);
	}

	@Override
	public PageResult<SysMenu> pageSysMenu(PageRequest pageRequest) {
		Page<SysMenu> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
		Page<SysMenu> result = this.page(page, new QueryWrapper<>());
		return new PageResult<>(result.getTotal(), result.getRecords());
	}
}
