package com.danmu.utils;

import com.danmu.domain.Room;
import com.danmu.msg.DyMessage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

/**
 * @Summary: 服务器心跳保持线程
 * @author: FerroD
 * @date:   2016-3-12
 * @version V1.0
 */
public class KeepAlive1 extends Thread {
    private BufferedOutputStream bos;
    //获取弹幕线程及心跳线程运行和停止标记
    private boolean readyFlag = false;

    public KeepAlive1(BufferedOutputStream bos , boolean readyFlag) {
        this.bos = bos;
        this.readyFlag = readyFlag;
    }

    @Override
    public void run()
    {
        //判断客户端就绪状态
        while(readyFlag)
        {
            //判断是否关播
            Room room = RoomHttp.roomGet("229346");
            if (room.getRoom_status().equals("2")){
                readyFlag = false;
            }

            //发送心跳保持协议给服务器端
            keepAlive(bos);
            try
            {
                //设置间隔45秒再发送心跳协议
                Thread.sleep(45000);        //keep live at least once per minute
            }
            catch (Exception e)
            {
                e.printStackTrace();

            }
        }
    }


    /**
     * 服务器心跳连接
     */
    public void keepAlive( BufferedOutputStream bos)
    {
        //获取与弹幕服务器保持心跳的请求数据包
        byte[] keepAliveRequest = DyMessage.getKeepAliveData((int)(System.currentTimeMillis() / 1000));

        try{
            //向弹幕服务器发送心跳请求数据包
            bos.write(keepAliveRequest, 0, keepAliveRequest.length);
            bos.flush();
//            logger.debug("服务器心跳连接成功   Send keep alive request successfully!");

        } catch(Exception e){
            e.printStackTrace();
            System.out.println("服务器心跳连接失败:" + e);
//            logger.error("服务器心跳连接失败   Send keep alive request failed!");
        }
    }

}
