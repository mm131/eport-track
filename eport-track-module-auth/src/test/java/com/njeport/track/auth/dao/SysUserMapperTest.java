package com.njeport.track.auth.dao;

import com.njeport.track.auth.dao.mapper.track.SysUserMapper;
import com.njeport.track.auth.dao.meta.track.SysUserDO;
import com.njeport.track.common.enums.DelFlagEnum;
import com.njeport.track.auth.enums.GenderEnum;
import com.njeport.track.auth.enums.StatusEnum;
import com.njeport.track.test.AbstractTestCase;
import org.springframework.util.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

public class SysUserMapperTest extends AbstractTestCase {
    @Resource
    private SysUserMapper sysUserMapper;

    @Test
    public void testGetByUsername(){
        SysUserDO sysUserDO = sysUserMapper.getByUsername("String");
        Assert.assertNotNull(sysUserDO);
    }

    @Test
    public void testGetRolesByUsername(){
        List<String> roleList = sysUserMapper.getRolesByUsername("String");
        Assert.assertFalse(CollectionUtils.isEmpty(roleList));
    }

//    @Rollback(false)
    @Test
    public void testInsert() {
        SysUserDO sysUserDO1 = create();
        sysUserMapper.insert(sysUserDO1);

        SysUserDO sysUserDO2 = sysUserMapper.getById(sysUserDO1.getId());
        Assert.assertEquals(sysUserDO1.getId(), sysUserDO2.getId());
    }

    @Test
    public void testUpdate() {
        SysUserDO sysUserDO1 = create();
        sysUserMapper.insert(sysUserDO1);

        sysUserDO1.setUsername("String Change");
        sysUserDO1.setRealname("String Change");
        sysUserDO1.setPassword("String Change");
        sysUserDO1.setSalt("String Change");
        sysUserDO1.setAvatar("String Change");
        sysUserDO1.setBirthday(new Date());
        sysUserDO1.setSex(GenderEnum.MALE);
        sysUserDO1.setEmail("String Change");
        sysUserDO1.setPhone("String Change");
        sysUserDO1.setOrgCode("String Change");
        sysUserDO1.setStatus(StatusEnum.NORMAL);
        sysUserDO1.setDelFlag(DelFlagEnum.NORMAL);
        sysUserDO1.setWorkNo("String Change");
        sysUserDO1.setPost("String Change");
        sysUserDO1.setTelephone("String Change");
        sysUserDO1.setCreateBy("String Change");
        sysUserDO1.setUpdateBy("String Change");
        sysUserMapper.update(sysUserDO1);

        SysUserDO sysUserDO2 = sysUserMapper.getById(sysUserDO1.getId());
        assertEqual(sysUserDO1, sysUserDO2);
    }

    @Test
    public void testDeleteById() {
        SysUserDO sysUserDO = create();
        sysUserMapper.insert(sysUserDO);
        String id = sysUserDO.getId();
        sysUserMapper.deleteById(id);
        Assert.assertNull(sysUserMapper.getById(id), "未删除成功");
    }

    private SysUserDO create(){
        SysUserDO sysUserDO = new SysUserDO();
        sysUserDO.setId("String");
        sysUserDO.setUsername("String");
        sysUserDO.setRealname("String");
        sysUserDO.setPassword("String");
        sysUserDO.setSalt("String");
        sysUserDO.setAvatar("String");
        sysUserDO.setBirthday(new Date());
        sysUserDO.setSex(GenderEnum.MALE);
        sysUserDO.setEmail("String");
        sysUserDO.setPhone("String");
        sysUserDO.setOrgCode("String");
        sysUserDO.setStatus(StatusEnum.NORMAL);
        sysUserDO.setDelFlag(DelFlagEnum.NORMAL);
        sysUserDO.setWorkNo("String");
        sysUserDO.setPost("String");
        sysUserDO.setTelephone("String");
        sysUserDO.setCreateBy("String");
        sysUserDO.setUpdateBy("String");
        return sysUserDO;
    }

    private void assertEqual(SysUserDO sysUserDO1, SysUserDO sysUserDO2) {
        Assert.assertEquals(sysUserDO1.getUsername(), sysUserDO2.getUsername());
        Assert.assertEquals(sysUserDO1.getRealname(), sysUserDO2.getRealname());
        Assert.assertEquals(sysUserDO1.getPassword(), sysUserDO2.getPassword());
        Assert.assertEquals(sysUserDO1.getSalt(), sysUserDO2.getSalt());
        Assert.assertEquals(sysUserDO1.getAvatar(), sysUserDO2.getAvatar());
        Assert.assertTrue(sysUserDO1.getBirthday().compareTo(sysUserDO2.getBirthday()) == 0);
        Assert.assertEquals(sysUserDO1.getSex(), sysUserDO2.getSex());
        Assert.assertEquals(sysUserDO1.getEmail(), sysUserDO2.getEmail());
        Assert.assertEquals(sysUserDO1.getPhone(), sysUserDO2.getPhone());
        Assert.assertEquals(sysUserDO1.getOrgCode(), sysUserDO2.getOrgCode());
        Assert.assertEquals(sysUserDO1.getStatus(), sysUserDO2.getStatus());
        Assert.assertEquals(sysUserDO1.getDelFlag(), sysUserDO2.getDelFlag());
        Assert.assertEquals(sysUserDO1.getWorkNo(), sysUserDO2.getWorkNo());
        Assert.assertEquals(sysUserDO1.getPost(), sysUserDO2.getPost());
        Assert.assertEquals(sysUserDO1.getTelephone(), sysUserDO2.getTelephone());
        Assert.assertEquals(sysUserDO1.getCreateBy(), sysUserDO2.getCreateBy());
        Assert.assertEquals(sysUserDO1.getUpdateBy(), sysUserDO2.getUpdateBy());
    }
}
