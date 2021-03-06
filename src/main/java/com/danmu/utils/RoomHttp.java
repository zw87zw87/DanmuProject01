package com.danmu.utils;

import com.danmu.domain.Room;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class RoomHttp implements Callable<List<Room>>{
    private static List<String> list;

    public RoomHttp(List<String> list) {
        this.list = list;
    }

    @Override
    public List<Room> call() throws Exception {

        List<Room> result = Lists.newArrayList();

        for (String roomId : list){
            result.add(roomGet(roomId));
        }
        return result;
    }

    public Room roomGet(String roomId){
        Logger logger = LoggerFactory.getLogger(RoomHttp.class);

        logger.info("请求房间信息 roomId: " + roomId);

        Room room = null;
        String line;
        String roomHttp = "http://open.douyucdn.cn/api/RoomApi/room/" + roomId;

        try {
            URL url = new URL(roomHttp);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            line = br.readLine();

            Gson gson = new Gson();
            Map<String,Object> map = gson.fromJson(line,new TypeToken<Map<String,Object>>(){}.getType());
            String status = map.get("error") + "";

            if (status.equals("0.0")) {
                String data = line.substring(line.indexOf("data") + 6, line.length() - 1);
                room = gson.fromJson(data, Room.class);
                logger.info("房间信息：" + room);
            }else {
                logger.error("房间未找到");
            }

            br.close();

        } catch (Exception e) {
            logger.error("请求房间信息错误：" + roomHttp + " " + e.getMessage());
            e.printStackTrace();
        }

        return room;
    }

}
