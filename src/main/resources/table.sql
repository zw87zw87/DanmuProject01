CREATE TABLE `chatmsg` (
  `id` int NOT NULL AUTO_INCREMENT,
  `rid` char(20),
  `uid` char(12),
  `nn` varchar(60),
  `txt` varchar(60),
  `level` int,
  `col` char(2),
  `ct` char(2),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ;


INSERT INTO chatmsg(rid,uid,nn,txt,level,col,ct) VALUES ('229346','53445488','眼睛有点涩丶','还没','10','0','0');
INSERT INTO chatmsg(rid,uid,nn,txt,level,col,ct) VALUES ('229346','620056','不散和弦','下午会播吧','1','0','0');













