package com.njeport.track.auth.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author kongming
 * @date 2019/12/29
 */
@Getter
@Setter
public class UserVO {
    private String id;
    private String username;
    private String realname;
    private String avatar;
    private Integer sex;
    private String sex_dictText;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    private String phone;
    private String email;
    private Integer status;
    private String status_dictText;
    private String workNo;
    private String workCode;
    private String telephone;

    private String password;
    private String selectedroles;
    private String selecteddeparts;
    private String oldpassword;
    private String confirmpassword;
}
