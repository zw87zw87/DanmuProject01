package com.danmu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("danmu")
public class DanmuController {
    private final static Logger logger = LoggerFactory.getLogger(DanmuController.class);

    @RequestMapping("/hello")
    public String danmuTest(){

        return "hello";
    }
}
