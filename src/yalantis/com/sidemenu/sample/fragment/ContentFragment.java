package yalantis.com.sidemenu.sample.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import yalantis.com.sidemenu.detection_progress.DetectionActivity;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.sample.AnalysisActivity;
import yalantis.com.sidemenu.sample.DashboardActivity;
import yalantis.com.sidemenu.sample.DistancesStatisticsActivity;
import yalantis.com.sidemenu.sample.MainActivity;
import yalantis.com.sidemenu.sample.R;
import yalantis.com.sidemenu.sample.WeiZhangActivity;

public class ContentFragment extends Fragment implements ScreenShotable {
	public static final String CLOSE = "Close";
	public static final String driving = "driving";
	public static final String distances = "distances";
	public static final String analysis = "analysis";
	public static final String detection = "detection";
	public static final String red_green = "red_green";

	private Activity mActivity;

	private View containerView;
	// 主页图片
	protected ImageView mImageView;
	// item号
	protected int res = -1;
	public Bitmap bitmap;
	private Intent intent;

	// 仪表盘
	private DashboardActivity dashboardActivity = null;
	private Handler handlerManageDash = null;

	// 里程统计
	private DistancesStatisticsActivity distancesStatisticsActivity = null;

	// 驾驶分析
	private AnalysisActivity analysisActivity = null;

	// 汽车检测
	private DetectionActivity detectionActivity = null;

	// 汽车违章
	private WeiZhangActivity weiZhangActivity = null;

	public WeiZhangActivity getWeiZhangActivity() {
		return weiZhangActivity;
	}

	public void setWeiZhangActivity(WeiZhangActivity weiZhangActivity) {
		this.weiZhangActivity = weiZhangActivity;
	}

	public static ContentFragment newInstance(int resId) {
		ContentFragment contentFragment = new ContentFragment();
		Bundle bundle = new Bundle();
		bundle.putInt(Integer.class.getName(), resId);
		contentFragment.setArguments(bundle);
		return contentFragment;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		this.containerView = view.findViewById(R.id.container);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		res = getArguments().getInt(Integer.class.getName());
		// Toast.makeText(getContext(), String.valueOf(res),
		// Toast.LENGTH_LONG).show();
		// if(res==1)
		// {
		// Toast.makeText(getContext(), String.valueOf(res),
		// Toast.LENGTH_LONG).show();
		// intent=new Intent();
		// intent.setClass(getContext(), oil_Action.class);
		// getContext().startActivity(intent);
		// }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// View rootView = inflater.inflate(R.layout.fragment_main, container,
		// false);
		View rootView = null;
		// Toast.makeText(getContext(), String.valueOf(res),
		// Toast.LENGTH_LONG).show();
		// if(res==2130837567)//主页图片的ID号会随机变换
		// {
		// rootView = inflater.inflate(R.layout.fragment_main, container,
		// false);
		// mImageView = (ImageView) rootView.findViewById(R.id.image_content);
		// mImageView.setClickable(true);
		// mImageView.setFocusable(true);
		// mImageView.setImageResource(res);
		// }
		// 行驶监控
		if (res == 1) {
			System.out.println("------------------------------------------1");
			rootView = inflater.inflate(R.layout.driving_fragment, container, false);
			dashboardActivity = new DashboardActivity(rootView);
			((MainActivity) mActivity).setHandlerDash(dashboardActivity.getHandler());
		}
		// 里程统计
		else if (res == 2) {
			System.out.println("------------------------------------------2");
			rootView = inflater.inflate(R.layout.distances_timeline, container, false);
			distancesStatisticsActivity = new DistancesStatisticsActivity(rootView);
			((MainActivity) mActivity).setHandlerDist(distancesStatisticsActivity.getHandler());
		}
		// 驾驶行为分析
		else if (res == 3) {
			System.out.println("------------------------------------------3");
			rootView = inflater.inflate(R.layout.analysis_listview, container, false);
			analysisActivity = new AnalysisActivity(rootView);
		}
		// 汽车检测
		else if (res == 4) {
			System.out.println("------------------------------------------4");
			rootView = inflater.inflate(R.layout.detection_fragment, container, false);
			detectionActivity = new DetectionActivity(rootView);
		}
		// 汽车违章查询
		else if (res == 5) {
			System.out.println("------------------------------------------5");
			rootView = inflater.inflate(R.layout.weizhang_activity_main, container, false);
			weiZhangActivity = new WeiZhangActivity(rootView);
		}
		// 返回主界面
		else {
			rootView = inflater.inflate(R.layout.fragment_main, container, false);
			mImageView = (ImageView) rootView.findViewById(R.id.image_content);
			mImageView.setClickable(true);
			mImageView.setFocusable(true);
			mImageView.setImageResource(res);
		}
		return rootView;
	}

	@Override
	public void takeScreenShot() {
		Thread thread = new Thread() {
			@Override
			public void run() {
				if (res > 10) {
					Bitmap bitmap = Bitmap.createBitmap(containerView.getWidth(), containerView.getHeight(),
							Bitmap.Config.ARGB_8888);
					Canvas canvas = new Canvas(bitmap);
					containerView.draw(canvas);
					ContentFragment.this.bitmap = bitmap;
				}
			}
		};
		thread.start();
	}

	@Override
	public Bitmap getBitmap() {
		return bitmap;
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mActivity = (MainActivity) activity;
	}
}
