package yalantis.com.sidemenu.sample;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import obd.example.com_bluetooth.model.DataBean;

import yalantis.com.sidemenu.adapter.TimeLineAdapter;
import yalantis.com.sidemenu.model.TimeLineModel;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class DistancesStatisticsActivity {

	View rootView = null;
	
	//里程统计--时间线
	private TimeLineAdapter adapter;
	private ListView timeline_listview;
	private static List<TimeLineModel> list=new ArrayList<TimeLineModel>();
	//里程统计--当前日期
	private TextView date_text;
	private static boolean fulfill=false;//判断是否有数据
	    
	public DistancesStatisticsActivity(View rootView)
	{
		this.rootView = rootView;
		timeline_listview = (ListView) rootView.findViewById(R.id.timeline_listview);
		init();
	}
	
	public void output_statistics()
	{
		System.out.println("list="+list.size());
		adapter=new TimeLineAdapter(rootView.getContext(), list);    		
		timeline_listview.setAdapter(adapter);
		
		SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("yyyy-MM-dd");
		String timelineDate=sDateFormat.format(new Date());
		date_text= (TextView) rootView.findViewById(R.id.current_day);
        date_text.setText(timelineDate);
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
				case 0x2001: {
					SimpleDateFormat   sDateFormat   =   new   SimpleDateFormat("HH:mm:ss");
					String timelineDate=sDateFormat.format(new Date());
					System.out.println("------------------------------------------!!!fulfill="+fulfill);
					removeMessages(0x2001);
					if (!DataBean.getRm().equals("null") && !fulfill)
					{
						list.add(new TimeLineModel(R.drawable.medicalcheck2,timelineDate,"出发"));
						fulfill = true;
					}
					if (DataBean.getRm().equals("null") && fulfill)
					{
						list.add(new TimeLineModel(R.drawable.nursingcareplan2,timelineDate,"离开"));
						fulfill = false;
					}
				}
				break;
			}
			output_statistics();
		}		
	};

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
	
	public void init()
	{
        output_statistics();
	}
}
