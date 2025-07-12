
package com.cc.server.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.server.entity.system.SysUser;
import com.cc.server.vo.system.SysUserVO;
import com.cc.frame.core.PageRequest;
import com.cc.frame.core.PageResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 新增用户，包含参数校验和业务逻辑
     */
    Integer createUser(SysUserVO userVO);
package com.cc.server.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cc.server.entity.system.SysUser;
import com.cc.server.vo.system.SysUserVO;
import com.cc.frame.core.PageRequest;
import com.cc.frame.core.PageResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author cc
 * @since 2025-03-01 20:26:58
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 查询表sys_user所有信息
     */
    List<SysUserVO> selectAllSysUser();

    /**
     * 根据主键id查询表sys_user信息
     *
     * @param id
     */
    SysUserVO selectSysUserById(@Param("id") Long id);

    /**
     * 根据条件查询表sys_user信息
     *
     * @param sysUserVO
     */
    List<SysUserVO> selectSysUserByCondition(SysUserVO sysUserVO);

    /**
     * 根据主键id查询表sys_user信息
     *
     * @param id
     */
    Integer deleteSysUserById(@Param("id") Long id);

    /**
     * 根据主键id更新表sys_user信息
     *
     * @param sysUserVO
     */
    Integer updateSysUserById(SysUserVO sysUserVO);

    /**
     * 新增表sys_user信息
     *
     * @param sysUserVO
     */
    Integer insertSysUser(SysUserVO sysUserVO);

    /**
     * 根据用户名、邮箱、手机号查询用户（包含密码验证）
     * @param sysUserVO 包含用户名、邮箱、手机号和密码的用户信息
     * @return 用户信息，如果验证失败返回null
     */
    SysUserVO getUserByNameEmailPhone(SysUserVO sysUserVO);

    /**
     * 验证用户登录
     * @param loginName 登录名（用户名、邮箱或手机号）
     * @param password 密码
     * @return 用户信息，如果验证失败返回null
     */
    SysUserVO validateUserLogin(String loginName, String password);

    /**
     * 检查用户是否存在
     * @param username 用户名
     * @param email 邮箱
     * @param phone 手机号
     * @return 如果用户存在返回用户信息，否则返回null
     */
    SysUserVO checkUserExists(String username, String email, String phone);

    /**
     * 根据登录名查询用户（不验证密码）
     * @param loginName 登录名（用户名、邮箱或手机号）
     * @return 用户信息，如果不存在返回null
     */
    SysUserVO getUserByLoginName(String loginName);

    /**
     * 分页查询用户
     */
    PageResult<SysUserVO> pageSysUser(PageRequest pageRequest);
}
