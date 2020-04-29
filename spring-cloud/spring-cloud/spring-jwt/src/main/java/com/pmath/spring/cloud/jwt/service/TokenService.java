package com.pmath.spring.cloud.jwt.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pmath.spring.cloud.jwt.dto.User;
import org.springframework.stereotype.Service;

@Service("TokenService")
public class TokenService {
    public String getToken(User user){
        String token = "";
        token = JWT.create().withAudience(user.getId()).sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
