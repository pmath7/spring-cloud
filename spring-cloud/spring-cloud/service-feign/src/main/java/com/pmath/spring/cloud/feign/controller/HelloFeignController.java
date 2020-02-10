package com.pmath.spring.cloud.feign.controller;

import com.pmath.spring.cloud.feign.service.HelloFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloFeignController {

    @Autowired
    private HelloFeignService helloFeignService;

    @RequestMapping("/helloFeign")
    public String helloFeign(String name){
        return helloFeignService.helloFeign(name);
    }
}
