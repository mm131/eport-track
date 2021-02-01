package com.njeport.track.common.constants;

/**
 * @author kongming
 * @date 2020/03/06
 */
public interface EportConstants {
    String TOKEN_HEADER = "X-Access-Token";
    String TOKEN_PREFIX = "track_user_token_";
    String REDIS_KEY_PREFIX = "eport:track:redis:";
    String CACHE_NAME = "eport:track:cache";
    String NJEDI_LOGIN_REDIS_KEY = "njedi_login";

    int MENU_TYPE_0  = 0;   //一级菜单
    int MENU_TYPE_1  = 1;   //子菜单
    int MENU_TYPE_2  = 2;   //按钮权限

    int STATUS_0 = 0;  //无效状态
    int STATUS_1 = 1;  //有效状态

    int DEL_FLAG_0 = 0; //未删除
    int DEL_FLAG_1 = 1; //已删除
}
