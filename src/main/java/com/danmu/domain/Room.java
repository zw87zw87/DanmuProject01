package com.danmu.domain;

import com.google.common.base.Objects;

import java.util.Date;
import java.util.List;

public class Room {
    String room_id;
    String room_thumb;
    String cate_id;
    String cate_name;
    String room_name;
    String room_status;
    String owner_name;
    String avatar;
    String online;
    String owner_weight;
    String fans_num;
    String start_time;
    List<Gift> gift;

    public static class Gift{
        String id;
        String name;
        String type;
        String pc;
        String gx;
        String desc;
        String intro;
        String mimg;
        String himg;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPc() {
            return pc;
        }

        public void setPc(String pc) {
            this.pc = pc;
        }

        public String getGx() {
            return gx;
        }

        public void setGx(String gx) {
            this.gx = gx;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getMimg() {
            return mimg;
        }

        public void setMimg(String mimg) {
            this.mimg = mimg;
        }

        public String getHimg() {
            return himg;
        }

        public void setHimg(String himg) {
            this.himg = himg;
        }

        @Override
        public String toString() {
            return "Gift{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", type='" + type + '\'' +
                    ", pc='" + pc + '\'' +
                    ", gx='" + gx + '\'' +
                    ", desc='" + desc + '\'' +
                    ", intro='" + intro + '\'' +
                    ", mimg='" + mimg + '\'' +
                    ", himg='" + himg + '\'' +
                    '}';
        }
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }

    public String getRoom_thumb() {
        return room_thumb;
    }

    public void setRoom_thumb(String room_thumb) {
        this.room_thumb = room_thumb;
    }

    public String getCate_id() {
        return cate_id;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getRoom_status() {
        return room_status;
    }

    public void setRoom_status(String room_status) {
        this.room_status = room_status;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getOwner_weight() {
        return owner_weight;
    }

    public void setOwner_weight(String owner_weight) {
        this.owner_weight = owner_weight;
    }

    public String getFans_num() {
        return fans_num;
    }

    public void setFans_num(String fans_num) {
        this.fans_num = fans_num;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public List<Gift> getGift() {
        return gift;
    }

    public void setGift(List<Gift> gift) {
        this.gift = gift;
    }

    @Override
    public String toString() {
        return "Room{" +
                "room_id='" + room_id + '\'' +
                ", room_thumb='" + room_thumb + '\'' +
                ", cate_id='" + cate_id + '\'' +
                ", cate_name='" + cate_name + '\'' +
                ", room_name='" + room_name + '\'' +
                ", room_status='" + room_status + '\'' +
                ", owner_name='" + owner_name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", online='" + online + '\'' +
                ", owner_weight='" + owner_weight + '\'' +
                ", fans_num='" + fans_num + '\'' +
                ", start_time='" + start_time + '\'' +
                ", gift=" + gift +
                '}';
    }
}
