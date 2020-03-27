package com.pmath.spring.cloud.jwt.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pmath.spring.cloud.jwt.dto.User;

public class JwtUtil {

    private String token = "";

    public String getToken(User user){
        token = JWT.create().withAudience(user.getId())
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
