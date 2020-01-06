package com.upc.srp.utils;

import com.auth0.jwt.JWT;
import com.upc.srp.dao.UserDao;
import com.upc.srp.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author by zhoutao
 * @description TODO
 * @date 2019/12/27 10:40
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        if(token == null || "".equals(token)){
            throw new RuntimeException("没有token，无法认证");
        }
        String username = JWT.decode(token).getAudience().get(0);
        User user = userDao.getUserByUserName(username);
        if(username == null){
            throw new RuntimeException("用户不存在");
        }
        if(!JWTUtil.verify(token, user)){
            throw new RuntimeException("token验证失败");
        }
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }
}
