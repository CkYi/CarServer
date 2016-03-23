package yalantis.com.sidemenu.adapter;

import java.util.List;

import yalantis.com.sidemenu.adapter.TimeLineAdapter.ViewHold;
import yalantis.com.sidemenu.model.analysisModel;
import yalantis.com.sidemenu.sample.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AnalysisAdapter extends BaseAdapter  {
	Context context;
	List<analysisModel> list;

	public AnalysisAdapter(Context context, List<analysisModel> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		if (list!=null) {
			return list.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (list!=null) {
			return list.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHold hold;
		if (convertView==null) {
			hold=new ViewHold();
			convertView=LayoutInflater.from(context).inflate(R.layout.analysis_item, null);
			convertView.setTag(hold);
		}else {
			hold=(ViewHold) convertView.getTag();
		}
		hold.imageView=(ImageView) convertView.findViewById(R.id.analysis_image);
		hold.content=(TextView) convertView.findViewById(R.id.analysis_content);
		hold.unit=(TextView) convertView.findViewById(R.id.analysis_unit);
		
		hold.imageView.setImageResource(list.get(position).getImageview());
		hold.content.setText(list.get(position).getContent());
		hold.unit.setText(list.get(position).getUnit());
		return convertView;
	}
	
	static class ViewHold{
		public TextView content;
		public TextView unit;
		public ImageView imageView;
	}
}
