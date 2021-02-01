package com.njeport.track.auth.dao.mapper.track;

import com.njeport.track.auth.dao.condition.PermissionQueryCondition;
import com.njeport.track.auth.dao.meta.track.SysPermissionDO;
import com.njeport.track.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermissionDO, String> {
    /**
     * 根据用户名返回其有权限的资源列表
     *
     * @param username 用户名
     * @return 资源列表
     */
    List<SysPermissionDO> getPermissionsByUsername(@Param("username") String username);

    /**
     * 根据条件查询资源列表
     *
     * @param params 查询条件
     * @return 资源列表
     */
    List<SysPermissionDO> getPermissionList(@Param("params") PermissionQueryCondition params);

    /**
     * 查询主键在指定集合中的记录数
     *
     * @param ids 主键集合
     * @return 主键在指定集合中的记录数
     */
    int countByIds(@Param("ids") List<String> ids);


    /**
     * 根据角色编号查询菜单
     *
     * @param roleId 角色编号
     * @return 资源列表
     */
    List<SysPermissionDO> getPermissionsByRoleId(@Param("roleId") String roleId);
}