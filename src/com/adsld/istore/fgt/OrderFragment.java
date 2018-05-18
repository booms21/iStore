package com.adsld.istore.fgt;

import com.adsld.istore.DetailActivity;
import com.adsld.istore.FgtActivity;
import com.adsld.istore.LoginActivity;
import com.adsld.istore.R;
import com.adsld.istore.item_search;

import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.spec.IvParameterSpec;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

/**
 * 订单Fragment
 */
public class OrderFragment extends BaseFragment {

	private final static String TAG = OrderFragment.class.getSimpleName();

	int[] imgp = { R.drawable.h5,R.drawable.h2,R.drawable.h6, R.drawable.h1, R.drawable.h3 }; // 定义图片数据
	String[] id = { "x", "8", "7","5","6" };

	ArrayList<HashMap<String, Object>> list;
	HashMap<String, Object> map;

	private TextView textView;
	ListView lv;
	ImageView iv;
    SharedPreferences sp;
    Intent	 i;
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_order, container, false);
		 i= new Intent(getActivity(),DetailActivity.class);
		 TextView	tt=(TextView) view.findViewById(R.id.tv_title);
		  	tt.setText("我的订单");
		  	 sp = getActivity().getSharedPreferences("save",Context.MODE_PRIVATE);
		lv = (ListView) view.findViewById(R.id.listView1);
		initdata( );
		
		
	
		return view;

	}

	public void initdata() {
		
		   db = SQLiteDatabase.openOrCreateDatabase(getActivity().getFilesDir().toString()+"/data.db3", null);
   	  	Log.e(TAG, "sqlite数据库打开..");	
		Cursor cursor=db.rawQuery("select * from orders where uid=?",new String[]{sp.getString("id", "")});
		if(cursor.getCount()==0){
    	  	Log.e(TAG, "sqlite数据库为空");	
    	 	Toast.makeText(getActivity(), "当前没有任何订单", Toast.LENGTH_SHORT).show();
    		
    	}else{
    		
    		list = new ArrayList<HashMap<String, Object>>();
    	     cursor.moveToFirst(); 
 		    while (!cursor.isAfterLast()) { 
 		   

   			map = new HashMap<String, Object>();
   		 Cursor cursor1=db.rawQuery("select name from goods where _id=?",new String[]{cursor.getInt(cursor.getColumnIndex("gid"))+""});
     	
		 while (cursor1.moveToNext()) { 
 		      
	
			 map.put("name",cursor1.getString(cursor1.getColumnIndex("name")));
     	      // do something useful with these 
     	     // cursor.moveToNext(); 
     	    }
		 
   			map.put("gid",cursor.getInt(cursor.getColumnIndex("gid")) );
			map.put("ordertime","时间： "+cursor.getString(cursor.getColumnIndex("ordertime")) );
			map.put("Quantity",cursor.getInt(cursor.getColumnIndex("Quantity")) );
			map.put("odprice","￥ "+cursor.getFloat(cursor.getColumnIndex("odprice")) );
			list.add(map);

 		        cursor.moveToNext(); 
 		      }
 		   setSimple();
    	}
		
		
	/*	for (int i = 0; i < imgp.length; i++) {

			map = new HashMap<String, Object>();

			map.put("imgp", imgp[i]);
			map.put("id", id[i]);
			list.add(map);
		}*/
	}

	public void setSimple() {
		SimpleAdapter adapter = new SimpleAdapter(mContext, list, R.layout.order_item, new String[] { "name","Quantity","odprice","ordertime" }, // 字符串数组，里面放参数名。
				new int[] { R.id.tv_n,R.id.tv_sl,R.id.tv_p,R.id.tv_date });

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				HashMap<String, String> map = (HashMap<String, String>) lv.getItemAtPosition(position);
			String name=map.get("name");
				switch (name) {
				case "iphonex":

					i.putExtra("key", "iphonex");

					startActivity(i);
					break;
				case "iphone8":

					i.putExtra("key", "iphone8");

					startActivity(i);
					break;
				case "iphone7":

					i.putExtra("key", "iphone7");

					startActivity(i);
					break;
				default:

					break;
				}
			}
		});
	}

	@Override
	public View initView() {

		return textView;
	}

	@Override
	public void initData() {
		super.initData();

		Log.e(TAG, "主页面的Fragment的数据被初始化了");
	}
}