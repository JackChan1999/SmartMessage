package com.google.smartmessage.dao;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.PhoneLookup;

import java.io.InputStream;
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
public class ContactDao {
	/**
	 * 通过号码获取联系人名字
	 * @param context
	 * @param address
	 * @return
	 */
	public static String getNameByAddress(Context context, String address){
		String name = null;
		Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, address);
		Cursor cursor = context.getContentResolver().query(uri, new String[]{PhoneLookup.DISPLAY_NAME}, null, null, null);
//		CursorUtils.printCursor(cursor);
		if (cursor != null){
			if(cursor.moveToFirst()){
				name = cursor.getString(0);
				cursor.close();
			}
		}
		return name;
	}
	
	/**
	 * 通过号码获取联系人头像
	 * @param context
	 * @param address
	 * @return
	 */
	public static Bitmap getAvatarByAddress(Context context, String address){
		Bitmap avatar = null;
		ContentResolver resolver = context.getContentResolver();
		Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, address);
		Cursor cursor = resolver.query(uri, new String[]{PhoneLookup._ID}, null, null, null);
		if (cursor != null){
			if(cursor.moveToFirst()){
				String _id = cursor.getString(0);
				InputStream is = Contacts.openContactPhotoInputStream(resolver,
						Uri.withAppendedPath(Contacts.CONTENT_URI, _id));
				avatar = BitmapFactory.decodeStream(is);
			}
		}
		return avatar;
	}
}
