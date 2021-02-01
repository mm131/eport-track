package com.njeport.track.auth.dao.mapper.track;

import com.njeport.track.auth.dao.meta.track.SysRolePermissionDO;
import com.njeport.track.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRolePermissionMapper extends BaseMapper<SysRolePermissionDO, String> {
    int insert(SysRolePermissionDO sysRolePermissionDO);

    int update(SysRolePermissionDO sysRolePermissionDO);

    SysRolePermissionDO getById(@Param("id") String id);

    int deleteById(@Param("id") String id);

    List<SysRolePermissionDO> getByRoleId(@Param("roleId") String roleId);

    void batchInsert(List<SysRolePermissionDO> list);

    void deleteByRolePerm(@Param("roleId") String roleId, @Param("permissionId") String permissionId);
}