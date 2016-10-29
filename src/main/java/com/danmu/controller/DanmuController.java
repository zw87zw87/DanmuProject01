package com.danmu.controller;

import com.danmu.client.DyBulletScreenClient;
import com.danmu.utils.KeepAlive;
import com.danmu.utils.KeepGetMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/danmu")
public class DanmuController {
    private final static Logger logger = LoggerFactory.getLogger(DanmuController.class);
    //弹幕池分组号，海量模式使用-9999
    private static final int groupId = -9999;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping("/hello")
    public String danmuTest(){
        String sql = "INSERT INTO chatmsg(rid,uid,nn,txt,level,col,ct) VALUES ('发发发','啊啊啊','嗯嗯嗯','对对对','1','0','0');";
        jdbcTemplate.execute(sql);

        logger.info("方法反反复复反复反复反复反复！！！！！！！！！！");
        return "hello";
    }

    @RequestMapping(value = "/join/{id}", method = RequestMethod.GET)
    public String danmuJoin(@PathVariable("id") String id){
        logger.info("进入直播间ID：" + id);

        //初始化弹幕Client
        DyBulletScreenClient danmuClient = DyBulletScreenClient.getInstance();
        //设置需要连接和访问的房间ID，以及弹幕池分组号
        danmuClient.init(id, groupId);

        //保持弹幕服务器心跳
        KeepAlive keepAlive = new KeepAlive();
        keepAlive.start();

        //获取弹幕服务器发送的所有信息
        KeepGetMsg keepGetMsg = new KeepGetMsg();
        keepGetMsg.start();

        return "join";
    }


}
