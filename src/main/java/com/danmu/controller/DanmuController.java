package com.danmu.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/danmu")
public class DanmuController {
    private final static Logger logger = LoggerFactory.getLogger(DanmuController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/hello")
    public String danmuTest(){
        String sql = "INSERT INTO chatmsg(rid,uid,nn,txt,level,col,ct) VALUES ('111','111','111','111','1','0','0');";
        jdbcTemplate.execute(sql);

        logger.info("1111111");
        return "hello";
    }
}
