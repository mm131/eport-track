package com.njeport.track.auth.dao.mapper.track;

import com.njeport.track.auth.dao.condition.DepartQueryCondition;
import com.njeport.track.auth.dao.meta.track.SysDepartDO;
import com.njeport.track.common.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDepartMapper extends BaseMapper<SysDepartDO, String> {
    /**
     * 查询所有机构信息
     *
     * @return 机构信息集合
     */
    List<SysDepartDO> getDepartList(@Param("params") DepartQueryCondition params);

    /**
     * 根据公司代码查询公司信息
     *
     * @return 公司信息
     */
    SysDepartDO queryCompanyByOrgCode(String orgCode);

}