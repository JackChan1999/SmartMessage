package com.google.smartmessage.bean;

import android.database.Cursor;

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
