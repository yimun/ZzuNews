package com.yimu.base;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter.ViewBinder;

import com.yimu.utils.AppCache;
import com.yimu.utils.AppUtil;
import com.yimu.zzunews.R;



public class BaseUi extends FragmentActivity {

	protected BaseHandler handler;
	protected BaseTaskPool taskPool;
	protected BaseSqlite sqlite;
	protected boolean showLoadBar = false;
	protected boolean isPaused = true; // 若当前activity不可见，不响应回调操作

	public boolean isStringEmpty(String str) {
		if (str == null || str.equalsIgnoreCase(""))
			return true;
		return false;
	}

	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		checkBaseurl(); // 检查baseUrl
		// debug memory
		debugMemory("onCreate");
		// async task handler
		this.handler = new BaseHandler(this);
		// init task pool
		this.taskPool = new BaseTaskPool(this);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

	/*private void checkBaseurl() {
		if(C.api.base == null)
			C.api.setBase(AppUtil.getSharedPreferences(this).getString("baseurl",
				""));
	}*/

	@Override
	protected void onResume() {
		super.onResume();
		// debug memory
		debugMemory("onResume");
		isPaused = false;
	}

	@Override
	protected void onPause() {
		super.onPause();
		// debug memory
		debugMemory("onPause");
		isPaused = true;
	}

	@Override
	public void onStart() {
		super.onStart();
		// debug memory
		debugMemory("onStart");
	}

	@Override
	public void onStop() {
		super.onStop();
		// debug memory
		debugMemory("onStop");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// debug memory
		debugMemory("onDestory");
	}


	public void overlay(Class<?> classObj) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		intent.setClass(this, classObj);
		startActivity(intent);
	}

	public void overlay(Class<?> classObj, Bundle params) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		intent.setClass(this, classObj);
		intent.putExtras(params);
		startActivity(intent);
	}

	public void forward(Class<?> classObj) {
		Intent intent = new Intent();
		intent.setClass(this, classObj);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		this.startActivity(intent);

	}

	public void forward(Class<?> classObj, Bundle params) {
		Intent intent = new Intent();
		intent.setClass(this, classObj);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtras(params);
		this.startActivity(intent);
	}

	public Context getContext() {
		return this;
	}

	public BaseHandler getHandler() {
		return this.handler;
	}

	public void setHandler(BaseHandler handler) {
		this.handler = handler;
	}

	public LayoutInflater getInflater() {
		return (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public View getLayout(int layoutId) {
		return getInflater().inflate(layoutId, null);
	}

	public View getWidget(int layoutId, int itemId) {
		return getLayout(layoutId).findViewById(itemId);
	}

	public BaseTaskPool getTaskPool() {
		return this.taskPool;
	}

	public void showLoadBar() {
		this.findViewById(R.id.PROGRESS_BAR).setVisibility(View.VISIBLE);
		showLoadBar = true;
	}

	public void hideLoadBar() {
		if (showLoadBar) {
			this.findViewById(R.id.PROGRESS_BAR).setVisibility(View.GONE);
			showLoadBar = false;
		}
	}


	public void loadImage(final String url) {
		taskPool.addTask(0, new BaseTask() {
			@Override
			public void onComplete() {
				AppCache.getCachedImage(getContext(), url);
				sendMessage(BaseTask.LOAD_IMAGE);
			}
		}, 0);
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////
	// logic method

	public void doFinish() {
		this.finish();
	}

	public void doEditText() {
		Intent intent = new Intent();
		intent.setAction(C.intent.action.EDITTEXT);
		this.startActivity(intent);
	}

	public void doEditText(Bundle data) {
		Intent intent = new Intent();
		intent.setAction(C.intent.action.EDITTEXT);
		intent.putExtras(data);
		this.startActivity(intent);
	}

	public void doEditBlog() {
		Intent intent = new Intent();
		intent.setAction(C.intent.action.EDITBLOG);
		this.startActivity(intent);
	}

	public void doEditBlog(Bundle data) {
		Intent intent = new Intent();
		intent.setAction(C.intent.action.EDITBLOG);
		intent.putExtras(data);
		this.startActivity(intent);
	}

	public void sendMessage(int what) {
		Message m = new Message();
		m.what = what;
		handler.sendMessage(m);
	}

	public void sendMessage(int what, int taskId) {
		Bundle b = new Bundle();
		b.putInt("task", taskId);
		Message m = new Message();
		m.what = what;
		m.setData(b);
		handler.sendMessage(m);
	}

	public void sendMessage(int what, int taskId, String data) {
		Bundle b = new Bundle();
		b.putInt("task", taskId);
		b.putString("data", data);
		Message m = new Message();
		m.what = what;
		m.setData(b);
		handler.sendMessage(m);
	}

	// local task
	public void doTaskAsync(int taskId, int delayTime) {
		taskPool.addTask(taskId, new BaseTask() {
			@Override
			public void onComplete() {
				sendMessage(BaseTask.TASK_COMPLETE, this.getId(), null);
			}

			@Override
			public void onError(String error) {
				sendMessage(BaseTask.NETWORK_ERROR, this.getId(), null);
			}
		}, delayTime);
	}

	public void doTaskAsync(int taskId, BaseTask baseTask, int delayTime) {
		taskPool.addTask(taskId, baseTask, delayTime);
	}

	// remote task
	public void doTaskAsync(int taskId, String taskUrl) {
		showLoadBar();
		taskPool.addTask(taskId, taskUrl, new BaseTask() {
			@Override
			public void onComplete(String httpResult) {
				sendMessage(BaseTask.TASK_COMPLETE, this.getId(), httpResult);
			}

			@Override
			public void onError(String error) {
				sendMessage(BaseTask.NETWORK_ERROR, this.getId(), null);
			}
		}, 0);
	}

	public void doTaskAsync(int taskId, String taskUrl,
			HashMap<String, String> taskArgs) {
		showLoadBar();
		taskPool.addTask(taskId, taskUrl, taskArgs, new BaseTask() {
			@Override
			public void onComplete(String httpResult) {
				sendMessage(BaseTask.TASK_COMPLETE, this.getId(), httpResult);
			}

			@Override
			public void onError(String error) {
				sendMessage(BaseTask.NETWORK_ERROR, this.getId(), null);
			}
		}, 0);
	}

	// 下三个为自身handler所回调的方法，可在activity类中重写
	/**
	 * remote task onComplete
	 * 
	 * @param taskId
	 * @param message
	 */
	public void onTaskComplete(int taskId, BaseMessage message) {
		if(isPaused){
			return;
		}
	}

	/**
	 * local task onComplete(not usual)
	 * 
	 * @param taskId
	 */
	public void onTaskComplete(int taskId) {
		if(isPaused){
			return;
		}
	}

	public void onNetworkError(int taskId) {
		if(isPaused){
			return;
		}
//		toastE(C.err.network);
	}

	public void onDbReadComplete(int taskId) {
		if(isPaused){
			return;
		}
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////
	// debug method

	public void debugMemory(String tag) {
		if (C.DEBUG_MODE) {
			Log.w(this.getClass().getSimpleName(),
					tag + ":" + AppUtil.getUsedMemory());
		}
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////
	// common classes

	public class BitmapViewBinder implements ViewBinder {
		//
		@Override
		public boolean setViewValue(View view, Object data,
				String textRepresentation) {
			if ((view instanceof ImageView) & (data instanceof Bitmap)) {
				ImageView iv = (ImageView) view;
				Bitmap bm = (Bitmap) data;
				iv.setImageBitmap(bm);
				return true;
			}
			return false;
		}
	}


}