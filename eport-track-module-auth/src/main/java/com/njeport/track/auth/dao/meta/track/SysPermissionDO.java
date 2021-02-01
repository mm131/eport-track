package com.njeport.track.auth.dao.meta.track;

import com.njeport.track.common.enums.DelFlagEnum;
import com.njeport.track.auth.enums.MenuTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SysPermissionDO {

    /**
     * 主键id
     */
    private String id;

    /**
     * 父id
     */
    private String parentId;

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
    private MenuTypeEnum menuType;

    /**
     * 菜单权限编码
     */
    private String perms;

    /**
     * 权限策略1显示2禁用
     */
    private String permsType;

    /**
     * 菜单排序
     */
    private Double sortNo;

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
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除状态 0正常 1已删除
     */
    private DelFlagEnum delFlag;

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

    public SysPermissionDO() {

    }
    public SysPermissionDO(boolean index) {
        if(index) {
            this.id = "9502685863ab87f0ad1134142788a385";
            this.name="首页";
            this.component="dashboard/Analysis";
            this.url="/dashboard/analysis";
            this.icon="home";
            this.menuType=MenuTypeEnum.ROOT_MENU;
            this.sortNo=0.0;
            this.ruleFlag=0;
            this.delFlag=DelFlagEnum.NORMAL;
            this.alwaysShow=false;
            this.route=true;
            this.keepAlive=true;
            this.leaf=true;
            this.hidden=false;
        }
    }
}
