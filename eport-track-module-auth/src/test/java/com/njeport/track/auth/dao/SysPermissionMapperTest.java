package com.njeport.track.auth.dao;

import com.google.common.collect.Lists;
import com.njeport.track.auth.dao.condition.PermissionQueryCondition;
import com.njeport.track.auth.dao.mapper.track.SysPermissionMapper;
import com.njeport.track.auth.dao.meta.track.SysPermissionDO;
import com.njeport.track.common.enums.DelFlagEnum;
import com.njeport.track.auth.enums.MenuTypeEnum;
import com.njeport.track.test.AbstractTestCase;
import org.springframework.util.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.util.List;

public class SysPermissionMapperTest extends AbstractTestCase {
    @Resource
    private SysPermissionMapper sysPermissionMapper;

    @Test
    public void testGetPermissionsByUsername() {
        List<SysPermissionDO> permissionList = sysPermissionMapper.getPermissionsByUsername("daniel");
        Assert.assertFalse(CollectionUtils.isEmpty(permissionList));
    }

    @Test
    public void testGetPermissionList() {
        List<SysPermissionDO> permissionList = sysPermissionMapper.getPermissionList(new PermissionQueryCondition());
        Assert.assertFalse(CollectionUtils.isEmpty(permissionList));
    }

    @Test
    public void testCountByIds() {
        int count = sysPermissionMapper.countByIds(Lists.newArrayList("String"));
        Assert.assertTrue(count > 0);
    }

    @Test
    public void testGetPermissionsByRoleId(){
        List<SysPermissionDO> permissionList = sysPermissionMapper.getPermissionsByRoleId("String");
        Assert.assertFalse(CollectionUtils.isEmpty(permissionList));
    }

    //    @Rollback(false)
    @Test
    public void testInsert() {
        SysPermissionDO sysPermissionDO1 = create();
        sysPermissionMapper.insert(sysPermissionDO1);

        SysPermissionDO sysPermissionDO2 = sysPermissionMapper.getById(sysPermissionDO1.getId());
        Assert.assertEquals(sysPermissionDO1.getId(), sysPermissionDO2.getId());
    }

    @Test
    public void testUpdate() {
        SysPermissionDO sysPermissionDO1 = create();
        sysPermissionMapper.insert(sysPermissionDO1);

        sysPermissionDO1.setParentId("String Change");
        sysPermissionDO1.setName("String Change");
        sysPermissionDO1.setUrl("String Change");
        sysPermissionDO1.setComponent("String Change");
        sysPermissionDO1.setComponentName("String Change");
        sysPermissionDO1.setRedirect("String Change");
        sysPermissionDO1.setMenuType(MenuTypeEnum.BUTTON);
        sysPermissionDO1.setPerms("String Change");
        sysPermissionDO1.setPermsType("String Change");
        sysPermissionDO1.setSortNo(1D);
        sysPermissionDO1.setAlwaysShow(false);
        sysPermissionDO1.setIcon("String Change");
        sysPermissionDO1.setRoute(false);
        sysPermissionDO1.setLeaf(false);
        sysPermissionDO1.setKeepAlive(false);
        sysPermissionDO1.setHidden(false);
        sysPermissionDO1.setDescription("String Change");
        sysPermissionDO1.setCreateBy("String Change");
        sysPermissionDO1.setUpdateBy("String Change");
        sysPermissionDO1.setDelFlag(DelFlagEnum.NORMAL);
        sysPermissionDO1.setRuleFlag(1);
        sysPermissionDO1.setStatus(1);
        sysPermissionDO1.setInternalOrExternal(false);
        sysPermissionMapper.update(sysPermissionDO1);

        SysPermissionDO sysPermissionDO2 = sysPermissionMapper.getById(sysPermissionDO1.getId());
        assertEqual(sysPermissionDO1, sysPermissionDO2);
    }

    @Test
    public void testDeleteById() {
        SysPermissionDO sysPermissionDO = create();
        sysPermissionMapper.insert(sysPermissionDO);
        String id = sysPermissionDO.getId();
        sysPermissionMapper.deleteById(id);
        Assert.assertNull(sysPermissionMapper.getById(id), "未删除成功");
    }

    private SysPermissionDO create() {
        SysPermissionDO sysPermissionDO = new SysPermissionDO();
        sysPermissionDO.setId("String");
        sysPermissionDO.setParentId("String");
        sysPermissionDO.setName("String");
        sysPermissionDO.setUrl("String");
        sysPermissionDO.setComponent("String");
        sysPermissionDO.setComponentName("String");
        sysPermissionDO.setRedirect("String");
        sysPermissionDO.setMenuType(MenuTypeEnum.BUTTON);
        sysPermissionDO.setPerms("String");
        sysPermissionDO.setPermsType("String");
        sysPermissionDO.setSortNo(1D);
        sysPermissionDO.setAlwaysShow(true);
        sysPermissionDO.setIcon("String");
        sysPermissionDO.setRoute(true);
        sysPermissionDO.setLeaf(true);
        sysPermissionDO.setKeepAlive(true);
        sysPermissionDO.setHidden(true);
        sysPermissionDO.setDescription("String");
        sysPermissionDO.setCreateBy("String");
        sysPermissionDO.setUpdateBy("String");
        sysPermissionDO.setDelFlag(DelFlagEnum.NORMAL);
        sysPermissionDO.setRuleFlag(1);
        sysPermissionDO.setStatus(1);
        sysPermissionDO.setInternalOrExternal(true);
        return sysPermissionDO;
    }

    private void assertEqual(SysPermissionDO sysPermissionDO1, SysPermissionDO sysPermissionDO2) {
        Assert.assertEquals(sysPermissionDO1.getParentId(), sysPermissionDO2.getParentId());
        Assert.assertEquals(sysPermissionDO1.getName(), sysPermissionDO2.getName());
        Assert.assertEquals(sysPermissionDO1.getUrl(), sysPermissionDO2.getUrl());
        Assert.assertEquals(sysPermissionDO1.getComponent(), sysPermissionDO2.getComponent());
        Assert.assertEquals(sysPermissionDO1.getComponentName(), sysPermissionDO2.getComponentName());
        Assert.assertEquals(sysPermissionDO1.getRedirect(), sysPermissionDO2.getRedirect());
        Assert.assertEquals(sysPermissionDO1.getMenuType(), sysPermissionDO2.getMenuType());
        Assert.assertEquals(sysPermissionDO1.getPerms(), sysPermissionDO2.getPerms());
        Assert.assertEquals(sysPermissionDO1.getPermsType(), sysPermissionDO2.getPermsType());
        Assert.assertEquals(sysPermissionDO1.getSortNo(), sysPermissionDO2.getSortNo());
        Assert.assertEquals(sysPermissionDO1.isAlwaysShow(), sysPermissionDO2.isAlwaysShow());
        Assert.assertEquals(sysPermissionDO1.getIcon(), sysPermissionDO2.getIcon());
        Assert.assertEquals(sysPermissionDO1.isRoute(), sysPermissionDO2.isRoute());
        Assert.assertEquals(sysPermissionDO1.isLeaf(), sysPermissionDO2.isLeaf());
        Assert.assertEquals(sysPermissionDO1.isKeepAlive(), sysPermissionDO2.isKeepAlive());
        Assert.assertEquals(sysPermissionDO1.isHidden(), sysPermissionDO2.isHidden());
        Assert.assertEquals(sysPermissionDO1.getDescription(), sysPermissionDO2.getDescription());
        Assert.assertEquals(sysPermissionDO1.getCreateBy(), sysPermissionDO2.getCreateBy());
        Assert.assertEquals(sysPermissionDO1.getUpdateBy(), sysPermissionDO2.getUpdateBy());
        Assert.assertEquals(sysPermissionDO1.getDelFlag(), sysPermissionDO2.getDelFlag());
        Assert.assertEquals(sysPermissionDO1.getRuleFlag(), sysPermissionDO2.getRuleFlag());
        Assert.assertEquals(sysPermissionDO1.getStatus(), sysPermissionDO2.getStatus());
        Assert.assertEquals(sysPermissionDO1.isInternalOrExternal(), sysPermissionDO2.isInternalOrExternal());
    }
}
