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
 * 首页Fragment
 */
public class HomeFragment extends BaseFragment {

	private final static String TAG = HomeFragment.class.getSimpleName();

	int[] imgp = { R.drawable.h5,R.drawable.h2,R.drawable.h6, R.drawable.h1, R.drawable.h3 }; // 定义图片数据
	String[] id = { "x", "8", "7","5","6" };

	ArrayList<HashMap<String, Object>> list;
	HashMap<String, Object> map;

	private TextView textView;
	ListView lv;
	ImageView iv;
    Intent	 i;
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
			@Nullable Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_home, container, false);
		 i= new Intent(getActivity(),DetailActivity.class);
		iv = (ImageView) view.findViewById(R.id.search);
		lv = (ListView) view.findViewById(R.id.listView1);
		initdata(imgp, id);
		setSimple();
		
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(getActivity(), item_search.class);
				startActivity(i);

			}
		});
		return view;

	}

	public void initdata(int[] imgp, String[] id) {
		list = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < imgp.length; i++) {

			map = new HashMap<String, Object>();

			map.put("imgp", imgp[i]);
			map.put("id", id[i]);
			list.add(map);
		}
	}

	public void setSimple() {
		SimpleAdapter adapter = new SimpleAdapter(mContext, list, R.layout.item, new String[] { "imgp" }, // 字符串数组，里面放参数名。
				new int[] { R.id.imageView13 });

		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				HashMap<String, String> map = (HashMap<String, String>) lv.getItemAtPosition(position);
		
				switch (map.get("id")) {
				case "x":

					i.putExtra("key", "iphonex");

					startActivity(i);
					break;
				case "8":

					i.putExtra("key", "iphone8");

					startActivity(i);
					break;
				case "7":

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