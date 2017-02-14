package com.google.smartmessage.receiver;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.google.smartmessage.utils.ToastUtils;

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
