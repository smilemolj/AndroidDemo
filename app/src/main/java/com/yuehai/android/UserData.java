package com.yuehai.android;

import com.yuehai.android.net.response.UserBean;
import com.yuehai.android.util.LogUtil;
import com.yuehai.android.util.SPUtil;

import java.util.Date;

public class UserData {
    private static String IS_LOGIN = "SP_IS_LOGIN";

    private static String TOKEN = "SP_USER_TOKEN";
    private static String REFRESH_TOKEN = "SP_USER_REFRESH_TOKEN";
    private static String TOKEN_HEADER = "SP_USER_TOKEN_HEADER";
    private static String EXPIRATION = "SP_USER_EXPIRATION";
    private static String USER_NAME = "SP_USER_USER_NAME";
    private static String NICK_NAME = "SP_USER_NICK_NAME";
    private static String PHONE = "SP_USER_PHONE";
    private static String EMAIL = "SP_USER_EMAIL";
    private static String AVATAR = "SP_USER_AVATAR";
    private static String STATUS = "SP_USER_STATUS";


    private UserData() {
    }

    public static UserData getInstance() {
        return ClassHolder.INSTANCE;
    }

    private static class ClassHolder {
        private static final UserData INSTANCE = new UserData();
    }

    private UserBean user;

    public UserBean getUser() {
        if (user == null) {
            SPUtil spUtil = SPUtil.getInstance(Contacts.SP_NAME);
            boolean isLogin = spUtil.getBoolean(IS_LOGIN, false);
            if (isLogin) {
                user = new UserBean();
                user.setAccessToken(spUtil.getString(TOKEN));
                user.setRefreshToken(spUtil.getString(REFRESH_TOKEN));
                user.setTokenHeader(spUtil.getString(TOKEN_HEADER));
                user.setExpiration(spUtil.getLong(EXPIRATION));
                user.setUserName(spUtil.getString(USER_NAME));
                user.setNickName(spUtil.getString(NICK_NAME));
                user.setPhone(spUtil.getString(PHONE));
                user.setEmail(spUtil.getString(EMAIL));
                user.setAvatar(spUtil.getString(AVATAR));
                user.setStatus(spUtil.getInt(STATUS, -1));
            }
        }
        return user;
    }

    public void saveUser(UserBean userBean) {
        this.user = userBean;
        LogUtil.e("======保存的过期时间是：" + new Date(user.getExpiration()));
        SPUtil.getInstance(Contacts.SP_NAME)
                .getEdit()
                .putBoolean(IS_LOGIN, true)
                .putString(TOKEN, userBean.getAccessToken())
                .putString(REFRESH_TOKEN, userBean.getRefreshToken())
                .putString(TOKEN_HEADER, userBean.getTokenHeader())
                .putLong(EXPIRATION, userBean.getExpiration())
                .putString(USER_NAME, userBean.getUserName())
                .putString(NICK_NAME, userBean.getNickName())
                .putString(PHONE, userBean.getPhone())
                .putString(EMAIL, userBean.getEmail())
                .putString(AVATAR, userBean.getAvatar())
                .putInt(STATUS, userBean.getStatus())
                .commit();
    }

    public void clearUser() {
        SPUtil.getInstance(Contacts.SP_NAME)
                .getEdit()
                .putBoolean(IS_LOGIN, false)
                .remove(TOKEN)
                .remove(TOKEN_HEADER)
                .remove(USER_NAME)
                .remove(NICK_NAME)
                .remove(PHONE)
                .remove(EMAIL)
                .remove(AVATAR)
                .remove(STATUS)
                .commit();
        this.user = null;
    }
}
