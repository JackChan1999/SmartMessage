package com.google.smartmessage.dialog;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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
public class DeleteMsgDialog extends BaseDialog {

	private TextView tv_deletemsg_title;
	private ProgressBar pb_deletemsg;
	private Button bt_deletemsg_cancel;
	private OnDeleteCancelListener onDeleteCancelListener;

	private int maxProgress;
	protected DeleteMsgDialog(Context context, int maxProgress, OnDeleteCancelListener onDeleteCancelListener) {
		super(context);
		this.maxProgress = maxProgress;
		this.onDeleteCancelListener = onDeleteCancelListener;
	}

	/**
	 * 显示删除进度对话框
	 * @param context
	 * @param maxProgress
	 * @param onDeleteCancelListener
	 */
	public static DeleteMsgDialog showDeleteDialog(Context context, int maxProgress,
												   OnDeleteCancelListener onDeleteCancelListener){
		DeleteMsgDialog dialog = new DeleteMsgDialog(context, maxProgress, onDeleteCancelListener);
		dialog.show();
		return dialog;
	}
	
	@Override
	public void initView() {
		setContentView(R.layout.dialog_delete);
		
		tv_deletemsg_title = (TextView) findViewById(R.id.tv_deletemsg_title);
		pb_deletemsg = (ProgressBar) findViewById(R.id.pb_deletemsg);
		bt_deletemsg_cancel = (Button) findViewById(R.id.bt_deletemsg_cancel);

	}

	@Override
	public void initListener() {
		bt_deletemsg_cancel.setOnClickListener(this);

	}

	@Override
	public void initData() {
		tv_deletemsg_title.setText("正在删除(0/" + maxProgress + ")");
		//给进度条设置最大值
		pb_deletemsg.setMax(maxProgress);

	}

	@Override
	public void processClick(View v) {
		switch (v.getId()) {
		case R.id.bt_deletemsg_cancel:
			if(onDeleteCancelListener != null){
				onDeleteCancelListener.onCancel();
			}
			dismiss();
			break;

		}

	}
	
	public interface OnDeleteCancelListener{
		void onCancel();
	}

	/**
	 * 刷新进度条和标题
	 * @param progress
	 */
	public void updateProgressAndTitle(int progress){
		pb_deletemsg.setProgress(progress);
		tv_deletemsg_title.setText("正在删除(" + progress + "/" + maxProgress + ")");
	}
}
