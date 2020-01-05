package com.upc.srp.controller;

import com.upc.srp.dto.Response;
import com.upc.srp.dto.User;
import com.upc.srp.service.AuthService;
import com.upc.srp.utils.AuthUtil;
import com.upc.srp.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author by zhoutao
 * @description 鉴权控制器
 * @date 2019/12/26 16:12
 */
@Controller
public class AuthController {
    
    @Autowired
    AuthService authService;

    @ResponseBody
    @GetMapping("/auth/key")
    public Response getKay(){
        String key = AuthUtil.getKeyString();
        if(!"".equals(key)){
            return Response.getBuilder()
                    .setCode(200)
                    .setMessage("success")
                    .setData(key).build();
        }else{
            return Response.getBuilder()
                    .setCode(400)
                    .setMessage("error").build();
        }
    }

    @ResponseBody
    @PostMapping("/auth/login")
    public Response login(@RequestBody User user){
        int result = authService.login(user);
        String token = JWTUtil.generateToken(user);
        if(result == 0){
            return Response.getBuilder().setCode(200).setMessage("success").setData(token).build();
        }else if(result == -1){
            return Response.getBuilder().setData(400).setMessage("error").setData("用户名不存在").build();
        }else{
            return Response.getBuilder().setData(400).setMessage("error").setData("用户名密码不匹配").build();
        }
    }

    @ResponseBody
    @PostMapping("/auth/register")
    public Response register(@RequestBody User user){
        int result = authService.register(user);
        if(result == 0){
            return Response.getBuilder().setCode(200).setMessage("success").setData("注册成功").build();
        }else if(result == -1){
            return Response.getBuilder().setCode(400).setMessage("error").setData("用户名已注册").build();
        }else{
            return Response.getBuilder().setCode(400).setMessage("error").setData("注册失败").build();
        }
    }

}
