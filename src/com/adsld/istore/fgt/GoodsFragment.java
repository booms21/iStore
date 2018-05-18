package com.adsld.istore.fgt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import com.adsld.istore.DetailActivity;
import com.adsld.istore.FgtActivity;
import com.adsld.istore.LoginActivity;
import com.adsld.istore.R;

public class GoodsFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
	private ViewPager vp;
	   private final static String TAG = BuyFragment.class.getSimpleName();
	//private LinearLayout ll_point;
	  private TextView textView;
	private TextView tv_desc,tt;
	private int[] imageResIds; // 存放图片资源id的数组
	private ArrayList<ImageView> imageViews; // 存放图片的集合
	private String[] contentDescs; // 图片内容描述
	//private int lastPosition;
	
	View view ;
	private Thread thread;
    FragmentTransaction tx;
    TableRow ipx,ip7,ip8;
    Intent i ;
	@Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
 view = inflater.inflate(R.layout.activity_select_iphone, container, false); 
   	 i= new Intent(getActivity(),DetailActivity.class);
		//super.onCreate(savedInstanceState);
   	ipx=(TableRow) view.findViewById(R.id.ipx);
	ip8=(TableRow) view.findViewById(R.id.ip8);
	ip7=(TableRow) view.findViewById(R.id.ip7);
	tt=(TextView) view.findViewById(R.id.tv_title);
	tt.setText("选购");

		initView();
		// 使用M-V-C模型
		// V--view视图
		initViews();
		// M--model数据
		initDataforimg();
		// C--control控制器(即适配器)
		initAdapter();
		// 开启图片的自动轮询
		this.thread =new Thread() {
			
			@Override
			public void run() {
				
				while (true) {
				
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						break;
					//	e.getStackTrace();
					}
				
					getActivity().runOnUiThread(new Runnable() {
						@Override
						public void run() { // 在子线程中开启子线程
							// 往下翻一页（setCurrentItem方法用来设置ViewPager的当前页）
							vp.setCurrentItem(vp.getCurrentItem() + 1);
							}
					});
				}
			}
		};
		this.thread.start();
		
		ipx.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				
				 i.putExtra("key", "iphonex");  
	              
                 startActivity(i);
				
				
			}
		});
ip8.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 i.putExtra("key", "iphone8");  
	              
                 startActivity(i);
			}
		});
ip7.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 i.putExtra("key", "iphone7");  
         
         startActivity(i);
	}
});
		 return view; 
		 
	}
	
	
	/*
	 * 初始化视图
	 */
	private void initViews() {
		// 初始化放小圆点的控件
		//ll_point = (LinearLayout) 	view.findViewById(R.id.ll_point);
		// 初始化ViewPager控件
		vp = (ViewPager) view.findViewById(R.id.vp);
		// 设置ViewPager的滚动监听
		vp.setOnPageChangeListener(this);
		// 显示图片描述信息的控件
		tv_desc = (TextView) view.findViewById(R.id.tv_desc);
	}

	/*
	 * 初始化数据
	 */
	private void initDataforimg() {
		// 初始化填充ViewPager的图片资源
		imageResIds = new int[] { R.drawable.ipx1, R.drawable.iphone81, R.drawable.ip72, R.drawable.ip73,
				R.drawable.iphone11 };

		// 图片的描述信息

		contentDescs = new String[] { "iPhoneX", "iPhone8", "iPhone7", "iPhone7s", "iPhone6" };

		// 保存图片资源的集合
		imageViews = new ArrayList<>();
		ImageView imageView;
		View pointView;

		// 获取屏幕宽度做为滚动条的总宽度
		WindowManager wm = getActivity().getWindowManager();
		int width = wm.getDefaultDisplay().getWidth();

		// 循环遍历图片资源，然后保存到集合中
		for (int i = 0; i < imageResIds.length; i++) {
			// 添加图片到集合中
			imageView = new ImageView(getActivity());
			imageView.setBackgroundResource(imageResIds[i]);
			imageViews.add(imageView);

			// 加小白点，指示器（这里的小圆点定义在了drawable下的选择器中了，也可以用小图片代替）
			pointView = new View(getActivity());
			pointView.setBackgroundResource(R.drawable.slide_line); // 使用选择器设置背景

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width / (imageResIds.length), 20); // 创建滚动条

			/*
			 * if (i != 0){ //如果不是第一个点，则设置点的左边距 layoutParams.leftMargin = 10; }
			 * pointView.setEnabled(false); //默认都是暗色的
			 */

			//ll_point.addView(pointView, layoutParams);
			// ll_point.setVisibility(View.INVISIBLE); //默认不显示

		}
	}

	/*
	 * 初始化适配器
	 */
	private void initAdapter() {

		// ll_point.getChildAt(0).setEnabled(true); //初始化控件时，设置第一个小圆点为亮色

		tv_desc.setText(contentDescs[0]); // 设置第一个图片对应的文字
		//lastPosition = 0; // 设置之前的位置为第一个

		vp.setAdapter(new MyPagerAdapter());
		// 设置默认显示中间的某个位置（这样可以左右滑动），这个数只有在整数范围内，可以随便设置
		vp.setCurrentItem(5000000); // 显示5000000这个位置的图片
	}

	// 界面销毁时，停止viewpager的轮询

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	
		 if (this.thread != null) {
	         this.thread.interrupt();
	     }
		  
		 Log.e(TAG,"111111111111111111111111");
	     

	}


	/*
	 * 自定义适配器，继承自PagerAdapter
	 */
	class MyPagerAdapter extends PagerAdapter {

		// 返回显示数据的总条数，为了实现无限循环，把返回的值设置为最大整数
		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		// 指定复用的判断逻辑，固定写法：view == object
		@Override
		public boolean isViewFromObject(View view, Object object) {
			// 当创建新的条目，又反回来，判断view是否可以被复用(即是否存在)
			return view == object;
		}

		// 返回要显示的条目内容
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// container 容器 相当于用来存放imageView
			// 从集合中获得图片
			int newPosition = position % 5; // 数组中总共有5张图片，超过数组长度时，取摸，防止下标越界
			ImageView imageView = imageViews.get(newPosition);

			// 把图片添加到container中

			container.addView(imageView);

			// 把图片返回给框架，用来缓存
			return imageView;
		}

		// 销毁条目
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// object:刚才创建的对象，即要销毁的对象
			container.removeView((View) object);
		}
	}

	// --------------以下是设置ViewPager的滚动监听所需实现的方法--------
	// 页面滑动
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	// 新的页面被选中
	@Override
	public void onPageSelected(int position) {
		// 当前的位置可能很大，为了防止下标越界，对要显示的图片的总数进行取余
		int newPosition = position % 5;

		// 设置描述信息
		tv_desc.setText(contentDescs[newPosition]);

		/*
		 * //设置小圆点为高亮或暗色 ll_point.getChildAt(lastPosition).setEnabled(false);
		 * ll_point.getChildAt(newPosition).setEnabled(true); lastPosition =
		 * newPosition; //记录之前的点
		 */

		// 设置滚动条显示隐藏
		//ll_point.getChildAt(lastPosition).setVisibility(View.INVISIBLE);
		//ll_point.getChildAt(newPosition).setVisibility(View.VISIBLE);
		//lastPosition = newPosition; // 记录之前的点

	}

	// 页面滑动状态发生改变
	@Override
	public void onPageScrollStateChanged(int state) {

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
        textView.setText("54654");
        Log.e(TAG,"主页面的Fragment的数据被初始化了");
    }
}
