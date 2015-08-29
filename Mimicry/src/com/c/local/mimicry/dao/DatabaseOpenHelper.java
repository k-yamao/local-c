/*
 * Licensed under the Apache License, Version 2.0 (the "License").
 */
package com.c.local.mimicry.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Databaseアクセスのヘルパークラス。
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
	/** データベースファイル名 */
	private static final String DATABASE_NAME = "data";
	/** データベースのバージョン */
	private static final int DATABASE_VERSION = 3;
	/** RSSフィードテーブル作成SQL */
	private static final String CREATE_MIMICRY_TABLE = "CREATE TABLE MIMICRY("
			+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "TITLE TEXT,"
			+ "OLD_STANDBY TEXT,"
			+ "MIMICRY_POINT TEXT,"
			+ "IMG_PATH TEXT,"
			+ "CHALLENGE_LEVEL TEXT,"
			+ "RATING TEXT,"
			+ "PC_URL TEXT, MIMICRYINT INTEGER, MIMICRYTEXT1 TEXT,MIMICRYTEXT2 TEXT)";

	/** MY MIMICRYテーブル作成SQL */
	private static final String CREATE_MY_MIMICRY_TABLE = "CREATE TABLE MY_MIMICRY("
			+ "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ "MIMICRY_ID INTEGER,"
			+ "TITLE TEXT,"
			+ "SAVE_DATE TEXT,"
			+ "IMG_PATH TEXT,"
			+ "FILE_URL TEXT," + "CHARACTER TEXT," + "FILE_NAME TEXT);";

	/** 設定テーブル作成SQL */
	private static final String CREATE_SETTING_TABLE = "CREATE TABLE SETTING("
			+ "_id INTEGER PRIMARY KEY AUTOINCREMENT," + "NICNAME TEXT);";

	/** MIMICRYテーブル削除SQL */
	private static final String DROP_MIMICRY_TABLE = "DROP TABLE IF EXISTS MIMICRY;";

	/** MY_MIMICRYテーブル削除SQL */
	private static final String DROP_MYMIMICRY_TABLE = "DROP TABLE IF EXISTS MY_MIMICRY;";

	/** 設定テーブル削除SQL */
	private static final String DROP_SETTING_TABLE = "DROP TABLE IF EXISTS SETTING;";

	private static final String INSERT_MIMICRY_TABLE0 = "INSERT INTO MIMICRY ("
			+ "_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, "
			+ "PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES "
			+ "(0, "
			+ "'オリジナルものまね', "
			+ "'', "
			+ "'', "
			+ "'http://farm8.static.flickr.com/7240/7319040888_4b4fc9303a_z.jpg', "
			+ "'？？？', " + "'？？？', " + "'http://www.mimicry.mobi/lib001', "
			+ "0, " + "''," + "'');";

	private static final String INSERT_MIMICRY_TABLE1 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (1,'ビートたけし','「ダンカンばかやろこのやろ」','しぐさもいれて','http://farm8.staticflickr.com/7140/7471455252_12cfdd4338_m.jpg','★★','★★★★','http://www.mimicry.mobi/lib001',0,'','');";
	private static final String INSERT_MIMICRY_TABLE2 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (2,'明石家さんま','「ほんまや」、「醤油こと」','友達に振ってもらって','http://farm8.staticflickr.com/7250/7471455174_2c81fa2693_m.jpg','★★','★★★★','http://www.mimicry.mobi/lib002',0,'','');";
	private static final String INSERT_MIMICRY_TABLE3 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (3,'長渕剛','「足元見失うんじゃねーぞ。花の大東京でよう。」、「Say」','YouTubeで検索して探して','http://farm9.staticflickr.com/8026/7471455316_3f456319b7_m.jpg','★★★★','★★★★','http://www.mimicry.mobi/lib003',0,'','');";
	private static final String INSERT_MIMICRY_TABLE4 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (4,'福山雅治','「実におもしろい」、「小雪はあんちゃんのことが好きなんだよ 」','武田鉄矢ぽい出だしで口はあまり動かさないので','http://farm8.staticflickr.com/7106/7471456364_dab2415a3b_m.jpg','★★★','★★★★★','http://www.mimicry.mobi/lib004',0,'','');";
	private static final String INSERT_MIMICRY_TABLE5 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (5,'高田延彦','「男の中の男」、「出てこいやー」、「サク、ヴァンダレイ」','くりーむの有田風でもおもしろい','http://farm9.staticflickr.com/8142/7471455386_f310a166f4_m.jpg','★★','★★★','http://www.mimicry.mobi/lib005',0,'','');";
	private static final String INSERT_MIMICRY_TABLE6 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (6,'織田裕二','「おい、何やってんだよ、為ー」、「レインボーブリッジ封鎖できません」','大げさにやってみて','http://farm9.staticflickr.com/8153/7471456456_e255211a46_m.jpg','★★★','★★★★','http://www.mimicry.mobi/lib006',0,'','');";
	private static final String INSERT_MIMICRY_TABLE7 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (7,'You','「あの～・・・、(名前)はさ、（単語）好き？」「～みたない」「だいたいあの・・・～とか、」「みんな死んじゃえばいいのに」','鼻で息をしない、けだるい感じで喋る！！','http://farm8.staticflickr.com/7257/7471456520_138c525e23_m.jpg','★★★','★★★★','http://www.mimicry.mobi/lib007',0,'','');";
	private static final String INSERT_MIMICRY_TABLE8 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (8,'菅野美穂','「あぁっ」「あれっ違う」「～だし、～だし、～なんです。」「あはははははー」','ガンモみたいなしゃべり方がいい','http://farm9.staticflickr.com/8141/7471455432_e20735a224_m.jpg','★★★★★','★★★★','http://www.mimicry.mobi/lib008',0,'','');";
	private static final String INSERT_MIMICRY_TABLE9 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (9,'さかなクン','「ぎょぎょっ」「～ぎょざいます。」','体を振動させながら','http://farm8.staticflickr.com/7270/7471456586_6cf56c6712_m.jpg','★★★','★★','http://www.mimicry.mobi/lib009',0,'','');";
	private static final String INSERT_MIMICRY_TABLE10 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (10,'哀川翔','「動けねぇ…、俺が動くと場面が変わる…」、「朝でまた朝ダブル太陽よ！」、「うそつけよ」','','http://farm9.staticflickr.com/8164/7471456640_a4eb16b138_m.jpg','★★','★★★★','http://www.mimicry.mobi/lib010',0,'','');";
	private static final String INSERT_MIMICRY_TABLE11 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (11,'加藤鷹','「うわっすごいこれ、なにこれ？、みてこれ」「あぁっすごい」「あーふぉら出てきた」「みてみてほら」「あーはいった」「うっーーどうなのイク？」','息使いを意識して','http://farm9.staticflickr.com/8009/7471456714_c35746ab6a_m.jpg','★','★★★★','http://www.mimicry.mobi/lib011',0,'','');";
	private static final String INSERT_MIMICRY_TABLE12 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (12,'田中邦衛','「ホタルゥ・・・」 「ほたるーいつでも富良野に帰ってくんだぞー」','「口を意識して」','http://farm8.staticflickr.com/7257/7471456780_b510db1f60_m.jpg','★★','★★★','http://www.mimicry.mobi/lib012',0,'','');";
	private static final String INSERT_MIMICRY_TABLE13 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (13,'ボビーオロゴン','「しらねーよ」、「ふざけんなよー」','鼻声、片言で','http://farm9.staticflickr.com/8013/7471456844_24976d0f79_m.jpg','★','★★','http://www.mimicry.mobi/lib013',0,'','');";
	private static final String INSERT_MIMICRY_TABLE14 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (14,'平泉成','「なんかい言ってもだめなものはだめだよ」「ねー母さん」「ご冗談」','しゃがれた声で','http://farm9.staticflickr.com/8153/7471455118_61b1ffd56d_m.jpg','★★','★★★','http://www.mimicry.mobi/lib014',0,'','');";
	private static final String INSERT_MIMICRY_TABLE15 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (15,'吉高由里子','「ハイボール飲んでうぃー」','勢いでごまかそう','http://farm9.staticflickr.com/8012/7471455486_d4a8424497_m.jpg','★★★★','★★★★★','http://www.mimicry.mobi/lib015',0,'','');";
	private static final String INSERT_MIMICRY_TABLE16 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (16,'ローラ','「やっほー、ローラだよ。」、「すーごく面白い話聞いたの」、「えーとなんだっけなー」、「いい感じ」、「へ～、そうなんだ～」、「OK」','「OK」のときはしぐさもいれて','http://farm9.staticflickr.com/8009/7471456926_11364f7fb6_m.jpg','★★','★★★★','http://www.mimicry.mobi/lib016',0,'','');";
	private static final String INSERT_MIMICRY_TABLE17 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (17,'田中真紀子','「田中真紀子でございます。」','鼻で息をせず、喉あけて','http://farm9.staticflickr.com/8028/7471455546_233efc3f9d_m.jpg','★★','★','http://www.mimicry.mobi/lib017',0,'','');";
	private static final String INSERT_MIMICRY_TABLE18 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (18,'チョコボーイ 山口','「すっつごいねっおばあちゃん」、「ほらほら入ちゃったー、2本入ちゃったっ」','YouTubeで検索','http://farm8.staticflickr.com/7275/7471455592_04fd3ebc66_m.jpg','★','★★★★★','http://www.mimicry.mobi/lib018',0,'','');";
	private static final String INSERT_MIMICRY_TABLE19 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (19,'クレヨンしんちゃん','「オラしんのすけだぞ～」','女性に人気、男がやるとボビーになるかも','http://farm8.staticflickr.com/7272/7471455044_7b21d31f90_m.jpg','★','★★','http://www.mimicry.mobi/lib019',0,'','');";
	private static final String INSERT_MIMICRY_TABLE20 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (20,'吉川晃司','「Be My baby、Be My baby」、「モニカ」、「～ちゃっ」','あぶないWコージで検索','http://farm8.staticflickr.com/7254/7471455688_7d398b3270_m.jpg','★★★','★★','http://www.mimicry.mobi/lib020',0,'','');";
	private static final String INSERT_MIMICRY_TABLE21 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (21,'達川光男','「あのね、達川光男ですよ。」「あのね、あのね、あのねのね。」','あのね。を連発','http://farm9.staticflickr.com/8167/7471456996_35c67ffe02_m.jpg','★★','★','http://www.mimicry.mobi/lib021',0,'','');";
	private static final String INSERT_MIMICRY_TABLE22 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (22,'持田香織','「アホになる気はあるのかお前らー！」','酔っ払った感じで','http://farm8.staticflickr.com/7117/7471457052_a14fb6b4ba_m.jpg','★★★★','★★★','http://www.mimicry.mobi/lib022',0,'','');";
	private static final String INSERT_MIMICRY_TABLE23 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (23,'坂東英二','「なんちゅーのかな」、「たまらんは、ほんまにもう」「ほんまに僕は野球より株でね。もうけなあかん」','鼻で息しない','http://farm9.staticflickr.com/8015/7471457104_5bf7bfe699_m.jpg','★★','★★★★','http://www.mimicry.mobi/lib023',0,'','');";
	private static final String INSERT_MIMICRY_TABLE24 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (24,'中尾彬','「中尾だよ」、「これでいいのか？」、「なかなかいいんだよ。」','低い声で、あごに手を置いて','http://farm9.staticflickr.com/8142/7471457170_6412f6b653_m.jpg','★★','★★★','http://www.mimicry.mobi/lib024',0,'','');";
	private static final String INSERT_MIMICRY_TABLE25 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (25,'川平慈英','「ハーフナーマイクゥーーー」、「ヤナギサワー」、「ハレルヤ」、「マンチェスター・ユナイテッド」','最後に「ねー松木さん」といってもいい','http://farm9.staticflickr.com/8160/7471455774_92b1e2c0ba_m.jpg','★★★','★★★★','http://www.mimicry.mobi/lib025',0,'','');";
	private static final String INSERT_MIMICRY_TABLE26 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (26,'トーカ堂北社長','「ピアスとネックレスをセットにして・・・消費税込み・・・198000円っ」','もうしわけなさそうに','http://farm9.staticflickr.com/8156/7471457228_ebf15c1b3a_m.jpg','★★','★★','http://www.mimicry.mobi/lib026',0,'','');";
	private static final String INSERT_MIMICRY_TABLE27 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (27,'千葉真一','「ハーーーーーーー」','息を吐くだけ','http://farm8.staticflickr.com/7123/7471457288_739b8062a8_m.jpg','★★★','★★','http://www.mimicry.mobi/lib027',0,'','');";
	private static final String INSERT_MIMICRY_TABLE28 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (28,'武藤敬司','「いーーーやっ」（ポーズ）','膝に爆弾を抱えている感じをだして','http://farm9.staticflickr.com/8011/7471457352_407c18545f_m.jpg','★★','★★','http://www.mimicry.mobi/lib028',0,'','');";
	private static final String INSERT_MIMICRY_TABLE29 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (29,'蝶野正洋','「I am 蝶野」「NWO」','大きな声、ガラガラ声','http://farm9.staticflickr.com/8008/7471455816_63677b0b2a_m.jpg','★★★','★★','http://www.mimicry.mobi/lib029',0,'','');";
	private static final String INSERT_MIMICRY_TABLE30 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (30,'ゆず','「厚ちゃん」、「悠人」、「帰ってきたぞ横浜ー」','夏色を聴いてね。','http://farm8.staticflickr.com/7269/7471457418_47dd883223_m.jpg','★★★','★★★','http://www.mimicry.mobi/lib030',0,'','');";
	private static final String INSERT_MIMICRY_TABLE31 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (31,'武田鉄矢','「僕はしにましぇぇ－ん 。」「ヒーー」','出だしに気をつけて','http://farm8.staticflickr.com/7250/7471455888_78397fb1b2_m.jpg','★★','★★★','http://www.mimicry.mobi/lib031',0,'','');";
	private static final String INSERT_MIMICRY_TABLE32 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (32,'渡部篤郎','「なめてんの、、、あーなめてんだ」「こいよっ、こいよっ」','首を振って、ふらふらしながら','http://farm8.staticflickr.com/7118/7471457504_6cd7bb9bfe_m.jpg','★★★','★★★★','http://www.mimicry.mobi/lib032',0,'','');";
	private static final String INSERT_MIMICRY_TABLE33 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (33,'長州力','「てめぇ、タコ、コラ！」、「きれてないですよ」','舌の使い方に気をつけて','http://farm9.staticflickr.com/8016/7471455960_d2c753a0b9_m.jpg','★★','★★★','http://www.mimicry.mobi/lib033',0,'','');";
	private static final String INSERT_MIMICRY_TABLE34 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (34,'渡哲也','「マグロ！2夜連続！！」','声を低く、勢いをもって','http://farm9.staticflickr.com/8017/7471457558_8997e817e8_m.jpg','★★★','★★★','http://www.mimicry.mobi/lib034',0,'','');";
	private static final String INSERT_MIMICRY_TABLE35 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (35,'タモリ','「いったんCMで～す。」「なわきゃない」','無表情でやってみて','http://farm8.staticflickr.com/7271/7471457600_d86eaa73cf_m.jpg','★★★','★★★★','http://www.mimicry.mobi/lib035',0,'','');";
	private static final String INSERT_MIMICRY_TABLE36 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (36,'渡辺篤史','「これ玄関ですかー」「この壁の漆喰の感じが・・・湿気を調整する素材ですよね。」','吐息が重要','http://farm8.staticflickr.com/7134/7471457686_3d13c27e5d_m.jpg','★★★','★','http://www.mimicry.mobi/lib036',0,'','');";
	private static final String INSERT_MIMICRY_TABLE37 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (37,'田中秀和','「グレート武多見参」、「日本で、はじめてプロレスが行われて51年、ついに、夢が叶う日、2002年5月2日、その時が来た、三沢光晴入場」','読点に気をつけて','http://farm9.staticflickr.com/8008/7471457754_d9fb947a87_m.jpg','★★','★','http://www.mimicry.mobi/lib037',0,'','');";
	private static final String INSERT_MIMICRY_TABLE38 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (38,'えなりかずき','「そんなこと言ったってしょうがないじゃないか」','なし','http://farm9.staticflickr.com/8154/7471457800_e4d2142251_m.jpg','★★','★★★★','http://www.mimicry.mobi/lib038',0,'','');";
	private static final String INSERT_MIMICRY_TABLE39 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (39,'柳沢慎吾','「あーーーあーーー」、「あばよ」','タバコを使って警察無線にもトライして','http://farm9.staticflickr.com/8168/7471456010_5afdd6b33f_m.jpg','★★★','★★★★','http://www.mimicry.mobi/lib039',0,'','');";
	private static final String INSERT_MIMICRY_TABLE40 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (40,'目玉おやじ','「おい鬼太郎！」','高い声で','http://farm8.staticflickr.com/7250/7471456064_781f6cfea5_m.jpg','★','★★★','http://www.mimicry.mobi/lib040',0,'','');";
	private static final String INSERT_MIMICRY_TABLE41 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (41,'鬼太郎','「父さん！」','調査中','http://farm9.staticflickr.com/8021/7471456210_3340172c0f_m.jpg','★','★★★','http://www.mimicry.mobi/lib041',0,'','');";
	private static final String INSERT_MIMICRY_TABLE42 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (42,'貴乃花親方','「私もですね。あの・・・いろいろ、あの・・・花田勝氏がですね。」「いかがなものかという」','顔の表情が重要','http://farm9.staticflickr.com/8146/7471456150_fbf95dbc34_m.jpg','★★★','★★★','http://www.mimicry.mobi/lib042',0,'','');";
	private static final String INSERT_MIMICRY_TABLE43 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (43,'西田敏行','「まあほんとね、これね」「ヒヒヒヒヒヒー」','笑い声が大切','http://farm8.staticflickr.com/7262/7471456272_50a00cd06c_m.jpg','★★★★','★★★','http://www.mimicry.mobi/lib043',0,'','');";
	private static final String INSERT_MIMICRY_TABLE44 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (44,'野村克也','「今日はもうさんざんな試合だったよ」、「カツノリがんばれや！」','あごを少し出すかんじで','http://farm9.staticflickr.com/8015/7471457860_109e4afc75_m.jpg','★★★','★★★','http://www.mimicry.mobi/lib044',0,'','');";
	private static final String INSERT_MIMICRY_TABLE45 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (45,'孫悟空','「おっすおら悟空、いっちょやってみっか」','なし','http://farm8.staticflickr.com/7124/7471457950_a5ab5a3964_m.jpg','★★','★★★★','http://www.mimicry.mobi/lib045',0,'','');";
	private static final String INSERT_MIMICRY_TABLE46 = "INSERT INTO MIMICRY (_id, TITLE, OLD_STANDBY, MIMICRY_POINT, IMG_PATH, CHALLENGE_LEVEL, RATING, PC_URL, MIMICRYINT, MIMICRYTEXT1, MIMICRYTEXT2) VALUES (46,'ティーバック','「可愛こちゃ～ん」、「ユゥ～タァ～」','これができるとアナゴさんとセルもできます。','http://farm9.staticflickr.com/8146/7519226768_2ee76be9a4_m.jpg','★★★','★★★★','http://www.mimicry.mobi/lib046',0,'','');";

	/**
	 * @param context
	 */
	public DatabaseOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(CREATE_MIMICRY_TABLE);
		database.execSQL(CREATE_MY_MIMICRY_TABLE);
		database.execSQL(CREATE_SETTING_TABLE);
		onInsert(database);
		Log.v("DatabaseOpenHelper", "Succeeded in create the tables.");
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		if (oldVersion == 1) {

			database.execSQL(DROP_MIMICRY_TABLE);
			database.execSQL(CREATE_MIMICRY_TABLE);
			onInsert(database);
			Log.v("DatabaseOpenHelper", "Succeeded in update the tables.");
		}
	}

	public void onInsert(SQLiteDatabase database) {
		database.execSQL(INSERT_MIMICRY_TABLE0);
		database.execSQL(INSERT_MIMICRY_TABLE1);
		database.execSQL(INSERT_MIMICRY_TABLE2);
		database.execSQL(INSERT_MIMICRY_TABLE3);
		database.execSQL(INSERT_MIMICRY_TABLE4);
		database.execSQL(INSERT_MIMICRY_TABLE5);
		database.execSQL(INSERT_MIMICRY_TABLE6);
		database.execSQL(INSERT_MIMICRY_TABLE7);
		database.execSQL(INSERT_MIMICRY_TABLE8);
		database.execSQL(INSERT_MIMICRY_TABLE9);
		database.execSQL(INSERT_MIMICRY_TABLE10);
		database.execSQL(INSERT_MIMICRY_TABLE11);
		database.execSQL(INSERT_MIMICRY_TABLE12);
		database.execSQL(INSERT_MIMICRY_TABLE13);
		database.execSQL(INSERT_MIMICRY_TABLE14);
		database.execSQL(INSERT_MIMICRY_TABLE15);
		database.execSQL(INSERT_MIMICRY_TABLE16);
		database.execSQL(INSERT_MIMICRY_TABLE17);
		database.execSQL(INSERT_MIMICRY_TABLE18);
		database.execSQL(INSERT_MIMICRY_TABLE19);
		database.execSQL(INSERT_MIMICRY_TABLE20);
		database.execSQL(INSERT_MIMICRY_TABLE21);
		database.execSQL(INSERT_MIMICRY_TABLE22);
		database.execSQL(INSERT_MIMICRY_TABLE23);
		database.execSQL(INSERT_MIMICRY_TABLE24);
		database.execSQL(INSERT_MIMICRY_TABLE25);
		database.execSQL(INSERT_MIMICRY_TABLE26);
		database.execSQL(INSERT_MIMICRY_TABLE27);
		database.execSQL(INSERT_MIMICRY_TABLE28);
		database.execSQL(INSERT_MIMICRY_TABLE29);
		database.execSQL(INSERT_MIMICRY_TABLE30);
		database.execSQL(INSERT_MIMICRY_TABLE31);
		database.execSQL(INSERT_MIMICRY_TABLE32);
		database.execSQL(INSERT_MIMICRY_TABLE33);
		database.execSQL(INSERT_MIMICRY_TABLE34);
		database.execSQL(INSERT_MIMICRY_TABLE35);
		database.execSQL(INSERT_MIMICRY_TABLE36);
		database.execSQL(INSERT_MIMICRY_TABLE37);
		database.execSQL(INSERT_MIMICRY_TABLE38);
		database.execSQL(INSERT_MIMICRY_TABLE39);
		database.execSQL(INSERT_MIMICRY_TABLE40);
		database.execSQL(INSERT_MIMICRY_TABLE41);
		database.execSQL(INSERT_MIMICRY_TABLE42);
		database.execSQL(INSERT_MIMICRY_TABLE43);
		database.execSQL(INSERT_MIMICRY_TABLE44);
		database.execSQL(INSERT_MIMICRY_TABLE45);
		database.execSQL(INSERT_MIMICRY_TABLE46);
	}

	public void onSettingUpgrade(SQLiteDatabase database) {

		database.execSQL(DROP_SETTING_TABLE);
		database.execSQL(CREATE_SETTING_TABLE);

	}
}
