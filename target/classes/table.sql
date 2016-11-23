DROP TABLE if EXISTS chatmsg;
CREATE TABLE `chatmsg` (
  `id` int NOT NULL AUTO_INCREMENT,
  `rid` char(10),
  `uid` char(12),
  `nn` char(40),
  `txt` char(20),
  `level` int,
  `col` char(2),
  `ct` char(2),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ;

INSERT INTO chatmsg(rid,uid,nn,txt,level,col,ct) VALUES ('229346','53445488','眼睛有点涩丶','还没','10','0','0');
INSERT INTO chatmsg(rid,uid,nn,txt,level,col,ct) VALUES ('229346','620056','不散和弦','下午会播吧','1','0','0');


DROP TABLE if EXISTS room;
CREATE TABLE `room` (
  `id` int NOT NULL AUTO_INCREMENT,
  `room_id` char(10),
  `room_thumb` char(40),
  `cate_id` char(3),
  `cate_name` char(10),
  `room_name` char(40),
  `room_status` char(1),
  `owner_name` char(40),
  `avatar` char(40),
  `online` int,
  `owner_weight` char(10),
  `fans_num` int,
  `start_time` datetime,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ;

INSERT INTO room(room_id,owner_name) VALUES ('229346','高冷男神钱小佳');
INSERT INTO room(room_id,owner_name) VALUES ('241123','火力堂主');
INSERT INTO room(room_id,owner_name) VALUES ('532152','YaphetS丶666');
INSERT INTO room(room_id,owner_name) VALUES ('32892','只有十五岁的涛妹');
INSERT INTO room(room_id,owner_name) VALUES ('58718','ChuaN黄福全');
INSERT INTO room(room_id,owner_name) VALUES ('320155','主播阿郎');
INSERT INTO room(room_id,owner_name) VALUES ('97376','天使焦520');
INSERT INTO room(room_id,owner_name) VALUES ('432033','贾贾贾政井');
INSERT INTO room(room_id,owner_name) VALUES ('60937','zard1991');
INSERT INTO room(room_id,owner_name) VALUES ('238611','B总001');
INSERT INTO room(room_id,owner_name) VALUES ('2073','傲天皇妃sama');










