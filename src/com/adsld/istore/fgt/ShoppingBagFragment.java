package com.adsld.istore.fgt;




import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ExpandableListView;

import android.widget.Toast;

import com.adsld.istore.ShopcartExpandableListViewAdapter;
import com.adsld.istore.ShopcartExpandableListViewAdapter.CheckInterface;
import com.adsld.istore.ShopcartExpandableListViewAdapter.ModifyCountInterface;
import com.adsld.istore.entity.GroupInfo;
import com.adsld.istore.entity.ProductInfo;
import com.adsld.istore.DetailActivity;
import com.adsld.istore.LoginActivity;
import com.adsld.istore.PayActivity;
import com.adsld.istore.R;
/**
 * 首页Fragment
 */
public class ShoppingBagFragment extends BaseFragment implements CheckInterface, ModifyCountInterface, OnClickListener {
	
	 SQLiteDatabase db;
   	 String 	 sql;
	 private ExpandableListView exListView;
	    private CheckBox cb_check_all;
	    private TextView tv_total_price;
	    private TextView tv_delete,tt;
	    private TextView tv_go_to_pay;
	    private Context context;
	    int flag=0;
	 
	    private float totalPrice = 0;// 购买的商品总价
	    private int totalCount = 0;// 购买的商品总数量
	    SharedPreferences sp;
	    int itCount;
		
	    private ShopcartExpandableListViewAdapter selva;
	    private List<GroupInfo> groups = new ArrayList<GroupInfo>();// 组元素数据列表
	    private Map<String, List<ProductInfo>> children = new HashMap<String, List<ProductInfo>>();// 子元素数据列表
    private final static String TAG = ShoppingBagFragment.class.getSimpleName();
 	Cursor cursor;
    private TextView textView;
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
    	View view = inflater.inflate(R.layout.activity_shopping_bag, container, false);
    	
 
    	tt=(TextView) view.findViewById(R.id.tv_title);
    	tt.setText("购物袋");
        sp = getActivity().getSharedPreferences("save",Context.MODE_PRIVATE);
   	 flag=sp.getInt("state", 2);  //得到登录状态
       
   
        
   
         initView1(view);
        //initEvents();
	       
		return view; 
    	
    	
    }
    
    private void initView1(View view)
    {
        context = getActivity();
        exListView = (ExpandableListView) view.findViewById(R.id.exListView);
        cb_check_all = (CheckBox) view.findViewById(R.id.all_chekbox);
        tv_total_price = (TextView) view.findViewById(R.id.tv_total_price);
        tv_delete = (TextView) view.findViewById(R.id.tv_delete);
        tv_go_to_pay = (TextView) view.findViewById(R.id.tv_go_to_pay);
        virtualData();
        
    }

    private void initEvents()
    {
        selva = new ShopcartExpandableListViewAdapter(groups, children, getActivity());
        selva.setCheckInterface(this);// 关键步骤1,设置复选框接口
        selva.setModifyCountInterface(this);// 关键步骤2,设置数量增减接口
        exListView.setAdapter(selva);

        for (int i = 0; i < selva.getGroupCount(); i++)
        {
            exListView.expandGroup(i);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
        }

        cb_check_all.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_go_to_pay.setOnClickListener(this);
        selva.notifyDataSetChanged();
    }

    /**
     * 模拟数据<br>
     * 遵循适配器的数据列表填充原则，组元素被放在一个List中，对应的组元素下辖的子元素被放在Map中，<br>
     * 其键是组元素的Id(通常是一个唯一指定组元素身份的值)
     */
    private void virtualData()
    {   
  
    	   try{
   	    	
   	        db = SQLiteDatabase.openOrCreateDatabase(getActivity().getFilesDir().toString()+"/data.db3", null);
       	  	Log.e(TAG, "sqlite数据库打开..+"+sp.getString("username", ""));	
       	 //db.execSQL("insert into spbag values(null,'iphone',100.5,'1')");
       	  	
       	  	

       	 	Cursor cursor1=db.rawQuery("select spbag from user where username=?",new String[]{sp.getString("username", "")});
       	 while (cursor1.moveToNext()) { 
     	    
       	  
       	 
       		 	 sql=cursor1.getString(cursor1.getColumnIndex("spbag"));
       	  		
       	      // do something useful with these 
       	     // cursor.moveToNext(); 
       	    }
     	if(sp.getString("username", "")==""){
     		
     		sql=",";	
     		
     	}
 if(sql.length()>1){
	
	sql = sql.substring(0,sql.length() - 1);
	
}
 if(sp.getInt("state", 2)!=1){//不在登录状态  
	  
     
   	Intent intent =new Intent(getActivity(),LoginActivity.class);//跳转到登录
	startActivity(intent);
	sql="";
    }

   	   	


   	    	//Cursor cursor=db.rawQuery(sql,new String[]{"1","3"});
   	       	
   	 cursor=db.rawQuery("select _id,name,price from goods where _id in ("+sql+")",null);
   	    	
   	    	
   	    	
   	    	
   	    	
   	    	
   	    	if(cursor.getCount()==0){
   	    	  	Log.e(TAG, "sqlite数据库为空");	
   	    	  	Toast.makeText(getActivity(), "购物车为空", Toast.LENGTH_SHORT).show();
   	    		
   	    	}else{
   	    		
   	     	 groups.clear();
   	      //children.clear();
   	             groups.add(new GroupInfo(0 + "", ""));
   	         
   	             List<ProductInfo> products = new ArrayList<ProductInfo>();
   	             
   	     
   	          cursor.moveToFirst(); 
  		    while (!cursor.isAfterLast()) { 
  		    
  		    	
  		    	Log.e(TAG,"--------------");
	                 products.add(new ProductInfo(cursor.getInt(0) + "", "商品", "",cursor.getString(1),cursor.getFloat(2) , 1));
  		       
  		    		
  		        // do something useful with these 
  		        cursor.moveToNext(); 
  		      }
  		  children.put("0", products);// 将组元素的一个唯一值，这里取Id，作为子元素List的Key
   	         
                   
  		initEvents();
   	    	}
   	    		
   	    		


   	    }
   	    catch(SQLiteException se){

   	   
   	     	Log.e(TAG, "sqlite错误:"+se);	


   	    }
  
    	
    	
    	
    	
   
    }

    @Override
    public void onClick(View v)
    {
        AlertDialog alert;
        switch (v.getId())
        {
        case R.id.all_chekbox:
            doCheckAll();
            break;
        case R.id.tv_go_to_pay:
            if (totalCount == 0)
            {
                Toast.makeText(context, "请选择要支付的商品", Toast.LENGTH_LONG).show();
                return;
            }
            alert = new AlertDialog.Builder(context).create();
            alert.setTitle("操作提示");
            alert.setMessage("总计:\n" + totalCount + "种商品\n" + totalPrice + "元");
            alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    return;
                }
            });
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {	Intent i= new Intent(getActivity(),PayActivity.class);
              
 
            	  
                String[] id=new String[cursor.getCount()];
                int[] count=new int[cursor.getCount()];
                float[] aprice=new float[cursor.getCount()];
                	for (int j = 0; j < cursor.getCount();j++) {
                		
                		 ProductInfo product = (ProductInfo) selva.getChild(0, j);
                         itCount += product.getCount();
                         id[j]=product.getId();
                         count[j]=itCount;
                         aprice[j]=itCount*(float) product.getPrice();
                        
					}
                	
               	 i.putExtra("id", sp.getString("id", ""));  
				 i.putExtra("price", totalPrice+"");  
				 i.putExtra("aid", id); 
				 
				 i.putExtra("aprice", aprice); 
				 
				 i.putExtra("count", count); 
				 
                 startActivity(i);

             
                	
                
                	
                	itCount=0;
             doDelete();
                }
            });
            alert.show();
            break;
        case R.id.tv_delete:
            if (totalCount == 0)
            {
                Toast.makeText(context, "请选择要移除的商品", Toast.LENGTH_LONG).show();
                return;
            }
            alert = new AlertDialog.Builder(context).create();
            alert.setTitle("操作提示");
            alert.setMessage("您确定要将这些商品从购物车中移除吗？");
            alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    return;
                }
            });
            alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    doDelete();
                }
            });
            alert.show();
            break;
        }
    }

    /**
     * 删除操作<br>
     * 1.不要边遍历边删除，容易出现数组越界的情况<br>
     * 2.现将要删除的对象放进相应的列表容器中，待遍历完后，以removeAll的方式进行删除
     */
    protected void doDelete()
    {
    	
   
    	
        List<GroupInfo> toBeDeleteGroups = new ArrayList<GroupInfo>();// 待删除的组元素列表
        for (int i = 0; i < groups.size(); i++)
        {
            GroupInfo group = groups.get(i);
            if (group.isChoosed())
            {

                toBeDeleteGroups.add(group);
            }
            List<ProductInfo> toBeDeleteProducts = new ArrayList<ProductInfo>();// 待删除的子元素列表
            List<ProductInfo> childs = children.get(group.getId());
     
            for (int j = 0; j < childs.size(); j++)
            {

                if (childs.get(j).isChoosed())
                {
                	
                	
                
                    toBeDeleteProducts.add(childs.get(j));
                    
                	try {
                		
                		 String newspbag="";

                		
                	              	       String[] str =sql.split(",");
                	              		for(int k=0;k<str.length;k++){


                	              	if(!str[k].equals(childs.get(j).getId())){

                	              	
                	              		newspbag+=str[k];
                	              		newspbag+=",";
                	              	 	
                	              	}
                	              		
                	              	
                	              		}  
                	              		sql=newspbag;
                	              		 Log.e(TAG, "-----"+newspbag);	
                	              		ContentValues values =new ContentValues();
                	              	   values.put("spbag",newspbag);
                	             
                	              	 
                	              	 String[] id1 =new String[1];
            					     id1[0]=sp.getString("id", "");
                	              		int result=db.update("user", values, "_id=?", id1);
                			        	
                			        	if(result<=0){
                			        		 Toast.makeText(getActivity(), "删除失败，不存在该用户", Toast.LENGTH_LONG).show();
                			        		
                			        	}else		 
                			        	;
                			        	
                			       
                		
                		
            	      
            		} catch (SQLiteException se) {
            			Toast.makeText(getActivity(), "删除失败" + se, Toast.LENGTH_LONG).show();
            		}
                }
            }
            childs.removeAll(toBeDeleteProducts);
        }
      
       // groups.removeAll(toBeDeleteGroups);
        
  
        selva.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked)
    {

        ProductInfo product = (ProductInfo) selva.getChild(groupPosition, childPosition);
        int currentCount = product.getCount();
        currentCount++;
        product.setCount(currentCount);
        
        ((TextView) showCountView).setText(currentCount + "");

        selva.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked)
    {

        ProductInfo product = (ProductInfo) selva.getChild(groupPosition, childPosition);
        int currentCount = product.getCount();
        if (currentCount == 1)
            return;
        currentCount--;

        product.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");

        selva.notifyDataSetChanged();
        calculate();
    }

    @Override
    public void checkGroup(int groupPosition, boolean isChecked)
    {/*
        GroupInfo group = groups.get(groupPosition);
        List<ProductInfo> childs = children.get(group.getId());
        for (int i = 0; i < childs.size(); i++)
        {
            childs.get(i).setChoosed(isChecked);
        }
        if (isAllCheck())
            cb_check_all.setChecked(true);
        else
            cb_check_all.setChecked(false);
        selva.notifyDataSetChanged();
        calculate();*/
    }

    @Override
    public void checkChild(int groupPosition, int childPosiTion, boolean isChecked)
    {
        boolean allChildSameState = true;// 判断改组下面的所有子元素是否是同一种状态
        GroupInfo group = groups.get(groupPosition);
        List<ProductInfo> childs = children.get(group.getId());
        for (int i = 0; i < childs.size(); i++)
        {
            if (childs.get(i).isChoosed() != isChecked)
            {
                allChildSameState = false;
                break;
            }
        }
        if (allChildSameState)
        {
           // group.setChoosed(isChecked);// 如果所有子元素状态相同，那么对应的组元素被设为这种统一状态
        } else
        {
            group.setChoosed(false);// 否则，组元素一律设置为未选中状态
        }

        if (isAllCheck())
            cb_check_all.setChecked(true);
        else
            cb_check_all.setChecked(false);
        selva.notifyDataSetChanged();
        calculate();
    }

    private boolean isAllCheck()
    {

        for (GroupInfo group : groups)
        {
            if (!group.isChoosed())
                return false;

        }

        return true;
    }

    /** 全选与反选 */
    private void doCheckAll()
    {
        for (int i = 0; i < groups.size(); i++)
        {
      //      groups.get(i).setChoosed(cb_check_all.isChecked());
            GroupInfo group = groups.get(i);
            List<ProductInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++)
            {
                childs.get(j).setChoosed(cb_check_all.isChecked());
            }
        }
        calculate();
        selva.notifyDataSetChanged();
       
    }

    /**
     * 统计操作<br>
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部的textView进行数据填充
     */
    private void calculate()
    {
    	
        totalCount = 0;
        totalPrice = 0;
        for (int i = 0; i < groups.size(); i++)
        {
            GroupInfo group = groups.get(i);
            List<ProductInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++)
            {
                ProductInfo product = childs.get(j);
                if (product.isChoosed())
                {
                    totalCount++;
                    totalPrice += product.getPrice() * product.getCount();
                }
            }
        }
        tv_total_price.setText("￥" + totalPrice);
        tv_go_to_pay.setText("去支付(" + totalCount + ")");
    }
    
    
    
 
    
    @Override
    public View initView() {
    
     
        Log.e(TAG,"主页面的Fragment的UI被初始化了");
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
   
        Log.e(TAG,"主页面的Fragment的数据被初始化了");
    }
}