package com.upc.srp.service.impl;

import com.upc.srp.dao.UserDao;
import com.upc.srp.model.User;
import com.upc.srp.service.AuthService;
import com.upc.srp.utils.AuthUtil;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

/**
 * @author by zhoutao
 * @description TODO
 * @date 2019/12/26 16:26
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserDao userDao;


    @Override
    public int login(User user) {
        User resultUser = userDao.getUserByUserName(user.getUsername());
        if(resultUser == null){
            return -1;
        }
        // 获取到盐值
        String salt = resultUser.getPassword().split("@")[1];
        // rsa解密
        byte[] pass = Base64.getDecoder().decode(user.getPassword().getBytes());
        String password = AuthUtil.decrypt(pass) + salt;
        byte[] encode =  Base64.getEncoder().encode(password.getBytes());
        password = new String(encode) + "@" + salt;
        if(password.equals(resultUser.getPassword())){
            user.setPassword(password);
            return 0;
        }
        return 1;
    }

    @Override
    public int register(User user) {
        if(userDao.getUserByUserName(user.getUsername()) != null){
            return -1;
        }
        byte[] pass = Base64.getDecoder().decode(user.getPassword().getBytes());
        String origin = AuthUtil.decrypt(pass);
        String salt = AuthUtil.getSalt();
        String password = origin + salt;
        password = new String(Base64.getEncoder().encode(password.getBytes())) + "@" + salt;
        user.setPassword(password);
        int result = userDao.insertUser(user);
        return result!=0?0:1;
    }
}
