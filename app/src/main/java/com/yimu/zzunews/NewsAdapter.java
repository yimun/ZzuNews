package com.yimu.zzunews;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yimu.base.BaseList;
import com.yimu.base.BaseModel;
import com.yimu.base.BaseUi;
import com.yimu.zzunews.model.News;


public class NewsAdapter extends BaseList {
		
	public NewsAdapter(BaseUi ui, ArrayList<? extends BaseModel> datalist) {
		super(ui, datalist);
	}

	public static class ViewHolder {
		private TextView mTitle;
		private TextView mUptiem;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (getCount() == 0) {
			return null;
		}
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = ui.getLayout(R.layout.item_list_news);
			holder = new ViewHolder();
			holder.mTitle = (TextView) convertView.findViewById(R.id.title);
			holder.mUptiem = (TextView) convertView.findViewById(R.id.uptime);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		News news = (News) datalist.get(position);
		holder.mTitle.setText(news.getTitle());
		holder.mUptiem.setText(news.getUptime());
		return convertView;
	}

}
