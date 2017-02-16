package com.google.smartmessage.utils;

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
public class CursorUtils {
    private static String tag = "CursorUtils";

    public static void printCursor(Cursor cursor) {
        //	获取行数
        LogUtils.i(tag, "一共有" + cursor.getCount() + "条数据");
        while (cursor.moveToNext()) {
            //	获取字段数量
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                //获取字段名
                String name = cursor.getColumnName(i);
                String content = cursor.getString(i);

                LogUtils.i(tag, name + ":" + content);
            }
            LogUtils.i(tag, "=================================");
        }
    }
}
