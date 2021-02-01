package com.njeport.track.auth.service;

import com.njeport.track.auth.bean.DepartIdModel;
import com.njeport.track.auth.bean.SysDepartTreeModel;
import com.njeport.track.auth.dao.meta.track.SysDepartDO;
import com.njeport.track.auth.vo.DepartVO;

import java.util.List;

/**
 * 部门服务
 *
 * @author kongming
 * @date 2019/12/25
 */
public interface SysDepartService {

    /**
     * 根据条件查询部门集合
     *
     * @return 部门集合
     */
    List<SysDepartTreeModel> queryTreeList();

    /**
     * 根据条件查询部门集合
     *
     * @return 部门集合
     */
    List<DepartIdModel> queryIdTree();

    /**
     * 根据公司代码查询公司信息
     *
     * @return 公司信息
     */
    SysDepartDO queryCompanyByOrgCode(String orgCode);

    /**
     * 创建新的部门
     *
     * @param depart 部门信息
     */
    void addDepart(DepartVO depart);

    /**
     * 更新已有部门
     *
     * @param depart 部门信息
     */
    void editDepart(DepartVO depart);

    /**
     * 删除指定的部门
     *
     * @param id 部门id
     */
    void deleteDepart(String id);
}
