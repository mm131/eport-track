package com.njeport.track.auth.dao;

import com.njeport.track.auth.dao.mapper.track.SysUserRoleMapper;
import com.njeport.track.auth.dao.meta.track.SysUserRoleDO;
import com.njeport.track.test.AbstractTestCase;
import org.springframework.test.annotation.Rollback;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;


public class SysUserRoleMapperTest extends AbstractTestCase {
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Rollback(false)
    @Test
    public void testInsert() {
        SysUserRoleDO sysUserRoleDO1 = create();
        sysUserRoleMapper.insert(sysUserRoleDO1);

        SysUserRoleDO sysUserRoleDO2 = sysUserRoleMapper.getById(sysUserRoleDO1.getId());
        Assert.assertEquals(sysUserRoleDO1.getId(), sysUserRoleDO2.getId());
    }

    @Test
    public void testUpdate() {
        SysUserRoleDO sysUserRoleDO1 = create();
        sysUserRoleMapper.insert(sysUserRoleDO1);

        sysUserRoleDO1.setUserId("String Change");
        sysUserRoleDO1.setRoleId("String Change");
        sysUserRoleMapper.update(sysUserRoleDO1);

        SysUserRoleDO sysUserRoleDO2 = sysUserRoleMapper.getById(sysUserRoleDO1.getId());
        assertEqual(sysUserRoleDO1, sysUserRoleDO2);
    }

    @Test
    public void testDeleteById() {
        SysUserRoleDO sysUserRoleDO = create();
        sysUserRoleMapper.insert(sysUserRoleDO);
        String id = sysUserRoleDO.getId();
        sysUserRoleMapper.deleteById(id);
        Assert.assertNull(sysUserRoleMapper.getById(id), "未删除成功");
    }

    private SysUserRoleDO create(){
        SysUserRoleDO sysUserRoleDO = new SysUserRoleDO();
        sysUserRoleDO.setId("String");
        sysUserRoleDO.setUserId("String");
        sysUserRoleDO.setRoleId("String");
        return sysUserRoleDO;
    }

    private void assertEqual(SysUserRoleDO sysUserRoleDO1, SysUserRoleDO sysUserRoleDO2) {
        Assert.assertEquals(sysUserRoleDO1.getUserId(), sysUserRoleDO2.getUserId());
        Assert.assertEquals(sysUserRoleDO1.getRoleId(), sysUserRoleDO2.getRoleId());
    }
}
