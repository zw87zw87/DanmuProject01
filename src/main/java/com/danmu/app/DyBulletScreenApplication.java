package com.danmu.app;

import com.danmu.client.DyBulletScreenClient;
import com.danmu.utils.KeepAlive;
import com.danmu.utils.KeepGetMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @summary: 弹幕Demo程序启动类 
 * @author: FerroD     
 * @date:   2016-3-12   
 * @version V1.0
 */
public class DyBulletScreenApplication
{
	private final static Logger logger = LoggerFactory.getLogger(DyBulletScreenApplication.class);
	//设置需要访问的房间ID信息
//	private static final int roomId = 301712;
//	private static final String roomId = "58428";
//	private static final String roomId = "229346";
	private static final String roomId = "241123";
//	private static final String roomId = "67554";

	//弹幕池分组号，海量模式使用-9999
	private static final int groupId = -9999;
	
	public static void main(String[] args)
	{
		//初始化弹幕Client
        DyBulletScreenClient danmuClient = DyBulletScreenClient.getInstance();
        //设置需要连接和访问的房间ID，以及弹幕池分组号
        danmuClient.init(roomId, groupId);
        
        //保持弹幕服务器心跳
        KeepAlive keepAlive = new KeepAlive();
        keepAlive.start();
        
        //获取弹幕服务器发送的所有信息
        KeepGetMsg keepGetMsg = new KeepGetMsg();
        keepGetMsg.start();
	}
}