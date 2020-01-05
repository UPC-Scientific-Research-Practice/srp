package com.upc.srp.dao;

import com.upc.srp.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author by zhoutao
 * @description TODO
 * @date 2019/12/26 16:55
 */
@Mapper
public interface UserDao {

    public User getUserByUserName(String username);

    public int insertUser(User user);
}
