package com.lingdian.ui.main.viewpager;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

public class ParentViewPager extends ViewPager{
      public ParentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public ParentViewPager(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
       return super.canScroll(v, checkV, dx, x, y);
    }
}