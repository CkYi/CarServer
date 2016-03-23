package yalantis.com.sidemenu.sample;

import java.util.ArrayList;
import java.util.List;

import yalantis.com.sidemenu.adapter.AnalysisAdapter;
import yalantis.com.sidemenu.model.analysisModel;
import android.view.View;
import android.widget.ListView;

public class AnalysisActivity {
	
	View rootView=null;    
    private AnalysisAdapter analysis_adapter;
    private ListView analysis_listview;
	
    public void output_analysis(List<analysisModel> analysis_list)
    {
    	if(analysis_list!=null)
    	{
    		analysis_adapter=new AnalysisAdapter(rootView.getContext(), analysis_list);    		
    		analysis_listview.setAdapter(analysis_adapter);
    	}
    }
    
    public void init()
    {		
    	List<analysisModel> analysis_list=new ArrayList<analysisModel>();
		analysis_list.add(new analysisModel(R.drawable.analysis_oil,"10.3","L/100KM"));
		analysis_list.add(new analysisModel(R.drawable.analysis_speed,"33.3","KM/h"));
		analysis_list.add(new analysisModel(R.drawable.analysis_distances,"278","KM"));
		
		output_analysis(analysis_list);		
		
    }
    
	public AnalysisActivity(View rootView)
	{
		this.rootView = rootView;
		analysis_listview=(ListView) rootView.findViewById(R.id.analysis_listview);
		init();
	}
}
