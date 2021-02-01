package com.njeport.track.auth.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.njeport.track.common.vo.HierarchyVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author kongming
 * @date 2019/12/25
 */
@Getter
@Setter
public class PermissionVO extends HierarchyVO<PermissionVO> {

    /**
     * 菜单标题
     */
    private String name;

    /**
     * 路径
     */
    private String url;

    /**
     * 组件
     */
    private String component;

    /**
     * 组件名字
     */
    private String componentName;

    /**
     * 一级菜单跳转地址
     */
    private String redirect;

    /**
     * 菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)
     */
    private Integer menuType;

    private String menuTypeText;

    /**
     * 菜单权限编码
     */
    private String perms;

    /**
     * 权限策略1显示2禁用
     */
    private String permsType;

    /**
     * 聚合子路由: 1是0否
     */
    private boolean alwaysShow;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 是否路由菜单: 0:不是  1:是（默认值1）
     */
    private boolean route;

    /**
     * 是否叶子节点: 1:是 0:不是
     */
    private boolean leaf;

    /**
     * 是否缓存该页面: 1:是 0:不是
     */
    private boolean keepAlive;

    /**
     * 是否隐藏路由: 0否,1是
     */
    private boolean hidden;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 删除状态 0正常 1已删除
     */
    private Integer delFlag;

    /**
     * 是否添加数据权限1是0否
     */
    private Integer ruleFlag;

    /**
     * 按钮权限状态(0无效1有效)
     */
    private Integer status;

    /**
     * 外链菜单打开方式 0:内部打开 1:外部打开
     */
    private boolean internalOrExternal;

    public String getId() {
        return id;
    }
}
