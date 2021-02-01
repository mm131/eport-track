package com.njeport.track.auth.service.impl;

import com.njeport.track.auth.bean.DepartIdModel;
import com.njeport.track.auth.bean.SysDepartTreeModel;
import com.njeport.track.auth.dao.condition.DepartQueryCondition;
import com.njeport.track.auth.dao.mapper.track.SysDepartMapper;
import com.njeport.track.auth.dao.meta.track.SysDepartDO;
import com.njeport.track.auth.service.SysDepartService;
import com.njeport.track.auth.utils.FindsDepartsChildrenUtil;
import com.njeport.track.auth.vo.DepartVO;
import com.njeport.track.common.enums.DelFlagEnum;
import com.njeport.track.common.utils.IdUtils;
import com.njeport.track.common.utils.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kongming
 * @date 2020/03/08
 */
@Slf4j
@Service
public class SysDepartServiceImpl implements SysDepartService {

    @Resource
    private SysDepartMapper departMapper;

    /**
     * 根据条件查询部门集合
     *
     * @return 部门集合
     */
    @Override
    public List<SysDepartTreeModel> queryTreeList() {
        List<SysDepartDO> departs = queryValidDeparts();
        return FindsDepartsChildrenUtil.wrapTreeDataToTreeList(departs);
    }

    /**
     * 根据条件查询部门集合
     *
     * @return 部门集合
     */
    @Override
    public List<DepartIdModel> queryIdTree() {
        List<SysDepartDO> departs = queryValidDeparts();
        return FindsDepartsChildrenUtil.wrapTreeDataToDepartIdTreeList(departs);
    }

    private List<SysDepartDO> queryValidDeparts() {
        DepartQueryCondition condition = new DepartQueryCondition();
        condition.setDelFlag(DelFlagEnum.NORMAL);
        condition.setColumn("depart_order");
        condition.setOrder("ASC");
        return departMapper.getDepartList(condition);
    }

    /**
     * 创建新的部门
     *
     * @param depart 部门信息
     */
    @Override
    public void addDepart(DepartVO depart) {
        Assert.hasText(depart.getDepartName(), "部门名称不能为空");

        SysDepartDO newDepart = new SysDepartDO();
        BeanUtils.copyProperties(depart, newDepart);
        newDepart.setId(IdUtils.randomId());
        newDepart.setOrgCode(newDepart.getId());
        newDepart.setCreateBy(SessionUtils.getCurrentUsername());
        departMapper.insert(newDepart);
    }

    /**
     * 根据公司代码查询公司信息
     *
     * @return 公司信息
     */
    @Override
    public SysDepartDO queryCompanyByOrgCode(String orgCode){
        return departMapper.queryCompanyByOrgCode(orgCode);
    }

    /**
     * 更新已有部门
     *
     * @param depart 部门信息
     */
    @Override
    public void editDepart(DepartVO depart) {
        SysDepartDO updateDepart = new SysDepartDO();
        BeanUtils.copyProperties(depart, updateDepart);
        departMapper.update(updateDepart);
    }

    /**
     * 删除指定的部门
     *
     * @param id 部门id
     */
    @Override
    public void deleteDepart(String id) {
        departMapper.deleteById(id);
    }
}
