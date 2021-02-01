package com.njeport.track.auth.dao.mapper.track;

import com.njeport.track.auth.dao.condition.RoleQueryCondition;
import com.njeport.track.auth.dao.meta.track.SysRoleDO;
import com.njeport.track.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRoleMapper extends BaseMapper<SysRoleDO, String> {
    /**
     * 根据条件查询角色列表
     *
     * @param params   查询条件
     * @param offset   页号
     * @param limit 每页记录数
     * @return 角色列表
     */
    List<SysRoleDO> getRoleList(@Param("params") RoleQueryCondition params,
                                @Param("limit") int limit,
                                @Param("offset") int offset);

    /**
     * 根据条件查询记录总数
     *
     * @param params 查询条件
     * @return 记录总数
     */
    int countAll(@Param("params") RoleQueryCondition params);

    /**
     * 查询主键在指定集合中的记录数
     *
     * @param ids 主键集合
     * @return 主键在指定集合中的记录数
     */
    int countByIds(@Param("ids") List<String> ids);

    /**
     * 批量删除角色记录
     *
     * @param ids 主键集合
     */
    int deleteByIds(@Param("ids") List<String> ids);
}