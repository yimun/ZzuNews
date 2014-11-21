package com.yimu.base;

import android.util.Log;


public class BaseTask {

	public static final int TASK_COMPLETE = 0;
	public static final int NETWORK_ERROR = 1;
	public static final int SHOW_LOADBAR = 2;
	public static final int HIDE_LOADBAR = 3;
	public static final int SHOW_TOAST = 4;
	public static final int LOAD_IMAGE = 5;
	public static final int DB_READ_COMPLETE = 6;
	
	private int id = 0;
	private String name = "";
	
	public BaseTask() {}
	
	public int getId () {
		return this.id;
	}
	
	public void setId (int id) {
		this.id = id;
	}
	
	public String getName () {
		return this.name;
	}
	
	public void setName (String name) {
		this.name = name;
	}
	
	public void onStart () {
		debugMsg("onStart");
	}
	
	public void onComplete () {
		debugMsg("onComplete");
	}
	
	public void onComplete (String httpResult) {
		debugMsg("onComplete"+httpResult);
	}
	
	public void onError (String error) {
		debugMsg("onError");
	}
	
	public void onStop () throws Exception {
		debugMsg("onStop");
	}
	
	void debugMsg(String msg){
		if(C.DEBUG_MODE)
			Log.w("BaseTask",msg);
	}
	
}