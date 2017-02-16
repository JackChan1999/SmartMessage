package com.google.smartmessage.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.google.smartmessage.R;
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
public class AutoSearchAdapter extends CursorAdapter {

	public AutoSearchAdapter(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return View.inflate(context, R.layout.item_auto_search_tv, null);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ViewHolder holder = getHolder(view);
		holder.tv_autosearch_name.setText(cursor.getString(cursor.getColumnIndex("display_name")));
		holder.tv_autosearch_address.setText(cursor.getString(cursor.getColumnIndex("data1")));
	}

	private ViewHolder getHolder(View view) {
		ViewHolder holder = (ViewHolder) view.getTag();
		if(holder == null){
			holder = new ViewHolder(view);
			view.setTag(holder);
		}
		return holder;

	}
	
	class ViewHolder{
		private TextView tv_autosearch_name;
		private TextView tv_autosearch_address;

		public ViewHolder(View view) {
			tv_autosearch_name = (TextView) view.findViewById(R.id.tv_autosearch_name);
			tv_autosearch_address = (TextView) view.findViewById(R.id.tv_autosearch_address);
		}
	}
	//点击下拉列表条目时的返回值
	@Override
	public CharSequence convertToString(Cursor cursor) {
		return cursor.getString(cursor.getColumnIndex("data1"));
	}
}
