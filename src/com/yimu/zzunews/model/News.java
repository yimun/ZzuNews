package com.yimu.zzunews.model;

import com.yimu.base.BaseModel;

public class News extends BaseModel {

	private String title;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUptime() {
		return uptime;
	}
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	private String uptime;
	private String url;

}
