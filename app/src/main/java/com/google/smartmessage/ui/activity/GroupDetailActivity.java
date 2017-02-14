package com.google.smartmessage.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.smartmessage.R;
import com.google.smartmessage.adapter.ConversationListAdapter;
import com.google.smartmessage.base.BaseActivity;
import com.google.smartmessage.bean.Conversation;
import com.google.smartmessage.dao.QueryHandler;
import com.google.smartmessage.globle.Constant;

public class GroupDetailActivity extends BaseActivity {

	private String groupName;
	private int groupId;
	private ListView lv_group_detail;
	private QueryHandler queryHandler;
	private ConversationListAdapter adapter;

	@Override
	public void initView() {
		setContentView(R.layout.activity_group_detail);
		lv_group_detail = (ListView) findViewById(R.id.lv_group_detail);
	}

	@Override
	public void initListener() {
		lv_group_detail.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//直接跳转至ConversationDetailActivity
				//进入会话详细
				Intent intent = new Intent(GroupDetailActivity.this, ConversationDetailActivity.class);
				//携带数据：address和thread_id
				Cursor cursor = (Cursor) adapter.getItem(position);
				Conversation conversation = Conversation.createFromCursor(cursor);
				intent.putExtra("address", conversation.address);
				intent.putExtra("thread_id", conversation.thread_id);
				startActivity(intent);
			}
		});

	}

	@Override
	public void initData() {
		Intent intent = getIntent();
		groupName = intent.getStringExtra("name");
		groupId = intent.getIntExtra("group_id", -1);
		initTitleBar();
		
		//复用会话列表的所有组件、参数、条目布局、查询字段、表
		//只要查询的条件不一样
		adapter = new ConversationListAdapter(this, null);
		lv_group_detail.setAdapter(adapter);
		
		String[] projection = {
				"sms.body AS snippet",
				"sms.thread_id AS _id",
				"groups.msg_count AS msg_count",
				"address AS address",
				"date AS date"
		};
		
		queryHandler = new QueryHandler(getContentResolver());
		queryHandler.startQuery(0, adapter, Constant.URI.URI_SMS_CONVERSATION, projection, buildQuery(), null, null);
	}

	@Override
	public void processClick(View v) {
		switch (v.getId()) {
		case R.id.iv_titlebar_back_btn:
			finish();
			break;

		}

	}

	private void initTitleBar() {
		((TextView)findViewById(R.id.tv_titlebar_title)).setText(groupName);
		findViewById(R.id.iv_titlebar_back_btn).setOnClickListener(this);

	}
	
	private String buildQuery() {
		//查询当前群组包含的所有会话的thread_id，这些会话都会被显示在GroupDetailActivity的界面中
		Cursor cursor = getContentResolver().query(Constant.URI.URI_THREAD_GROUP_QUERY, new String[]{"thread_id"}, "group_id = " + groupId, null, null);
		String selection = "thread_id in (";
		while (cursor.moveToNext()) {
			if(cursor.isLast())
				//最后一个会话id后面不要逗号
				selection += cursor.getString(0);
			else
				selection += cursor.getString(0) + ", ";
		}
		selection += ")";
		return selection;

	}
}
