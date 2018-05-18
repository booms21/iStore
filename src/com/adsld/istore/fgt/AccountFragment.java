package com.adsld.istore.fgt;


import com.adsld.istore.FgtActivity;
import com.adsld.istore.LoginActivity;
import com.adsld.istore.R;

import android.R.raw;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.opengl.Visibility;
/**
 * 首页Fragment
 */
public class AccountFragment extends BaseFragment {

    private final static String TAG = AccountFragment.class.getSimpleName();

    private TextView textView;
    SharedPreferences sp;
    TableRow zffs,shfs;
    TextView act_n,act_m,wlxx,tt;
    Button grsc,wdwl,tcdl,wddd;
    int flag=0;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.activity_account, container, false); 
    	initView();
    	act_n=(TextView) view.findViewById(R.id.account_name);
    	act_m=(TextView) view.findViewById(R.id.account_mail);
    	grsc= (Button) view.findViewById(R.id.grsc);

    	wdwl= (Button) view.findViewById(R.id.wdwl);
    	shfs=(TableRow) view.findViewById(R.id.shfs);
    	zffs=(TableRow) view.findViewById(R.id.zffs);
    	wlxx= (TextView) view.findViewById(R.id.wlxx);
    	tcdl=(Button) view.findViewById(R.id.tcdl);
    	tt=(TextView) view.findViewById(R.id.tv_title);
    	wddd=(Button) view.findViewById(R.id.wddd);
    	tt.setText("账户");
        sp = getActivity().getSharedPreferences("save",Context.MODE_PRIVATE);
        
        
        
         if(sp.getInt("state", 2)==1){//在登录状态
        	 act_n.setText(sp.getString("username", "null"));
        	 act_m.setEnabled(false);
        	 flag=1;
        	 act_m.setText(sp.getString("tel", "null"));
        	  
        		tcdl.setVisibility(View.VISIBLE);
        	 
         }else{
        	  act_m.setText("登录以查看更多...");
        	 
         }
          
    
         wddd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
								   if(sp.getInt("state", 2)==1){//登录状态  
						  
									    FragmentManager fm = getFragmentManager();  
			        			        FragmentTransaction tx = fm.beginTransaction();  
			        			        OrderFragment fThree1 = new OrderFragment();  
			        			        tx.hide(AccountFragment.this);
			        			        tx.add(R.id.frameLayout , fThree1);  
//			        			      tx.replace(R.id.id_content, fThree, "THREE");  
			        			        tx.addToBackStack(null);  
			        			        tx.commit(); 		   
									   
			       }else{
			      	Intent intent =new Intent(mContext,LoginActivity.class);//跳转到登录
		    		startActivity(intent);
			       }
							}
		});
         tcdl.setOnClickListener(new OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				// TODO Auto-generated method stub
 				 new AlertDialog.Builder(getActivity())
 				  .setTitle("退出登录")  
 				  
 			      .setMessage("您确定要退出登录吗？")  
                  .setNegativeButton("取消", null)
 			      .setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						  SharedPreferences.Editor spe = sp.edit();
	        	    	    spe.putInt("state",0);
	        	    	    
	        	    	   spe.commit();
	        	    	 
	        			        FragmentManager fm = getFragmentManager();  
	        			        FragmentTransaction tx = fm.beginTransaction();  
	        			        tx.remove(AccountFragment.this);
	        			        tx.add(R.id.frameLayout , AccountFragment.this);  
//	        			      tx.replace(R.id.id_content, fThree, "THREE");  
	        			        tx.addToBackStack(null);  
	        			        tx.commit(); 
	        			        
	        	    	   Toast.makeText(getActivity(), "已退出登陆", Toast.LENGTH_SHORT).show();
					}
				}).show(); 
              
 			      
 				
               
 			}
 		});
         zffs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 new AlertDialog.Builder(getActivity())
				  .setTitle("我的支付方式")  
				  
			      .setMessage("请联系管理员充值余额")  

			      .setPositiveButton("知道了", null)  

			      .show(); 
				
              
			}
		});
         shfs.setOnClickListener(new OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				// TODO Auto-generated method stub
 				 AdressFragment fThree = new AdressFragment();  
 		        FragmentManager fm = getFragmentManager();  
 		       FragmentTransaction  tx = fm.beginTransaction();  
 		        tx.hide(AccountFragment.this);
 		        tx.add(R.id.frameLayout , fThree);  
// 		      tx.replace(R.id.id_content, fThree, "THREE");  
 		        tx.addToBackStack(null);  
 		        tx.commit(); 
 		       
 			}
 		});
    	wlxx.setText("暂无物流信息");
    	grsc.setOnClickListener(new OnClickListener() {
    		@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
    	     
			       if(flag==1){//登录状态  
			    	   Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT).show();
			       }else{
			      	Intent intent =new Intent(mContext,LoginActivity.class);//跳转到登录
		    		startActivity(intent);
			       }}
		});
    	

      	wdwl.setOnClickListener(new OnClickListener() {
    		@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			       Log.e(TAG,"grsc");
			       if(flag==1){//登录状态  
			  		 AdressFragment fThree = new AdressFragment();  
		 		        FragmentManager fm = getFragmentManager();  
		 		       FragmentTransaction  tx = fm.beginTransaction();  
		 		        tx.hide(AccountFragment.this);
		 		        tx.add(R.id.frameLayout , fThree);  
//		 		      tx.replace(R.id.id_content, fThree, "THREE");  
		 		        tx.addToBackStack(null);  
		 		        tx.commit(); 
			       }else{
			      	Intent intent =new Intent(mContext,LoginActivity.class);//跳转到登录
		    		startActivity(intent);
			       }}
			
		});
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	act_m.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			       Log.e(TAG,"登录了");
			   	Intent intent =new Intent(mContext,LoginActivity.class);
	    		startActivity(intent);
			}
		});
    	TextView tv=(TextView) view.findViewById(R.id.tv_title);
    	tv.setText("账户");
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