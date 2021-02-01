package com.njeport.track.auth.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.njeport.track.auth.bean.TreeModel;
import com.njeport.track.auth.dao.condition.PermissionQueryCondition;
import com.njeport.track.auth.dao.condition.RoleQueryCondition;
import com.njeport.track.auth.dao.mapper.track.SysPermissionMapper;
import com.njeport.track.auth.dao.mapper.track.SysRoleMapper;
import com.njeport.track.auth.dao.meta.track.SysPermissionDO;
import com.njeport.track.auth.dao.meta.track.SysRoleDO;
import com.njeport.track.auth.service.SysRoleService;
import com.njeport.track.auth.vo.RoleVO;
import com.njeport.track.common.constants.StringConstants;
import com.njeport.track.common.enums.DelFlagEnum;
import com.njeport.track.common.utils.IdUtils;
import com.njeport.track.common.utils.PagingUtils;
import com.njeport.track.common.utils.SessionUtils;
import com.njeport.track.common.utils.StringUtils;
import com.njeport.track.common.vo.CommonPagingVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author kongming
 * @date 2019/12/29
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysPermissionMapper sysPermissionMapper;

    /**
     * 创建新的角色
     *
     * @param role 待创建角色
     * @return 新创建角色的主键
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addRole(RoleVO role) {
        Assert.hasText(role.getRoleName(), "角色名称不能为空");
        Assert.hasText(role.getRoleCode(), "角色编码不能为空");
        SysRoleDO sysRoleDO = new SysRoleDO();
        String roleId = IdUtils.randomId();
        sysRoleDO.setId(roleId);
        sysRoleDO.setRoleCode(role.getRoleCode());
        sysRoleDO.setRoleName(role.getRoleName());
        sysRoleDO.setCreateBy(SessionUtils.getCurrentUsername());
        sysRoleMapper.insert(sysRoleDO);
        return roleId;
    }

    /**
     * 更新角色
     *
     * @param roleVO 角色信息
     */
    @Override
    public void editRole(RoleVO roleVO) {
        SysRoleDO roleDO = sysRoleMapper.getById(roleVO.getId());
        Assert.notNull(roleDO, "角色不存在");

        SysRoleDO updateRole = new SysRoleDO();
        BeanUtils.copyProperties(roleVO, updateRole);
        updateRole.setUpdateBy(SessionUtils.getCurrentUsername());
        sysRoleMapper.update(updateRole);
    }

    /**
     * 删除单挑角色
     *
     * @param id 角色id
     */
    @Override
    public void deleteRole(String id) {
        SysRoleDO roleDO = sysRoleMapper.getById(id);
        Assert.notNull(roleDO, "角色不存在");
        sysRoleMapper.deleteById(id);
    }

    /**
     * 批量删除角色
     *
     * @param ids 角色id集合
     */
    @Override
    public void batchDeleteRoles(String ids) {
        Assert.isTrue(StringUtils.isNotEmpty(ids), "未选中角色id");
        sysRoleMapper.deleteByIds(Arrays.asList(ids.split(StringConstants.COMMA)));
    }

    /**
     * 根据条件返回角色列表
     *
     * @param condition 查询条件
     * @param pageNo    页号
     * @param pageSize  每页记录数
     * @return 角色列表
     */
    @Override
    public CommonPagingVO<SysRoleDO> queryRolesByPage(RoleQueryCondition condition, int pageNo, int pageSize) {
        int total = sysRoleMapper.countAll(condition);
        PagingUtils.Paging paging = PagingUtils.getPaging(total, pageNo, pageSize);

        condition.setColumn(StringUtils.camelToUnderline(condition.getColumn()));
        List<SysRoleDO> roles = sysRoleMapper.getRoleList(condition, pageSize, (pageNo - 1) * pageSize);
        return CommonPagingVO.getCommonPagingVO(paging, roles);
    }

    /**
     * 查询所有角色
     */
    @Override
    public List<SysRoleDO> queryAll() {
        return sysRoleMapper.getRoleList(new RoleQueryCondition(), -1, 0);
    }

    /**
     * 用户角色授权功能，查询菜单权限树
     */
    @Override
    public Map<String, Object> queryTreeList() {
        PermissionQueryCondition params = new PermissionQueryCondition();
        params.setDelFlag(DelFlagEnum.NORMAL);
        params.setColumn("sort_no");
        params.setOrder("ASC");
        List<SysPermissionDO> permissions = sysPermissionMapper.getPermissionList(params);
        List<String> permissionIds = permissions.stream().map(SysPermissionDO::getId).collect(Collectors.toList());
        List<TreeModel> treeList = Lists.newArrayList();
        getTreeModelList(treeList, permissions, null);
        Map<String, Object> resMap = Maps.newHashMap();
        resMap.put("treeList", treeList);
        resMap.put("ids", permissionIds);
        return resMap;
    }

    private void getTreeModelList(List<TreeModel> treeList, List<SysPermissionDO> metaList, TreeModel temp) {
        for (SysPermissionDO permission : metaList) {
            String tempPid = permission.getParentId();
            TreeModel tree = new TreeModel(permission.getId(), tempPid, permission.getName(), permission.getRuleFlag(), permission.isLeaf());
            if (temp == null && StringUtils.isEmpty(tempPid)) {
                treeList.add(tree);
                if (!tree.getIsLeaf()) {
                    getTreeModelList(treeList, metaList, tree);
                }
            } else if (temp != null && tempPid != null && tempPid.equals(temp.getKey())) {
                temp.getChildren().add(tree);
                if (!tree.getIsLeaf()) {
                    getTreeModelList(treeList, metaList, tree);
                }
            }
        }
    }
}
