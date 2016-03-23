package yalantis.com.sidemenu.detection_progress;

import java.util.ArrayList;
import java.util.List;

import yalantis.com.sidemenu.sample.R;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class DetectionActivity {

	View rootView=null;
	 private WaterWaveProgress waveProgress;
	 private int current_progress=0;
	 private static final int PRO=32767;
	 private SeekBar bar;	 
	 private TextView detection_fault;
	 private List tempList;
	 
	//汽车检测-进度条和水球动画刷新
		private Handler handler = new Handler(){  
	        @Override  
	       public void handleMessage(Message msg) {
	           super.handleMessage(msg);
	           switch (msg.what) {  
	           case PRO: 
	        	   if(current_progress<tempList.size())
	        	   {
	                     bar.setProgress(current_progress+1);
	                     waveProgress.setProgress(current_progress+1);
	                     detection_fault.setText(tempList.get(current_progress).toString());
	                     current_progress++;
	                     handler.sendEmptyMessageDelayed(PRO, 20);
	                     break;
	        	   }
	           default:  
	               break;
	           }  
	       }  
	   };
	   
	 //汽车检测--添加检测PID
	   public void init_analysis(List detection_list)
	   {		   
		   detection_list.add("P0101");
		   detection_list.add("P0102");
		   detection_list.add("P0103");
		   detection_list.add("P0104");
		   detection_list.add("P0105");
		   detection_list.add("P0106");
		   detection_list.add("P0107");
		   detection_list.add("P0108");
		   detection_list.add("P0109");
		   detection_list.add("P01010");
	   }   
	 
	   public void output_rst(List detection_list)
	   {
		   bar = (SeekBar) rootView.findViewById(R.id.detection_seekbar);
		   detection_fault = (TextView) rootView.findViewById(R.id.detection_fault);
			
			waveProgress = (WaterWaveProgress) rootView.findViewById(R.id.detection_waterWaveProgress);
			waveProgress.setShowProgress(true);
			waveProgress.animateWave();
			waveProgress.setShowProgress(true);
			waveProgress.setShowNumerical(true);
			if(detection_list!=null)
			{
				bar.setMax(detection_list.size());
				waveProgress.setMaxProgress(detection_list.size());
				
				tempList = detection_list;
				
			   handler.sendEmptyMessage(PRO);
			}
	   }
	   
	public DetectionActivity(View rootView)
	{
		this.rootView = rootView;
		List detection_list= new ArrayList();
		init_analysis(detection_list);		
		
		output_rst(detection_list);		
	}
}
