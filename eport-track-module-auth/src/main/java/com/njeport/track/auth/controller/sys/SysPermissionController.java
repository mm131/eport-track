package com.njeport.track.auth.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.njeport.track.auth.dao.condition.PermissionQueryCondition;
import com.njeport.track.auth.service.SysPermissionService;
import com.njeport.track.auth.vo.PermissionVO;
import com.njeport.track.common.vo.CommonReturnVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author kongming
 * @date 2019/12/25
 */
@RestController
@RequestMapping("/sys/permission")
@Slf4j
public class SysPermissionController {

    @Resource
    private SysPermissionService sysPermissionService;

    /**
     * 创建菜单
     *
     * @param permission 待新增的菜单信息
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonReturnVO addPermission(@RequestBody PermissionVO permission) {
        return CommonReturnVO.succ(sysPermissionService.addPermission(permission), "新增菜单成功");
    }

    /**
     * 获取菜单列表
     *
     * @return 菜单列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonReturnVO listPermissions(PermissionQueryCondition condition) {
        return CommonReturnVO.succ(sysPermissionService.queryPermissions(condition));
    }

    /**
     * 更新菜单
     *
     * @param permission 待更新的菜单信息
     */
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public CommonReturnVO updatePermission(@RequestBody PermissionVO permission) {
        sysPermissionService.updatePermission(permission);
        return CommonReturnVO.succ(null, "菜单更新成功");
    }

    /**
     * 获取用户有权访问的菜单列表
     *
     * @return 菜单列表
     */
    @RequestMapping(value = "/listUserPermissions", method = RequestMethod.GET)
    public CommonReturnVO listUserPermissions() {
        return CommonReturnVO.succ(sysPermissionService.queryUserPermissions());
    }

    /**
     * 获取角色对应的菜单id列表
     *
     * @param roleId 角色id
     * @return 菜单id列表
     */
    @RequestMapping(value = "/queryRolePermission", method = RequestMethod.GET)
    public CommonReturnVO queryRolePermission(@RequestParam(name = "roleId", required = true) String roleId) {
        List<String> permissionIds = sysPermissionService.queryRolePermission(roleId);
        return CommonReturnVO.succ(permissionIds);
    }

    /**
     * 角色授权
     */
    @RequestMapping(value = "/saveRolePermission", method = RequestMethod.POST)
    public CommonReturnVO saveRolePermission(@RequestBody JSONObject json) {
        String roleId = json.getString("roleId");
        String permissionIds = json.getString("permissionIds");
        String lastPermissionIds = json.getString("lastpermissionIds");
        sysPermissionService.saveRolePermission(roleId, permissionIds, lastPermissionIds);
        return CommonReturnVO.succ(null, "角色授权成功");
    }

    /**
     * 获取全部的权限树
     */
    @RequestMapping(value = "/queryTreeList", method = RequestMethod.GET)
    public CommonReturnVO queryTreeList() {
        Map<String, Object> resMap = sysPermissionService.queryTreeList();
        return CommonReturnVO.succ(resMap);
    }
}
