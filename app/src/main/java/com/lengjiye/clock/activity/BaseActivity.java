package com.lengjiye.clock.activity;

import android.app.Activity;

//import com.baidu.mobstat.StatService;

public class BaseActivity extends Activity {

	@Override
	protected void onDestroy() {

		super.onDestroy();
	}
	
	@Override
	protected void onPause() {
		super.onPause();

		/**
		 * 页面结束（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
		 * 不能与StatService.onPageStart一级onPageEnd函数交叉使用
		 */
//		StatService.onPause(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();

		/**
		 * 页面起始（每个Activity中都需要添加，如果有继承的父Activity中已经添加了该调用，那么子Activity中务必不能添加）
		 * 不能与StatService.onPageStart一级onPageEnd函数交叉使用
		 */
//		StatService.onResume(this);
	}

}
