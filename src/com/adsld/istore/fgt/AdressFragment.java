package com.adsld.istore.fgt;

import com.adsld.istore.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdressFragment extends BaseFragment{
	   private final static String TAG = BuyFragment.class.getSimpleName();
	//private LinearLayout ll_point;
	  private TextView textView,tt;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.activity_dz, container, false);
    	tt=(TextView) view.findViewById(R.id.tv_title);
    	tt.setText("账户");
    	initView();
    	
    	return view; 
	
    }
	
    @Override
    public View initView() {
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        Log.e(TAG,"页面的Fragment的UI被初始化了");
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("账户");
        Log.e(TAG,"页面的Fragment的数据被初始化了");
    }
}
