package com.yuehai.android.net.request;

/**
 * Created by zhaoyuehai 2019/3/29
 */
public class RegisterUserBean {
    private String userName;
    private String password;
    private String phone;

    public RegisterUserBean(String userName, String password, String phone) {
        this.userName = userName;
        this.password = password;
        this.phone = phone;
    }
}
