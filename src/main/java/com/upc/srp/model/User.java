package com.upc.srp.model;

/**
 * @author by zhoutao
 * @description TODO
 * @date 2019/12/26 16:24
 */
public class User {

    private String username;
    private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
