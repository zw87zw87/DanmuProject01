package com.danmu.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danmu.msg.DyMessage;
import com.danmu.msg.MsgView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @Summary: 弹幕客户端类
 * @author: FerroD
 * @date:   2016-3-12
 * @version V1.0
 */
public class DyBulletScreenClient1 implements Runnable
{
    private final Logger logger = LoggerFactory.getLogger(DyBulletScreenClient.class);
    private static DyBulletScreenClient instance;

    private static List<Object[]> danmuParams = Lists.newArrayList();
    private static int danmuSize = 10;
    private static int count = 1;

    private static String rid = "";
    private static String uid = "";
    private static String nn = "";
    private static String txt = "";
    private static String level = "";
    private static String col = "";
    private static String ct = "";


    @Autowired
    private JdbcTemplate jdbcTemplate;

    //第三方弹幕协议服务器地址
    private static final String hostName = "openbarrage.douyutv.com";

    //第三方弹幕协议服务器端口
    private static final int port = 8601;

    //设置字节获取buffer的最大值
    private static final int MAX_BUFFER_LENGTH = 4096;

    //socket相关配置
    private Socket sock;
    private BufferedOutputStream bos;
    private BufferedInputStream bis;

    //获取弹幕线程及心跳线程运行和停止标记
    private boolean readyFlag = false;

//    private DyBulletScreenClient1(){}

    /**
     * 单例获取方法，客户端单例模式访问
     * @return
     */
//    public static DyBulletScreenClient1 getInstance(){
//        if(null == instance){
//            instance = new DyBulletScreenClient1();
//        }
//        return instance;
//    }

    /**
     * 客户端初始化，连接弹幕服务器并登陆房间及弹幕池
     * @param roomId 房间ID
     * @param groupId 弹幕池分组ID
     */
    public void init(String roomId, int groupId){
        //连接弹幕服务器
        this.connectServer();
        //登陆指定房间
        this.loginRoom(roomId);
        //加入指定的弹幕池
        this.joinGroup(roomId, groupId);
        //设置客户端就绪标记为就绪状态
        readyFlag = true;
    }

    /**
     * 获取弹幕客户端就绪标记
     * @return
     */
    public boolean getReadyFlag(){
        return readyFlag;
    }

    /**
     * 连接弹幕服务器
     */
    private void connectServer()
    {
        logger.debug("尝试连接弹幕服务器...");
        try
        {
            //获取弹幕服务器访问host
            String host = InetAddress.getByName(hostName).getHostAddress();
            logger.info("host---->>>"+host);
            //建立socke连接
            sock = new Socket(host, port);
            //设置socket输入及输出
            bos = new BufferedOutputStream(sock.getOutputStream());
            bis= new BufferedInputStream(sock.getInputStream());
        } catch(Exception e)
        {
            e.printStackTrace();
        }

        logger.debug("连接弹幕服务器成功...");
    }

    /**
     * 登录指定房间
     * @param roomId
     */
    private void loginRoom(String roomId)
    {
        //获取弹幕服务器登陆请求数据包
        byte[] loginRequestData = DyMessage.getLoginRequestData(roomId);
        logger.info("登陆房间请求数据包>>>"+loginRequestData.toString());

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
    private void joinGroup(String roomId, int groupId)
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
     * 服务器心跳连接
     */
    public void keepAlive()
    {
        //获取与弹幕服务器保持心跳的请求数据包
        byte[] keepAliveRequest = DyMessage.getKeepAliveData((int)(System.currentTimeMillis() / 1000));

        try{
            //向弹幕服务器发送心跳请求数据包
            bos.write(keepAliveRequest, 0, keepAliveRequest.length);
            bos.flush();
            logger.debug("服务器心跳连接成功   Send keep alive request successfully!");

        } catch(Exception e){
            e.printStackTrace();
            logger.error("服务器心跳连接失败   Send keep alive request failed!");
        }
    }

    /**
     * 获取服务器返回信息
     */
    public void getServerMsg(){
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
    private void parseServerMsg(Map<String, Object> msg){
        if(msg.get("type") != null){
            //服务器反馈错误信息
            if(msg.get("type").equals("error")){
                logger.debug(msg.toString());
                //结束心跳和获取弹幕线程
                this.readyFlag = false;
            }
            /***@TODO 根据业务需求来处理获取到的所有弹幕及礼物信息***********/
            //判断消息类型
            if(msg.get("type").equals("chatmsg")){//弹幕消息
                logger.info("弹幕消息===>" + msg.toString());

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
                logger.info("礼物消息===>" + msg.toString());
            } else {
                logger.info("其他消息===>" + msg.toString());
            }

            //@TODO 其他业务信息根据需要进行添加

        }
        else {
            logger.error("返回弹幕消息错误！！！！！返回消息为空。" + msg.toString());
        }
    }

    private void batchInsertDanmu(List<Object[]> params) {
//		if (params != null && params.size() > 0) {
//			String sql = "insert into chatmsg(rid,uid,nn,txt,level,col,ct) values(?,?,?,?,?,?,?)";
//			jdbcTemplate.batchUpdate(sql, params);
//		}
        String sql = "INSERT INTO chatmsg(rid,uid,nn,txt,level,col,ct) VALUES ('229346','53445488','眼睛有点涩丶','还没','10','0','0');";
        jdbcTemplate.execute(sql);
    }



    @Override
    public void run() {

        //判断客户端就绪状态
        while(getReadyFlag())
        {
            //发送心跳保持协议给服务器端
            keepAlive();
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
}
