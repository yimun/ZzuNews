package com.yimu.zzunews.model;

import com.yimu.base.BaseModel;


public class NewsList extends BaseModel{

	private String name;
	private String preg;
	private String url;
	private String urlprefix;
	private String encode;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegex() {
		return preg;
	}
	public void setRegex(String regex) {
		this.preg = regex;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUrlprefix() {
		return urlprefix;
	}
	public void setUrlprefix(String urlprefix) {
		this.urlprefix = urlprefix;
	}
	public String getEncode() {
		return encode;
	}
	public void setEncode(String encode) {
		this.encode = encode;
	}
	
	
	
}
