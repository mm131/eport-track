package com.njeport.track.auth.controller.sys;

import com.njeport.track.auth.dao.condition.UserQueryCondition;
import com.njeport.track.auth.service.SysUserService;
import com.njeport.track.auth.vo.UserVO;
import com.njeport.track.common.utils.IdUtils;
import com.njeport.track.common.vo.CommonReturnVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/sys/user")
@Slf4j
public class SysUserController {

    @Resource
    private SysUserService userService;

    /**
     * 创建用户
     *
     * @param user 待新增的用户信息
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CommonReturnVO addUser(@RequestBody UserVO user) {
        userService.addUser(user);
        return CommonReturnVO.succ(null, "用户创建成功");
    }

    /**
     * 更新用户
     *
     * @param user 待新增的用户信息
     */
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public CommonReturnVO editUser(@RequestBody UserVO user) {
        userService.editUser(user);
        return CommonReturnVO.succ(null, "修改用户成功");
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public CommonReturnVO deleteUser(@RequestParam(name = "id", required = true) String id) {
        userService.deleteUser(id);
        return CommonReturnVO.succ(null, "删除用户成功");
    }

    /**
     * 获取用户列表
     *
     * @return 用户列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public CommonReturnVO listUsers(UserQueryCondition condition,
                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        return CommonReturnVO.succ(userService.queryUsersByPage(condition, pageNo, pageSize));
    }


    /**
     * 获取所有用户列表--不分页
     *
     * @return 用户列表
     */
    @RequestMapping(value = "/queryUsersAll", method = RequestMethod.GET)
    public CommonReturnVO queryUsersAll() {
        return CommonReturnVO.succ(userService.queryUsersAll());
    }

    /**
     * 获取用户角色
     */
    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public CommonReturnVO queryUserRole(@RequestParam("userid") String userId) {
        return CommonReturnVO.succ(userService.queryRoles(userId));
    }

    /**
     * 获取用户部门
     */
    @RequestMapping(value = "/depart", method = RequestMethod.GET)
    public CommonReturnVO queryUserDepart(@RequestParam("userId") String userId) {
        return CommonReturnVO.succ(userService.queryRoles(userId));
    }

    /**
     * 生成在添加用户情况下没有主键的问题,返回给前端,根据该id绑定部门数据
     */
    @RequestMapping(value = "/generateUserId", method = RequestMethod.GET)
    public CommonReturnVO generateUserId() {
        return CommonReturnVO.succ(IdUtils.randomId());
    }

    /**
     * 更新用户
     *
     * @param user 待新增的用户信息
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.PUT)
    public CommonReturnVO updatePassword(@RequestBody UserVO user) {
        userService.updatePassword(user);
        return CommonReturnVO.succ(null, "修改用户成功");
    }
}
