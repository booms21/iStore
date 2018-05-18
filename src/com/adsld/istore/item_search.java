package com.adsld.istore;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class item_search extends Activity {
    Button clearpw,cancel;
    EditText keyword;
    ListView item_tip;
    Intent	 i;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
 

        	 i= new Intent(item_search.this,DetailActivity.class);
        clearpw = (Button)findViewById(R.id.clear_password);
        cancel = (Button)findViewById(R.id.cancel);
        keyword = (EditText)findViewById(R.id.keyword);
        item_tip = (ListView)findViewById(R.id.search_item);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
finish();
            }
        });

        keyword.addTextChangedListener(new TextWatcher() {
            ArrayAdapter<String> arrayAdapter = null;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable editable) {
                String kw = keyword.getText().toString();
                String iPhone = "iph",iPad = "ipa",Mac = "mac",Watch = "wat";
                if(kw.length()>2){
                    if(kw.toUpperCase().indexOf(iPhone.toUpperCase())!=-1){
                        String[] strInfo = {"iPhone X","iPhone 8","iPhone 7","iPhone 配件"};
                        arrayAdapter = new ArrayAdapter<String>(item_search.this,android.R.layout.simple_list_item_1, strInfo);
                        item_tip.setAdapter(arrayAdapter);
                    }else if(kw.toUpperCase().indexOf(iPad.toUpperCase())!=-1){
                        String[] strInfo = {"iPad Pro","iPad 2017款","iPad mini4","iPad 配件"};
                        arrayAdapter = new ArrayAdapter<String>(item_search.this,android.R.layout.simple_list_item_1,strInfo);
                        item_tip.setAdapter(arrayAdapter);
                    }else if(kw.toUpperCase().indexOf(Mac.toUpperCase())!=-1){
                        String[] strInfo = {"MacBook Pro","MacBook","iMac","Mac 配件"};
                        arrayAdapter = new ArrayAdapter<String>(item_search.this,android.R.layout.simple_list_item_1,strInfo);
                        item_tip.setAdapter(arrayAdapter);
                    }else if(kw.toUpperCase().indexOf(Watch.toUpperCase())!=-1){
                        String[] strInfo = {"Apple Watch Series 3","Apple Watch Nike+","Apple Watch 表带","Apple Watch 配件"};
                        arrayAdapter = new ArrayAdapter<String>(item_search.this,android.R.layout.simple_list_item_1,strInfo);
                        item_tip.setAdapter(arrayAdapter);
                    }
                } else if(kw.length() == 0){clearpw.setVisibility(View.INVISIBLE);}
                else if (kw.length() <=2 && kw.length()>0){
                    clearpw.setVisibility(View.VISIBLE);
                    String[] tj = {"为你推荐","iPhone 8","iPhone 7","iPhone X"};
                    arrayAdapter = new ArrayAdapter<String>(item_search.this,android.R.layout.simple_list_item_1,tj);
                    item_tip.setAdapter(arrayAdapter);
                }
            }
        });

        clearpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword.setText("");
                clearpw.setVisibility(View.INVISIBLE);
            }
        });


        keyword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String kw = keyword.getText().toString().trim();
                ArrayAdapter<String> arrayAdapter = null;
                if(keyCode == KeyEvent.KEYCODE_ENTER) {
                    //Toast.makeText(item_search.this, "用户按了回车键，即将搜索"+keyword.getText(), Toast.LENGTH_SHORT).show();
                    String iPhone = "iph", iPad = "ipa", Mac = "mac", Watch = "wat";
                    if (kw.length() > 2) {
                        if (kw.toUpperCase().indexOf(iPhone.toUpperCase()) != -1) {
                            String[] strInfo = {"iPhone X      >", "iPhone 8      >", "iPhone 7      >", "iPhone 配件      >"};
                            arrayAdapter = new ArrayAdapter<String>(item_search.this, android.R.layout.simple_list_item_1, strInfo);
                            item_tip.setAdapter(arrayAdapter);
                            Toast.makeText(item_search.this, "用户按了回车键，显示"+kw+"的搜索结果", Toast.LENGTH_SHORT).show();
                        } else if (kw.toUpperCase().indexOf(iPad.toUpperCase()) != -1) {
                            String[] strInfo = {"iPad Pro      >", "iPad 2017款      >", "iPad mini4      >", "iPad 配件      >"};
                            arrayAdapter = new ArrayAdapter<String>(item_search.this, android.R.layout.simple_list_item_1, strInfo);
                            item_tip.setAdapter(arrayAdapter);
                            Toast.makeText(item_search.this, "用户按了回车键，显示"+kw+"的搜索结果", Toast.LENGTH_SHORT).show();
                        } else if (kw.toUpperCase().indexOf(Mac.toUpperCase()) != -1) {
                            String[] strInfo = {"MacBook Pro      >", "MacBook      >", "iMac      >", "Mac 配件      >"};
                            arrayAdapter = new ArrayAdapter<String>(item_search.this, android.R.layout.simple_list_item_1, strInfo);
                            item_tip.setAdapter(arrayAdapter);
                            Toast.makeText(item_search.this, "用户按了回车键，显示"+kw+"的搜索结果", Toast.LENGTH_SHORT).show();
                        } else if (kw.toUpperCase().indexOf(Watch.toUpperCase()) != -1) {
                            String[] strInfo = {"Apple Watch Series 3      >", "Apple Watch Nike+      >", "Apple Watch 表带      >", "Apple Watch 配件      >"};
                            arrayAdapter = new ArrayAdapter<String>(item_search.this, android.R.layout.simple_list_item_1, strInfo);
                            item_tip.setAdapter(arrayAdapter);
                            Toast.makeText(item_search.this, "用户按了回车键，显示"+kw+"的搜索结果", Toast.LENGTH_SHORT).show();
                        } else {
                            String[] tj = {"为你推荐","iPhone 8      >","MacBook Pro      >","Apple Watch Series 3      >"};
                            arrayAdapter = new ArrayAdapter<String>(item_search.this,android.R.layout.simple_list_item_1,tj);
                            item_tip.setAdapter(arrayAdapter);
                            Toast.makeText(item_search.this, "用户按了回车键", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return false;
            }
        });
  
        item_tip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	
            @Override
            public void onItemClick(AdapterView<?>parent,View arg1,int pos,long id){
                String result = parent.getItemAtPosition(pos).toString();
                
				switch (result) {
				case "iPhone X":

					i.putExtra("key", "iphonex");

					startActivity(i);
					break;
				case "iPhone 8":

					i.putExtra("key", "iphone8");

					startActivity(i);
					break;
				case "iPhone 7":

					i.putExtra("key", "iphone7");

					startActivity(i);
					break;
				default:

					break;
				}
            

                
               // Toast.makeText(item_search.this,"用户选择了"+result+"，即将跳转至该项目", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
