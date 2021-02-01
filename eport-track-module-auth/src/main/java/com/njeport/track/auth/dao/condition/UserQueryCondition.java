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
public class UserQueryCondition extends PageCondition {
    private String username;
    private Integer sex;
    private String email;
    private String phone;
    private Integer status;
}
