package com.njeport.track.auth.controller.sys;

import com.njeport.track.auth.dao.condition.RoleQueryCondition;
import com.njeport.track.auth.service.SysRoleService;
import com.njeport.track.auth.vo.RoleVO;
import com.njeport.track.common.vo.CommonReturnVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author kongming
 * @date 2019/12/25
 */
@RestController
@RequestMapping("/sys/role")
@Slf4j
public class SysRoleController {

    @Resource
    private SysRoleService roleService;

    /**
     * 创建角色
     *
     * @param role 待新增的角色信息
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonReturnVO addRole(@RequestBody RoleVO role) {
        return CommonReturnVO.succ(roleService.addRole(role), "创建成功");
    }

    /**
     * 更新角色
     */
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public CommonReturnVO editRole(@RequestBody RoleVO role) {
        roleService.editRole(role);
        return CommonReturnVO.succ(null, "更新角色成功");
    }

    /**
     * 获取角色列表
     *
     * @return 角色列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonReturnVO listRoles(RoleQueryCondition condition,
                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return CommonReturnVO.succ(roleService.queryRolesByPage(condition, pageNo, pageSize));
    }

    /**
     * 删除角色
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public CommonReturnVO delete(@RequestParam(name="id",required=true) String id) {
        roleService.deleteRole(id);
        return CommonReturnVO.succ(null, "删除角色成功");
    }

    /**
     * 批量删除角色
     */
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.DELETE)
    public CommonReturnVO deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        roleService.batchDeleteRoles(ids);
        return CommonReturnVO.succ(null, "批量删除角色成功");
    }

    /**
     * 查询所有的角色
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public CommonReturnVO listAllRoles() {
        return CommonReturnVO.succ(roleService.queryAll());
    }

    /**
     * 用户角色授权功能
     */
    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    public CommonReturnVO queryTreeList(){
        return CommonReturnVO.succ(roleService.queryTreeList());
    }
}
