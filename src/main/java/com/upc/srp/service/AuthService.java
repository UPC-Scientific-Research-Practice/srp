package com.upc.srp.service;

import com.upc.srp.model.User;

/**
 * @author by zhoutao
 * @description TODO
 * @date 2019/12/26 16:26
 */
public interface AuthService {

    /**
     * @description 登录方法，用于用户登录安全认证
     * @param user
     * @return int
     * @author zhoutao
     * @date 2019/12/26 16:51
     */
    public int login(User user);


    /**
     * @description 注册方法，用于用户注册的处理，安全认证
     * @param user
     * @return int
     * @author zhoutao
     * @date 2019/12/26 16:51
     */
    public int register(User user);

}
