package com.njeport.track.auth.dao.condition;

import com.njeport.track.common.base.PageCondition;
import lombok.Getter;
import lombok.Setter;

/**
 * @author kongming
 * @date 2019/12/29
 */
@Getter
@Setter
public class RoleQueryCondition extends PageCondition {
    private String roleCode;
    private String roleName;
}
