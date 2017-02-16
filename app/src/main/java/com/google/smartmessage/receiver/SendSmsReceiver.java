package com.google.smartmessage.receiver;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.smartmessage.utils.ToastUtils;
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
public class SendSmsReceiver extends BroadcastReceiver {
	
	public static final String ACTION_SEND_SMS = "com.itheima.momo.sendsms";
	@Override
	public void onReceive(Context context, Intent intent) {
		int code = getResultCode();
		if(code == Activity.RESULT_OK ){
			ToastUtils.showShortToast("发送成功");
		}
		else{
			ToastUtils.showShortToast("发送失败");
		}
	}

}
