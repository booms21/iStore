package com.adsld.istore;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import android.util.Log;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.adsld.istore.R;

import com.adsld.istore.fgt.BaseFragment;


public class DetailActivity extends Activity implements ViewPager.OnPageChangeListener {
	private ViewPager vp;

	//private LinearLayout ll_point;

	    private ViewPager vp_detail;
	    private int[] imageResIds; //存放图片资源id的数组
	    private ArrayList<ImageView> imageViews; //存放图片的集合
		SQLiteDatabase db;
	    String TAG = BaseFragment.class.getSimpleName(),id,spbag,money;
	    SharedPreferences sp;
	private Thread thread;
    FragmentTransaction tx;
    TextView itdc,price,tt;
    Button jr,xd;
    int flag=0;
   
    String key,priceo,gid;
    ImageView detl;
   
	@Override
	   protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        sp = getSharedPreferences("save",Context.MODE_PRIVATE);
	 flag=sp.getInt("state", 2);  //得到登录状态
	   id=sp.getString("id", ""); 
	   detl=(ImageView) findViewById(R.id.detl);
  	  db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().toString()+"/data.db3", null);
  	Log.e(TAG, "sqlite数据库打开..");	
       	 
  	tt=(TextView) findViewById(R.id.tv_title);
	tt.setText("选购");
        itdc= (TextView) findViewById(R.id.gd_title);
        price= (TextView) findViewById(R.id.gd_price);
        jr =(Button) findViewById(R.id.bt_add);
        xd =(Button) findViewById(R.id.bt_purchase);
  

        jr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				  if(flag==1){//登录状态  
					  ContentValues values =new ContentValues();
					   
					  

					    
					 	Cursor cursor3=db.rawQuery("select spbag from user where _id=?",new String[]{id});
					  	 while (cursor3.moveToNext()) { 
					      
			       		 	 spbag=cursor3.getString(cursor3.getColumnIndex("spbag"));
			       	  		
			       	      // do something useful with these 
			       	     // cursor.moveToNext(); 
			       	    }
					     
					 
					  	spbag+=gid;

					  	 
					  	 
					    values.put("spbag",ss(spbag)+",");
					  	 
					  	 
					  	 
					  	 
					     String[] id1 =new String[1];
					     id1[0]=sp.getString("id", "");
				
			        	int result=db.update("user", values, "_id=?", id1);
			        	
			        	if(result<=0){
			        		 Toast.makeText(DetailActivity.this, "加入购物袋失败，不存在该用户", Toast.LENGTH_LONG).show();
			        		
			        	}else		 
			        		Toast.makeText(DetailActivity.this, "已加入购物袋", Toast.LENGTH_SHORT).show();
					  
					  
			       }else{
			    	   
	
			      	Intent intent =new Intent(DetailActivity.this,LoginActivity.class);//跳转到登录
		    		startActivity(intent);
			       }
				
				
			}
		});
        
  xd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				  if(flag==1){//登录状态  
					

						// TODO Auto-generated method stub
						Intent i= new Intent(DetailActivity.this,PayActivity.class);
					
						 i.putExtra("id", id);  
						 i.putExtra("gid", gid); 
						 i.putExtra("Quantity", 1); 
						 i.putExtra("price", priceo); 
		                 startActivity(i);
					  
					  
			       }else{
			      	Intent intent =new Intent(DetailActivity.this,LoginActivity.class);//跳转到登录
		    		startActivity(intent);
			       }
				
				
			}
		});
  
     //使用M-V-C模型
        Intent getIntent = getIntent();  
       key=  getIntent.getStringExtra("key");
        
     

   
 
      
      
      
      
      
  	Cursor cursor=db.rawQuery("select _id,price,introduce,imgurl from goods where name=? ",new String[]{key});
      	
   	if(cursor.getCount()==0){
   	  	Log.e(TAG, "sqlite数据库为空");	
   	  	Toast.makeText(this, "没有任何产品", Toast.LENGTH_SHORT).show();
   		
   	}else{
   	



while (cursor.moveToNext()) { 
	    
	  
  	Log.e(TAG,"--------------");
  	price.setText("￥"+cursor.getString(cursor.getColumnIndex("price")));
  	money=cursor.getString(cursor.getColumnIndex("price"));
  	itdc.setText("￥"+cursor.getString(cursor.getColumnIndex("introduce")));
     gid=cursor.getString(cursor.getColumnIndex("_id"));
     priceo=cursor.getString(cursor.getColumnIndex("price"));	
      // do something useful with these 
     // cursor.moveToNext(); 
    }
	       
	    		
	        // do something useful with these 


	  
	
        
           
	
   	}
      
      
     //V--view视图
     initViews();
     //M--model数据
     initData2();
     //C--control控制器(即适配器)
     initAdapter();
     //开启图片的自动轮询
     new Thread(){
         @Override
         public void run() {
         
             while(true){
                 try {
                     Thread.sleep(6000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() { //在子线程中开启子线程
                         //往下翻一页（setCurrentItem方法用来设置ViewPager的当前页）
                         vp_detail.setCurrentItem(vp_detail.getCurrentItem()+1);
                     }
                 });
             }
         }
     }.start();
	
 }

 /*
     初始化视图
  */
	private static String ss(String name)
	{
	    String[] str = name.split(",");
	    if (str.length == 0 )
	    {
	        return null;
	    }
	    List<String> list = new ArrayList();

	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < str.length; i++)
	    {
	        if (!list.contains(str[i]))
	        {
	            list.add(str[i]);
	            sb.append(str[i] + ",");
	        }  
	    }
	    return sb.toString().substring(0, sb.toString().length() - 1);
	}
 private void initViews()
 {
     //初始化ViewPager控件
     vp_detail = (ViewPager) findViewById(R.id.vp_detail);
     //设置ViewPager的滚动监听
     vp_detail.setOnPageChangeListener(this);
 }


 /*
   初始化数据
  */
 @SuppressLint("NewApi")
private void initData2()
 {
	
		switch (key) {
		case "iphonex":

		    imageResIds = new int[]{
		             R.drawable.ipx1,
		             R.drawable.ipx2,
		             R.drawable.ipx1,
		             R.drawable.ipx2,
		             R.drawable.ipx1
		     };
		   	
		   
		

			break;
		case "iphone8":

		    imageResIds = new int[]{
		             R.drawable.ip81,
		             R.drawable.ip82,
		             R.drawable.ip83,
		             R.drawable.ip7,
		             R.drawable.ip81
		     };
	
		  
			break;
		case "iphone7":

		    imageResIds = new int[]{
		             R.drawable.ip71,
		             R.drawable.ip72,
		             R.drawable.ip73,
		             R.drawable.ip71,
		             R.drawable.ip72
		             
		     };
		
		    
			break;
		default:
		    imageResIds = new int[]{
		             R.drawable.ip7,
		             R.drawable.mac11,
		             R.drawable.ipad11,
		             R.drawable.mac11,
		             R.drawable.ipad11
		     };
		  
		    
			break;
		}
     //初始化填充ViewPager的图片资源
 


     //保存图片资源的集合
     imageViews = new ArrayList<>();
     ImageView imageView;

     //循环遍历图片资源，然后保存到集合中
     for (int i = 0; i < imageResIds.length; i++){
         //添加图片到集合中
         imageView = new ImageView(this);
         imageView.setBackgroundResource(imageResIds[i]);
         imageViews.add(imageView);
     }
 }


 /*
   初始化适配器
  */
 private void initAdapter() {
     vp_detail.setAdapter(new MyPagerAdapter());
     //设置默认显示中间的某个位置（这样可以左右滑动），这个数只有在整数范围内，可以随便设置
     vp_detail.setCurrentItem(5000000); //显示5000000这个位置的图片
 }


 //界面销毁时，停止viewpager的轮询

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	
		 if (this.thread != null) {
	         this.thread.interrupt();
	     }
		  
		
	     

	}
 /*
   自定义适配器，继承自PagerAdapter
  */
 class MyPagerAdapter extends PagerAdapter {

     //返回显示数据的总条数，为了实现无限循环，把返回的值设置为最大整数
     @Override
     public int getCount() {
         return Integer.MAX_VALUE;
     }

     //指定复用的判断逻辑，固定写法：view == object
     @Override
     public boolean isViewFromObject(View view, Object object) {
         //当创建新的条目，又反回来，判断view是否可以被复用(即是否存在)
         return view == object;
     }

     //返回要显示的条目内容
     @Override
     public Object instantiateItem(ViewGroup container, int position) {
         //container  容器  相当于用来存放imageView
         //从集合中获得图片
         int newPosition = position % 5; //数组中总共有5张图片，超过数组长度时，取摸，防止下标越界
         ImageView imageView = imageViews.get(newPosition);
         //把图片添加到container中
         container.addView(imageView);
         //把图片返回给框架，用来缓存
         return imageView;
     }

     //销毁条目
     @Override
     public void destroyItem(ViewGroup container, int position, Object object) {
         //object:刚才创建的对象，即要销毁的对象
         container.removeView((View) object);
     }
 }

 //--------------以下是设置ViewPager的滚动监听所需实现的方法--------
 //页面滑动
 @Override
 public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

 }

 //新的页面被选中
 @Override
 public void onPageSelected(int position) {

 }

 //页面滑动状态发生改变
 @Override
 public void onPageScrollStateChanged(int state) {

 }

}
