package com.njeport.track.auth.dao.mapper.track;

import com.njeport.track.auth.dao.condition.UserQueryCondition;
import com.njeport.track.auth.dao.meta.track.SysUserDO;
import com.njeport.track.auth.vo.UserVO;
import com.njeport.track.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUserDO, String> {
    /**
     * 根据用户名返回用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    SysUserDO getByUsername(@Param("username") String username);

    /**
     * 根据用户id返回角色id
     *
     * @param userId 用户主键
     * @return 角色集合
     */
    List<String> getRolesByUserId(@Param("userId") String userId);

    /**
     * 根据用户名返回角色集合
     *
     * @param username 用户名
     * @return 角色集合
     */
    List<String> getRolesByUsername(@Param("username") String username);

    /**
     * 根据条件查询用户列表
     *
     * @param params 查询条件
     * @return 用户列表
     */
    List<SysUserDO> getUserList(@Param("params") UserQueryCondition params,
                                @Param("limit") int limit,
                                @Param("offset") int offset);

    /**
     * 根据条件查询记录总数
     *
     * @param params 查询条件
     * @return 记录总数
     */
    int countAll(@Param("params") UserQueryCondition params);


    /**
     * 查询所有用户
     * @return 记录总数
     */
    List<UserVO> queryUsersAll();
}