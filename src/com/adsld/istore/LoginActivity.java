package com.adsld.istore;



import com.adsld.istore.fgt.AccountFragment;
import com.adsld.istore.fgt.BaseFragment;

import android.R.string;
import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class LoginActivity extends Activity  {
	  private final static String TAG = AccountFragment.class.getSimpleName();
	 SQLiteDatabase db;
    Button dl,showpass;
    TextView remenber;
    int flag = 0,rflag = 0,id;//flag用来判断密码隐藏，rflag用来判断记住密码，rflag为0时，是没记住的状态
    SharedPreferences sp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏状态栏
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

       
        getShared();//得到记住的账号和密码

        dl = (Button) findViewById(R.id.button1);
        remenber = (TextView) findViewById(R.id.remenber_password);
        showpass = (Button) findViewById(R.id.show_password);
        final Button clear = (Button)findViewById(R.id.clear_password);
        final EditText username = (EditText)findViewById(R.id.username);
        final EditText password = (EditText)findViewById(R.id.password);
        ImageView wechat = (ImageView)findViewById(R.id.wechat_login);
        ImageView qq = (ImageView) findViewById(R.id.qq_login);

        SpannableString ss = new SpannableString("手机号/邮箱/账号");// 新建一个可以添加属性的文本对象
        SpannableString ss2 = new SpannableString("请输入密码");
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(12, true);// 新建一个属性对象,设置文字的大小
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);// 附加属性到文本
        ss2.setSpan(ass, 0, ss2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        username.setHint(new SpannedString(ss));// 设置hint
        password.setHint(new SpannedString(ss2));

        dl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String un = username.getText().toString().trim();
                String pw = password.getText().toString().trim();
             try{
        	    	
        	        db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().toString()+"/data.db3", null);
            	  	Log.e(TAG, "sqlite数据库打开..");	
        	      	
        	   	
        	    	Cursor cursor=db.rawQuery("select _id,tel from user where username=? and password=?",new String[]{un,pw});
        	       	
        	    	if(cursor.getCount()==0){
        	    	  	Log.e(TAG, "sqlite数据库为空");	
        	    	  	Toast.makeText(LoginActivity.this, "登录失败！账号或者密码不匹配", Toast.LENGTH_SHORT).show();
        	    		
        	    	}else{
        	    	    SharedPreferences.Editor spe = sp.edit();
        	    	    spe.putInt("state",1);
        	    	    
        	    	    
        	         	 while (cursor.moveToNext()) { 
        	          	    
        	              	  
        	         		  spe.putString("id", cursor.getString(cursor.getColumnIndex("_id")));
        	         		 spe.putString("tel", cursor.getString(cursor.getColumnIndex("tel")));
        	       	  		
        	       	      // do something useful with these 
        	       	     // cursor.moveToNext(); 
        	       	    }
        	    	  
        	    	    
                        if(rflag==1) {
                            spe.putString("username", un);
                            spe.putString("password", pw);
                            spe.putInt("rflag",1);
                            spe.commit();
                        }else if(rflag == 0){
                            spe.putString("username", un);
                            spe.putString("password", "");
                            spe.putInt("rflag",0);
                            spe.commit();
                        }
                        
                        
                        
                        
                        
        	    		 Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
        	    	       
                         Intent i = new Intent(LoginActivity.this,FgtActivity.class);
                         startActivity(i);
        	    	}
        	    		
        	    		


        	    }
        	    catch(SQLiteException se){

        	   
        	     	Log.e(TAG, "sqlite错误:"+se);	


        	    }
        	    db.close();
            
                   
                    
                    
                
             
                    
                    
             
               
            }
        });

        /**功能：密码框有输入时显示清除密码按钮
         * 作者：张梓鹏 修改时间：2017年10月20日
         */
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText rpw = (EditText)findViewById(R.id.password);
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    rpw.setText(str1);
                    rpw.setSelection(start);
                    Toast.makeText(LoginActivity.this,"密码中不能带有空格！",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
                EditText pw = (EditText)findViewById(R.id.password);
                Button clear = (Button)findViewById(R.id.clear_password);
                if(pw.getText().toString().length() > 0)
                    clear.setVisibility(View.VISIBLE);
                else if(pw.getText().toString().length() == 0)
                    clear.setVisibility(View.INVISIBLE);
            }
        });

        clear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                password.setText("");
                clear.setVisibility(View.INVISIBLE);
            }
        });

        /**功能：跳转到注册页面
         * 作者：张梓鹏 修改时间：2017年10月20日
         */
        final TextView register = (TextView)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        /**功能：记住/忘记用户名和密码
         * 作者：张梓鹏  修改时间：2017年10月18日
         */
        remenber.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String name,pass;
                name = username.getText().toString().trim();
                pass = password.getText().toString().trim();
                if(rflag == 0){
                    remenber.setText("√记住密码");
                    rflag = 1;
                }else if(rflag == 1){
                    SharedPreferences.Editor spe = sp.edit();
                    spe.clear();
                    spe.commit();
                    remenber.setText("记住密码");
                    rflag = 0;
                }
            }
        });

        /**功能：显示/隐藏密码
         * 作者：张梓鹏  修改时间：2017年10月18日
         */
        showpass.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(flag == 0){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    flag = 1;
                    showpass.setBackgroundResource(R.drawable.showpw);
                    password.setSelection(password.getText().toString().length());
                }else if(flag == 1){
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    flag = 0;
                    showpass.setBackgroundResource(R.drawable.hidepw);
                    password.setSelection(password.getText().toString().length());
                }
            }
        });

        /**功能：跳转微信和QQ
         * 作者：张梓鹏  修改时间：2017年10月19日
         */
        wechat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(LoginActivity.this, "您尚未安装微信", Toast.LENGTH_SHORT).show();
                }
            }
        });
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mobileqq");
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "您尚未安装QQ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**功能：得到记住的用户名和密码并且放在控件上
     *作者：张梓鹏  修改时间：2017年10月18日
     */
    public void getShared(){
        EditText user = (EditText) findViewById(R.id.username);
        EditText pass = (EditText) findViewById(R.id.password);
        sp = getSharedPreferences("save",0);
        String username = sp.getString("username", "");
        String password = sp.getString("password", "");
        int rr = sp.getInt("rflag",2);
        if(rr==0){
            TextView remenber = (TextView)findViewById(R.id.remenber_password);
            rflag = 0;
            remenber.setText("记住密码");
            user.setText(username.toString().trim());
        }else if(rr==1){
            rflag = 1;
            TextView bt = (TextView) findViewById(R.id.remenber_password);
            Button bt2 = (Button) findViewById(R.id.clear_password);
            user.setText(username.toString());
            pass.setText(password.toString());
            if(pass.length()>0) {
                bt2.setVisibility(View.VISIBLE);
            }
            bt.setText("√记住密码");
        }else{Toast.makeText(LoginActivity.this,"欢迎使用iStore，我们将为你提供最好的服务！",Toast.LENGTH_SHORT).show();}
    }
}