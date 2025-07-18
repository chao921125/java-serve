package com.cc.server.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cc.frame.utils.BeanCopyUtil;
import com.cc.server.entity.system.SysUser;
import com.cc.server.mapper.system.SysUserMapper;
import com.cc.server.vo.system.SysUserVO;
import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.cc.server.service.system.SysUserService;
import com.cc.frame.core.PageRequest;
import com.cc.frame.core.PageResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

	/**
	 * 新增用户，包含参数校验和业务逻辑
	 */
	public Integer createUser(SysUserVO userVO) {
		String username = userVO.getUserName();
		String password = userVO.getPassword();
		if (username == null || username.trim().isEmpty()) {
			throw new IllegalArgumentException("用户名不能为空");
		}
		if (password == null || password.trim().isEmpty()) {
			throw new IllegalArgumentException("密码不能为空");
		}
		// 假设 User 常量类可用
		if (username.length() < com.cc.frame.constants.User.USERNAME_MIN_LENGTH || username.length() > com.cc.frame.constants.User.USERNAME_MAX_LENGTH) {
			throw new IllegalArgumentException("用户名长度不合法");
		}
		if (password.length() < com.cc.frame.constants.User.PASSWORD_MIN_LENGTH || password.length() > com.cc.frame.constants.User.PASSWORD_MAX_LENGTH) {
			throw new IllegalArgumentException("密码长度不合法");
		}
		// 检查用户是否已存在
		SysUserVO existUser = checkUserExists(username, userVO.getEmail(), userVO.getPhone());
		if (existUser != null) {
			throw new IllegalArgumentException("用户已存在");
		}
		userVO.setStatus(com.cc.frame.constants.User.NORMAL);
		return insertSysUser(userVO);
	}
	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
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
			sysUser.setPassword(passwordEncoder.encode(sysUserVO.getPassword()));
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
		sysUser.setPassword(passwordEncoder.encode(sysUserVO.getPassword()));
		return sysUserMapper.insertSysUser(sysUser);
	}

	/**
	 * 根据用户名、邮箱、手机号查询用户（包含密码验证）
	 * @param sysUserVO 包含用户名、邮箱、手机号和密码的用户信息
	 * @return 用户信息，如果验证失败返回null
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
		if (sysUserVO.getPassword() != null && !passwordEncoder.matches(sysUserVO.getPassword(), dbUser.getPassword())) {
			return null;
		}
		return BeanCopyUtil.convert(dbUser, SysUserVO.class);
	}

	/**
	 * 验证用户登录
	 * @param loginName 登录名（用户名、邮箱或手机号）
	 * @param password 密码
	 * @return 用户信息，如果验证失败返回null
	 */
	@Override
	public SysUserVO validateUserLogin(String loginName, String password) {
		if (loginName == null || password == null) {
			return null;
		}

		// 1. 根据登录名查询用户
		SysUser dbUser = getUserByLoginNameFromDB(loginName);
		if (dbUser == null) {
			return null;
		}

		// 2. 验证密码
		if (!passwordEncoder.matches(password, dbUser.getPassword())) {
			return null;
		}

		// 3. 返回用户信息
		return BeanCopyUtil.convert(dbUser, SysUserVO.class);
	}

	/**
	 * 检查用户是否存在
	 * @param username 用户名
	 * @param email 邮箱
	 * @param phone 手机号
	 * @return 如果用户存在返回用户信息，否则返回null
	 */
	@Override
	public SysUserVO checkUserExists(String username, String email, String phone) {
		SysUser dbUser = null;
		
		// 检查用户名
		if (username != null && !username.trim().isEmpty()) {
			SysUser query = new SysUser();
			query.setUserName(username);
			dbUser = sysUserMapper.loginSysUser(query);
		}
		
		// 检查邮箱
		if (dbUser == null && email != null && !email.trim().isEmpty()) {
			SysUser query = new SysUser();
			query.setEmail(email);
			dbUser = sysUserMapper.loginSysUser(query);
		}
		
		// 检查手机号
		if (dbUser == null && phone != null && !phone.trim().isEmpty()) {
			SysUser query = new SysUser();
			query.setPhone(phone);
			dbUser = sysUserMapper.loginSysUser(query);
		}
		
		return dbUser != null ? BeanCopyUtil.convert(dbUser, SysUserVO.class) : null;
	}

	/**
	 * 根据登录名查询用户（不验证密码）
	 * @param loginName 登录名（用户名、邮箱或手机号）
	 * @return 用户信息，如果不存在返回null
	 */
	@Override
	public SysUserVO getUserByLoginName(String loginName) {
		SysUser dbUser = getUserByLoginNameFromDB(loginName);
		return dbUser != null ? BeanCopyUtil.convert(dbUser, SysUserVO.class) : null;
	}

	/**
	 * 从数据库根据登录名查询用户（私有方法）
	 * @param loginName 登录名
	 * @return 用户实体，如果不存在返回null
	 */
	private SysUser getUserByLoginNameFromDB(String loginName) {
		if (loginName == null || loginName.trim().isEmpty()) {
			return null;
		}

		SysUser dbUser = null;
		
		// 1. 尝试按用户名查询
		SysUser query = new SysUser();
		query.setUserName(loginName);
		dbUser = sysUserMapper.loginSysUser(query);
		
		// 2. 如果用户名不存在，尝试按邮箱查询
		if (dbUser == null) {
			query = new SysUser();
			query.setEmail(loginName);
			dbUser = sysUserMapper.loginSysUser(query);
		}
		
		// 3. 如果邮箱不存在，尝试按手机号查询
		if (dbUser == null) {
			query = new SysUser();
			query.setPhone(loginName);
			dbUser = sysUserMapper.loginSysUser(query);
		}
		
		return dbUser;
	}

	public Integer logicDeleteSysUserById(Long id) {
		SysUser user = sysUserMapper.selectSysUserById(id);
		if (user == null) return 0;
		user.setStatus("9");
		return sysUserMapper.updateSysUserById(user);
	}

	// ...existing code...

	@Override
	public PageResult<SysUserVO> pageSysUser(PageRequest pageRequest) {
		Page<SysUser> page = new Page<>(pageRequest.getPageNum(), pageRequest.getPageSize());
		Page<SysUser> result = this.page(page, new QueryWrapper<>());
		List<SysUserVO> voList = BeanCopyUtil.convertList(result.getRecords(), SysUserVO.class);
		return new PageResult<>(result.getTotal(), voList);
	}
}
