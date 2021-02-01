package com.njeport.track.common.utils;

import com.njeport.track.common.vo.LoginUserVO;

/**
 * @author kongming
 * @date 2019/12/18
 */
public class SessionUtils {
    private static final ThreadLocal<LoginUserVO> userThread = new ThreadLocal<LoginUserVO>();

    public static LoginUserVO getCurrentUser() {
        return userThread.get();
    }

    public static String getCurrentUserId() {
        LoginUserVO user = getCurrentUser();
        return user == null ? null : user.getUserId();
    }

    public static String getCurrentUsername() {
        LoginUserVO user = getCurrentUser();
        return user == null ? null : user.getUsername();
    }

    public static String getOrgCode() {
        LoginUserVO user = getCurrentUser();
        return user == null ? null : user.getOrgCode();
    }

    public static void setCurrentUser(LoginUserVO user) {
        userThread.set(user);
    }

    public static void clearCurrentUser() {
        userThread.remove();
    }
}
