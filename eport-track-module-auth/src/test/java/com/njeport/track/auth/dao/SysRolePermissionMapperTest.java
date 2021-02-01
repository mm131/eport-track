package com.njeport.track.auth.dao;

import com.njeport.track.auth.dao.mapper.track.SysRolePermissionMapper;
import com.njeport.track.auth.dao.meta.track.SysRolePermissionDO;
import com.njeport.track.test.AbstractTestCase;
import org.springframework.test.annotation.Rollback;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;


public class SysRolePermissionMapperTest extends AbstractTestCase {
    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Rollback(false)
    @Test
    public void testInsert() {
        SysRolePermissionDO sysRolePermissionDO1 = create();
        sysRolePermissionMapper.insert(sysRolePermissionDO1);

        SysRolePermissionDO sysRolePermissionDO2 = sysRolePermissionMapper.getById(sysRolePermissionDO1.getId());
        Assert.assertEquals(sysRolePermissionDO1.getId(), sysRolePermissionDO2.getId());
    }

    @Test
    public void testUpdate() {
        SysRolePermissionDO sysRolePermissionDO1 = create();
        sysRolePermissionMapper.insert(sysRolePermissionDO1);

        sysRolePermissionDO1.setRoleId("String Change");
        sysRolePermissionDO1.setPermissionId("String Change");
        sysRolePermissionMapper.update(sysRolePermissionDO1);

        SysRolePermissionDO sysRolePermissionDO2 = sysRolePermissionMapper.getById(sysRolePermissionDO1.getId());
        assertEqual(sysRolePermissionDO1, sysRolePermissionDO2);
    }

    @Test
    public void testDeleteById() {
        SysRolePermissionDO sysRolePermissionDO = create();
        sysRolePermissionMapper.insert(sysRolePermissionDO);
        String id = sysRolePermissionDO.getId();
        sysRolePermissionMapper.deleteById(id);
        Assert.assertNull(sysRolePermissionMapper.getById(id), "未删除成功");
    }

    private SysRolePermissionDO create(){
        SysRolePermissionDO sysRolePermissionDO = new SysRolePermissionDO();
        sysRolePermissionDO.setId("String");
        sysRolePermissionDO.setRoleId("String");
        sysRolePermissionDO.setPermissionId("String");
        return sysRolePermissionDO;
    }

    private void assertEqual(SysRolePermissionDO sysRolePermissionDO1, SysRolePermissionDO sysRolePermissionDO2) {
        Assert.assertEquals(sysRolePermissionDO1.getRoleId(), sysRolePermissionDO2.getRoleId());
        Assert.assertEquals(sysRolePermissionDO1.getPermissionId(), sysRolePermissionDO2.getPermissionId());
    }
}
