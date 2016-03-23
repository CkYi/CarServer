package yalantis.com.sidemenu.adapter;

import java.util.List;

import yalantis.com.sidemenu.model.WeiZhangListModel;
import yalantis.com.sidemenu.sample.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WeiZhangListAdapter extends BaseAdapter {
	
	private  List<WeiZhangListModel> mDate;
	private Context mContext;

	public WeiZhangListAdapter( Context mContext,List<WeiZhangListModel> mDate){
		this.mContext=mContext;
		this.mDate=mDate;		
	}
	

	@Override
	public int getCount() {
		return mDate.size();
	}

	@Override
	public Object getItem(int position) {
		return mDate.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = View.inflate(mContext, R.layout.weizhang_listitem_citys, null);
		
		//初始化
		WeiZhangListModel model=mDate.get(position) ;
		TextView txt_name =(TextView) view.findViewById(R.id.txt_name);
		//ImageView image=(ImageView)view.findViewById(R.id.iv_1);
		

		//绑定数据
		txt_name.setText(model.getTextName());
		txt_name.setTag(model.getNameId());
		//返回
		return view;
	}

}
