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
