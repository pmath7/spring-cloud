package com.pmath.spring.cloud.jwt.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.pmath.spring.cloud.jwt.annotation.PassToken;
import com.pmath.spring.cloud.jwt.annotation.UserLoginToken;
import com.pmath.spring.cloud.jwt.dto.User;
import com.pmath.spring.cloud.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        String token = request.getHeader("token");
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();

        if(method.isAnnotationPresent(PassToken.class)){
            PassToken passToken = method.getAnnotation(PassToken.class);
            if(passToken.required()){
                return true;
            }
        }
        if(method.isAnnotationPresent(UserLoginToken.class)){
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if(userLoginToken.required()){
                if(token == null){
                    throw new RuntimeException("无效token，请重新登陆");
                }
            }
            String userId;
            try {
                userId = JWT.decode(token).getAudience().get(0);
            } catch(JWTDecodeException j){
                throw new RuntimeException("401");
            }
            User user = userService.findUserById(userId);
            if(user == null){
                throw new RuntimeException("用户不存在，请重新登陆");
            }
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
            try {
                jwtVerifier.verify(token);
            } catch(JWTDecodeException j){
                throw new RuntimeException("401");
            }
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
