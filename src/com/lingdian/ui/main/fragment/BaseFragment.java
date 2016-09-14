package com.lingdian.ui.main.fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingdian.ui.ChaxunKehuAvtivity;
import com.lingdian.ui.Shanchuk;
import com.lingdian.ui.Tianjiak;
import com.lingdian.ui.Xiugai;
import com.lingdian.ui.Xiugaik;
import com.lingdian.ui.Yonghu;
import com.lingdian.ui.main.SaleDetailActivity;
import com.lingdian.ui.main.tools.RadialMenuWidget;
import com.lingdian.ui.main.tools.RadialMenuWidget.RadialMenuEntry;
import com.lingdian.ui.sale.ErweimaActivity;
import com.lingdian.ui.sale.SaleSearchActivity;
import com.lingdian.ui.tongji.KucunTongjiActivity;
import com.lingdian.warehouse.R;

public class BaseFragment extends Fragment {

	protected RadialMenuWidget PieMenu;
	protected LinearLayout ll;
	static Context mContext;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext = getActivity();
		ll = new LinearLayout(getActivity());
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		this.getActivity().addContentView(ll, params);
	}

	
	/**
	 * dip 转px px 转dip
	 * 
	 * @return
	 */
	public static int dip2px(Context context, float f) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (f * scale + 0.5f);
	}

	/*
	 * 获取控件高
	 */
	public static int getHeight(View view) {
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		return (view.getMeasuredHeight());
	}

	/*
	 * 获取控件宽
	 */
	public static int getWidth(View view) {
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		return (view.getMeasuredWidth());
	}

	public class Close implements RadialMenuEntry {

		LinearLayout add_shangpin;
		TextView text_details;
		public Close(LinearLayout add_shangpin,TextView text_details){
			this.add_shangpin = add_shangpin;
			this.text_details = text_details;
		}
		
		public String getName() {
			return "Close";
		}

		public String getLabel() {
			return null;
		}

		public int getIcon() {
			return android.R.drawable.ic_menu_close_clear_cancel;
		}

		public List<RadialMenuEntry> getChildren() {
			return null;
		}

		public void menuActiviated() {

			// Need to figure out how to to the layout.removeView(PieMenu)
			// ll.removeView(PieMenu);
			((LinearLayout) PieMenu.getParent()).removeView(PieMenu);
			add_shangpin.setVisibility(View.VISIBLE);
			if(text_details != null){
				text_details.setText("");
			}
			
		}
	}

	/** 没有子菜单的菜单*/
	public static class Menu1 implements RadialMenuEntry {
		String name;
		String lable;
		public Menu1(String name,String lable){
			this.name = name;
			this.lable = lable;
		}
		public String getName() {
			return name;
		}

		public String getLabel() {
			return lable;
		}

		public int getIcon() {
			return 0;
		}

		public List<RadialMenuEntry> getChildren() {
			return null;
		}

		public void menuActiviated() {
			 final Intent intent = new Intent();
			 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
			
			if(name.equals("5")){
				 intent.setClass(mContext, Yonghu.class);
			}else if(name.equals("6")){
				intent.setClass(mContext, Xiugai.class);
			}else if(name.equals("7")){
				intent.setClass(mContext, Tianjiak.class);
			}else if(name.equals("8")){
				intent.setClass(mContext, ChaxunKehuAvtivity.class);
			}else if(name.equals("9")){
				intent.setClass(mContext, Xiugaik.class);
			}else if(name.equals("10")){
				intent.setClass(mContext, Shanchuk.class);
			}else if(name.equals("11")){
				intent.setClass(mContext, SaleSearchActivity.class);
			}else if(name.equals("12")){
				intent.setClass(mContext, KucunTongjiActivity.class);
			}else if(name.equals("13")){
				intent.setClass(mContext, SaleDetailActivity.class);
			}else if(name.equals("14")){
				intent.setClass(mContext, ErweimaActivity.class);
			}
			new Handler().postDelayed(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					mContext.startActivity(intent);
				}
			}, 1);
		}
	}
	/** 三个子菜单的菜单*/
	public static class Menu2 implements RadialMenuEntry {
		public String getName() {
			return "Menu2 - Children";
		}

		public String getLabel() {
			return "Menu2";
		}

		public int getIcon() {
			return R.drawable.icon;
		}

		private List<RadialMenuEntry> children = new ArrayList<RadialMenuEntry>(
				Arrays.asList(new StringOnly(), new IconOnly(),
						new StringAndIcon()));

		public List<RadialMenuEntry> getChildren() {
			return children;
		}

		public void menuActiviated() {
			System.out.println("Menu #2 Activated - Children");
		}
	}

	/** 没有子菜单带图标的菜单*/
	public static class Menu3 implements RadialMenuEntry {
		public String getName() {
			return "Menu3 - No Children";
		}

		public String getLabel() {
			return null;
		}

		public int getIcon() {
			return R.drawable.icon;
		}

		public List<RadialMenuEntry> getChildren() {
			return null;
		}

		public void menuActiviated() {
			System.out.println("Menu #3 Activated - No Children");
		}
	}

	/** 只有图标没有子菜单的的菜单*/
	public static class IconOnly implements RadialMenuEntry {
		public String getName() {
			return "IconOnly";
		}

		public String getLabel() {
			return null;
		}

		public int getIcon() {
			return R.drawable.icon;
		}

		public List<RadialMenuEntry> getChildren() {
			return null;
		}

		public void menuActiviated() {
			System.out.println("IconOnly Menu Activated");
		}
	}

	/** 带文字和图片的菜单*/
	public static class StringAndIcon implements RadialMenuEntry {
		public String getName() {
			return "StringAndIcon";
		}

		public String getLabel() {
			return "String";
		}

		public int getIcon() {
			return R.drawable.icon;
		}

		public List<RadialMenuEntry> getChildren() {
			return null;
		}

		public void menuActiviated() {
			System.out.println("StringAndIcon Menu Activated");
		}
	}
	/** 只有文字的菜单*/
	public static class StringOnly implements RadialMenuEntry {
		public String getName() {
			return "StringOnly";
		}

		public String getLabel() {
			return "String\nOnly";
		}

		public int getIcon() {
			return 0;
		}

		public List<RadialMenuEntry> getChildren() {
			return null;
		}

		public void menuActiviated() {
			System.out.println("StringOnly Menu Activated");
		}
	}
	/** 两个子菜单的菜单*/
	  public static class NewTestMenu implements RadialMenuEntry
	   {
	      public String getName() { return "NewTestMenu"; } 
		  public String getLabel() { return "New\nTest\nMenu"; }
	      public int getIcon() { return 0; }
	      private List<RadialMenuEntry> children = new ArrayList<RadialMenuEntry>( Arrays.asList( new StringOnly(), new IconOnly() ) );
	      public List<RadialMenuEntry> getChildren() { return children; }
	      public void menuActiviated()
	      {
	         System.out.println( "New Test Menu Activated");
	      }
	   }
	/** 有四个子菜单的菜单*/
	public static class CircleOptions implements RadialMenuEntry {
		
		TextView textShow;
		public String name;
		public String lable;
		
		private List<RadialMenuEntry> children;
		public CircleOptions(TextView textShow,String name,String lable,String redName,String redLable,
				String yellowName,String yellowLable,String greenName,String greenLable
				,String blueName,String blueLable){
			this.name = name;
			this.lable = lable;
			this.textShow = textShow;
			
			RedCircle redCircle =new RedCircle(redName,redLable);
			YellowCircle yellowCircle = new YellowCircle(yellowName, yellowLable);
			GreenCircle greenCircle = new GreenCircle(greenName, greenLable);
			BlueCircle blueCircle = new BlueCircle(blueName, blueLable);
			children = new ArrayList<RadialMenuEntry>(
					Arrays.asList(redCircle, yellowCircle,
							greenCircle,blueCircle));
		}
		public String getName() {
			return name;
		}

		public String getLabel() {
			return lable;
		}

		public int getIcon() {
			return 0;
		}


		public List<RadialMenuEntry> getChildren() {
			return children;
		}

		public void menuActiviated() {
			if(name.equals("1")){
				textShow.setText("请添加进货渠道的供应商，以便区分不同商品的来源，为以后的统计提供方便，'供应商信息'菜单包括'添加供应商'、'查询供应商'、'修改供应商'、'删除供应商'四个子菜单");
			}else if(name.equals("2")){
				textShow.setText("添加商品信息之前必须先添加供应商，供应商是添加商品必填项，'商品信息'菜单包括'添加商品'、'查询商品'、'修改商品'、'删除商品'四个子菜单，'添加商品'时可以直接点击''进行扫码，不需要手动输入，如果没法扫上，可以用手动添加！");
			}else if(name.equals("3")){
				textShow.setText("点击'商品入库'之前必须在'商品信息'中添加商品，这样才能有相关的商品信息进行入库，入库操作是将您批发的货物以批发价入库，方便后面进行盈利统计，也分为四个子菜单：添加，查询，修改，删除");
			}else if(name.equals("4")){
				textShow.setText("点击'商品出库'之前商品必须入库操作，这样才能进行出库，添加出库的作用是将入库的信息出库，进行零售价设置，后面的'扫码卖货'中扫出来的商品就是从该库中查找，这是您的零售价，可以对外面开放查看");
			}
		}
	}

	public static class RedCircle implements RadialMenuEntry {
		public String redName;
		public String redLable;
		
		public RedCircle(String redName,String redLable){
			this.redName = redName;
			this.redLable = redLable;
		}
		
		public String getName() {
			return redName;
		}

		public String getLabel() {
			return redLable;
		}

		public int getIcon() {
			return R.drawable.red_circle;
		}

		public List<RadialMenuEntry> getChildren() {
			return null;
		}

		public void menuActiviated() {
			
		}
	}

	public static class YellowCircle implements RadialMenuEntry {
		
		public String yellowName;
		public String yellowLable;
		
		public YellowCircle(String yellowName,String yellowLable){
			this.yellowName = yellowName;
			this.yellowLable = yellowLable;
		}
		public String getName() {
			return yellowName;
		}

		public String getLabel() {
			return yellowLable;
		}

		public int getIcon() {
			return R.drawable.yellow_circle;
		}

		public List<RadialMenuEntry> getChildren() {
			return null;
		}

		public void menuActiviated() {
			System.out.println("Yellow Circle Activated");
		}
	}

	public static class GreenCircle implements RadialMenuEntry {
		
		public String greenName;
		public String greenLable;
		
		public GreenCircle(String greenName,String greenLable){
			this.greenName = greenName;
			this.greenLable = greenLable;
		}
		public String getName() {
			return greenName;
		}

		public String getLabel() {
			return greenLable;
		}

		public int getIcon() {
			return R.drawable.green_circle;
		}

		public List<RadialMenuEntry> getChildren() {
			return null;
		}

		public void menuActiviated() {
			System.out.println("Green Circle Activated");
		}
	}

	public static class BlueCircle implements RadialMenuEntry {
		
		public String blueName;
		public String blueLable;
		
		public BlueCircle(String blueName,String blueLable){
			this.blueName = blueName;
			this.blueLable = blueLable;
		}
		
		public String getName() {
			return blueName;
		}

		public String getLabel() {
			return blueLable;
		}

		public int getIcon() {
			return R.drawable.blue_circle;
		}

		public List<RadialMenuEntry> getChildren() {
			return null;
		}

		public void menuActiviated() {
			System.out.println("Blue Circle Activated");
		}
	}

}
