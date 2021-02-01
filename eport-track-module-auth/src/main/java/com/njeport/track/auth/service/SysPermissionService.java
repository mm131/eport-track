package com.njeport.track.auth.service;

import com.alibaba.fastjson.JSONObject;
import com.njeport.track.auth.dao.condition.PermissionQueryCondition;
import com.njeport.track.auth.vo.PermissionVO;

import java.util.List;
import java.util.Map;

/**
 * 菜单服务
 *
 * @author kongming
 * @date 2019/12/25
 */
public interface SysPermissionService {

    /**
     * 创建新的菜单
     *
     * @param permission 待创建菜单
     * @return 新创建菜单的主键
     */
    String addPermission(PermissionVO permission);

    /**
     * 根据条件查询菜单集合
     *
     * @param condition 查询条件
     * @return 菜单集合
     */
    List<PermissionVO> queryPermissions(PermissionQueryCondition condition);

    /**
     * 修改已有的菜单
     *
     * @param permission 待更新的菜单信息
     */
    void updatePermission(PermissionVO permission);

    /**
     * 查询用户有权访问的菜单和按钮
     *
     * @return 用户有权访问的菜单和按钮
     */
    JSONObject queryUserPermissions();

    /**
     * 查询用户有权访问的菜单和按钮
     *
     * @param roleId 角色id
     * @return 用户有权访问的菜单和按钮
     */
    List<String> queryRolePermission(String roleId);

    /**
     * 保存授权
     */
    void saveRolePermission(String roleId,String permissionIds,String lastPermissionIds);

    /**
     * 获取全部的权限树
     */
    Map<String, Object> queryTreeList();
}
