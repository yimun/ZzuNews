package com.yimu.base;

public final class C {
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// core settings (important)
	public static final class packageName{
		public static final String model = "com.yimu.zzunews.model";
	}
	// preference name 
	public static final String PREFERENCES_NAME = "com.yimu.zzunews.pref";
	// do show debug message
	public static final boolean DEBUG_MODE = true;
	
	public static final class dir {
		public static final String base				= "/sdcard/dutschedule";
		public static final String faces			= base + "/faces";
		public static final String images			= base + "/images";
	}
	
	public static final class api {
		public static String base = "http://yimutest.sinaapp.com/dutserver/index.php?r=";
	
		
		public static final String newslist			= "zzunews/newslist";
		public static final String news		        = "zzunews/news";
		
		
	}
	
	public static final class task {
		//net task
		public static final int newslist		     = 1001;
		public static final int news	         	 = 1002;
		
	}
	
	public static final class err {
		public static final String network			= "网络有点不给力";
		public static final String server			= "网络有点不给力";
		public static final String jsonFormat		= "服务器消息格式错误";
		public static final String auth             = "用户名或密码错误";
		public static final String emptydata        = "暂无数据，刷新看看";
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// intent & action settings
	
	public static final class intent {
		public static final class action {
			public static final String EDITTEXT		= "com.siwe.dutschedule.EDITTEXT";
			public static final String EDITBLOG		= "com.siwe.dutschedule.EDITBLOG";
		}
	}
	
	public static final class action {
		public static final class edittext {
			public static final int CONFIG			= 2001;
			public static final int COMMENT			= 2002;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	// additional settings
	
	public static final class web {
		public static final String base				= "http://192.168.9.122:8002";
		public static final String index			= base + "/index.php";
		public static final String gomap			= base + "/gomap.php";
	}
}