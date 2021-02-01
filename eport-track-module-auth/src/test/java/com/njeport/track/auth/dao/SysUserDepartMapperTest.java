package com.njeport.track.auth.dao;

import com.njeport.track.auth.dao.mapper.track.SysUserDepartMapper;
import com.njeport.track.auth.dao.meta.track.SysUserDepartDO;
import com.njeport.track.test.AbstractTestCase;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;


public class SysUserDepartMapperTest extends AbstractTestCase {
    @Resource
    private SysUserDepartMapper sysUserDepartMapper;

    @Test
    public void testInsert() {
        SysUserDepartDO sysUserDepartDO1 = create();
        sysUserDepartMapper.insert(sysUserDepartDO1);

        SysUserDepartDO sysUserDepartDO2 = sysUserDepartMapper.getById(sysUserDepartDO1.getId());
        assertEqual(sysUserDepartDO1, sysUserDepartDO2);
    }

    @Test
    public void testUpdate() {
        SysUserDepartDO sysUserDepartDO1 = create();
        sysUserDepartMapper.insert(sysUserDepartDO1);

        sysUserDepartDO1.setUserId("String Change");
        sysUserDepartDO1.setDepId("String Change");
        sysUserDepartMapper.update(sysUserDepartDO1);

        SysUserDepartDO sysUserDepartDO2 = sysUserDepartMapper.getById(sysUserDepartDO1.getId());
        assertEqual(sysUserDepartDO1, sysUserDepartDO2);
    }

    @Test
    public void testDeleteById() {
        SysUserDepartDO sysUserDepartDO = create();
        sysUserDepartMapper.insert(sysUserDepartDO);
        String id = sysUserDepartDO.getId();
        sysUserDepartMapper.deleteById(id);
        Assert.assertNull(sysUserDepartMapper.getById(id), "未删除成功");
    }

    private SysUserDepartDO create(){
        SysUserDepartDO sysUserDepartDO = new SysUserDepartDO();
        sysUserDepartDO.setId("String");
        sysUserDepartDO.setUserId("String");
        sysUserDepartDO.setDepId("String");
        return sysUserDepartDO;
    }

    private void assertEqual(SysUserDepartDO sysUserDepartDO1, SysUserDepartDO sysUserDepartDO2) {
        Assert.assertEquals(sysUserDepartDO1.getUserId(), sysUserDepartDO2.getUserId());
        Assert.assertEquals(sysUserDepartDO1.getDepId(), sysUserDepartDO2.getDepId());
    }
}
