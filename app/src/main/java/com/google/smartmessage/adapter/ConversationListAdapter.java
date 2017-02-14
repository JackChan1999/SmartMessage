package com.google.smartmessage.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.smartmessage.R;
import com.google.smartmessage.bean.Conversation;
import com.google.smartmessage.dao.ContactDao;
import com.google.smartmessage.factory.ThreadPoolFactory;

import java.util.ArrayList;
import java.util.List;

public class ConversationListAdapter extends CursorAdapter {

	private boolean isSelectMode = false;
	//记录选择模式下选中哪些条目
	private List<Integer> selectedConversationIds = new ArrayList<Integer>();
	@SuppressWarnings("deprecation")
	public ConversationListAdapter(Context context, Cursor c) {
		super(context, c);
	}

	//返回的View对象就是listView的条目
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return View.inflate(context, R.layout.item_conversation_list, null);
	}

	//设置listView每个条目显示的内容
	@SuppressWarnings("deprecation")
	@Override
	public void bindView(View view, final Context context, Cursor cursor) {
		final ViewHolder holder = getHolder(view);
		//根据cursor内容创建会话对象，此时cursor的指针已经移动至正确的位置
		final Conversation conversation = Conversation.createFromCursor(cursor);
		
		//判断当前是否进入选择模式
		if(isSelectMode){
			holder.iv_check.setVisibility(View.VISIBLE);
			//判断集合中是否包含会话id，从而确定该条目是否被选中
			if(selectedConversationIds.contains(conversation.thread_id)){
				holder.iv_check.setBackgroundResource(R.mipmap.common_checkbox_checked);
			}
			else{
				holder.iv_check.setBackgroundResource(R.mipmap.common_checkbox_normal);
			}
		}
		else{
			holder.iv_check.setVisibility(View.GONE);
		}

		ThreadPoolFactory.getNormalPool().execute(new Runnable() {
			@Override
			public void run() {
				//按号码查询是否存有联系人
				final String name = ContactDao.getNameByAddress(context, conversation.address);
				//获取联系人头像
				final Bitmap avatar = ContactDao.getAvatarByAddress(context, conversation.address);

				((Activity)context).runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if(TextUtils.isEmpty(name)){
							holder.tv_conversation_address.setText(conversation.address +
									"(" + conversation.msg_count + ")");
						}
						else{
							holder.tv_conversation_address.setText(name + "(" + conversation.msg_count + ")");
						}

						//判断是否成功拿到头像
						if(avatar == null){
							holder.iv_conversation_avatar.setBackgroundResource(R.mipmap.img_default_avatar);
						}
						else{
							holder.iv_conversation_avatar.setBackgroundDrawable(new BitmapDrawable(avatar));
						}
					}
				});
			}
		});


		holder.tv_conversation_body.setText(conversation.snippet);
		
		//设置时间
		//判断是否今天
		if(DateUtils.isToday(conversation.date)){
			//如果是，显示时分
			holder.tv_conversation_date.setText(DateFormat.getTimeFormat(context).format(conversation.date));
		}
		else{
			//如果不是，显示年月日
			holder.tv_conversation_date.setText(DateFormat.getDateFormat(context).format(conversation.date));
		}

	}

	//参数就是条目的View对象
	private ViewHolder getHolder(View view) {
		//先判断条目view对象中是否有holder
		ViewHolder holder = (ViewHolder) view.getTag();
		if(holder == null){
			//如果没有，就创建一个，并存入view对象
			holder = new ViewHolder(view);
			view.setTag(holder);
		}
		return holder;
	}
	
	public boolean getIsSelectMode() {
		return isSelectMode;
	}

	public void setIsSelectMode(boolean isSelectMode) {
		this.isSelectMode = isSelectMode;
	}

	class ViewHolder{
		
		private ImageView iv_conversation_avatar;
		private TextView tv_conversation_address;
		private TextView tv_conversation_body;
		private TextView tv_conversation_date;
		private ImageView iv_check;

		//参数就是条目的View对象
		public ViewHolder(View view) {
			//在构造方法中完成封装条目的所有组件
			iv_conversation_avatar = (ImageView) view.findViewById(R.id.iv_conversation_avatar);
			tv_conversation_address = (TextView) view.findViewById(R.id.tv_conversation_address);
			tv_conversation_body = (TextView) view.findViewById(R.id.tv_conversation_body);
			tv_conversation_date = (TextView) view.findViewById(R.id.tv_conversation_date);
			iv_check = (ImageView) view.findViewById(R.id.iv_check);
		}
	}
	
	/**
	 * 把选中的条目存入集合
	 */
	public void selectSingle(int position){
		//从cursor中取出position对应的会话
		Cursor cursor = (Cursor) getItem(position);
		Conversation conversation = Conversation.createFromCursor(cursor);
		if(selectedConversationIds.contains(conversation.thread_id)){
			//强转为integer,否则是把参数作为索引而不是要删除的元素
			selectedConversationIds.remove((Integer)conversation.thread_id);
		}
		else{
			selectedConversationIds.add(conversation.thread_id);
		}
		
		notifyDataSetChanged();
	}
	
	public void selectAll(){
		Cursor cursor = getCursor();
		cursor.moveToPosition(-1);
		//遍历cursor取出所有会话id
		//把所有会话id,全部添加到集合中
		selectedConversationIds.clear();
		while(cursor.moveToNext()){
			Conversation conversation = Conversation.createFromCursor(cursor);
			selectedConversationIds.add(conversation.thread_id);
		}
		
		notifyDataSetChanged();
	}
	
	public void cancelSelect(){
		//清空集合
		selectedConversationIds.clear();
		notifyDataSetChanged();
	}

	public List<Integer> getSelectedConversationIds() {
		return selectedConversationIds;
	}

	
}
