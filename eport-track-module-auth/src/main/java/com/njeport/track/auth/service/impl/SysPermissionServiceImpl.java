package com.njeport.track.auth.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.njeport.track.auth.bean.TreeModel;
import com.njeport.track.auth.dao.condition.PermissionQueryCondition;
import com.njeport.track.auth.dao.mapper.track.SysPermissionMapper;
import com.njeport.track.auth.dao.mapper.track.SysRolePermissionMapper;
import com.njeport.track.auth.dao.meta.track.SysPermissionDO;
import com.njeport.track.auth.dao.meta.track.SysRolePermissionDO;
import com.njeport.track.auth.enums.MenuTypeEnum;
import com.njeport.track.common.constants.StringConstants;
import com.njeport.track.common.enums.DelFlagEnum;
import com.njeport.track.auth.service.HierarchyCollectionService;
import com.njeport.track.auth.service.SysPermissionService;
import com.njeport.track.auth.utils.PermissionDataUtils;
import com.njeport.track.auth.vo.PermissionVO;
import com.njeport.track.common.constants.EportConstants;
import com.njeport.track.common.utils.IdUtils;
import com.njeport.track.common.utils.MD5Utils;
import com.njeport.track.common.utils.SessionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author kongming
 * @date 2019/12/29
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Resource
    private HierarchyCollectionService hierarchyCollectionService;

    @Resource
    private SysPermissionMapper permissionMapper;

    @Resource
    private SysRolePermissionMapper rolePermissionMapper;

    /**
     * 创建新的菜单
     *
     * @param permission 待创建菜单
     * @return 新创建菜单的主键
     */
    @Override
    public String addPermission(PermissionVO permission) {
        Assert.hasText(permission.getName(), "菜单名称不能为空");
        SysPermissionDO sysPermissionDO = new SysPermissionDO();
        BeanUtils.copyProperties(permission, sysPermissionDO);
        String resourceId = IdUtils.randomId();
        sysPermissionDO.setId(resourceId);
        sysPermissionDO.setCreateBy(SessionUtils.getCurrentUsername());
        sysPermissionDO.setMenuType(MenuTypeEnum.getByValue(permission.getMenuType()));
        sysPermissionDO.setDelFlag(DelFlagEnum.NORMAL);
        permissionMapper.insert(sysPermissionDO);
        return resourceId;
    }

    /**
     * 根据条件查询菜单集合
     *
     * @param condition 查询条件
     * @return 菜单集合
     */
    @Override
    public List<PermissionVO> queryPermissions(PermissionQueryCondition condition) {
        condition.setDelFlag(DelFlagEnum.NORMAL);
        condition.setColumn("sort_no");
        condition.setOrder("ASC");
        List<SysPermissionDO> resources = permissionMapper.getPermissionList(condition);
        return wrapResources(resources, condition.isHierarchy());
    }

    private List<PermissionVO> wrapResources(List<SysPermissionDO> resources, boolean isHierarchy) {
        List<PermissionVO> resourceList = resources.stream().map(resource -> {
            PermissionVO resourceVO = new PermissionVO();
            BeanUtils.copyProperties(resource, resourceVO);
            if (resource.getSortNo() == null) {
                resourceVO.setSortNo(0.0);
            }
            resourceVO.setMenuTypeText(resource.getMenuType().toString());
            return resourceVO;
        }).collect(Collectors.toList());
        return isHierarchy ? hierarchyCollectionService.buildHierarchyList(resourceList) : resourceList;
    }

    /**
     * 修改已有的菜单
     *
     * @param resource 待更新的菜单信息
     */
    @Override
    public void updatePermission(PermissionVO resource) {
        Assert.hasText(resource.getId(), "必须传入菜单id");
        SysPermissionDO sysResourceDO = permissionMapper.getById(resource.getId());
        Assert.notNull(sysResourceDO, "菜单不存在");

        SysPermissionDO updateResourceDO = new SysPermissionDO();
        BeanUtils.copyProperties(resource, updateResourceDO);
        updateResourceDO.setUpdateBy(SessionUtils.getCurrentUsername());
        permissionMapper.update(updateResourceDO);
    }

    /**
     * 查询用户有权访问的菜单
     *
     * @return 用户有权访问的菜单
     */
    @Override
    public JSONObject queryUserPermissions() {
        String username = SessionUtils.getCurrentUsername();
        List<SysPermissionDO> metaList = permissionMapper.getPermissionsByUsername(username);
        PermissionDataUtils.addIndexPage(metaList);

        JSONObject json = new JSONObject();
        JSONArray menujsonArray = new JSONArray();
        this.getPermissionJsonArray(menujsonArray, metaList, null);
        JSONArray authjsonArray = new JSONArray();
        this.getAuthJsonArray(authjsonArray, metaList);

        PermissionQueryCondition queryPermission = new PermissionQueryCondition();
        queryPermission.setDelFlag(DelFlagEnum.NORMAL);
        queryPermission.setMenuType(MenuTypeEnum.BUTTON);
        List<SysPermissionDO> allAuthList = permissionMapper.getPermissionList(queryPermission);
        JSONArray allauthjsonArray = new JSONArray();
        this.getAllAuthJsonArray(allauthjsonArray, allAuthList);

        //路由菜单
        json.put("menu", menujsonArray);
        //按钮权限
        json.put("auth", authjsonArray);
        //全部权限配置（按钮权限，访问权限）
        json.put("allAuth", allauthjsonArray);
        return json;
    }

    /**
     * 查询用户有权访问的菜单和按钮
     *
     * @param roleId 角色id
     * @return 用户有权访问的菜单和按钮
     */
    @Override
    public List<String> queryRolePermission(String roleId) {
        List<SysRolePermissionDO> list = rolePermissionMapper.getByRoleId(roleId);
        return list.stream().map(SysRolePermissionDO::getPermissionId).collect(Collectors.toList());
    }

    /**
     * 保存授权
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRolePermission(String roleId, String permissionIds, String lastPermissionIds) {
        List<String> add = getDiff(lastPermissionIds, permissionIds);
        if (add != null && add.size() > 0) {
            List<SysRolePermissionDO> list = Lists.newArrayList();
            for (String p : add) {
                if (StringUtils.isNotEmpty(p)) {
                    list.add(new SysRolePermissionDO(IdUtils.randomId(), roleId, p));
                }
            }
            rolePermissionMapper.batchInsert(list);
        }

        List<String> delete = getDiff(permissionIds, lastPermissionIds);
        if (delete != null && delete.size() > 0) {
            for (String permissionId : delete) {
                rolePermissionMapper.deleteByRolePerm(roleId, permissionId);
            }
        }
    }

    /**
     * 获取全部的权限树
     */
    @Override
    public Map<String, Object> queryTreeList() {
        PermissionQueryCondition params = new PermissionQueryCondition();
        params.setDelFlag(DelFlagEnum.NORMAL);
        params.setColumn("sort_no");
        params.setOrder("ASC");
        List<SysPermissionDO> permissions = permissionMapper.getPermissionList(params);
        List<String> permissionIds = permissions.stream().map(SysPermissionDO::getId).collect(Collectors.toList());
        List<TreeModel> treeList = Lists.newArrayList();
        getTreeModelList(treeList, permissions, null);

        Map<String, Object> resMap = Maps.newHashMap();
        resMap.put("treeList", treeList); // 全部树节点数据
        resMap.put("ids", permissionIds);
        return resMap;
    }

    /**
     * 获取菜单JSON数组
     */
    private void getPermissionJsonArray(JSONArray jsonArray, List<SysPermissionDO> metaList, JSONObject parentJson) {
        for (SysPermissionDO permission : metaList) {
            if (permission.getMenuType() == null) {
                continue;
            }
            String tempPid = permission.getParentId();
            JSONObject json = getPermissionJsonObject(permission);
            if (json == null) {
                continue;
            }
            if (parentJson == null && StringUtils.isEmpty(tempPid)) {
                jsonArray.add(json);
                if (!permission.isLeaf()) {
                    getPermissionJsonArray(jsonArray, metaList, json);
                }
            } else if (parentJson != null && StringUtils.isNotEmpty(tempPid) && tempPid.equals(parentJson.getString("id"))) {
                // 类型( 0：一级菜单 1：子菜单 2：按钮 )
                if (permission.getMenuType() == MenuTypeEnum.BUTTON) {
                    JSONObject metaJson = parentJson.getJSONObject("meta");
                    if (metaJson.containsKey("permissionList")) {
                        metaJson.getJSONArray("permissionList").add(json);
                    } else {
                        JSONArray permissionList = new JSONArray();
                        permissionList.add(json);
                        metaJson.put("permissionList", permissionList);
                    }
                    // 类型( 0：一级菜单 1：子菜单 2：按钮 )
                } else if (permission.getMenuType() == MenuTypeEnum.CHILD_MENU || permission.getMenuType() == MenuTypeEnum.ROOT_MENU) {
                    if (parentJson.containsKey("children")) {
                        parentJson.getJSONArray("children").add(json);
                    } else {
                        JSONArray children = new JSONArray();
                        children.add(json);
                        parentJson.put("children", children);
                    }
                    if (!permission.isLeaf()) {
                        getPermissionJsonArray(jsonArray, metaList, json);
                    }
                }
            }
        }
    }

    /**
     * 根据菜单配置生成路由json
     */
    private JSONObject getPermissionJsonObject(SysPermissionDO permission) {
        JSONObject json = new JSONObject();
        // 类型(0：一级菜单 1：子菜单 2：按钮)
        if (permission.getMenuType() == MenuTypeEnum.BUTTON) {
            return null;
        } else if (permission.getMenuType() == MenuTypeEnum.CHILD_MENU || permission.getMenuType() == MenuTypeEnum.ROOT_MENU) {
            json.put("id", permission.getId());
            if (permission.isRoute()) {
                json.put("route", "1");// 表示生成路由
            } else {
                json.put("route", "0");// 表示不生成路由
            }

            if (isWWWHttpUrl(permission.getUrl())) {
                json.put("path", MD5Utils.MD5Encode(permission.getUrl(), "utf-8"));
            } else {
                json.put("path", permission.getUrl());
            }

            // 重要规则：路由name (通过URL生成路由name,路由name供前端开发，页面跳转使用)
            if (StringUtils.isNotEmpty(permission.getComponentName())) {
                json.put("name", permission.getComponentName());
            } else {
                json.put("name", urlToRouteName(permission.getUrl()));
            }

            // 是否隐藏路由，默认都是显示的
            if (permission.isHidden()) {
                json.put("hidden", true);
            }
            // 聚合路由
            if (permission.isAlwaysShow()) {
                json.put("alwaysShow", true);
            }
            json.put("component", permission.getComponent());
            JSONObject meta = new JSONObject();
            // 由用户设置是否缓存页面 用布尔值
            if (permission.isKeepAlive()) {
                meta.put("keepAlive", true);
            } else {
                meta.put("keepAlive", false);
            }

            /*update_begin author:wuxianquan date:20190908 for:往菜单信息里添加外链菜单打开方式 */
            //外链菜单打开方式
            if (permission.isInternalOrExternal()) {
                meta.put("internalOrExternal", true);
            } else {
                meta.put("internalOrExternal", false);
            }
            /* update_end author:wuxianquan date:20190908 for: 往菜单信息里添加外链菜单打开方式*/

            meta.put("title", permission.getName());
            if (StringUtils.isEmpty(permission.getParentId())) {
                // 一级菜单跳转地址
                json.put("redirect", permission.getRedirect());
                if (StringUtils.isNotEmpty(permission.getIcon())) {
                    meta.put("icon", permission.getIcon());
                }
            } else {
                if (StringUtils.isNotEmpty(permission.getIcon())) {
                    meta.put("icon", permission.getIcon());
                }
            }
            if (isWWWHttpUrl(permission.getUrl())) {
                meta.put("url", permission.getUrl());
            }
            json.put("meta", meta);
        }
        return json;
    }

    private boolean isWWWHttpUrl(String url) {
        return url != null && (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("{{"));
    }

    private String urlToRouteName(String url) {
        if (StringUtils.isNotEmpty(url)) {
            if (url.startsWith("/")) {
                url = url.substring(1);
            }
            url = url.replace("/", "-");

            // 特殊标记
            url = url.replace(":", "@");
            return url;
        } else {
            return null;
        }
    }

    /**
     * 获取权限JSON数组
     */
    private void getAuthJsonArray(JSONArray jsonArray, List<SysPermissionDO> metaList) {
        for (SysPermissionDO permission : metaList) {
            if (permission.getMenuType() == null) {
                continue;
            }
            JSONObject json;
            if (permission.getMenuType() == MenuTypeEnum.BUTTON && EportConstants.STATUS_1 == permission.getStatus()) {
                json = new JSONObject();
                json.put("action", permission.getPerms());
                json.put("type", permission.getPermsType());
                json.put("describe", permission.getName());
                jsonArray.add(json);
            }
        }
    }

    /**
     * 获取权限JSON数组
     */
    private void getAllAuthJsonArray(JSONArray jsonArray, List<SysPermissionDO> allList) {
        JSONObject json;
        for (SysPermissionDO permission : allList) {
            json = new JSONObject();
            json.put("action", permission.getPerms());
            json.put("status", permission.getStatus());
            json.put("type", permission.getPermsType());
            json.put("describe", permission.getName());
            jsonArray.add(json);
        }
    }

    private List<String> getDiff(String main, String diff) {
        if (StringUtils.isEmpty(diff)) {
            return null;
        }
        if (StringUtils.isEmpty(main)) {
            return Arrays.asList(diff.split(StringConstants.COMMA));
        }
        String[] mainArr = main.split(StringConstants.COMMA);
        String[] diffArr = diff.split(StringConstants.COMMA);
        Map<String, Integer> map = new HashMap<>();
        for (String string : mainArr) {
            map.put(string, 1);
        }
        List<String> res = new ArrayList<String>();
        for (String key : diffArr) {
            if (StringUtils.isNotEmpty(key) && !map.containsKey(key)) {
                res.add(key);
            }
        }
        return res;
    }

    private void getTreeModelList(List<TreeModel> treeList, List<SysPermissionDO> metaList, TreeModel temp) {
        for (SysPermissionDO permission : metaList) {
            String tempPid = permission.getParentId();
            TreeModel tree = new TreeModel(permission);
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
