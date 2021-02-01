package com.njeport.track.auth.dao;

import com.njeport.track.auth.dao.mapper.track.SysVerificationCodeMapper;
import com.njeport.track.auth.dao.meta.track.SysVerificationCodeDO;
import com.njeport.track.auth.enums.VerificationBusinessIdEnum;
import com.njeport.track.auth.enums.VerificationTypeEnum;
import com.njeport.track.test.AbstractTestCase;
import org.springframework.test.annotation.Rollback;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.util.Date;

public class SysVerificationCodeMapperTest extends AbstractTestCase {
    @Resource
    private SysVerificationCodeMapper sysVerificationCodeMapper;

    @Rollback(false)
    @Test
    public void testInsert() {
        SysVerificationCodeDO sysVerificationCodeDO1 = create();
        sysVerificationCodeMapper.insert(sysVerificationCodeDO1);

        SysVerificationCodeDO sysVerificationCodeDO2 = sysVerificationCodeMapper.getById(sysVerificationCodeDO1.getId());
        Assert.assertEquals(sysVerificationCodeDO1.getId(), sysVerificationCodeDO2.getId());
    }

    @Test
    public void testUpdate() {
        SysVerificationCodeDO sysVerificationCodeDO1 = create();
        sysVerificationCodeMapper.insert(sysVerificationCodeDO1);

        sysVerificationCodeDO1.setCode("String Change");
        sysVerificationCodeDO1.setBusinessId(VerificationBusinessIdEnum.REGISTER);
        sysVerificationCodeDO1.setType(VerificationTypeEnum.WEB);
        sysVerificationCodeDO1.setExpireTime(new Date());
        sysVerificationCodeMapper.update(sysVerificationCodeDO1);

        SysVerificationCodeDO sysVerificationCodeDO2 = sysVerificationCodeMapper.getById(sysVerificationCodeDO1.getId());
        assertEqual(sysVerificationCodeDO1, sysVerificationCodeDO2);
    }

    @Test
    public void testDeleteById() {
        SysVerificationCodeDO sysVerificationCodeDO = create();
        sysVerificationCodeMapper.insert(sysVerificationCodeDO);
        String id = sysVerificationCodeDO.getId();
        sysVerificationCodeMapper.deleteById(id);
        Assert.assertNull(sysVerificationCodeMapper.getById(id), "未删除成功");
    }

    private SysVerificationCodeDO create(){
        SysVerificationCodeDO sysVerificationCodeDO = new SysVerificationCodeDO();
        sysVerificationCodeDO.setId("String");
        sysVerificationCodeDO.setCode("String");
        sysVerificationCodeDO.setBusinessId(VerificationBusinessIdEnum.REGISTER);
        sysVerificationCodeDO.setType(VerificationTypeEnum.WEB);
        sysVerificationCodeDO.setExpireTime(new Date());
        return sysVerificationCodeDO;
    }

    private void assertEqual(SysVerificationCodeDO sysVerificationCodeDO1, SysVerificationCodeDO sysVerificationCodeDO2) {
        Assert.assertEquals(sysVerificationCodeDO1.getCode(), sysVerificationCodeDO2.getCode());
        Assert.assertEquals(sysVerificationCodeDO1.getBusinessId(), sysVerificationCodeDO2.getBusinessId());
        Assert.assertEquals(sysVerificationCodeDO1.getType(), sysVerificationCodeDO2.getType());
        Assert.assertTrue(sysVerificationCodeDO1.getExpireTime().compareTo(sysVerificationCodeDO2.getExpireTime()) == 0);
    }
}
