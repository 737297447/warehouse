/**
 * 
 */
package com.lingdian.ui.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lingdian.ui.main.fragment.JiBenxinxiFragment;
import com.lingdian.ui.main.fragment.AboutFragment;
import com.lingdian.ui.main.fragment.SaleFragment;
import com.lingdian.ui.main.fragment.UserFragment;
import com.lingdian.ui.main.fragment.SaleTongjiFragment;
import com.lingdian.ui.main.viewpager.IconPagerAdapter;


public class MianPagerAdapter extends FragmentStatePagerAdapter implements IconPagerAdapter{
    String [] type;
    int [] icon;
    private int mCount;
    public MianPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public MianPagerAdapter(FragmentManager fragmentManager,String[] type,int [] icon) {
        super(fragmentManager);
        this.type=type;
        this.icon = icon;
        mCount = type.length;
    }

    @Override
    public Fragment getItem(int position) {
    	if (position==0) {
    		return new JiBenxinxiFragment();
		}else if(position==1){
			return new SaleTongjiFragment();
		}else if(position==2){
			return new UserFragment();
		}else if(position==3){
			return new SaleFragment();
		}else{
			return new AboutFragment();
		}
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return type[position % mCount];
    }
    
	@Override
	public int getIconResId(int index) {
		// TODO Auto-generated method stub
		return icon[index];
	}

}