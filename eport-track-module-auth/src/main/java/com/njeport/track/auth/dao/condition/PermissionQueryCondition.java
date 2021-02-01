package com.njeport.track.auth.dao.condition;

import com.njeport.track.auth.enums.MenuTypeEnum;
import com.njeport.track.common.base.PageCondition;
import com.njeport.track.common.enums.DelFlagEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author kongming
 * @date 2019/12/29
 */
@Getter
@Setter
public class PermissionQueryCondition extends PageCondition {
    private String resourceName;
    private DelFlagEnum delFlag;
    private MenuTypeEnum menuType;
    private boolean isHierarchy;
}
