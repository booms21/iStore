package com.adsld.istore;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PayActivity extends Activity {
	TextView zf, zf1, ye, tt;
	LinearLayout ly;
	SQLiteDatabase db;
	String TAG = PayActivity.class.getSimpleName(), id, price, gid;
	float money, zj;
	int shul, sl;
	int[] count;
	String[] aid;
	float[] aprice;
	AlertDialog alert;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay);
		zf = (TextView) findViewById(R.id.tv_title2);
		zf1 = (TextView) findViewById(R.id.tv_title6);
		ye = (TextView) findViewById(R.id.tv_title4);
		tt = (TextView) findViewById(R.id.tv_title);
		tt.setText("账户");

		Intent getIntent = getIntent();
		id = getIntent.getStringExtra("id");
		price = getIntent.getStringExtra("price");
		shul = getIntent.getIntExtra("Quantity", 0);
		gid = getIntent.getStringExtra("gid");

		aid = getIntent.getStringArrayExtra("aid");
		aprice = getIntent.getFloatArrayExtra("aprice");
		count = getIntent.getIntArrayExtra("count");

		zf.setText(price + "元");
		zf1.setText("余额支付:" + price + "元");
		ly = (LinearLayout) findViewById(R.id.linearLayout3);
		db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().toString() + "/data.db3", null);
		Log.e(TAG, "sqlite数据库打开..");

		Cursor cursor3 = db.rawQuery("select money from user where _id=?", new String[] { id });
		while (cursor3.moveToNext()) {

			money = cursor3.getFloat(cursor3.getColumnIndex("money"));

			// do something useful with these
			// cursor.moveToNext();
		}
		ye.setText("可用余额：" + money + "元");
		ly.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alert = new AlertDialog.Builder(PayActivity.this).create();
				alert.setTitle("操作提示");
				alert.setMessage("总计:" + price + "元");
				alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				});
				alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						float price1 = Float.parseFloat(price);

						if (money < price1) {
							Toast.makeText(PayActivity.this, "抱歉，余额不足 请联系管理员进行充值", Toast.LENGTH_SHORT).show();

							finish();
						} else {

							money -= price1;
						}

						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if (aid != null) {

							for (int k = 0; k < aid.length; k++) {

								try {

									Cursor cursor2 = db.rawQuery(
											"select Quantity,odprice from orders where uid=? and gid=?",
											new String[] { id, aid[k] });

									while (cursor2.moveToNext()) {

										sl = cursor2.getInt(cursor2.getColumnIndex("Quantity"));
										zj = cursor2.getFloat(cursor2.getColumnIndex("odprice"));
										// do something useful with these
										// cursor.moveToNext();
									}
									if (cursor2.getCount() >= 1) {

										ContentValues values = new ContentValues();
										values.put("ordertime", df.format(new Date()));
										values.put("Quantity", sl += count[k]);
										values.put("odprice", zj += aprice[k]);

										int result = db.update("orders", values, "uid=?  and gid=?",
												new String[] { id, aid[k] + "" });

										if (result <= 0) {
											Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_LONG).show();

										}

									} else {

										db.execSQL("insert into orders values(null,?,?,?,?,?)",
												new String[] { aid[k] + "", df.format(new Date()), count[k] + "",
														aprice[k] + "", id });

									}
									// Toast.makeText(getActivity(),
									// count[0]+"----------"+aprice[0],
									// Toast.LENGTH_LONG).show();
								}

								catch (SQLiteException se) {

									Log.e(TAG, "添加错误:" + se);

								}
							}
						} else {

							try {

								Cursor cursor1 = db.rawQuery(
										"select Quantity,odprice from orders where uid=? and gid=?",
										new String[] { id, gid });

								while (cursor1.moveToNext()) {

									sl = cursor1.getInt(cursor1.getColumnIndex("Quantity"));
									zj = cursor1.getFloat(cursor1.getColumnIndex("odprice"));
									// do something useful with these
									// cursor.moveToNext();
								}
								if (cursor1.getCount() >= 1) {

									ContentValues values = new ContentValues();
									values.put("ordertime", df.format(new Date()));
									values.put("Quantity", sl += shul);
									values.put("odprice", zj += price1);

									int result = db.update("orders", values, "uid=?  and gid=?",
											new String[] { id, gid });

									if (result <= 0) {
										Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_LONG).show();

									}

								} else {

									db.execSQL("insert into orders values(null,?,?,?,?,?)",
											new String[] { gid, df.format(new Date()), shul + "", price, id });

								}

							} catch (SQLiteException se) {

								Log.e(TAG, "添加错误:" + se);

							}

						}

						ContentValues values = new ContentValues();

						values.put("money", money);

						String[] id1 = new String[1];
						id1[0] = id;

						int result = db.update("user", values, "_id=?", id1);

						if (result <= 0) {
							Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_LONG).show();

						} else

							Toast.makeText(PayActivity.this, "支付成功,我们会在24小时内对商品进行发货,在我的订单查看订单信息", Toast.LENGTH_LONG)
									.show();
						finish();

					}
				});
				alert.show();
			}
		});

	}
}
