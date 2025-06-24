package com.cc.server.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.frame.utils.BeanCopyUtil;
import com.cc.server.entity.system.SysUser;
import com.cc.server.mapper.system.SysUserMapper;
import com.cc.server.vo.system.SysUserVO;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.cc.server.service.system.SysUserService;

import java.util.ArrayList;
import java.util.List;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
	@Resource
	private SysUserMapper sysUserMapper;

	/**
	 * 查询表sys_user所有信息
	 */
	@Override
	public List<SysUserVO> selectAllSysUser() {
		List<SysUser> sysUserList = sysUserMapper.selectAllSysUser();
		return BeanCopyUtil.convertList(sysUserList, SysUserVO.class);
	}

	/**
	 * 根据主键id查询表sys_user信息
	 *
	 * @param id
	 */
	@Override
	public SysUserVO selectSysUserById(@Param("id") Long id) {
		SysUser sysUser = sysUserMapper.selectSysUserById(id);
		return BeanCopyUtil.convert(sysUser, SysUserVO.class);
	}

	/**
	 * 根据条件查询表sys_user信息
	 *
	 * @param sysUserVO
	 */
	@Override
	public List<SysUserVO> selectSysUserByCondition(SysUserVO sysUserVO) {
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(sysUserVO, sysUser);
		List<SysUser> sysUserList = sysUserMapper.selectSysUserByCondition(sysUser);
		return BeanCopyUtil.convertList(sysUserList, SysUserVO.class);
	}

	/**
	 * 根据主键id查询表sys_user信息
	 *
	 * @param id
	 */
	@Override
	public Integer deleteSysUserById(@Param("id") Long id) {
		return sysUserMapper.deleteSysUserById(id);
	}

	/**
	 * 根据主键id更新表sys_user信息
	 *
	 * @param sysUserVO
	 */
	@Override
	public Integer updateSysUserById(SysUserVO sysUserVO) {
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(sysUserVO, sysUser);
		if (sysUserVO.getPassword() != null && !sysUserVO.getPassword().isEmpty()) {
			sysUser.setPassword(md5(sysUserVO.getPassword()));
		}
		return sysUserMapper.updateSysUserById(sysUser);
	}

	/**
	 * 新增表sys_user信息
	 *
	 * @param sysUserVO
	 */
	@Override
	public Integer insertSysUser(SysUserVO sysUserVO) {
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(sysUserVO, sysUser);
		sysUser.setPassword(md5(sysUserVO.getPassword()));
		return sysUserMapper.insertSysUser(sysUser);
	}

	/**
	 * 新增表sys_user信息
	 *
	 * @param sysUserVO
	 */
	@Override
	public SysUserVO getUserByNameEmailPhone(SysUserVO sysUserVO) {
		SysUser dbUser = null;
		if (sysUserVO.getUserName() != null) {
			SysUser query = new SysUser();
			query.setUserName(sysUserVO.getUserName());
			dbUser = sysUserMapper.loginSysUser(query);
		}
		if (dbUser == null && sysUserVO.getEmail() != null) {
			SysUser query = new SysUser();
			query.setEmail(sysUserVO.getEmail());
			dbUser = sysUserMapper.loginSysUser(query);
		}
		if (dbUser == null && sysUserVO.getPhone() != null) {
			SysUser query = new SysUser();
			query.setPhone(sysUserVO.getPhone());
			dbUser = sysUserMapper.loginSysUser(query);
		}
		if (dbUser == null) {
			return null;
		}
		if (sysUserVO.getPassword() != null && !md5(sysUserVO.getPassword()).equals(dbUser.getPassword())) {
			return null;
		}
		return BeanCopyUtil.convert(dbUser, SysUserVO.class);
	}

	public Integer logicDeleteSysUserById(Long id) {
		SysUser user = sysUserMapper.selectSysUserById(id);
		if (user == null) return 0;
		user.setStatus("9");
		return sysUserMapper.updateSysUserById(user);
	}

	private String md5(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
			BigInteger no = new BigInteger(1, messageDigest);
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
