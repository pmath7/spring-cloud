package com.pmath.spring.cloud.feign.service;

import org.springframework.stereotype.Component;

@Component
public class HelloFeignServiceHystrix implements HelloFeignService{
    @Override
    public String helloFeign(String name) {
        return "hi "+name+", sorry, this is feign hystrix error.";
    }
}
