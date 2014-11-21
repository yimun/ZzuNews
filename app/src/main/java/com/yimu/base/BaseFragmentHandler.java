package com.yimu.base;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.yimu.utils.AppUtil;



public class BaseFragmentHandler extends Handler {

	protected BaseFragment fragment;

	public BaseFragmentHandler(BaseFragment fragment) {
		this.fragment = fragment;
	}

	public BaseFragmentHandler(Looper looper) {
		super(looper);
	}

	@Override
	public void handleMessage(Message msg) {

		int taskId;
		String result;
		fragment.hideLoadBar();
		switch (msg.what) {

		case BaseTask.TASK_COMPLETE:
			taskId = msg.getData().getInt("task");
			result = msg.getData().getString("data");
			if (result != null) {
				try {
					fragment.onTaskComplete(taskId, AppUtil.getMessage(result));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (!AppUtil.isEmptyInt(taskId)) {
				fragment.onTaskComplete(taskId);
			} else {
			}
			break;
		case BaseTask.NETWORK_ERROR:
			taskId = msg.getData().getInt("task");
			fragment.onNetworkError(taskId);
			break;
		case BaseTask.DB_READ_COMPLETE:
			taskId = msg.getData().getInt("task");
			fragment.onDbReadComplete(taskId);
			break;
		}

	}

}