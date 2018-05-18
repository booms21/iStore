package com.adsld.istore;

import com.adsld.istore.fgt.AccountFragment;
import com.adsld.istore.fgt.BaseFragment;

import android.app.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity {
	  private final static String TAG = AccountFragment.class.getSimpleName();
	 	 SQLiteDatabase db;
	 	EditText username;
	 	EditText password;
	 	EditText tel;
	 	 Button tologin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 隐藏状态栏
        setContentView(R.layout.activity_register);
        
        SharedPreferences         sp = getSharedPreferences("save",0);
        String name = sp.getString("username","defaultname");
        
        
   
        Toast.makeText(RegisterActivity.this, name, Toast.LENGTH_SHORT).show();
         username = (EditText)findViewById(R.id.username);
         password = (EditText)findViewById(R.id.password1);
         tel = (EditText)findViewById(R.id.tel);
        final EditText rpassword = (EditText)findViewById(R.id.password2);
        Button register = (Button)findViewById(R.id.register);
         tologin = (Button)findViewById(R.id.returnlogin);
        final Button showpw = (Button)findViewById(R.id.show_password);
       

        /** 功能：判断两次密码是否相同并给予提示  和  密码非空限制
         * 作者：张梓鹏  修改时间：2017年10月21日
         */
        rpassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText rpw = (EditText)findViewById(R.id.password2);
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    rpw.setText(str1);
                    rpw.setSelection(start);
                    Toast.makeText(RegisterActivity.this,"不能输入空格！！",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void afterTextChanged(Editable e) {
                EditText pw = (EditText)findViewById(R.id.password1);
                EditText rpw = (EditText)findViewById(R.id.password2);
                TextView tip = (TextView)findViewById(R.id.rpwtips);
                if(!pw.getText().toString().trim().equals(rpw.getText().toString().trim()))
                    tip.setText("请注意，两次输入的密码不一致!");
                else
                    tip.setText("");
            }
        });
        /** 功能：第一个密码框非空限制
         *  作者：张梓鹏  修改时间：2017年10月21日
         */
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                EditText pw = (EditText)findViewById(R.id.password1);
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    String str1 = "";
                    for (int i = 0; i < str.length; i++) {
                        str1 += str[i];
                    }
                    pw.setText(str1);
                    pw.setSelection(start);
                    Toast.makeText(RegisterActivity.this,"不能输入空格！！",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        tologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        /** 功能：显示/隐藏密码
         *  作者：张梓鹏  修改时间：2017年10月21日
         */
        showpw.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(showpw.getText().toString().equals("显示密码 | SHOW PASSWORD")){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    rpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showpw.setText("隐藏密码 | HIDE PASSWORD");
                }else if(showpw.getText().toString().equals("隐藏密码 | HIDE PASSWORD")){
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    rpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showpw.setText("显示密码 | SHOW PASSWORD");
                }
            }
        });

        /** 功能：注册判断输入为空，判断两次密码是否相同，并且实现注册功能
         *  作者：张梓鹏  修改时间：2017年10月21日
         */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString().trim();
                String pw = password.getText().toString().trim();
                String rpw = rpassword.getText().toString().trim();
                
       
                
                
                
                if(user.length()==0||pw.length()==0||rpw.length()==0){
                    Toast.makeText(RegisterActivity.this, "用户名和密码不可为空", Toast.LENGTH_SHORT).show();
                }else{
                    if(!pw.equals(rpw)){
                    	
                    	
                    	
                        Toast.makeText(RegisterActivity.this, "两次的密码不同", Toast.LENGTH_SHORT).show();
                    }else{
                    	
                    	
                    	inserdata();

                    	 Toast.makeText(RegisterActivity.this, "账户："+user+" 注册成功！", Toast.LENGTH_SHORT).show();
                    	 tologin.performClick();
                    }
                }
            }
        });
    }
    public void inserdata() {
   	  db = SQLiteDatabase.openOrCreateDatabase(getFilesDir().toString()+"/data.db3", null);
	  	Log.e(TAG, "sqlite数据库打开..");	
	 
	    try{
	    	
	    
	      	db.execSQL("insert into user values(null,?,?,?,?,?,?,?)",new String[]{username.getText().toString(),password.getText().toString(),"","",tel.getText().toString(),"100000",""});
	   	
	 


	    }
	    catch(SQLiteException se){

	   
	     	Log.e(TAG, "添加错误:"+se);	


	    }
	    db.close();
	    
	}
    
    
}