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
public class Conversation {

    public String snippet;
    public int    thread_id;
    public String msg_count;
    public String address;
    public long   date;

    /**
     * 创建会话bean对象
     *
     * @param cursor
     * @return
     */
    public static Conversation createFromCursor(Cursor cursor) {
        Conversation conversation = new Conversation();
        conversation.snippet = cursor.getString(cursor.getColumnIndex("snippet"));
        conversation.thread_id = cursor.getInt(cursor.getColumnIndex("_id"));
        conversation.msg_count = cursor.getString(cursor.getColumnIndex("msg_count"));
        conversation.address = cursor.getString(cursor.getColumnIndex("address"));
        conversation.date = cursor.getLong(cursor.getColumnIndex("date"));
        return conversation;
    }
}
