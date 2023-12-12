package com.ideabobo.model;
import com.alibaba.fastjson.JSON;
public class Dbtablemapping {
	public static Object parseStringModel(String value, String table) {
		Object object = null;
		switch (table) {
			case "wct_chengji": object = JSON.parseObject(value, Chengji.class); break;
			case "wct_choose": object = JSON.parseObject(value, Choose.class); break;
			case "wct_choosem": object = JSON.parseObject(value, Choosem.class); break;
			case "wct_huihua": object = JSON.parseObject(value, Huihua.class); break;
			case "wct_kaoshi": object = JSON.parseObject(value, Kaoshi.class); break;
			case "wct_kaoshim": object = JSON.parseObject(value, Kaoshim.class); break;
			case "wct_message": object = JSON.parseObject(value, Message.class); break;
			case "wct_notice": object = JSON.parseObject(value, Notice.class); break;
			case "wct_posts": object = JSON.parseObject(value, Posts.class); break;
			case "wct_qiandao": object = JSON.parseObject(value, Qiandao.class); break;
			case "wct_qiandaoreplay": object = JSON.parseObject(value, Qiandaoreplay.class); break;
			case "wct_qunzu": object = JSON.parseObject(value, Qunzu.class); break;
			case "wct_replay": object = JSON.parseObject(value, Replay.class); break;
			case "wct_user": object = JSON.parseObject(value, User.class); break;
			case "wct_yzmessage": object = JSON.parseObject(value, Yzmessage.class); break;
			case "wct_zuoye": object = JSON.parseObject(value, Zuoye.class); break;
			case "wct_zuoyereplay": object = JSON.parseObject(value, Zuoyereplay.class); break;
		}
		return object;
}
public static Object getModelByTable(String table) {
	Object object = null;
	switch (table) {
			case "wct_chengji": object = new Chengji(); break;
			case "wct_choose": object = new Choose(); break;
			case "wct_choosem": object = new Choosem(); break;
			case "wct_huihua": object = new Huihua(); break;
			case "wct_kaoshi": object = new Kaoshi(); break;
			case "wct_kaoshim": object = new Kaoshim(); break;
			case "wct_message": object = new Message(); break;
			case "wct_notice": object = new Notice(); break;
			case "wct_posts": object = new Posts(); break;
			case "wct_qiandao": object = new Qiandao(); break;
			case "wct_qiandaoreplay": object = new Qiandaoreplay(); break;
			case "wct_qunzu": object = new Qunzu(); break;
			case "wct_replay": object = new Replay(); break;
			case "wct_user": object = new User(); break;
			case "wct_yzmessage": object = new Yzmessage(); break;
			case "wct_zuoye": object = new Zuoye(); break;
			case "wct_zuoyereplay": object = new Zuoyereplay(); break;
		}
		return object;
	}
}
