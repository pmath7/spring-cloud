package com.pmath.spring.cloud.ribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloRibbonService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "helloError")
    public String helloRibbon(String name){
        return restTemplate.getForObject("http://SERVICE-HELLO/hello?name="+name, String.class);
    }

    public String helloError(String name){
        return "hi "+name+", sorry ,this is ribbon error.";
    }
}
