package com.njeport.track.auth.service;

import com.njeport.track.auth.dao.condition.UserQueryCondition;
import com.njeport.track.auth.dao.meta.track.SysUserDO;
import com.njeport.track.auth.vo.UserVO;
import com.njeport.track.common.vo.CommonPagingVO;

import java.util.List;
import java.util.Set;

/**
 * 用户服务
 *
 * @author kongming
 * @date 2019/12/18
 */
public interface SysUserService {

    /**
     * 创建新的用户
     *
     * @param userVO 待创建用户
     * @return 新用户的主键
     */
    String addUser(UserVO userVO);

    /**
     * 更新已有用户
     *
     * @param userVO 用户信息
     */
    void editUser(UserVO userVO);

    /**
     * 删除用户
     *
     * @param userId 用户id
     */
    void deleteUser(String userId);

    /**
     * 根据用户名称查询用户信息
     *
     * @param username 用户名称
     * @return 用户信息
     */
    SysUserDO queryByUsername(String username);

    /**
     * 根据条件返回用户列表
     *
     * @param condition 查询条件
     * @param pageNo    页号
     * @param pageSize  每页记录数
     * @return 用户列表
     */
    CommonPagingVO<UserVO> queryUsersByPage(UserQueryCondition condition, int pageNo, int pageSize);

    /**
     * 检查用户是否有效
     *
     * @param sysUser 用户信息
     */
    void checkValidUser(SysUserDO sysUser);

    /**
     * 根据用户名获取用户的角色集合
     *
     * @param userId 用户id
     * @return 角色集合
     */
    Set<String> queryRoles(String userId);

    /**
     * 根据用户名获取用户可以访问的资源集合
     *
     * @param username 用户名
     * @return 有权访问的资源集合
     */
    Set<String> queryResources(String username);

    /**
     * 添加用户和用户角色关系
     */
    void addUserWithRole(SysUserDO user, String roles);

    /**
     * 修改用户和用户角色关系
     */
    void editUserWithRole(SysUserDO user, String roles);

    /**
     * 添加用户和用户部门关系
     */
    void addUserWithDepart(SysUserDO user, String selectedParts);

    /**
     * 修改用户和用户部门关系
     */
    void editUserWithDepart(SysUserDO user, String departs);

    /**
     * 更新用户密码
     *
     * @param user 用户信息
     */
    void updatePassword(UserVO user);

    /**
     * 查询所有用户
     * @return 记录总数
     */
    List<UserVO> queryUsersAll();
}
