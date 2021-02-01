package com.njeport.track.auth.service;

import com.njeport.track.common.vo.LoginUserVO;

/**
 * @author kongming
 * @date 2019/12/18
 */
public interface JwtService {

    /**
     * 将 token 解析为登录用户信息
     *
     * @param token jwt token字符串
     * @return 登录用户信息
     */
    LoginUserVO parseToken(String token);
}
