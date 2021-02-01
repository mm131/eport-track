package com.njeport.track.auth.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author kongming
 * @date 2020/02/17
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationCode {
    private String id;
    private String img;
    @JsonIgnore
    private String code;
}
