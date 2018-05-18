package com.adsld.istore;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.adsld.istore.R;
import com.adsld.istore.fgt.*;


import java.util.ArrayList;



public class FgtActivity extends FragmentActivity{

	  public static FgtActivity instance = null; 
    FrameLayout frameLayout;

    RadioGroup rgMain;

    //装fragment的实例集合
    private ArrayList<BaseFragment> fragments;

    private int position = 0;

    //缓存Fragment或上次显示的Fragment
    private Fragment tempFragment;
	private final static String TAG = HomeFragment.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fgt);
        frameLayout =(FrameLayout) findViewById(R.id.frameLayout);
        instance = this;
        rgMain =(RadioGroup) findViewById(R.id.rg_main);
      
 

        //初始化Fragment
        initFragment();
        //设置RadioGroup的监听
        initListener();
        
    }

    private void initListener() {
       
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_home: //首页
                        position = 0;
                        break;
                    case R.id.rb_buy: //选购
                        position = 1;
                        break;
                    case R.id.rb_bag: //购物袋
                        position = 2;
                        break;
                    case R.id.rb_ant: //个人中心
                        position = 3;
                        break;
             
                    default:
                        position = 0;
                        break;
                }
                //根据位置得到相应的Fragment
                BaseFragment baseFragment = getFragment(position);
                /**
                 * 第一个参数: 上次显示的Fragment
                 * 第二个参数: 当前正要显示的Fragment
                 */
                switchFragment(tempFragment,baseFragment);

            }
        });
        rgMain.check(R.id.rb_home);
    
    }

    /**
     * 添加的时候按照顺序
     */
    private void initFragment(){
        fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new BuyFragment());
        fragments.add(new ShoppingBagFragment());
        fragments.add(new AccountFragment());

    }

    /**
     * 根据位置得到对应的 Fragment
     * @param position
     * @return
     */
    private BaseFragment getFragment(int position){
        if(fragments != null && fragments.size()>0){
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }
        return null;
    }

    /**
     * 切换Fragment
     * @param fragment
     * @param nextFragment
     */
    private void switchFragment(Fragment fragment,BaseFragment nextFragment){
        if (tempFragment != nextFragment){
            tempFragment = nextFragment;
            if (nextFragment != null){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //判断nextFragment是否添加成功
                if (!nextFragment.isAdded()){
                    //隐藏当前的Fragment
                    if (fragment != null){
                        transaction.remove(fragment);
                        getSupportFragmentManager().popBackStack();
                       
                    }
                    //添加Fragment
                   transaction.add(R.id.frameLayout,nextFragment).commit();
                 
                   // Toast.makeText(this, "dldlss", Toast.LENGTH_SHORT).show();
                }else {
                    //隐藏当前Fragment
                    if (fragment != null){
                        transaction.remove(fragment);
                        getSupportFragmentManager().popBackStack();  
                    }
            
                    transaction.show(nextFragment).commit();
               
                }
            }
        }
    }
}