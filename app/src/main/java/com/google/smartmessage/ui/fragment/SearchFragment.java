package com.google.smartmessage.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

import com.google.smartmessage.R;
import com.google.smartmessage.adapter.ConversationListAdapter;
import com.google.smartmessage.base.BaseFragment;
import com.google.smartmessage.bean.Conversation;
import com.google.smartmessage.dao.QueryHandler;
import com.google.smartmessage.globle.Constant;
import com.google.smartmessage.ui.activity.ConversationDetailActivity;
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
 * des ：搜索
 * gitVersion：$Rev$
 * updateAuthor：$Author$
 * updateDate：$Date$
 * updateDes：${TODO}
 * ============================================================
 **/
public class SearchFragment extends BaseFragment {

	private EditText et_search_list;
	private ListView lv_search_list;
	private ConversationListAdapter adapter;
	private QueryHandler queryHandler;
	String[] projection = {
			"sms.body AS snippet",
			"sms.thread_id AS _id",
			"groups.msg_count AS msg_count",
			"address AS address",
			"date AS date"
	};
	
	@Override
	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_search, null);
		et_search_list = (EditText) view.findViewById(R.id.et_search_list);
		lv_search_list = (ListView) view.findViewById(R.id.lv_search_list);
		return view;
	}

	@Override
	public void initListener() {
		//添加文本改变侦听
		et_search_list.addTextChangedListener(new TextWatcher() {
			
			//文本只要一改变，此方法调用
			//s:当前文本框的文本
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				queryHandler.startQuery(0, adapter, Constant.URI.URI_SMS_CONVERSATION,
						projection, "body like '%" + s + "%'", null, "date desc");
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
		lv_search_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//直接跳转至ConversationDetailActivity
				//进入会话详细
				Intent intent = new Intent(getActivity(), ConversationDetailActivity.class);
				//携带数据：address和thread_id
				Cursor cursor = (Cursor) adapter.getItem(position);
				Conversation conversation = Conversation.createFromCursor(cursor);
				intent.putExtra("address", conversation.address);
				intent.putExtra("thread_id", conversation.thread_id);
				startActivity(intent);
			}
		});
		
		lv_search_list.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				//输入法管理器
				//隐藏输入法软键盘
				InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				
			}
		});
	}

	@Override
	public void initData() {
		adapter = new ConversationListAdapter(getActivity(), null);
		lv_search_list.setAdapter(adapter);
		
		queryHandler = new QueryHandler(getActivity().getContentResolver());
	}

	@Override
	public void processClick(View v) {

	}

}
