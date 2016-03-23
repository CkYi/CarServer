package yalantis.com.sidemenu.sample;

import java.io.Serializable;

import yalantis.com.sidemenu.weizhang.WeiZhangProvinceList;
import yalantis.com.sidemenu.weizhang.WeiZhangShortNameList;
import yalantis.com.sidemenu.weizhang.WeizhangResult;

import com.cheshouye.api.client.WeizhangClient;
import com.cheshouye.api.client.WeizhangIntentService;
import com.cheshouye.api.client.json.CarInfo;
import com.cheshouye.api.client.json.CityInfoJson;
import com.cheshouye.api.client.json.InputConfigJson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WeiZhangActivity{
	
	View rootView;
	
	private String defaultChepai = "鄂";
	
	TextView short_name;
	TextView query_city;
	View btn_cpsz;
	Button btn_query;

	EditText chepai_number;
	EditText chejia_number;
	EditText engine_number;
	
	// 行驶证图示
	private View popXSZ;

	public WeiZhangActivity(View rootView)
	{
		this.rootView = rootView;
		initWeizhangWindow();
	}

	public void initWeizhangWindow()
	{
//		Toast.makeText(rootView.getContext(), rootView.getContext().toString(), Toast.LENGTH_LONG).show();
		// 标题
		TextView txtTitle = (TextView) rootView.findViewById(R.id.txtTitle);
		txtTitle.setText("车辆违章查询");

				// ********************************************************
				Log.d("初始化服务代码","");
				Intent weizhangIntent = new Intent(rootView.getContext(), WeizhangIntentService.class);
				weizhangIntent.putExtra("appId",1429);// 申请的appId
				weizhangIntent.putExtra("appKey", "f7724dd7d2f8d1099fb0da4c9ddbb9d0 ");//申请的appKey	
				rootView.getContext().startService(weizhangIntent);
				// ********************************************************

				// 选择省份缩写
				query_city = (TextView) rootView.findViewById(R.id.cx_city);
				chepai_number = (EditText) rootView.findViewById(R.id.chepai_number);
				chejia_number = (EditText) rootView.findViewById(R.id.chejia_number);
				engine_number = (EditText) rootView.findViewById(R.id.engine_number);
				short_name = (TextView) rootView.findViewById(R.id.chepai_sz);

				// ----------------------------------------------

				btn_cpsz = (View) rootView.findViewById(R.id.btn_cpsz);
				btn_query = (Button) rootView.findViewById(R.id.btn_query);
				
				btn_cpsz.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(rootView.getContext(), WeiZhangShortNameList.class);
						intent.putExtra("select_short_name", short_name.getText());
						((Activity)rootView.getContext()).startActivityForResult(intent, 0);
					}
				});

				query_city.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(rootView.getContext(), WeiZhangProvinceList.class);
						((Activity)rootView.getContext()).startActivityForResult(intent, 1);
					}
				});

				btn_query.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						// 获取违章信息
						CarInfo car = new CarInfo();
						String quertCityStr = null;
						String quertCityIdStr = null;

						final String shortNameStr = short_name.getText().toString()
								.trim();
						final String chepaiNumberStr = chepai_number.getText()
								.toString().trim();
						if (query_city.getText() != null
								&& !query_city.getText().equals("")) {
							quertCityStr = query_city.getText().toString().trim();

						}

						if (query_city.getTag() != null
								&& !query_city.getTag().equals("")) {
							quertCityIdStr = query_city.getTag().toString().trim();
							car.setCity_id(Integer.parseInt(quertCityIdStr));
						}
						final String chejiaNumberStr = chejia_number.getText()
								.toString().trim();
						final String engineNumberStr = engine_number.getText()
								.toString().trim();

						Intent intent = new Intent();

						car.setChejia_no(chejiaNumberStr);
						car.setChepai_no(shortNameStr + chepaiNumberStr);

						car.setEngine_no(engineNumberStr);

						Bundle bundle = new Bundle();
						bundle.putSerializable("carInfo", car);
						intent.putExtras(bundle);

						boolean result = checkQueryItem(car);

						if (result) {
							intent.setClass(rootView.getContext(), WeizhangResult.class);
							rootView.getContext().startActivity(intent);

						}
					}
				});

				// 根据默认查询地城市id, 初始化查询项目
				// setQueryItem(defaultCityId, defaultCityName);
				short_name.setText(defaultChepai);

				// 显示隐藏行驶证图示
				popXSZ = (View) rootView.findViewById(R.id.popXSZ);
				popXSZ.setOnTouchListener(new popOnTouchListener());
				hideShowXSZ();
	}
	
	// 避免穿透导致表单元素取得焦点
	private class popOnTouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View arg0, MotionEvent arg1) {
				popXSZ.setVisibility(View.GONE);
				return true;
		}
	}
	
	public void setActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(data==null)
			return;
		if(requestCode==0)
		{
			Bundle bundle = data.getExtras();
			String ShortName = bundle.getString("short_name");
			short_name.setText(ShortName);
		}
		if(requestCode==1)
		{
			Bundle bundle1 = data.getExtras();
			String cityId = bundle1.getString("city_id");
			Log.e("!!!!!!!!!!!!", cityId);
			setQueryItem(Integer.parseInt(cityId));
		}
	}
	
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (data == null)
//			return;
//
//		switch (requestCode) {
//		case 0:
//			Bundle bundle = data.getExtras();
//			String ShortName = bundle.getString("short_name");
//			short_name.setText(ShortName);
//			break;
//		case 1:
//			Bundle bundle1 = data.getExtras();
//			// String cityName = bundle1.getString("city_name");
//			String cityId = bundle1.getString("city_id");
//			// query_city.setText(cityName);
//			// query_city.setTag(cityId);
//			// InputConfigJson inputConfig =
//			// WeizhangClient.getInputConfig(Integer.parseInt(cityId));
//			// System.out.println(inputConfig.toJson());
//			setQueryItem(Integer.parseInt(cityId));
//
//			break;
//		}
//	}

	// 根据城市的配置设置查询项目
	private void setQueryItem(int cityId) {

		InputConfigJson cityConfig = WeizhangClient.getInputConfig(cityId);

		// 没有初始化完成的时候;
		if (cityConfig != null) {
			CityInfoJson city = WeizhangClient.getCity(cityId);

			query_city.setText(city.getCity_name());
			query_city.setTag(cityId);

			int len_chejia = cityConfig.getClassno();
			int len_engine = cityConfig.getEngineno();

			View row_chejia = (View) rootView.findViewById(R.id.row_chejia);
			View row_engine = (View) rootView.findViewById(R.id.row_engine);

			// 车架号
			if (len_chejia == 0) {
				row_chejia.setVisibility(View.GONE);
			} else {
				row_chejia.setVisibility(View.VISIBLE);
				setMaxlength(chejia_number, len_chejia);
				if (len_chejia == -1) {
					chejia_number.setHint("请输入完整车架号");
				} else if (len_chejia > 0) {
					chejia_number.setHint("请输入车架号后" + len_chejia + "位");
				}
			}

			// 发动机号
			if (len_engine == 0) {
				row_engine.setVisibility(View.GONE);
			} else {
				row_engine.setVisibility(View.VISIBLE);
				setMaxlength(engine_number, len_engine);
				if (len_engine == -1) {
					engine_number.setHint("请输入完整车发动机号");
				} else if (len_engine > 0) {
					engine_number.setHint("请输入发动机后" + len_engine + "位");
				}
			}
		}
	}

	// 提交表单检测
	private boolean checkQueryItem(CarInfo car) {
		if (car.getCity_id() == 0) {
			Toast.makeText(rootView.getContext(), "请选择查询地", 0).show();
			return false;
		}

		if (car.getChepai_no().length() != 7) {
			Toast.makeText(rootView.getContext(), "您输入的车牌号有误", 0).show();
			return false;
		}

		if (car.getCity_id() > 0) {
			InputConfigJson inputConfig = WeizhangClient.getInputConfig(car
					.getCity_id());
			int engineno = inputConfig.getEngineno();
			int registno = inputConfig.getRegistno();
			int classno = inputConfig.getClassno();

			// 车架号
			if (classno > 0) {
				if (car.getChejia_no().equals("")) {
					Toast.makeText(rootView.getContext(), "输入车架号不为空", 0).show();
					return false;
				}

				if (car.getChejia_no().length() != classno) {
					Toast.makeText(rootView.getContext(), "输入车架号后" + classno + "位",
							0).show();
					return false;
				}
			} else if (classno < 0) {
				if (car.getChejia_no().length() == 0) {
					Toast.makeText(rootView.getContext(), "输入全部车架号", 0).show();
					return false;
				}
			}

			//发动机
			if (engineno > 0) {
				if (car.getEngine_no().equals("")) {
					Toast.makeText(rootView.getContext(), "输入发动机号不为空", 0).show();
					return false;
				}

				if (car.getEngine_no().length() != engineno) {
					Toast.makeText(rootView.getContext(),
							"输入发动机号后" + engineno + "位", 0).show();
					return false;
				}
			} else if (engineno < 0) {
				if (car.getEngine_no().length() == 0) {
					Toast.makeText(rootView.getContext(), "输入全部发动机号", 0).show();
					return false;
				}
			}

			// 注册证书编号
			if (registno > 0) {
				if (car.getRegister_no().equals("")) {
					Toast.makeText(rootView.getContext(), "输入证书编号不为空", 0).show();
					return false;
				}
				
				if (car.getRegister_no().length() != registno) {
					Toast.makeText(rootView.getContext(),
							"输入证书编号后" + registno + "位", 0).show();
					return false;
				}
			} else if (registno < 0) {
				if (car.getRegister_no().length() == 0) {
					Toast.makeText(rootView.getContext(), "输入全部证书编号", 0).show();
					return false;
				}
			}
			return true;
		}
		return false;

	}

	// 设置/取消最大长度限制
	private void setMaxlength(EditText et, int maxLength) {
		if (maxLength > 0) {
			et.setFilters(new InputFilter[] { new InputFilter.LengthFilter(
					maxLength) });
		} else { // 不限制
			et.setFilters(new InputFilter[] {});
		}
	}

	// 显示隐藏行驶证图示
	private void hideShowXSZ() {
		View btn_help1 = (View) rootView.findViewById(R.id.ico_chejia);
		View btn_help2 = (View) rootView.findViewById(R.id.ico_engine);
		Button btn_closeXSZ = (Button) rootView.findViewById(R.id.btn_closeXSZ);

		btn_help1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				popXSZ.setVisibility(View.VISIBLE);
			}
		});
		btn_help2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				popXSZ.setVisibility(View.VISIBLE);
			}
		});
		btn_closeXSZ.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				popXSZ.setVisibility(View.GONE);
			}
		});
	}
	
}
