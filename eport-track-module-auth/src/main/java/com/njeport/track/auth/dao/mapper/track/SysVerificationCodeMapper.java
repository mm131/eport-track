package com.njeport.track.auth.dao.mapper.track;

import com.njeport.track.auth.dao.meta.track.SysVerificationCodeDO;
import com.njeport.track.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysVerificationCodeMapper extends BaseMapper<SysVerificationCodeDO, String> {
    int insert(SysVerificationCodeDO sysVerificationCodeDO);

    int update(SysVerificationCodeDO sysVerificationCodeDO);

    SysVerificationCodeDO getById(@Param("id") String id);

    int deleteById(@Param("id") String id);
}