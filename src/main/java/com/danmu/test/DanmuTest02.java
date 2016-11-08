package com.danmu.test;

import com.danmu.msg.DyMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DanmuTest02 {
    private static final Logger logger = LoggerFactory.getLogger(DanmuTest02.class);
    //第三方弹幕协议服务器地址
    private static final String hostName = "openbarrage.douyutv.com";
    //第三方弹幕协议服务器端口
    private static final int port = 8601;
    //设置字节获取buffer的最大值
    private static final int MAX_BUFFER_LENGTH = 4096;
    //弹幕池分组号，海量模式使用-9999
    private static final int groupId = -9999;
//    //socket相关配置
//    private Socket sock;
//    private BufferedOutputStream bos;
//    private BufferedInputStream bis;
//    //获取弹幕线程及心跳线程运行和停止标记
//    private boolean readyFlag = false;


    public static void main(String[] args) {
        int numTasks = 2;
        String roomId0 = "229346";
        String roomId1 = "56040";

        ExecutorService exec = Executors.newCachedThreadPool();

        for (int i = 0; i < numTasks; i++) {
            exec.execute(createTask(i , "roomId" + i));
        }

    }

    // 定义一个简单的任务
    private static Runnable createTask(final int taskID , final String roomId) {
        return new Runnable() {
            private Socket socket = null;
//            private int port=8821;

            public void run() {
                System.out.println("Task " + taskID + ":start");
                System.out.println("roomId: " + roomId);
                try {
                    String host = InetAddress.getByName(hostName).getHostAddress();
                    socket = new Socket(host, port);

                    BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
                    BufferedInputStream bis= new BufferedInputStream(socket.getInputStream());

                    loginRoom(roomId, bos, bis);

                    joinGroup(roomId, groupId, bos);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };
    }

    /**
     * 登录指定房间
     * @param roomId
     */
    private static void loginRoom(String roomId,BufferedOutputStream bos,BufferedInputStream bis)
    {
        //获取弹幕服务器登陆请求数据包
        byte[] loginRequestData = DyMessage.getLoginRequestData(roomId);
        logger.info("登陆房间请求数据包>>>"+new String(loginRequestData));

        try{
            //发送登陆请求数据包给弹幕服务器
            bos.write(loginRequestData, 0, loginRequestData.length);
            bos.flush();

            //初始化弹幕服务器返回值读取包大小
            byte[] recvByte = new byte[MAX_BUFFER_LENGTH];
            //获取弹幕服务器返回值
            bis.read(recvByte, 0, recvByte.length);

            //解析服务器返回的登录信息
            if(DyMessage.parseLoginRespond(recvByte)){
                logger.debug("登录房间号：" + roomId + " 成功！");
            } else {
                logger.error("登录房间号：" + roomId + " 失败！" + recvByte.toString());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 加入弹幕分组池
     * @param roomId
     * @param groupId
     */
    private static void joinGroup(String roomId, int groupId,BufferedOutputStream bos)
    {
        //获取弹幕服务器加弹幕池请求数据包
        byte[] joinGroupRequest = DyMessage.getJoinGroupRequest(roomId, groupId);

        try{
            //想弹幕服务器发送加入弹幕池请求数据
            bos.write(joinGroupRequest, 0, joinGroupRequest.length);
            bos.flush();
            logger.debug(" 加入弹幕分组池成功   Send join group request successfully!");

        } catch(Exception e){
            e.printStackTrace();
            logger.error(" 加入弹幕分组池失败   Send join group request failed!");
        }
    }
}
