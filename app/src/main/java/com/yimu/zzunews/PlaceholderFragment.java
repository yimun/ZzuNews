package com.yimu.zzunews;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.yimu.base.BaseFragment;
import com.yimu.base.BaseMessage;
import com.yimu.base.C;
import com.yimu.zzunews.model.News;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends BaseFragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	private ListView mainListView;
	private ArrayList<News> listNewsItems = new ArrayList<News>();
	private NewsAdapter mAdapter;
	

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static PlaceholderFragment newInstance(int sectionNumber) {
		PlaceholderFragment fragment = new PlaceholderFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public PlaceholderFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		mainListView = (ListView) rootView.findViewById(R.id.listView);
		mAdapter = new NewsAdapter(baseUi, listNewsItems);
		mainListView.setAdapter(mAdapter);
		mainListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String url = listNewsItems.get(position).getUrl();
				Intent intent= new Intent();        
			    intent.setAction("android.intent.action.VIEW");    
			    Uri content_url = Uri.parse(url);   
			    intent.setData(content_url);  
			    startActivity(intent);

			}
		});
		doTaskGetNews();
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		initWithContent(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
				ARG_SECTION_NUMBER));
	}
	
	
	private void doTaskGetNews(){
		try {
			int index = getArguments().getInt(
					ARG_SECTION_NUMBER);
			HashMap<String, String> urlParams = new HashMap<String, String>();
			urlParams.put("type", String.valueOf(index));
			doTaskAsync(C.task.news, C.api.news, urlParams);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		// TODO Auto-generated method stub
		super.onTaskComplete(taskId, message);
		switch(taskId){
		case C.task.news:
			if (!message.isSuccess())
				return;
			try {
				listNewsItems.clear();
				listNewsItems.addAll((ArrayList<News>) message.getResultList("News"));
				mAdapter.notifyDataSetChanged();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		}
	}

	@Override
	public void onNetworkError(int taskId) {
		// TODO Auto-generated method stub
		super.onNetworkError(taskId);
		Toast.makeText(this.baseUi, C.err.network, Toast.LENGTH_SHORT).show();
	}
	
}
