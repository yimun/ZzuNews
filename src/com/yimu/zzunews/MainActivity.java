package com.yimu.zzunews;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.yimu.base.BaseMessage;
import com.yimu.base.BaseUi;
import com.yimu.base.C;
import com.yimu.zzunews.model.NewsList;

public class MainActivity extends BaseUi implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	ArrayList<NewsList> listNewsList = new ArrayList<NewsList>();
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mNavigationDrawerFragment.initWithContent(this);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
		doTaskGetNewsList();
		
		
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position)).commit();
	}

	public void onSectionAttached(int number) {
		if(number < listNewsList.size())
			mTitle = listNewsList.get(number).getName();
		
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	protected void doTaskGetNewsList() {
		try {
			this.doTaskAsync(C.task.newslist, C.api.newslist, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	
	}

	@Override
	public void onTaskComplete(int taskId, BaseMessage message) {
		// TODO Auto-generated method stub
		debugMemory("task complete");
		super.onTaskComplete(taskId, message);
		switch(taskId){
		case C.task.newslist:
			if (!message.isSuccess())
				return;
			try {
				listNewsList.clear();
//				myAdapter.notifyDataSetChanged();
				listNewsList = (ArrayList<NewsList>) message.getResultList("NewsList");
				mNavigationDrawerFragment.updateDrawer(listNewsList);
				onNavigationDrawerItemSelected(0);
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
		Toast.makeText(this, C.err.network, Toast.LENGTH_SHORT).show();
	}
	
	

}
