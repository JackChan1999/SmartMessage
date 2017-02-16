package com.google.smartmessage.bean;

import android.database.Cursor;
/**
 * ============================================================
 * Copyright：Google有限公司版权所有 (c) 2017
 * Author：   陈冠杰
 * Email：    815712739@qq.com
 * GitHub：   https://github.com/JackChen1999
 * 博客：     http://blog.csdn.net/axi295309066
 * 微博：     AndroidDeveloper
 * <p>
 * Project_Name：SmartMessage
 * Package_Name：PACKAGE_NAME
 * Version：1.0
 * time：2016/2/16 12:35
 * des ：${TODO}
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
public class Sms {
	private String body;
	private long date;
	private int type;
	private int _id;
	
	public static Sms createFromCursor(Cursor cursor){
		Sms sms = new Sms();
		sms.setBody(cursor.getString(cursor.getColumnIndex("body")));
		sms.setDate(cursor.getLong(cursor.getColumnIndex("date")));
		sms.setType(cursor.getInt(cursor.getColumnIndex("type")));
		sms.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
		return sms;
		
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	
	
}
