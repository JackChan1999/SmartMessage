package com.google.smartmessage.dao;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

import com.google.smartmessage.globle.Constant;
import com.google.smartmessage.receiver.SendSmsReceiver;

import java.util.List;

public class SmsDao {

	public static void sendSms(Context context, String address, String body){
		SmsManager manager = SmsManager.getDefault();
		List<String> smss = manager.divideMessage(body);
		
		Intent intent = new Intent(SendSmsReceiver.ACTION_SEND_SMS);
		//短信发出去后，系统会发送一条广播，告知我们短信发送是成功还是失败
		PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
		for (String text : smss) {
			//这个api只负责发短信，不会把短信写入数据库
			manager.sendTextMessage(address, null, text, sentIntent, null);
		
			//把短信插入短信数据库
			insertSms(context, address, text);
		}
	}
	
	public static void insertSms(Context context, String address, String body){
		ContentValues values = new ContentValues();
		values.put("address", address);
		values.put("body", body);
		values.put("type", Constant.SMS.TYPE_SEND);
		
		context.getContentResolver().insert(Constant.URI.URI_SMS, values);
	}
}
