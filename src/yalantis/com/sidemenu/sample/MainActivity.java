package yalantis.com.sidemenu.sample;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import obd.example.com_bluetooth.connection.Manager;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.sample.fragment.ContentFragment;
import yalantis.com.sidemenu.util.ViewAnimator;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements ViewAnimator.ViewAnimatorListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ContentFragment contentFragment;
    private ViewAnimator viewAnimator;
    private LinearLayout linearLayout;

    private Builder alertdialog_builder ;
    
    //OBD
    private Builder device_builder;
	private List<BluetoothDevice> bluetoothlist=new ArrayList<BluetoothDevice>();
	private BluetoothAdapter blueAdapter;
	private BluetoothDevice searchdevice=null;
	private Set set_saved_device=new HashSet();
	private String[] addresslist=null;
	//声明OBD进度条对话框
	ProgressDialog m_pDialog;
	private boolean find=false;
	private boolean march=false;
	//和OBD进行数据交互
	private boolean BTConnect = false;
	private Manager manager;
	private Handler handlerDash = null;
	private Handler handlerDist = null;
    
	//Manager--OBD数据交互
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
//			case 0x1000: {
//				removeMessages(0x1000);
//				if (handlerDash != null) {
//					System.out.println("------------------------------------------!!!la");
//					handlerDash.sendEmptyMessage(0x2000);
//				}
//				break;
//			}
			//dashboard
			case 0x3000: {
				removeMessages(0x3000);
				manager.getSendThread().setStrJSON(manager.getGetThread().getStrJSON());
				if(handlerDash!=null){
					System.out.println("------------------!!!ll");
					handlerDash.sendEmptyMessage(0x2000);
				}
				if(handlerDist!=null){
					System.out.println("--------------!!!dididi");
					handlerDist.sendEmptyMessage(0x2001);
				}
				break;
			}			
			}
		}
	};
	
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	contentFragment.getWeiZhangActivity().setActivityResult(requestCode, resultCode, data);
	}
    //dash监控
    public Handler getHandlerDash() {
		return handlerDash;
	}
    public void setHandlerDash(Handler handlerDash) {
		this.handlerDash = handlerDash;
	}
    //dist监控
    public Handler getHandlerDist() {
		return handlerDist;
	}
	public void setHandlerDist(Handler handlerDist) {
		this.handlerDist = handlerDist;
	}

	/*
     * (non-Javadoc)
     * 搜索蓝牙OBD
     */
    private BroadcastReceiver receiver=new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			
			String action=intent.getAction();			
			
			if(BluetoothDevice.ACTION_FOUND.equals(action)){
				searchdevice=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);	
				System.out.println("searchdevice="+searchdevice.toString());				
				
				if(!set_saved_device.contains(searchdevice))set_saved_device.add(searchdevice);
				
//				if(set_saved_device.contains(searchdevice) && !BTConnect){
//					m_pDialog.dismiss();
//			       	m_pDialog.cancel();
//			       	blueAdapter.cancelDiscovery();
//					manager.OnConnectBT(searchdevice);
//					BTConnect = manager.getSetBT().BTConnect();
//					System.out.println("march connected");
//					Toast.makeText(MainActivity.this, "连接成功", Toast.LENGTH_LONG).show();
//					manager.Extract();					
//				}
				//if(!BTConnect)
				{
					//set_saved_device.add(searchdevice);
					if(!BTConnect)
					{
						blueAdapter.cancelDiscovery();
						m_pDialog.dismiss();
				       	m_pDialog.cancel();
						
				       	if(bluetoothlist!=null)bluetoothlist.clear();
				       	for(Iterator ite = set_saved_device.iterator();ite.hasNext();)
				       	{
				       		BluetoothDevice tempdevice = (BluetoothDevice)ite.next();
				       		System.out.println("tempdevice="+tempdevice.toString()+" searchdevice="+searchdevice.toString());
				       		bluetoothlist.add(tempdevice);
				       	}
				       	System.out.println(bluetoothlist.size());
						device_builder = new Builder(MainActivity.this);
						device_builder.setTitle("请选择OBD设备");
						addresslist = new String[bluetoothlist.size()];
						for(int i=0;i<bluetoothlist.size();i++)addresslist[i]=bluetoothlist.get(i).getAddress();			
						device_builder.setItems(addresslist,new OnClickListener() {						
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								Log.e("haha", String.valueOf(which)+"   "+addresslist[which]+"          "+bluetoothlist.get(which));
								manager.OnConnectBT((BluetoothDevice)bluetoothlist.get(which));
								BTConnect = manager.getSetBT().BTConnect();
								System.out.println("connected");
								Toast.makeText(MainActivity.this, "连接成功", Toast.LENGTH_LONG).show();
								manager.Extract();
								blueAdapter.cancelDiscovery();
								dialog.dismiss();
							}
						});
						device_builder.create().show();
					}					
//					else
//					{
//						Toast.makeText(MainActivity.this, "连接失败", Toast.LENGTH_LONG).show();
//						MainActivity.this.finish();
//					}
				}				
			}else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				setProgressBarIndeterminateVisibility(false);
			}
		}		
	};
   
	public void search_bluetooth()
	{
			blueAdapter=BluetoothAdapter.getDefaultAdapter();
			blueAdapter.startDiscovery();
				
			if(blueAdapter==null){
				Toast.makeText(MainActivity.this, "本机不支持蓝牙功能", Toast.LENGTH_SHORT).show();
				finish();
			}
			if(!blueAdapter.isEnabled())
			{
				//通过意图开启
				Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); 
				intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 3000);     
				startActivity(intent);
			}
			
			//得到所有已经配对的蓝牙适配器对象
			Set<BluetoothDevice> devices = blueAdapter.getBondedDevices();
			if(devices.size()>0)
			{
				for(Iterator ite = devices.iterator();ite.hasNext();)
				{
					BluetoothDevice setdevice = (BluetoothDevice) ite.next();	
					set_saved_device.add(setdevice);
				}
			}
				
			IntentFilter intentFilter = new IntentFilter();     
			intentFilter.addAction(BluetoothDevice.ACTION_FOUND);     
			intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);     
			intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);     
			intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
			intentFilter.addAction("android.bluetooth.device.action.PAIRING_REQUEST");				
			registerReceiver(receiver, intentFilter);
	}
    //蓝牙设备搜索进度框
    public void search_bluetooth_progress()
    {
    	//创建ProgressDialog对象
        m_pDialog = new ProgressDialog(MainActivity.this);
     // 设置进度条风格，风格为圆形，旋转的
        m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // 设置ProgressDialog 标题
        m_pDialog.setTitle("OBD设备搜索");         
     // 设置ProgressDialog 提示信息
        m_pDialog.setMessage("正在搜索中...");
     // 让ProgressDialog显示
        m_pDialog.show();
        search_bluetooth();
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contentFragment = ContentFragment.newInstance(R.drawable.content_car);
        
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, contentFragment)
                .commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });
        
        manager=new Manager(handler);
        
        alertdialog_builder = new Builder(this);
        alertdialog_builder.setMessage("是否确定搜索OBD设备?");  
        alertdialog_builder.setTitle("搜索OBD设备");
        alertdialog_builder.setPositiveButton("确定", new OnClickListener() { 
        	 public void onClick(DialogInterface dialog, int which) {  
        		 search_bluetooth_progress();
        		 dialog.dismiss();        	     
        	 }
        });  
        alertdialog_builder.setNegativeButton("取消", new OnClickListener() {
        	  public void onClick(DialogInterface dialog, int which) {
        	    dialog.dismiss();
        	    MainActivity.this.finish();
        	 }
        });  
        alertdialog_builder.create().show();
        
        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, contentFragment, drawerLayout, this);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(ContentFragment.CLOSE, R.drawable.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(ContentFragment.driving, R.drawable.driving);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(ContentFragment.distances, R.drawable.distances);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(ContentFragment.analysis, R.drawable.analysis);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(ContentFragment.detection, R.drawable.detection);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(ContentFragment.red_green, R.drawable.red_green);
        list.add(menuItem5);
    }


    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition,int finalI) {
//        this.res = this.res == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;
//        View view = findViewById(R.id.content_frame);
//        int finalRadius = Math.max(view.getWidth(), view.getHeight());
//        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
//        animator.setInterpolator(new AccelerateInterpolator());
//        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);
//
//        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
//        animator.start();
        contentFragment = ContentFragment.newInstance(finalI);        
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position,int finalI) {
        switch (slideMenuItem.getName()) {
//        杩斿洖涓荤晫闈�
//            case ContentFragment.CLOSE:
//                return screenShotable;
            default:
                return replaceFragment(screenShotable, position,finalI);
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }
}
