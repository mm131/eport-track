package com.njeport.track.auth.controller.sys;

import com.njeport.track.auth.service.SysDepartService;
import com.njeport.track.auth.vo.DepartVO;
import com.njeport.track.common.vo.CommonReturnVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author kongming
 * @date 2019/12/25
 */
@RestController
@RequestMapping("/sys/depart")
@Slf4j
public class SysDepartController {

    @Resource
    private SysDepartService sysDepartService;

    /**
     * 获取部门列表
     *
     * @return 菜单列表
     */
    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    public CommonReturnVO queryTreeList() {
        return CommonReturnVO.succ(sysDepartService.queryTreeList());
    }

    /**
     * 获取部门列表
     *
     * @return 菜单列表
     */
    @RequestMapping(value = "/idTree", method = RequestMethod.GET)
    public CommonReturnVO queryIdTree() {
        return CommonReturnVO.succ(sysDepartService.queryIdTree());
    }

    /**
     * 根据公司代码获取公司信息
     *
     * @return 菜单列表
     */
    @RequestMapping(value = "/queryCompanyByOrgCode", method = RequestMethod.GET)
    public CommonReturnVO queryCompanyByOrgCode(String orgCode) {
        return CommonReturnVO.succ(sysDepartService.queryCompanyByOrgCode(orgCode));
    }


    /**
     * 创建部门
     *
     * @param depart 待新增的部门信息
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonReturnVO addDepart(@RequestBody DepartVO depart) {
        sysDepartService.addDepart(depart);
        return CommonReturnVO.succ(null, "部门创建成功");
    }

    /**
     * 更新部门
     *
     * @param depart 待更新的部门信息
     */
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public CommonReturnVO editDepart(@RequestBody DepartVO depart) {
        sysDepartService.editDepart(depart);
        return CommonReturnVO.succ(null, "部门更新成功");
    }

    /**
     * 删除指定的部门
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public CommonReturnVO deleteDepart(String id) {
        sysDepartService.deleteDepart(id);
        return CommonReturnVO.succ(null, "部门删除成功");
    }
}
