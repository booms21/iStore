package com.adsld.istore.fgt;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
/**
 * 基类Fragment
 * 首页：HomeFragment
 * 分类：TypeFragment
 * 发现：CommunityFragment
 * 购物车：ShoppingCartFragment
 *  用户中心：UserFragment
 *  等等都要继承该类
 */

public abstract class BaseFragment extends Fragment{

    protected Context mContext;
	  	SQLiteDatabase db;
    /**
     * 当该类被系统创建的时候回调
     * @param savedInstanceState
     */
    private final static String TAG = BaseFragment.class.getSimpleName();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mContext = getActivity();
 
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
   
     	Log.e(TAG, "shuaxle");
    	return initView();
        
    }

    //抽象类，由孩子实现，实现不同的效果
    public abstract View initView();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 当子类需要联网请求数据的时候，可以重写该方法，该方法中联网请求
     */
    public void initData() {
    	
  	  String TAG = BaseFragment.class.getSimpleName();
  	   
  	  db = SQLiteDatabase.openOrCreateDatabase(getActivity().getFilesDir().toString()+"/data.db3", null);
  	Log.e(TAG, "sqlite数据库打开..");	
 
    try{
    	
    
    	Cursor cursor=db.rawQuery("select * from user ", null);
   	
    	if(cursor.getCount()==0){
    	  	Log.e(TAG, "sqlite数据库user为空");	
    	
    		
    	}


    }
    catch(SQLiteException se){
    	db.execSQL("create table user(_id integer primary key autoincrement,"
    +"username varchar(20),"
    +"password varchar(20),"
    +"like varchar(255),"
    +"spbag varchar(255),"
    +"tel varchar(20),"
    +"money float,"
    +"address varchar(255))");
   
    


    }
    try{
      	
        
      	Cursor cursor=db.rawQuery("select * from goods", null);
     	
      	if(cursor.getCount()==0){
      	  	Log.e(TAG, "sqlite数据库goods为空");	
      	
      	  db.execSQL("insert into goods values(null,'iphonex',6666.5,'Apple/苹果 iPhone X/无需合约版/深空灰色/256G  双十一聚优惠','iphonex.png') ,(null,'iphone8',4888.5,'【预定再减1100元】Apple/苹果 iPhone 8 64G 银色 无需合约版 全网通手机苹果8立省1100元 抢先预定锁定货源','iphone8.png'),(null,'iphone7',4777.5,'苹果7/送壳膜/12期分期/Apple/苹果 iPhone 7 黑色32G裸机国行全网通4G手机/送壳膜 全国联保 顺丰包邮','iphone7.png')");
      	 
          
      	}


      }
      catch(SQLiteException se){
      	db.execSQL("create table goods(_id integer primary key autoincrement,"
      +"name varchar(20),"
      +"price float,"
      +"introduce varchar(255),"  
      +"imgurl varchar(255))");
      	db.execSQL("insert into goods values(null,'iphonex',6666.5,'Apple/苹果 iPhone X/无需合约版/深空灰色/256G  双十一聚优惠','iphonex.png') ,(null,'iphone8',4888.5,'【预定再减1100元】Apple/苹果 iPhone 8 64G 银色 无需合约版 全网通手机苹果8立省1100元 抢先预定锁定货源','iphone8.png'),(null,'iphone7',4777.5,'苹果7/送壳膜/12期分期/Apple/苹果 iPhone 7 黑色32G裸机国行全网通4G手机/送壳膜 全国联保 顺丰包邮','iphone7.png')");
      


      }
  try{
      	
        
      	Cursor cursor=db.rawQuery("select * from orders", null);
     	
      	if(cursor.getCount()==0){
      	  	Log.e(TAG, "sqlite数据库order为空");	
      	
    
      	 
          
      	}


      }
      catch(SQLiteException se){
      	db.execSQL("create table orders(_id integer primary key autoincrement,"
      +"gid varchar(20),"
      +"ordertime varchar(20),"
      +"Quantity integer," 
      +"odprice float,"
      +"uid varchar(20))");
      


      }
 
    
    

    
    }
}