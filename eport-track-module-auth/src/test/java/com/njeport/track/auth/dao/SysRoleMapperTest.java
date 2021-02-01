package com.njeport.track.auth.dao;

import com.njeport.track.auth.dao.condition.RoleQueryCondition;
import com.njeport.track.auth.dao.mapper.track.SysRoleMapper;
import com.njeport.track.auth.dao.meta.track.SysRoleDO;
import com.njeport.track.test.AbstractTestCase;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.util.List;

public class SysRoleMapperTest extends AbstractTestCase {
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Test
    public void testGetRoleList(){
        List<SysRoleDO> roleList = sysRoleMapper.getRoleList(new RoleQueryCondition(), 10, 0);
        Assert.assertFalse(CollectionUtils.isEmpty(roleList));
    }

    @Test
    public void testCountAll(){
        int count = sysRoleMapper.countAll(new RoleQueryCondition());
        Assert.assertTrue(count > 0);
    }

    @Rollback(false)
    @Test
    public void testInsert() {
        SysRoleDO sysRoleDO1 = create();
        sysRoleMapper.insert(sysRoleDO1);

        SysRoleDO sysRoleDO2 = sysRoleMapper.getById(sysRoleDO1.getId());
        Assert.assertEquals(sysRoleDO1.getId(), sysRoleDO2.getId());
    }

    @Test
    public void testUpdate() {
        SysRoleDO sysRoleDO1 = create();
        sysRoleMapper.insert(sysRoleDO1);

        sysRoleDO1.setRoleName("String Change");
        sysRoleDO1.setRoleCode("String Change");
        sysRoleDO1.setDescription("String Change");
        sysRoleDO1.setCreateBy("String Change");
        sysRoleDO1.setUpdateBy("String Change");
        sysRoleMapper.update(sysRoleDO1);

        SysRoleDO sysRoleDO2 = sysRoleMapper.getById(sysRoleDO1.getId());
        assertEqual(sysRoleDO1, sysRoleDO2);
    }

    @Test
    public void testDeleteById() {
        SysRoleDO sysRoleDO = create();
        sysRoleMapper.insert(sysRoleDO);
        String id = sysRoleDO.getId();
        sysRoleMapper.deleteById(id);
        Assert.assertNull(sysRoleMapper.getById(id), "未删除成功");
    }

    private SysRoleDO create(){
        SysRoleDO sysRoleDO = new SysRoleDO();
        sysRoleDO.setId("String");
        sysRoleDO.setRoleName("String");
        sysRoleDO.setRoleCode("String");
        sysRoleDO.setDescription("String");
        sysRoleDO.setCreateBy("String");
        sysRoleDO.setUpdateBy("String");
        return sysRoleDO;
    }

    private void assertEqual(SysRoleDO sysRoleDO1, SysRoleDO sysRoleDO2) {
        Assert.assertEquals(sysRoleDO1.getRoleName(), sysRoleDO2.getRoleName());
        Assert.assertEquals(sysRoleDO1.getRoleCode(), sysRoleDO2.getRoleCode());
        Assert.assertEquals(sysRoleDO1.getDescription(), sysRoleDO2.getDescription());
        Assert.assertEquals(sysRoleDO1.getCreateBy(), sysRoleDO2.getCreateBy());
        Assert.assertEquals(sysRoleDO1.getUpdateBy(), sysRoleDO2.getUpdateBy());
    }
}
