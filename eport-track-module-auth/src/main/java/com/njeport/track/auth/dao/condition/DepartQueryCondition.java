package com.njeport.track.auth.dao.condition;

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
public class DepartQueryCondition extends PageCondition {
    private DelFlagEnum delFlag;
    private boolean isHierarchy;
}
