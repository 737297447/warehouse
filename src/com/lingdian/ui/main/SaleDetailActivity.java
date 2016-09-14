package com.lingdian.ui.main;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lingdian.ui.BaseActivity;
import com.lingdian.ui.DialogDemo;
import com.lingdian.ui.SqlHelpdemo;
import com.lingdian.util.ui.dialog.ChooseRemindDialog;
import com.lingdian.warehouse.R;
import com.zxing.activity.CaptureActivity;

public class SaleDetailActivity extends BaseActivity {

	private LinearLayout detail_linear_layout_maihuo;
	private TextView all_prise;
	private SqlHelpdemo db;
	private SQLiteDatabase sDatabase = null;
	private String huowuName;
	private String huowuPrise;
	private String huowuNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sale);
		initView();
	}

	public void initView() {
		db = new SqlHelpdemo(getApplicationContext(), "store.db", null, 1);
		sDatabase = db.getWritableDatabase();
		all_prise = (TextView) findViewById(R.id.all_prise);
		detail_linear_layout_maihuo = (LinearLayout) findViewById(R.id.detail_linear_layout_maihuo);
		title_back_layout = (LinearLayout) findViewById(R.id.title_back_layout);
		title_back_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		title_name = (TextView) findViewById(R.id.title_name);
		title_name.setText("商品销售");
	}

	/** 卖货 */
	public void maihuo(View v) {
		Intent openCameraIntent = new Intent(SaleDetailActivity.this,
				CaptureActivity.class);
		startActivityForResult(openCameraIntent, 0);
	}

	/** 结账 */
	public void jiezhang(View v) {

		if (detail_linear_layout_maihuo.getChildCount() != 0) {
			getAllData(detail_linear_layout_maihuo);
			all_prise.setText("0");
			detail_linear_layout_maihuo.removeAllViews();
		} else {
			Toast.makeText(SaleDetailActivity.this,
					"当前扫码单中还没有扫描货物，请点击“扫码卖货”进行扫码！", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 处理扫描结果（在界面上显示）
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			String ss[];
			if (!TextUtils.isEmpty(scanResult)) {

				System.out.println(scanResult.substring(0,2));
				if (!scanResult.substring(0,2).equals("$$")) {
					getErweima(scanResult);
				} else {
					ss = scanResult.split(",");
					if(ss.length == 4){
						getErweima(ss[2]);
					}
				}
			}
		}
	}

	/** 根据扫描回来的条码进行查询货物 */
	public void getErweima(String tiaoma) {
		String selectStr = "select * from chuku where guige like ?";
		Cursor cursor = sDatabase.rawQuery(selectStr, new String[] { tiaoma });
		int count = cursor.getCount();
		if (count != 0) {
			if (cursor.moveToFirst()) {
				huowuName = cursor.getString(4);
				huowuPrise = cursor.getString(7);
				huowuNum = cursor.getString(8);
			}
			cursor.close();

			if (!TextUtils.isEmpty(huowuName)) {
				// 第一次添加货物页面没有数据
				if (detail_linear_layout_maihuo.getChildCount() == 0) {
					addField(huowuName, huowuPrise);
				} else {
					checkName(detail_linear_layout_maihuo, huowuName,
							huowuPrise);
				}
				int allPrise = getPrise(detail_linear_layout_maihuo);
				all_prise.setText(allPrise + "");
			}
		} else {
			DialogDemo.builder(SaleDetailActivity.this, "提示",
					"该货物还没有出库，请选择“添加出库”按钮选择出库，才能销售");
		}
	}

	/** 添加字段 */
	private void addField(String keyName, String valueName) {
		final View view = getLayoutInflater().inflate(
				R.layout.activity_maihuo_details_layout, null);
		final ViewHolder holder = new ViewHolder();
		holder.detail_layout_key = (TextView) view
				.findViewById(R.id.detail_layout_key);
		holder.detail_layout_valye = (TextView) view
				.findViewById(R.id.detail_layout_valye);
		holder.detail_layout_close = (Button) view
				.findViewById(R.id.detail_layout_close);
		holder.detail_layout_num = (TextView) view
				.findViewById(R.id.detail_layout_num);

		detail_linear_layout_maihuo.addView(view);
		view.setTag(holder);

		holder.detail_layout_key.setText(keyName);
		holder.detail_layout_valye.setText(valueName);
		holder.detail_layout_num.setText("1");

		holder.detail_layout_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String nowNum = holder.detail_layout_num.getText().toString();
				if (!nowNum.equals("1")) {
					int iNowNum = Integer.valueOf(nowNum);
					--iNowNum;
					holder.detail_layout_num.setText(iNowNum + "");
					int allPrise = getPrise(detail_linear_layout_maihuo);
					all_prise.setText(allPrise + "");
					Toast.makeText(SaleDetailActivity.this,
							"( ͡° ͜ʖ ͡°) 当前货物数量 -1 ！", Toast.LENGTH_SHORT)
							.show();
				} else {
					// 删除状态
					ChooseRemindDialog rmdDlg = new ChooseRemindDialog(
							SaleDetailActivity.this,
							R.style.oa_MyDialogStyleTop, "请确认", "确定要删除此货物么",
							"删除", "取消") {
						@Override
						public void doBtn1Click() {
							this.dismiss();

							detail_linear_layout_maihuo.removeView(view);

							int allPrise = getPrise(detail_linear_layout_maihuo);
							all_prise.setText(allPrise + "");
						}

						@Override
						public void doBtn2Click() {
							this.dismiss();
						}
					};
					rmdDlg.show();
				}
			}
		});

	}

	public void updataNumView(ViewHolder holder) {
		String num = holder.detail_layout_num.getText().toString();
		int i = Integer.valueOf(num);
		++i;
		holder.detail_layout_num.setText(i + "");
	}

	class ViewHolder {
		public TextView detail_layout_key;
		public TextView detail_layout_valye;
		public TextView detail_layout_num;
		public Button detail_layout_close;
	}

	private int getPrise(LinearLayout linearLayout) {
		int onePrise = 0;
		int prise = 0;
		for (int i = 0; i < linearLayout.getChildCount(); i++) {
			View view = linearLayout.getChildAt(i);
			ViewHolder holder = (ViewHolder) view.getTag();
			if (holder != null) {
				String sPrise = holder.detail_layout_valye.getText().toString()
						.trim();
				String sNum = holder.detail_layout_num.getText().toString()
						.trim();
				if (!TextUtils.isEmpty(sPrise)) {
					int iPrise = Integer.valueOf(sPrise);
					int iNum = Integer.valueOf(sNum);
					onePrise = iPrise * iNum;
					prise += onePrise;
				}
			}
		}
		return prise;
	}

	/** 检测第二次以后添加的货物在页面上是否有数据，如果有，在后面数据+1，如果没有，新增一条新货物 */
	private void checkName(LinearLayout linearLayout, String name, String prise) {
		boolean isAdd = false;
		for (int i = 0; i < linearLayout.getChildCount(); i++) {
			View view = linearLayout.getChildAt(i);
			ViewHolder holder = (ViewHolder) view.getTag();
			String lName = holder.detail_layout_key.getText().toString();
			if (lName.equals(name)) {
				if (holder != null) {
					updataNumView(holder);
				}
				// 当判断第二次扫的书本名称在之前已经有了，直接跳出，不再继续循环
				return;
			} else {
				isAdd = true;
			}
		}
		if (isAdd) {
			addField(name, prise);
		}

	}

	private void getAllData(LinearLayout linearLayout) {

		for (int i = 0; i < linearLayout.getChildCount(); i++) {
			View view = linearLayout.getChildAt(i);
			ViewHolder holder = (ViewHolder) view.getTag();
			if (holder != null) {
				String sName = holder.detail_layout_key.getText().toString()
						.trim();
				String sPrise = holder.detail_layout_valye.getText().toString()
						.trim();
				String sNum = holder.detail_layout_num.getText().toString()
						.trim();
				int prise = Integer.valueOf(sPrise);
				int num = Integer.valueOf(sNum);
				int allPrise = prise * num;

				deleteChukuData(sName, allPrise + "", sNum);

			}
		}

	}

	/** 插入到商品销售的表中 */
	private void insertSale(String sName, String sPrise, String sNum) {
		String names[];
		String prises[];
		String nums[];
		int i = 0;
		String sqlSelect = "select spname,spprise,spnum from sale";
		Cursor cursor = sDatabase.rawQuery(sqlSelect, null);
		cursor.moveToFirst();
		int count = cursor.getCount();
		if (count != 0) {
			names = new String[count];
			prises = new String[count];
			nums = new String[count];
			do {
				names[i] = cursor.getString(0);
				prises[i] = cursor.getString(1);
				nums[i] = cursor.getString(2);
				i++;
			} while (cursor.moveToNext());
			cursor.close();

			boolean updataOrInsert = false;

			for (int j = 0; j < names.length; j++) {
				if (sName.equals(names[j])) {
					int oldPrise = Integer.valueOf(prises[j]);
					int nowPrise = oldPrise + Integer.valueOf(sPrise);
					System.out.println("oldPrise  " + oldPrise + "sPrise"
							+ sPrise + "=" + "nowPrise" + nowPrise);
					int oldNum = Integer.valueOf(nums[j]);
					int nowNum = oldNum + Integer.valueOf(sNum);
					sDatabase.execSQL("update sale set spprise='" + nowPrise
							+ "',spnum='" + nowNum + "' where spname ='"
							+ sName + "'");
					return;
				} else {
					updataOrInsert = true;
				}
			}

			if (updataOrInsert) {
				int id = 0;
				String select = "select max(_id) from sale";
				Cursor seCursor = sDatabase.rawQuery(select, null);
				try {
					seCursor.moveToFirst();
					id = Integer.parseInt(seCursor.getString(0));
					id += 1;
				} catch (Exception e) {
					// TODO: handle exception
					id = 0;
				}
				sDatabase.execSQL("insert into sale values('" + id + "','"
						+ sName + "','" + sPrise + "','" + sNum + "')");
				seCursor.close();
			}

		} else {
			int firstId = 0;
			sDatabase.execSQL("insert into sale values('" + firstId + "','"
					+ sName + "','" + sPrise + "','" + sNum + "')");
			return;
		}

	}

	/** 当卖货完成时，从出库数据库将卖出的货物数量删除 */
	public void deleteChukuData(String sName, String sPrise, String sNum) {

		String nowChukushu = null;
		String selectStr4 = "select num from chuku where products='" + sName
				+ "'";
		Cursor cursor4 = sDatabase.rawQuery(selectStr4, null);
		do {
			try {
				nowChukushu = cursor4.getString(0);

			} catch (Exception e) {
				// TODO: handle exception
			}
		} while (cursor4.moveToNext());

		int chukuInt = Integer.valueOf(nowChukushu);// 仓库中数量
		int iNum = Integer.valueOf(sNum);// 卖出的数量
		if (chukuInt < iNum) {
			DialogDemo
					.builder(SaleDetailActivity.this, "提示",
							"仓库中没有这么多货物，交易不成功，请不要将一个商品扫码两次！！如果操作正确，请点击“查询出库”查看库存数量，是否出库正确");
		} else {
			int shengyuInt = chukuInt - iNum;// 剩余多少
			sDatabase.execSQL("update chuku set num='" + shengyuInt
					+ "' where products ='" + sName + "'");

			insertSale(sName, sPrise, sNum);
			String money = all_prise.getText().toString().trim();
			Toast.makeText(SaleDetailActivity.this, "结账成功，请收取" + money + "元钱",
					Toast.LENGTH_SHORT).show();
		}

	}

}
