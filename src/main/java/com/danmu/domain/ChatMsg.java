package com.danmu.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "chatmsg")
public class ChatMsg implements Serializable{
    private String rid;         //房间id
    private String uid;         //发送者uid
    private String nn;          //发送者昵称
    private String txt;         //弹幕文本内容
    private int level;         //用户等级
    private String col;         //颜色弹幕
    private String ct;          //客户端类型


    @Override
    public String toString() {
        return "ChatMsg{" +
                "rid='" + rid + '\'' +
                ", uid='" + uid + '\'' +
                ", nn='" + nn + '\'' +
                ", txt='" + txt + '\'' +
                ", level=" + level +
                ", col='" + col + '\'' +
                ", ct='" + ct + '\'' +
                '}';
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNn() {
        return nn;
    }

    public void setNn(String nn) {
        this.nn = nn;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }
}
