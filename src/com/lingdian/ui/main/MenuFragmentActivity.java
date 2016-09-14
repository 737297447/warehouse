package com.lingdian.ui.main;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.lingdian.ui.main.adapter.MianPagerAdapter;
import com.lingdian.ui.main.viewpager.ParentViewPager;
import com.lingdian.ui.main.viewpager.TabPageIndicator;
import com.lingdian.warehouse.R;

public class MenuFragmentActivity extends FragmentActivity {

	protected static final String[] type = new String[] { "首页", 
			"统计", "用户", "售货","关于", }; // 对应于每个大Fragment的小Fragment
	protected static final int[] icon = new int[] { R.drawable.selector_shouye, R.drawable.selector_tongji,
		R.drawable.selector_yonghu, R.drawable.selector_maihuo, R.drawable.selector_about }; // 对应于每个大Fragment的小Fragment
	private MianPagerAdapter mAdapter;
	private ParentViewPager mPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main_layout);
		mAdapter = new MianPagerAdapter(getSupportFragmentManager(), type, icon);
		mPager = (ParentViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		mAdapter.notifyDataSetChanged();
		TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(mPager);
		indicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				if (arg0 == 0) {

				} else if (arg0 == 1) {

				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	private long mExitTime;

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Object mHelperUtils;
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();

			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}