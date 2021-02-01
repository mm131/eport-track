package com.njeport.track.auth.service;

import com.njeport.track.auth.dao.condition.RoleQueryCondition;
import com.njeport.track.auth.dao.meta.track.SysRoleDO;
import com.njeport.track.auth.vo.RoleVO;
import com.njeport.track.common.vo.CommonPagingVO;

import java.util.List;
import java.util.Map;

/**
 * 角色服务
 *
 * @author kongming
 * @date 2019/12/25
 */
public interface SysRoleService {

    /**
     * 创建新的角色
     *
     * @param role 待创建角色
     * @return 新创建角色的主键
     */
    String addRole(RoleVO role);

    /**
     * 更新角色
     *
     * @param role 角色信息
     */
    void editRole(RoleVO role);

    /**
     * 删除单挑角色
     *
     * @param id 角色id
     */
    void deleteRole(String id);

    /**
     * 批量删除角色
     *
     * @param ids 角色id集合
     */
    void batchDeleteRoles(String ids);

    /**
     * 根据条件返回角色列表
     *
     * @param condition 查询条件
     * @param pageNo    页号
     * @param pageSize  每页记录数
     * @return 角色列表
     */
    CommonPagingVO<SysRoleDO> queryRolesByPage(RoleQueryCondition condition, int pageNo, int pageSize);

    /**
     * 查询所有角色
     */
    List<SysRoleDO> queryAll();

    /**
     * 用户角色授权功能，查询菜单权限树
     */
    Map<String,Object> queryTreeList();
}
