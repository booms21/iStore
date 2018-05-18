package com.adsld.istore.fgt;


import com.adsld.istore.R;
import com.adsld.istore.item_search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 购买Fragment
 */
public class BuyFragment extends BaseFragment {

    private final static String TAG = BuyFragment.class.getSimpleName();

    private TextView textView,tt;
    FragmentTransaction tx;
    ImageView mac,iphone,ipad,other,iv;
    @Override
   
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.activity_select, container, false); 
    	initView();
    	mac =(ImageView) view.findViewById(R.id.img_Mac);
    	iphone =(ImageView) view.findViewById(R.id.img_iPhone);
    	ipad =(ImageView) view.findViewById(R.id.img_iPad);
    	other =(ImageView) view.findViewById(R.id.img_other);
   	 iv=(ImageView) view.findViewById(R.id.search);
   	tt=(TextView) view.findViewById(R.id.tv_title);
	tt.setText("选购");
    	
    	iv.setOnClickListener(new OnClickListener() {
    		
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			 Intent i = new Intent(getActivity(),item_search.class);
    	         startActivity(i);
    		
    		}
    	});
    	iphone.setOnClickListener(new View.OnClickListener() {
		@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			 Log.e(TAG,"进入了选择产品类型");
			 GoodsFragment fThree = new GoodsFragment();  
		        FragmentManager fm = getFragmentManager();  
		         tx = fm.beginTransaction();  
		        tx.hide(BuyFragment.this);
		        tx.add(R.id.frameLayout , fThree);  
//		      tx.replace(R.id.id_content, fThree, "THREE");  
		        tx.addToBackStack(null);  
		        tx.commit(); 
			//  inflater.inflate(R.layout.activity_select_iphone, container,false);			 

		
		}
		});
    	mac.setOnClickListener(new View.OnClickListener() {
    		@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    			 Toast.makeText(getActivity(), "抱歉，现只开放iPhone的购买", Toast.LENGTH_SHORT).show();
    			}
    		});
    	ipad.setOnClickListener(new View.OnClickListener() {
    		@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    			 Toast.makeText(getActivity(), "抱歉，现只开放iPhone的购买", Toast.LENGTH_SHORT).show();
    			}
    		});
    	other.setOnClickListener(new View.OnClickListener() {
    		@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    			 Toast.makeText(getActivity(), "抱歉，现只开放iPhone的购买", Toast.LENGTH_SHORT).show();
    			}
    		});
    	
    	 return view; 
    }
    
   
    
   

    
  
    
    
    @Override
    public View initView() {
    	
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        Log.e(TAG,"主页面的Fragment的UI被初始化了");
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText("buy");
        Log.e(TAG,"主页面的Fragment的数据被初始化了");
    }
}