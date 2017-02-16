package com.google.smartmessage.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.smartmessage.R;
import com.google.smartmessage.adapter.ConversationDetailAdapter;
import com.google.smartmessage.base.BaseActivity;
import com.google.smartmessage.dao.ContactDao;
import com.google.smartmessage.dao.QueryHandler;
import com.google.smartmessage.dao.SmsDao;
import com.google.smartmessage.globle.Constant;
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
public class ConversationDetailActivity extends BaseActivity {

	private String address;
	private int thread_id;
	private QueryHandler queryHandler;
	private ConversationDetailAdapter adapter;
	private ListView lv_conversation_detail;
	private EditText et_conversation_detail;
	private Button bt_conversation_detail_send;

	@Override
	public void initView() {
		setContentView(R.layout.activity_conversation_detail);
		
		lv_conversation_detail = (ListView) findViewById(R.id.lv_conversation_detail);
		et_conversation_detail = (EditText) findViewById(R.id.et_conversation_detail);
		bt_conversation_detail_send = (Button) findViewById(R.id.bt_conversation_detail_send);
		
		//只要ListView刷新，就会滑动
		lv_conversation_detail.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
	}

	@Override
	public void initListener() {
		bt_conversation_detail_send.setOnClickListener(this);

	}

	@Override
	public void initData() {
		//拿到传递过来的数据
		Intent intent = getIntent();
		if(intent != null){
			address = intent.getStringExtra("address");
			thread_id = intent.getIntExtra("thread_id", -1);
			
			initTitleBar();
		}
		
		//给会话详细界面的listview设置adapter，显示会话的所有短信
		adapter = new ConversationDetailAdapter(this, null, lv_conversation_detail);
		lv_conversation_detail.setAdapter(adapter);
		
		//按照会话id查询属于该会话的所有短信
		String[] projection = {
				"_id",
				"body",
				"type",
				"date"
		};
		String selection = "thread_id = " + thread_id;
		
		//异步查询短信
		queryHandler = new QueryHandler(getContentResolver());
		queryHandler.startQuery(0, adapter, Constant.URI.URI_SMS, projection, selection, null, "date");
//		Cursor cursor = getContentResolver().query(Constant.URI.URI_SMS, projection, selection, null, null);
	}

	/**
	 * 初始化标题栏
	 */
	private void initTitleBar() {
		
		findViewById(R.id.iv_titlebar_back_btn).setOnClickListener(this);
		String name = ContactDao.getNameByAddress(this, address);
		((TextView)findViewById(R.id.tv_titlebar_title)).setText(TextUtils.isEmpty(name)? address : name);

	}
	
	@Override
	public void processClick(View v) {
		switch (v.getId()) {
		case R.id.iv_titlebar_back_btn:
			finish();
			break;
		case R.id.bt_conversation_detail_send:
			String body = et_conversation_detail.getText().toString();
			//判断用户是否输入短信内容
			if(!TextUtils.isEmpty(body)){
				SmsDao.sendSms(this, address, body);
				et_conversation_detail.setText("");
			}
			break;
		}

	}

}
