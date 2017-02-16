package com.google.smartmessage.adapter;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.smartmessage.R;
import com.google.smartmessage.bean.Conversation;
import com.google.smartmessage.dao.ContactDao;
import com.google.smartmessage.factory.ThreadPoolFactory;

import java.util.ArrayList;
import java.util.List;

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
public class SmsCursorAdapter extends android.support.v4.widget.CursorAdapter {

    private boolean       isSelectMode            = false;
    private List<Integer> selectedConversationIds = new ArrayList<Integer>();

    public SmsCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public SmsCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_conversation_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final RecyclerViewAdapter.ViewHolder holder = getHolder(view);
        final Conversation conversation = Conversation.createFromCursor(cursor);

        //判断当前是否进入选择模式
        if (isSelectMode) {
            holder.checkBox.setVisibility(View.VISIBLE);
            //判断集合中是否包含会话id，从而确定该条目是否被选中
            if (selectedConversationIds.contains(conversation.thread_id)) {
                holder.checkBox.setChecked(true,true);
            } else {
                holder.checkBox.setChecked(false,true);
            }
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }

        ThreadPoolFactory.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                //按号码查询是否存有联系人
                final String name = ContactDao.getNameByAddress(mContext, conversation.address);
                //获取联系人头像
                final Bitmap avatar = ContactDao.getAvatarByAddress(mContext, conversation.address);

                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(name)) {
                            holder.tv_conversation_address.setText(conversation.address +
                                    "(" + conversation.msg_count + ")");
                        } else {
                            holder.tv_conversation_address.setText(name + "(" + conversation
                                    .msg_count + ")");
                        }

                        //判断是否成功拿到头像
                        if (avatar == null) {
                            holder.iv_conversation_avatar.setBackgroundResource(R.mipmap
                                    .img_default_avatar);
                        } else {
                            holder.iv_conversation_avatar.setBackgroundDrawable(new
                                    BitmapDrawable(avatar));
                        }
                    }
                });
            }
        });


        holder.tv_conversation_body.setText(conversation.snippet);

        //设置时间
        //判断是否今天
        if (DateUtils.isToday(conversation.date)) {
            //如果是，显示时分
            holder.tv_conversation_date.setText(DateFormat.getTimeFormat(mContext).format
                    (conversation.date));
        } else {
            //如果不是，显示年月日
            holder.tv_conversation_date.setText(DateFormat.getDateFormat(mContext).format
                    (conversation.date));
        }
    }

    private RecyclerViewAdapter.ViewHolder getHolder(View view) {
        RecyclerViewAdapter.ViewHolder holder = (RecyclerViewAdapter.ViewHolder) view.getTag();
        if (holder == null) {
            holder = new RecyclerViewAdapter.ViewHolder(view);
            view.setTag(holder);
        }
        return holder;
    }
}
