package com.pmath.spring.cloud.jwt.api;

import com.alibaba.fastjson.JSONObject;
import com.pmath.spring.cloud.jwt.annotation.UserLoginToken;
import com.pmath.spring.cloud.jwt.dto.User;
import com.pmath.spring.cloud.jwt.service.TokenService;
import com.pmath.spring.cloud.jwt.service.UserService;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class UserApi {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    //@PostMapping("/login")
    @RequestMapping(path = "/login",method = RequestMethod.POST, produces = "application/json")
    public Object login(@RequestBody User user){
        JSONObject jsonObject = new JSONObject();
        User userForBean = userService.findUserById(user.getId());
        if(userForBean == null){
            jsonObject.put("message","登陆失败，用户不存在");
            return  jsonObject;
        } else{
            if(!userForBean.getPassword().equals(user.getPassword())){
                jsonObject.put("message","登陆失败，密码错误");
                return jsonObject;
            }else{
                String token = tokenService.getToken(userForBean);
                jsonObject.put("token",token);
                jsonObject.put("user",userForBean);
                return jsonObject;
            }
        }
    }
    @UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已经验证通过";
    }

}
