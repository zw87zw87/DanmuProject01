package com.danmu.test;

import com.danmu.domain.Room;
import com.danmu.msg.DyMessage;
import com.danmu.msg.MsgView;
import com.danmu.utils.KeepAlive1;
import com.danmu.utils.RoomHttp;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Map;
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
        String roomId1 = "229346";
        String roomId2 = "532152";
        String roomId3 = "244548";
        String roomId4 = "56040";
        String roomId5 = "453751";

//        Room room = RoomHttp.roomGet(roomId1);
        // 1.开播  2.关播
//        logger.info("是否开播：" + room.getRoom_status());

        ExecutorService exec = Executors.newCachedThreadPool();
//
////        for (int i = 0; i < numTasks; i++) {
////            exec.execute(createTask(i , "roomId" + i));
////        }
//
        exec.execute(createTask(roomId1));
//        exec.execute(createTask(roomId2));
//        exec.execute(createTask(roomId3));
//        exec.execute(createTask(roomId4));
//        exec.execute(createTask(roomId5));

        exec.shutdown();

    }

    // 定义一个简单的任务
    private static Runnable createTask(final String roomId) {
        return new Runnable() {
            private Socket socket = null;
            //获取弹幕线程及心跳线程运行和停止标记
            private boolean readyFlag = false;

            public void run() {
                System.out.println("roomId: " + roomId);
                try {
                    String host = InetAddress.getByName(hostName).getHostAddress();
                    socket = new Socket(host, port);

                    BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
                    BufferedInputStream bis= new BufferedInputStream(socket.getInputStream());

                    loginRoom(roomId, bos, bis);

                    joinGroup(roomId, groupId, bos);

                    readyFlag = true;

                    //保持弹幕服务器心跳
                    KeepAlive1 keepAlive1 = new KeepAlive1(bos,readyFlag);
                    keepAlive1.start();


//                    while (readyFlag){
//                        getServerMsg(bis);
//                    }

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
//        logger.info("登陆房间请求数据包>>>"+new String(loginRequestData));

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


    /**
     * 获取服务器返回信息
     */
    public static void getServerMsg(BufferedInputStream bis){
        //初始化获取弹幕服务器返回信息包大小
        byte[] recvByte = new byte[MAX_BUFFER_LENGTH];
        //定义服务器返回信息的字符串
        String dataStr;
        try {
            //读取服务器返回信息，并获取返回信息的整体字节长度
            int recvLen = bis.read(recvByte, 0, recvByte.length);

            //根据实际获取的字节数初始化返回信息内容长度
            byte[] realBuf = new byte[recvLen];
            //按照实际获取的字节长度读取返回信息
            System.arraycopy(recvByte, 0, realBuf, 0, recvLen);
            //根据TCP协议获取返回信息中的字符串信息
            dataStr = new String(realBuf, 12, realBuf.length - 12);

            //循环处理socekt黏包情况
            while(dataStr.lastIndexOf("type@=") > 5){
                //对黏包中最后一个数据包进行解析
                MsgView msgView = new MsgView(StringUtils.substring(dataStr, dataStr.lastIndexOf("type@=")));
                //分析该包的数据类型，以及根据需要进行业务操作
                parseServerMsg(msgView.getMessageList());
                //处理黏包中的剩余部分
                dataStr = StringUtils.substring(dataStr, 0, dataStr.lastIndexOf("type@=") - 12);
            }
            //对单一数据包进行解析
            MsgView msgView = new MsgView(StringUtils.substring(dataStr, dataStr.lastIndexOf("type@=")));
            //分析该包的数据类型，以及根据需要进行业务操作
            parseServerMsg(msgView.getMessageList());

//			//批量插入数据库，设置每100条，插入到数据库
//			if (count > danmuSize){
//				count = 0;
//				batchInsertDanmu(danmuParams);
//				danmuParams.clear();
//			}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 解析从服务器接受的协议，并根据需要订制业务需求
     * @param msg
     */
    private static void parseServerMsg(Map<String, Object> msg){
        if(msg.get("type") != null){
            //服务器反馈错误信息
            if(msg.get("type").equals("error")){
                logger.debug(msg.toString());
                //结束心跳和获取弹幕线程
//                this.readyFlag = false;
            }
            /***@TODO 根据业务需求来处理获取到的所有弹幕及礼物信息***********/
            //判断消息类型
            if(msg.get("type").equals("chatmsg")){//弹幕消息
//                logger.info("弹幕消息===>" + msg.toString());

                logger.info("弹幕=> 房间号：" + msg.get("rid") + " 用户名：" + msg.get("nn") + " 消息：" + msg.get("txt") + "...." + msg.get("col") + "...." + msg.get("ct") + "......" + msg.get("level"));

//				if (msg.get("rid") != null) rid = (String)msg.get("rid");
//				if (msg.get("uid") != null) rid = (String)msg.get("uid");
//				if (msg.get("nn") != null) rid = (String)msg.get("nn");
//				if (msg.get("txt") != null) rid = (String)msg.get("txt");
//				if (msg.get("level") != null) rid = (String)msg.get("level");
//				if (msg.get("col") != null) rid = (String)msg.get("col");
//				if (msg.get("ct") != null) rid = (String)msg.get("ct");
//
//				//弹幕计数器
//				count++;
//				Object[] param = {rid,uid,nn,txt,level,col,ct};
//				danmuParams.add(param);

            } else if(msg.get("type").equals("dgb")){//赠送礼物信息
//                logger.info("礼物消息===>" + msg.toString());
            } else {
//                logger.info("其他消息===>" + msg.toString());
            }

            //@TODO 其他业务信息根据需要进行添加

        }
        else {
//            logger.error("返回弹幕消息错误！！！！！返回消息为空。" + msg.toString());
        }
    }

}
