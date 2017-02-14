package com.google.smartmessage.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

public abstract class BaseActivity extends FragmentActivity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initView();
		initListener();
		initData();
	}
	
	public abstract void initView();
	public abstract void initData();
	protected void processClick(View v){

	};
	protected void initListener(){

	};


	@Override
	public void onClick(View v) {
		processClick(v);
		
	}
}
