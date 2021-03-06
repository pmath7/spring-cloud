package com.pmath.spring.cloud.ribbon.controller;

import com.pmath.spring.cloud.ribbon.service.HelloRibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloRibbonController {

    @Autowired
    private HelloRibbonService helloRibbonService;

    @RequestMapping("/helloRibbon")
    public String helloRibbon(String name){
        return helloRibbonService.helloRibbon(name);
    }
}
