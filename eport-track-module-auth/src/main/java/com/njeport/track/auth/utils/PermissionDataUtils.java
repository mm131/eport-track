package com.njeport.track.auth.utils;

import com.njeport.track.auth.dao.meta.track.SysPermissionDO;
import com.njeport.track.auth.enums.MenuTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class PermissionDataUtils {

    /**
     * 智能处理错误数据，简化用户失误操作
     */
    public static SysPermissionDO intelligentProcessData(SysPermissionDO permission) {
        if (permission == null) {
            return null;
        }

        // 组件
        if (StringUtils.isNotEmpty(permission.getComponent())) {
            String component = permission.getComponent();
            if (component.startsWith("/")) {
                component = component.substring(1);
            }
            if (component.startsWith("views/")) {
                component = component.replaceFirst("views/", "");
            }
            if (component.startsWith("src/views/")) {
                component = component.replaceFirst("src/views/", "");
            }
            if (component.endsWith(".vue")) {
                component = component.replace(".vue", "");
            }
            permission.setComponent(component);
        }

        // 请求URL
        if (StringUtils.isNotEmpty(permission.getUrl())) {
            String url = permission.getUrl();
            if (url.endsWith(".vue")) {
                url = url.replace(".vue", "");
            }
            if (!url.startsWith("http") && !url.startsWith("/") && !url.trim().startsWith("{{")) {
                url = "/" + url;
            }
            permission.setUrl(url);
        }

        // 一级菜单默认组件
        if (MenuTypeEnum.ROOT_MENU == permission.getMenuType() && StringUtils.isEmpty(permission.getComponent())) {
            // 一级菜单默认组件
            permission.setComponent("layouts/RouteView");
        }
        return permission;
    }

    /**
     * 如果没有index页面 需要new 一个放到list中
     */
    public static void addIndexPage(List<SysPermissionDO> metaList) {
        boolean hasIndexMenu = false;
        for (SysPermissionDO sysPermission : metaList) {
            if ("首页".equals(sysPermission.getName())) {
                hasIndexMenu = true;
                break;
            }
        }
        if (!hasIndexMenu) {
            metaList.add(0, new SysPermissionDO(true));
        }
    }
}
