package com.njeport.track.auth.dao.mapper.track;

import com.njeport.track.auth.dao.meta.track.SysUserDepartDO;
import com.njeport.track.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserDepartMapper extends BaseMapper<SysUserDepartDO, String> {
    int deleteByUserId(@Param("userId") String userId);
}