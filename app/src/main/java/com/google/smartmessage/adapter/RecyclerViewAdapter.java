package com.google.smartmessage.adapter;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.smartmessage.R;
import com.google.smartmessage.bean.Conversation;
import com.google.smartmessage.dao.ContactDao;
import com.google.smartmessage.factory.ThreadPoolFactory;
import com.google.smartmessage.ui.widget.SmoothCheckBox;

import java.util.ArrayList;
import java.util.List;

import static com.google.smartmessage.R.id.iv_check;
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
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private CursorAdapter mCursorAdapter;
    private Context       mContext;
    private boolean       isSelectMode            = false;
    private List<Integer> selectedConversationIds = new ArrayList<Integer>();
    protected OnItemClickListener mOnItemClickListener;

    public RecyclerViewAdapter(Context context) {

        mContext = context;

        mCursorAdapter = new CursorAdapter(mContext, null, 0) {

            @Override
            public View newView(Context context, Cursor cursor, ViewGroup parent) {
                return RecyclerViewAdapter.this.newView(context, cursor, parent);
            }

            @Override
            public void bindView(View view, Context context, Cursor cursor) {
                RecyclerViewAdapter.this.bindView(view, context, cursor);
            }
        };
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_conversation_list, parent, false);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        final ViewHolder holder = getHolder(view);
        final Conversation conversation = Conversation.createFromCursor(cursor);
        TextView tvDate = holder.tv_conversation_date;

        //判断当前是否进入选择模式
        if (isSelectMode) {
            holder.checkBox.setVisibility(View.VISIBLE);
            //判断集合中是否包含会话id，从而确定该条目是否被选中
            if (selectedConversationIds.contains(conversation.thread_id)) {
                holder.checkBox.setChecked(true);
            } else {
                holder.checkBox.setChecked(false);
            }
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }

        ThreadPoolFactory.getNormalPool().execute(new Runnable() {
            @Override
            public void run() {
                final String name = ContactDao.getNameByAddress(mContext, conversation.address);
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
        //String date = TimeUtils.millis2String(conversation.date,"yyyy-MM-dd");

        if (DateUtils.isToday(conversation.date)) {
            holder.tv_conversation_date.setText(DateFormat.getTimeFormat(mContext).format(conversation.date));
        } else {
            holder.tv_conversation_date.setText(DateFormat.getDateFormat(mContext).format(conversation.date));
        }
    }

    public void startTranslation(TextView tvDate){
        ObjectAnimator animator = ObjectAnimator.ofFloat(tvDate,View.TRANSLATION_X,tvDate.getLeft(),tvDate.getLeft()-100);
        animator.setDuration(200).start();
    }

    @Override
    public int getItemCount() {
        return mCursorAdapter.getCount();
    }

    public Cursor getItem(int position){
       return (Cursor) mCursorAdapter.getItem(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        mCursorAdapter.getCursor().moveToPosition(position);
        mCursorAdapter.bindView(holder.itemView, mContext, mCursorAdapter.getCursor());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mCursorAdapter.newView(mContext, mCursorAdapter.getCursor(), parent);
        ViewHolder viewHolder = new ViewHolder(view);
        setListener(parent,viewHolder,viewType);
        return viewHolder;
    }

    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    mOnItemClickListener.onItemClick(v, viewHolder , position);
                }
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = viewHolder.getAdapterPosition();
                    return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
                }
                return false;
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView      iv_conversation_avatar;
        public TextView       tv_conversation_address;
        public TextView       tv_conversation_body;
        public TextView       tv_conversation_date;
        public SmoothCheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_conversation_avatar = (ImageView) itemView.findViewById(R.id.iv_conversation_avatar);
            tv_conversation_address = (TextView) itemView.findViewById(R.id
                    .tv_conversation_address);
            tv_conversation_body = (TextView) itemView.findViewById(R.id.tv_conversation_body);
            tv_conversation_date = (TextView) itemView.findViewById(R.id.tv_conversation_date);
            checkBox = (SmoothCheckBox) itemView.findViewById(iv_check);
        }
    }

    private ViewHolder getHolder(View view) {
        ViewHolder holder = (ViewHolder) view.getTag();
        if (holder == null) {
            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        return holder;
    }

    public Cursor swapCursor(Cursor newCursor) {
        return mCursorAdapter.swapCursor(newCursor);
    }

    public void changeCursor(Cursor newCursor) {
        mCursorAdapter.changeCursor(newCursor);
        notifyDataSetChanged();
    }

    public void selectAll() {
        Cursor cursor = mCursorAdapter.getCursor();
        cursor.moveToPosition(-1);
        //遍历cursor取出所有会话id
        //把所有会话id,全部添加到集合中
        selectedConversationIds.clear();
        while (cursor.moveToNext()) {
            Conversation conversation = Conversation.createFromCursor(cursor);
            selectedConversationIds.add(conversation.thread_id);
        }

        notifyDataSetChanged();
    }

    public void cancelSelect() {
        //清空集合
        selectedConversationIds.clear();
        notifyDataSetChanged();
    }

    public List<Integer> getSelectedConversationIds() {
        return selectedConversationIds;
    }

    /**
     * 把选中的条目存入集合
     */
    public void selectSingle(int position) {
        //从cursor中取出position对应的会话
        Cursor cursor = getItem(position);
        Conversation conversation = Conversation.createFromCursor(cursor);
        if (selectedConversationIds.contains(conversation.thread_id)) {
            //强转为integer,否则是把参数作为索引而不是要删除的元素
            selectedConversationIds.remove((Integer) conversation.thread_id);
        } else {
            selectedConversationIds.add(conversation.thread_id);
        }

        notifyItemChanged(position);
    }

    public boolean getIsSelectMode() {
        return isSelectMode;
    }

    public void setIsSelectMode(boolean isSelectMode) {
        this.isSelectMode = isSelectMode;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, RecyclerView.ViewHolder holder,  int position);

        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder,  int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

}