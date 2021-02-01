package com.njeport.track.auth.dao;

import com.njeport.track.auth.dao.condition.DepartQueryCondition;
import com.njeport.track.auth.dao.mapper.track.SysDepartMapper;
import com.njeport.track.auth.dao.meta.track.SysDepartDO;
import com.njeport.track.common.enums.DelFlagEnum;
import com.njeport.track.auth.enums.StatusEnum;
import com.njeport.track.test.AbstractTestCase;
import org.springframework.util.CollectionUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.util.List;

public class SysDepartMapperTest extends AbstractTestCase {
    @Resource
    private SysDepartMapper sysDepartMapper;

    @Test
    public void testGetDepartList(){
        List<SysDepartDO> departList = sysDepartMapper.getDepartList(new DepartQueryCondition());
        Assert.assertFalse(CollectionUtils.isEmpty(departList));
    }

//    @Rollback(false)
    @Test
    public void testInsert() {
        SysDepartDO sysDepartDO1 = create();
        sysDepartMapper.insert(sysDepartDO1);

        SysDepartDO sysDepartDO2 = sysDepartMapper.getById(sysDepartDO1.getId());
        Assert.assertEquals(sysDepartDO1.getId(), sysDepartDO2.getId());
    }

    @Test
    public void testUpdate() {
        SysDepartDO sysDepartDO1 = create();
        sysDepartMapper.insert(sysDepartDO1);

        sysDepartDO1.setParentId("String Change");
        sysDepartDO1.setDepartName("String Change");
        sysDepartDO1.setDepartNameEn("String Change");
        sysDepartDO1.setDepartNameAbbr("String Change");
        sysDepartDO1.setDepartOrder(1);
        sysDepartDO1.setDescription("String Change");
        sysDepartDO1.setOrgCategory("String Change");
        sysDepartDO1.setOrgType("String Change");
        sysDepartDO1.setOrgCode("String Change");
        sysDepartDO1.setMobile("String Change");
        sysDepartDO1.setFax("String Change");
        sysDepartDO1.setAddress("String Change");
        sysDepartDO1.setMemo("String Change");
        sysDepartDO1.setStatus(StatusEnum.NORMAL);
        sysDepartDO1.setDelFlag(DelFlagEnum.NORMAL);
        sysDepartDO1.setCreateBy("String Change");
        sysDepartDO1.setUpdateBy("String Change");
        sysDepartMapper.update(sysDepartDO1);

        SysDepartDO sysDepartDO2 = sysDepartMapper.getById(sysDepartDO1.getId());
        assertEqual(sysDepartDO1, sysDepartDO2);
    }

    @Test
    public void testDeleteById() {
        SysDepartDO sysDepartDO = create();
        sysDepartMapper.insert(sysDepartDO);
        String id = sysDepartDO.getId();
        sysDepartMapper.deleteById(id);
        Assert.assertNull(sysDepartMapper.getById(id), "未删除成功");
    }

    private SysDepartDO create(){
        SysDepartDO sysDepartDO = new SysDepartDO();
        sysDepartDO.setId("String");
        sysDepartDO.setParentId("String");
        sysDepartDO.setDepartName("String");
        sysDepartDO.setDepartNameEn("String");
        sysDepartDO.setDepartNameAbbr("String");
        sysDepartDO.setDepartOrder(1);
        sysDepartDO.setDescription("String");
        sysDepartDO.setOrgCategory("String");
        sysDepartDO.setOrgType("String");
        sysDepartDO.setOrgCode("String");
        sysDepartDO.setMobile("String");
        sysDepartDO.setFax("String");
        sysDepartDO.setAddress("String");
        sysDepartDO.setMemo("String");
        sysDepartDO.setStatus(StatusEnum.NORMAL);
        sysDepartDO.setDelFlag(DelFlagEnum.NORMAL);
        sysDepartDO.setCreateBy("String");
        sysDepartDO.setUpdateBy("String");
        return sysDepartDO;
    }

    private void assertEqual(SysDepartDO sysDepartDO1, SysDepartDO sysDepartDO2) {
        Assert.assertEquals(sysDepartDO1.getParentId(), sysDepartDO2.getParentId());
        Assert.assertEquals(sysDepartDO1.getDepartName(), sysDepartDO2.getDepartName());
        Assert.assertEquals(sysDepartDO1.getDepartNameEn(), sysDepartDO2.getDepartNameEn());
        Assert.assertEquals(sysDepartDO1.getDepartNameAbbr(), sysDepartDO2.getDepartNameAbbr());
        Assert.assertEquals(sysDepartDO1.getDepartOrder(), sysDepartDO2.getDepartOrder());
        Assert.assertEquals(sysDepartDO1.getDescription(), sysDepartDO2.getDescription());
        Assert.assertEquals(sysDepartDO1.getOrgCategory(), sysDepartDO2.getOrgCategory());
        Assert.assertEquals(sysDepartDO1.getOrgType(), sysDepartDO2.getOrgType());
        Assert.assertEquals(sysDepartDO1.getOrgCode(), sysDepartDO2.getOrgCode());
        Assert.assertEquals(sysDepartDO1.getMobile(), sysDepartDO2.getMobile());
        Assert.assertEquals(sysDepartDO1.getFax(), sysDepartDO2.getFax());
        Assert.assertEquals(sysDepartDO1.getAddress(), sysDepartDO2.getAddress());
        Assert.assertEquals(sysDepartDO1.getMemo(), sysDepartDO2.getMemo());
        Assert.assertEquals(sysDepartDO1.getStatus(), sysDepartDO2.getStatus());
        Assert.assertEquals(sysDepartDO1.getDelFlag(), sysDepartDO2.getDelFlag());
        Assert.assertEquals(sysDepartDO1.getCreateBy(), sysDepartDO2.getCreateBy());
        Assert.assertEquals(sysDepartDO1.getUpdateBy(), sysDepartDO2.getUpdateBy());
    }
}
