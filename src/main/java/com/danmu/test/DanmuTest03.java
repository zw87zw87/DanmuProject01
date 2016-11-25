package com.danmu.test;

import com.danmu.client.DyBulletScreenClient2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DanmuTest03 {
    public static void main(String[] args) {

        String roomId1 = "229346";
        String roomId2 = "532152";
        String roomId3 = "244548";
        String roomId4 = "56040";
        String roomId5 = "453751";

        DyBulletScreenClient2 dy = new DyBulletScreenClient2(roomId1);

        ExecutorService exec = Executors.newCachedThreadPool();

//        for (int i = 0; i < numTasks; i++) {
//            exec.execute(createTask(i , "roomId" + i));
//        }

        exec.execute(dy);
//        exec.execute(createTask(roomId2));
//        exec.execute(createTask(roomId3));
//        exec.execute(createTask(roomId4));
//        exec.execute(createTask(roomId5));

        exec.shutdown();
    }
}
