package com.danmu.test;

import com.danmu.client.DyBulletScreenClient1;
import com.danmu.msg.DyMessage;
import com.danmu.utils.KeepAlive;
import com.danmu.utils.KeepGetMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class DanmuTest01 {
    private final static Logger logger = LoggerFactory.getLogger(DanmuTest01.class);
    //设置需要访问的房间ID信息
//	private static final int roomId = 301712;
//	private static final String roomId = "58428";
    private static final String roomId1 = "229346";
    private static final String roomId2 = "16101";
//	private static final String roomId = "67554";

    //弹幕池分组号，海量模式使用-9999
    private static final int groupId = -9999;

    public static void main(String[] args)
    {
                //初始化弹幕Client
        DyBulletScreenClient1 danmuClient2 = new DyBulletScreenClient1();
        //设置需要连接和访问的房间ID，以及弹幕池分组号
        danmuClient2.init(roomId2, groupId);



        //初始化弹幕Client
        DyBulletScreenClient1 danmuClient1 = new DyBulletScreenClient1();
        //设置需要连接和访问的房间ID，以及弹幕池分组号
        danmuClient1.init(roomId1, groupId);

        //保持弹幕服务器心跳
//        danmuClient1.keepAlive();

        //获取弹幕服务器发送的所有信息
        danmuClient1.getServerMsg();
        danmuClient1.getServerMsg();
        danmuClient1.getServerMsg();
        logger.info("1111111111111111111111111111111111111111111");
//        danmuClient1.getServerMsg();
//        danmuClient1.getServerMsg();
//        danmuClient1.getServerMsg();
//        danmuClient1.getServerMsg();
//        danmuClient1.getServerMsg();




//        //初始化弹幕Client
//        DyBulletScreenClient1 danmuClient2 = new DyBulletScreenClient1();
//        //设置需要连接和访问的房间ID，以及弹幕池分组号
//        danmuClient1.init(roomId2, groupId);
//
//        //保持弹幕服务器心跳
//        danmuClient2.keepAlive();
//
//        //获取弹幕服务器发送的所有信息
//        danmuClient2.getServerMsg();
//        danmuClient2.getServerMsg();
//        danmuClient2.getServerMsg();
//        danmuClient2.getServerMsg();
//        danmuClient2.getServerMsg();
        danmuClient2.getServerMsg();
        danmuClient2.getServerMsg();
        danmuClient2.getServerMsg();





        logger.info("22222222222222222222222222222222222222222222222");
        danmuClient1.getServerMsg();
        danmuClient1.getServerMsg();
        danmuClient1.getServerMsg();







        logger.info("3333333333333333333333333333333333333333");
        danmuClient2.getServerMsg();
        danmuClient2.getServerMsg();
        danmuClient2.getServerMsg();
    }
}
