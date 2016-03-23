package yalantis.com.sidemenu.sample;

import java.util.ArrayList;
import java.util.List;

import obd.example.com_bluetooth.model.DataBean;
import yalantis.com.sidemenu.driving_monitor.HighlightCR;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

public class DashboardActivity {

	View rootView = null;
	private DashboardView dashboardView_rpm = null;
	private DashboardView dashboardView_speed = null;
	private float last_speed=0.0f;
	private float last_rpm=0.0f;
	
	public DashboardActivity(){}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0x2000: {
				System.out.println("------------------------------------------!!!lalala");
				removeMessages(0x2000);
				float realtimerpm = 0.0f;
				if (!DataBean.getRm().equals("null")) {
					int rpm = Integer.parseInt(DataBean.getRm(), 16) / 4;
					int temprpm=rpm/100;
					realtimerpm = (float) temprpm / 10.0f;
				} else {
					realtimerpm = 0.0f;
				}
				float realtimespd = 0.0f;
				if (!DataBean.getSpd().equals("null")) {
					int spd = Integer.parseInt(DataBean.getSpd(), 16);
					realtimespd = (float) spd;
				} else {
					realtimespd = 0.0f;
				}
				Log.e("dash~~~~~~~~~~~~~~~~", String.valueOf(realtimerpm)+"  "+String.valueOf(realtimespd));
				last_speed = realtimespd;
				last_rpm = realtimerpm;
				dashboardView_rpm.setRealTimeValueWithAnim(realtimerpm);
				dashboardView_speed.setRealTimeValueWithAnim(realtimespd);
			}
				break;
			}
		}
	};

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public DashboardActivity(View rootView) {
		this.rootView = rootView;
		dashboardView_rpm = (DashboardView) rootView.findViewById(R.id.dashboard_view_rpm);
		dashboardView_speed = (DashboardView) rootView.findViewById(R.id.dashboard_view_speed);
		init();
	}

	public void init() {
		List<HighlightCR> highlight_rpm = new ArrayList<HighlightCR>();
		List<HighlightCR> highlight_speed = new ArrayList<HighlightCR>();

		setStatistics(highlight_rpm, highlight_speed);
	}

	public void setStatistics(List<HighlightCR> highlight_rpm, List<HighlightCR> highlight_speed) {
		if (highlight_rpm != null && highlight_speed != null) {
			float realtimerpm = 0.0f;
			if (!DataBean.getRm().equals("null")) {
				int rpm = Integer.parseInt(DataBean.getRm(), 16) / 4;
				int temprpm=rpm/100;
				realtimerpm = (float) temprpm / 10.0f;
			} else {
				realtimerpm = 0.0f;
			}
			float realtimespd = 0.0f;
			if (!DataBean.getSpd().equals("null")) {
				int spd = Integer.parseInt(DataBean.getSpd(), 16);
				realtimespd = (float) spd;
			} else {
				realtimespd = 0.0f;
			}
			highlight_rpm.add(new HighlightCR(180, 180, Color.parseColor("#03A9F4")));
			dashboardView_rpm.setStripeHighlightColorAndRange(highlight_rpm);
			dashboardView_rpm.setPostfix("×1000r/min");
			dashboardView_rpm.setMaxValue(10);

			highlight_speed.add(new HighlightCR(170, 140, Color.parseColor("#607D8B")));
			highlight_speed.add(new HighlightCR(310, 60, Color.parseColor("#795548")));
			dashboardView_speed.setStripeHighlightColorAndRange(highlight_speed);

			last_speed = realtimespd;
			last_rpm = realtimerpm;
			setValue(realtimerpm, realtimespd);
		}
	}

	public void setValue(float rpm, float speed) {
		dashboardView_rpm.setRealTimeValueWithAnim(rpm);
		dashboardView_speed.setRealTimeValueWithAnim(speed);
	}
}
