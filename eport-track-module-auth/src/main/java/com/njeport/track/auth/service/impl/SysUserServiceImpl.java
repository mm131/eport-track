package com.njeport.track.auth.service.impl;

import com.njeport.track.auth.dao.condition.UserQueryCondition;
import com.njeport.track.auth.dao.mapper.track.SysPermissionMapper;
import com.njeport.track.auth.dao.mapper.track.SysUserDepartMapper;
import com.njeport.track.auth.dao.mapper.track.SysUserMapper;
import com.njeport.track.auth.dao.mapper.track.SysUserRoleMapper;
import com.njeport.track.auth.dao.meta.track.SysPermissionDO;
import com.njeport.track.auth.dao.meta.track.SysUserDO;
import com.njeport.track.auth.dao.meta.track.SysUserDepartDO;
import com.njeport.track.auth.dao.meta.track.SysUserRoleDO;
import com.njeport.track.common.constants.StringConstants;
import com.njeport.track.common.enums.DelFlagEnum;
import com.njeport.track.auth.enums.StatusEnum;
import com.njeport.track.common.utils.*;
import com.njeport.track.auth.service.SysUserService;
import com.njeport.track.auth.vo.UserVO;
import com.njeport.track.auth.enums.GenderEnum;
import com.njeport.track.common.vo.CommonPagingVO;
import com.njeport.track.common.vo.LoginUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author kongming
 * @date 2019/12/18
 */
@Service
@Slf4j
public class SysUserServiceImpl implements SysUserService {

    private static final String DEFAULT_PASSWORD = "12345678";

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Resource
    private SysPermissionMapper sysPermissionMapper;

    @Resource
    private SysUserDepartMapper sysUserDepartMapper;

    /**
     * 创建新的用户
     *
     * @param userVO 待创建用户
     * @return 新用户的主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addUser(UserVO userVO) {
        Assert.hasText(userVO.getUsername(), "缺少登录账号信息");
        Assert.isNull(sysUserMapper.getByUsername(userVO.getUsername()), "用户名(登录账号) 已存在");
        SysUserDO createdUser = createUser(userVO);
        addUserWithRole(createdUser, userVO.getSelectedroles());
        addUserWithDepart(createdUser, userVO.getSelecteddeparts());
        return createdUser.getId();
    }

    /**
     * 更新已有用户
     *
     * @param userVO 用户信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editUser(UserVO userVO) {
        SysUserDO sysUser = sysUserMapper.getById(userVO.getId());
        Assert.notNull(sysUser, "用户不存在");

        // 更新用户主档
        SysUserDO updatedUser = new SysUserDO();
        BeanUtils.copyProperties(userVO, updatedUser);
        updatedUser.setUpdateBy(SessionUtils.getCurrentUsername());
        updatedUser.setSex(GenderEnum.getByValue(userVO.getSex()));
        sysUserMapper.update(updatedUser);

        // 更新关联关系
        editUserWithRole(updatedUser, userVO.getSelectedroles());
        editUserWithDepart(updatedUser, userVO.getSelecteddeparts());
    }

    /**
     * 删除用户
     *
     * @param userId 用户id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(String userId) {
        sysUserMapper.deleteById(userId);
        sysUserRoleMapper.deleteByUserId(userId);
        sysUserDepartMapper.deleteByUserId(userId);
    }

    /**
     * 新增用户记录
     *
     * @param userVO 待新增用户信息
     * @return 新用户id
     */
    private SysUserDO createUser(UserVO userVO) {
        SysUserDO sysUserDO = new SysUserDO();
        BeanUtils.copyProperties(userVO, sysUserDO);
        String userId = IdUtils.randomId();
        sysUserDO.setId(userId);
        sysUserDO.setSalt(IdUtils.randomSalt(8));
        String password = StringUtils.isEmpty(userVO.getPassword()) ? DEFAULT_PASSWORD : userVO.getPassword();
        sysUserDO.setPassword(EncryptUtils.encrypt(userVO.getUsername(), password, sysUserDO.getSalt()));
        sysUserDO.setCreateBy(SessionUtils.getCurrentUsername());
        sysUserDO.setSex(userVO.getSex() == null ? GenderEnum.MALE : GenderEnum.getByValue(userVO.getSex()));
        sysUserDO.setWorkNo(userVO.getWorkNo());
        sysUserDO.setDelFlag(DelFlagEnum.NORMAL);
        sysUserDO.setStatus(StatusEnum.NORMAL);
        sysUserMapper.insert(sysUserDO);
        return sysUserDO;
    }

    /**
     * 根据用户名称查询用户信息
     *
     * @param username 用户名称
     * @return 用户信息
     */
    @Override
    public SysUserDO queryByUsername(String username) {
        return sysUserMapper.getByUsername(username);
    }

    /**
     * 根据条件返回用户列表
     *
     * @param condition 查询条件
     * @param pageNo    页号
     * @param pageSize  每页记录数
     * @return 用户列表
     */
    @Override
    public CommonPagingVO<UserVO> queryUsersByPage(UserQueryCondition condition, int pageNo, int pageSize) {
        int total = sysUserMapper.countAll(condition);
        PagingUtils.Paging paging = PagingUtils.getPaging(total, pageNo, pageSize);
        condition.setColumn(StringUtils.camelToUnderline(condition.getColumn()));
        List<SysUserDO> users = sysUserMapper.getUserList(condition, pageSize, (pageNo - 1) * pageSize);
        List<UserVO> userList = users.stream().map(user -> {
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVO.setSex(user.getSex() == null ? null : user.getSex().intValue());
            userVO.setSex_dictText(user.getSex() == null ? null : user.getSex().toString());
            userVO.setStatus(user.getStatus() == null ? null : user.getStatus().intValue());
            userVO.setStatus_dictText(user.getStatus() == null ? null : user.getStatus().toString());
            userVO.setPassword(null);
            return userVO;
        }).collect(Collectors.toList());
        return CommonPagingVO.getCommonPagingVO(paging, userList);
    }

    /**
     * 检查用户是否有效
     *
     * @param sysUser 用户信息
     */
    @Override
    public void checkValidUser(SysUserDO sysUser) {
        Assert.notNull(sysUser, "用户不存在");
        Assert.isTrue(sysUser.getDelFlag() == DelFlagEnum.NORMAL, "该账户已停用，请联系管理员");
    }

    /**
     * 根据用户名获取用户的角色集合
     *
     * @param userId 用户id
     * @return 角色集合
     */
    @Override
    public Set<String> queryRoles(String userId) {
        List<String> roleCodes = sysUserMapper.getRolesByUserId(userId);
        return new HashSet<>(roleCodes);
    }

    /**
     * 根据用户名获取用户可以访问的资源集合
     *
     * @param username 用户名
     * @return 有权访问的资源集合
     */
    @Override
    public Set<String> queryResources(String username) {
        Set<String> permissionSet = new HashSet<>();
        List<SysPermissionDO> permissions = sysPermissionMapper.getPermissionsByUsername(username);
        for (SysPermissionDO permission : permissions) {
            if (StringUtils.isNotEmpty(permission.getPerms())) {
                permissionSet.add(permission.getPerms());
            }
        }
        return permissionSet;
    }

    /**
     * 添加用户和用户角色关系
     */
    @Override
    public void addUserWithRole(SysUserDO user, String roles) {
        if (StringUtils.isNotEmpty(roles)) {
            String[] arr = roles.split(StringConstants.COMMA);
            for (String roleId : arr) {
                String userRoleId = IdUtils.randomId();
                SysUserRoleDO userRole = new SysUserRoleDO(userRoleId, user.getId(), roleId);
                sysUserRoleMapper.insert(userRole);
            }
        }
    }

    /**
     * 修改用户和用户角色关系
     */
    @Override
    public void editUserWithRole(SysUserDO user, String roles) {
        sysUserRoleMapper.deleteByUserId(user.getId());
        addUserWithRole(user, roles);
    }

    /**
     * 添加用户和用户部门关系
     */
    @Override
    public void addUserWithDepart(SysUserDO user, String selectedParts) {
        if (StringUtils.isNotEmpty(selectedParts)) {
            String[] arr = selectedParts.split(StringConstants.COMMA);
            for (String departId : arr) {
                String userDepartId = IdUtils.randomId();
                SysUserDepartDO userDepart = new SysUserDepartDO(userDepartId, user.getId(), departId);
                sysUserDepartMapper.insert(userDepart);
            }
        }
        SysUserDO updateUser = new SysUserDO();
        updateUser.setId(user.getId());
        updateUser.setOrgCode(selectedParts);
        updateUser.setUpdateBy(SessionUtils.getCurrentUsername());
        sysUserMapper.update(updateUser);
    }

    /**
     * 修改用户和用户部门关系
     */
    @Override
    public void editUserWithDepart(SysUserDO user, String departs) {
        sysUserDepartMapper.deleteByUserId(user.getId());
        addUserWithDepart(user, departs);
    }

    /**
     * 更新用户密码
     *
     * @param user 用户信息
     */
    @Override
    public void updatePassword(UserVO user) {
        Assert.hasText(user.getOldpassword(), "旧密码不能为空");
        Assert.hasText(user.getConfirmpassword(), "新密码不能为空");
        String userId = SessionUtils.getCurrentUserId();
        SysUserDO userDO = sysUserMapper.getById(userId);

        // 校验旧密码是否正确
        String tempOld = EncryptUtils.encrypt(userDO.getUsername(), user.getOldpassword(), userDO.getSalt());
        Assert.isTrue(Objects.equals(tempOld, userDO.getPassword()), "旧密码不正确");

        // 获取新的盐和密码
        String newSalt = IdUtils.randomSalt(8);
        String newPass = EncryptUtils.encrypt(userDO.getUsername(), user.getConfirmpassword(), newSalt);

        // 更新盐与密码
        SysUserDO updateUser = new SysUserDO();
        updateUser.setId(userId);
        updateUser.setSalt(newSalt);
        updateUser.setPassword(newPass);
        updateUser.setUpdateBy(userDO.getUsername());
        sysUserMapper.update(updateUser);
    }

    /**
     * 查询所有用户
     * @return 记录总数
     */
    @Override
    public List<UserVO> queryUsersAll(){
        return sysUserMapper.queryUsersAll();
    }
}
