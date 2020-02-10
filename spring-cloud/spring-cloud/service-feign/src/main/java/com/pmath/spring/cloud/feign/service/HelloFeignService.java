package com.pmath.spring.cloud.feign.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-hello",fallback = HelloFeignServiceHystrix.class)
public interface HelloFeignService {
    @RequestMapping(value = "/hello")
    String helloFeign(@RequestParam(value = "name")String name);
}
