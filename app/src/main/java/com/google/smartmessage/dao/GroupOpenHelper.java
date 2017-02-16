package com.google.smartmessage.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
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
public class GroupOpenHelper extends SQLiteOpenHelper {

	//单例模式获取GroupOpenHelper实例
	private static GroupOpenHelper instance;
	
	public static GroupOpenHelper getInstance(Context context){
		if(instance == null){
			instance = new GroupOpenHelper(context, "group.db", null, 1);
		}
		return instance;
	}
	
	private GroupOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//创建groups表
		db.execSQL("create table groups(" +
				"_id integer primary key autoincrement, " +
				"name varchar, " +
				"create_date integer, " +
				"thread_count integer" + 
				")");
		//创建会话和群组的映射表
		db.execSQL("create table thread_group(" +
				"_id integer primary key autoincrement, " +
				"group_id integer, " +
				"thread_id integer" + 
				")");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
