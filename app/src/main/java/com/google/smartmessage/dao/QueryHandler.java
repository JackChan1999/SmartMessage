package com.google.smartmessage.dao;


import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.widget.CursorAdapter;
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
public class QueryHandler extends AsyncQueryHandler {

	public QueryHandler(ContentResolver cr) {
		super(cr);
	}

	//查询完毕时调用
	//arg0、arg1：查询开始时携带的数据
	//arg2:查询结果
	@Override
	protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
		super.onQueryComplete(token, cookie, cursor);
//		CursorUtils.printCursor(cursor);
		if(cookie != null && cookie instanceof CursorAdapter){
			//查询得到的cursor，交给CursorAdapter，由它把cursor的内容显示至listView
			((CursorAdapter)cookie).changeCursor(cursor);
		}
	}
}
