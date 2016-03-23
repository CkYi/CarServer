package obd.example.com_bluetooth.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;
import android.util.Log;
import obd.example.com_bluetooth.model.DataBean;

/**
 * Created by Lenovo on 2015/10/22.
 */
public class GetThread implements Runnable {
	private Handler handler;
	private BufferedReader readerBT = null;
	private int count = 0;
	private String id = "15927653175";
	private String time;
	/*
	 * load:负载 abs:绝对值 temp:温度 en:引擎 envi:环境 press:压力 ctrl控制
	 * 
	 */
	// 2016/1/30/20:36

	private String oil_situation = "null";
	private String calculate_load = "null";
	private String coolant = "null";
	private String short_oil_update_1 = "null";
	private String long_oil_update_1 = "null";
	private String short_oil_update_2 = "null";
	private String long_oil_update_2 = "null";
	private String oil_press = "null";
	private String tank_value = "null";
	private String rpm = "null";
	private String speed = "null";
	private String ignition = "null";
	private String oiltank_air_temp = "null";
	private String air_speed = "null";
	private String throttle = "null";
	private String second_air = "null";
	private String oxygen_sensor = "null";
	private String oxygen_sensor_volt1_channel1 = "null";
	private String oxygen_sensor_volt2_channel1 = "null";
	private String oxygen_sensor_volt3_channel1 = "null";
	private String oxygen_sensor_volt4_channel1 = "null";
	private String oxygen_sensor_volt1_channel2 = "null";
	private String oxygen_sensor_volt2_channel2 = "null";
	private String oxygen_sensor_volt3_channel2 = "null";
	private String oxygen_sensor_volt4_channel2 = "null";
	private String en_startruntime = "null";
	private String oil_pressure_manifold = "null";
	private String oil_pressure_sprinkle = "null";
	private String oil_lvl = "null";
	private String steam_press = "null";
	private String pressure = "null";
	private String catalyzer_channel1_sensor1 = "null";
	private String catalyzer_channel2_sensor1 = "null";
	private String catalyzer_channel1_sensor2 = "null";
	private String catalyzer_channel2_sensor2 = "null";
	private String ctrl_module_voltage = "null";
	private String abs_load = "null";
	private String equivalent = "null";
	private String relative_throttle = "null";
	private String envi_oidtank_press = "null";
	private String absolute_throttle_B = "null";
	private String absolute_throttle_A = "null";
	private String accelarate_D = "null";
	private String accelarate_E = "null";
	private String accelarate_F = "null";
	private String throttle_control = "null";
	private String ethanol_percent = "null";
	private String absolute_oil_pressure = "null";
	private String relative_acc_position = "null";
	private String en_oil_temp = "null";
	private String oil_cos = "null";
	private String require_torque = "null";
	private String practical_torque = "null";
	private String refer_torque = "null";

	private boolean first = true;
	private boolean launch = false;
	private JSONObject jsonObject = new JSONObject();
	private String strJSON = "null";
	private SendThread sendThread = new SendThread();

	/*
	 * Manager use this method to get the data of Json;
	 */
	public String getStrJSON() {
		return strJSON;
	}

	public GetThread(Handler handler) {
		this.handler = handler;
	}

	public void run() {
		new Thread() {
			@Override
			public void run() {
				try {
					String result;
					String head;
					while (true) {
						if ((readerBT = chatConnect.getReaderBT()) == null) {
							continue;
						} else {
							if ((result = readerBT.readLine()) != null) {
								Log.e("~~~~~~~~~~~~~~", result);
								// if (result.contains("OK")) {
								// if (first == false) {
								// launch = true;
								// }
								// if (first == true) {
								// first = false;
								// }
								// }
								if (SendThread.pid_count == 0) {
									launch = true;
								}
								// System.out.println("pid_count=" +
								// SendThread.pid_count);
								// System.out.println("launch=" + launch);
								if (!result.contains("41 ")) {
									continue;
								}
								System.out.println(result);
								String[] array = result.split(" ");
								head = array[1];

								// 2016/1/30/11:25
								if (head.contains("03")) {
									oil_situation = array[2] + array[3];
									jsonObject.put("03", oil_situation);

								} else if (head.contains("04")) {
									calculate_load = array[2];
									jsonObject.put("04", calculate_load);

								} else if (head.contains("05")) {
									coolant = array[2];
									jsonObject.put("05", coolant);
									System.out.println("coolant----------" + (Integer.parseInt(coolant, 16) - 40));

								} else if (head.contains("06")) {
									short_oil_update_1 = array[2];
									jsonObject.put("06", short_oil_update_1);

								} else if (head.contains("07")) {
									long_oil_update_1 = array[2];
									jsonObject.put("07", long_oil_update_1);

								} else if (head.contains("08")) {
									short_oil_update_2 = array[2];
									jsonObject.put("08", short_oil_update_2);

								} else if (head.contains("09")) {
									long_oil_update_2 = array[2];
									jsonObject.put("09", long_oil_update_2);

								} else if (head.contains("0A")) {
									oil_press = array[2];
									jsonObject.put("0A", oil_press);

								} else if (head.contains("0B")) {
									tank_value = array[2];
									jsonObject.put("0B", tank_value);

									System.out.println("tank_value----------" + Integer.parseInt(tank_value, 16));

								} else if (head.contains("0C")) {
									rpm = array[2] + array[3];
									jsonObject.put("0C", rpm);
									DataBean.setRm(rpm);

									int rpm_A = Integer.parseInt(rpm.substring(0, 2), 16);
									int rpm_B = Integer.parseInt(rpm.substring(2, 4), 16);
									double rm = ((rpm_A * 255) + rpm_B) / 4;

									System.out.println("rpm----------" + rm);

								} else if (head.contains("0D")) {
									speed = array[2];
									jsonObject.put("0D", speed);
									DataBean.setSpd(speed);

									System.out.println("spd----------" + Integer.parseInt(speed, 16));

								} else if (head.contains("0E")) {
									ignition = array[2];
									jsonObject.put("0E", ignition);

								} else if (head.contains("0F")) {
									oiltank_air_temp = array[2];
									jsonObject.put("0F", oiltank_air_temp);

								} else if (head.contains("10")) {
									air_speed = array[2] + array[3];
									jsonObject.put("10", air_speed);
									int air_speed_A = Integer.parseInt(air_speed.substring(0, 2), 16);
									int air_speed_B = Integer.parseInt(air_speed.substring(2, 4), 16);
									double air_s = ((air_speed_A * 255) + air_speed_B) / 100;

									System.out.println("air_speed----------" + air_s);

								} else if (head.contains("11")) {
									throttle = array[2];
									jsonObject.put("11", throttle);

								} else if (head.contains("12")) {
									second_air = array[2];
									jsonObject.put("12", second_air);

								} else if (head.contains("13")) {
									oxygen_sensor = array[2];
									jsonObject.put("13", oxygen_sensor);

								} else if (head.contains("14")) {
									oxygen_sensor_volt1_channel1 = array[2] + array[3];
									jsonObject.put("14", oxygen_sensor_volt1_channel1);
								} else if (head.contains("15")) {
									oxygen_sensor_volt2_channel1 = array[2] + array[3];
									jsonObject.put("15", oxygen_sensor_volt2_channel1);

								} else if (head.contains("16")) {
									oxygen_sensor_volt3_channel1 = array[2] + array[3];
									jsonObject.put("16", oxygen_sensor_volt3_channel1);

								} else if (head.contains("17")) {
									oxygen_sensor_volt4_channel1 = array[2] + array[3];
									jsonObject.put("17", oxygen_sensor_volt4_channel1);

								} else if (head.contains("18")) {
									oxygen_sensor_volt1_channel2 = array[2] + array[3];
									jsonObject.put("18", oxygen_sensor_volt1_channel2);

								} else if (head.contains("19")) {
									oxygen_sensor_volt2_channel2 = array[2] + array[3];
									jsonObject.put("19", oxygen_sensor_volt2_channel2);

								} else if (head.contains("1A")) {
									oxygen_sensor_volt3_channel2 = array[2] + array[3];
									jsonObject.put("1A", oxygen_sensor_volt3_channel2);

								} else if (head.contains("1B")) {
									oxygen_sensor_volt4_channel2 = array[2] + array[3];
									jsonObject.put("1B", oxygen_sensor_volt4_channel2);

								} else if (head.contains("1F")) {
									en_startruntime = array[2] + array[3];
									jsonObject.put("1F", en_startruntime);

								} else if (head.contains("22")) {
									oil_pressure_manifold = array[2] + array[3];
									jsonObject.put("22", oil_pressure_manifold);

								} else if (head.contains("23")) {
									oil_pressure_sprinkle = array[2] + array[3];
									jsonObject.put("23", oil_pressure_sprinkle);

								} else if (head.contains("2F")) {
									oil_lvl = array[2];
									jsonObject.put("2F", oil_lvl);

								} else if (head.contains("32")) {
									steam_press = array[2] + array[3];
									jsonObject.put("32", steam_press);

								} else if (head.contains("33")) {
									pressure = array[2];
									jsonObject.put("33", pressure);

								} else if (head.contains("3C")) {
									catalyzer_channel1_sensor1 = array[2] + array[3];
									jsonObject.put("3C", catalyzer_channel1_sensor1);

								} else if (head.contains("3D")) {
									catalyzer_channel2_sensor1 = array[2] + array[3];
									jsonObject.put("3D", catalyzer_channel2_sensor1);

								} else if (head.contains("3E")) {
									catalyzer_channel1_sensor2 = array[2] + array[3];
									jsonObject.put("3E", catalyzer_channel1_sensor2);

								} else if (head.contains("3F")) {
									catalyzer_channel2_sensor2 = array[2] + array[3];
									jsonObject.put("3F", catalyzer_channel2_sensor2);

								} else if (head.contains("42")) {
									ctrl_module_voltage = array[2] + array[3];
									jsonObject.put("42", ctrl_module_voltage);

								} else if (head.contains("43")) {
									abs_load = array[2] + array[3];
									jsonObject.put("43", abs_load);

								} else if (head.contains("44")) {
									equivalent = array[2] + array[3];
									jsonObject.put("44", equivalent);

								} else if (head.contains("45")) {
									relative_throttle = array[2];
									jsonObject.put("45", relative_throttle);

								} else if (head.contains("46")) {
									envi_oidtank_press = array[2];
									jsonObject.put("46", envi_oidtank_press);

								} else if (head.contains("47")) {
									absolute_throttle_B = array[2];
									jsonObject.put("47", absolute_throttle_B);

								} else if (head.contains("48")) {
									absolute_throttle_A = array[2];
									jsonObject.put("48", absolute_throttle_A);

								} else if (head.contains("49")) {
									accelarate_D = array[2];
									jsonObject.put("49", accelarate_D);

								} else if (head.contains("4A")) {
									accelarate_E = array[2];
									jsonObject.put("4A", accelarate_E);

								} else if (head.contains("4B")) {
									accelarate_F = array[2];
									jsonObject.put("4B", accelarate_F);

								} else if (head.contains("4C")) {
									throttle_control = array[2];
									jsonObject.put("4C", throttle_control);

								} else if (head.contains("52")) {
									ethanol_percent = array[2];
									jsonObject.put("52", ethanol_percent);

								} else if (head.contains("59")) {
									absolute_oil_pressure = array[2] + array[3];
									jsonObject.put("59", absolute_oil_pressure);

								} else if (head.contains("5A")) {
									relative_acc_position = array[2];
									jsonObject.put("5A", relative_acc_position);

								} else if (head.contains("5C")) {
									en_oil_temp = array[2] + array[3];
									jsonObject.put("5C", en_oil_temp);

								} else if (head.contains("5E")) {
									oil_cos = array[2] + array[3];
									jsonObject.put("5E", oil_cos);

								} else if (head.contains("61")) {
									require_torque = array[2];
									jsonObject.put("61", require_torque);

								} else if (head.contains("62")) {
									practical_torque = array[2];
									jsonObject.put("62", practical_torque);

								} else if (head.contains("63")) {
									refer_torque = array[2] + array[3];
									jsonObject.put("63", refer_torque);
								}

								if (launch == true) {
									launch = false;
									if (oil_cos.equals("null")) {
										jsonObject.put("5E", "00");
									}
									if (speed == "null") {
										jsonObject.put("0D", "00");
									}
									if (rpm.equals("null")) {
										jsonObject.put("0C", "00");
									}
									if (tank_value.equals("null")) {
										jsonObject.put("0B", "00");
									}
									if (oxygen_sensor_volt1_channel1.equals("null")) {
										jsonObject.put("14", "00");
									}
									if (air_speed.equals("null")) {
										jsonObject.put("10", "00");
									}
									if (coolant.equals("null")) {
										jsonObject.put("05", "00");
									}

									if (calculate_load.equals("null")) {
										jsonObject.put("04", "00");
									}
									if (oil_press.equals("null")) {
										jsonObject.put("0A", "00");
									}
									if (oiltank_air_temp.equals("null")) {
										jsonObject.put("0F", "00");
									}

									if (en_startruntime.equals("null")) {
										jsonObject.put("1F", "00");
									}
									if (oil_lvl.equals("null")) {
										jsonObject.put("2F", "00");
									}
									if (steam_press.equals("null")) {
										jsonObject.put("32", "00");
									}
									if (ctrl_module_voltage.equals("null")) {
										jsonObject.put("42", "00");
									}
									if (abs_load.equals("null")) {
										jsonObject.put("43", "00");
									}
									if (envi_oidtank_press.equals("null")) {
										jsonObject.put("46", "00");
									}
									if (en_oil_temp.equals("null")) {
										jsonObject.put("5C", "00");
									}

									if (oil_situation == "null") {
										jsonObject.put("03", "00");
									}
									if (short_oil_update_1 == "null") {
										jsonObject.put("06", "00");
									}
									if (long_oil_update_1 == "null") {
										jsonObject.put("07", "00");
									}
									if (short_oil_update_2 == "null") {
										jsonObject.put("08", "00");
									}
									if (long_oil_update_2 == "null") {
										jsonObject.put("09", "00");
									}
									if (ignition == "null") {
										jsonObject.put("0E", "00");
									}
									if (throttle == "null") {
										jsonObject.put("11", "00");
									}
									if (second_air == "null") {
										jsonObject.put("12", "00");
									}
									if (oxygen_sensor == "null") {
										jsonObject.put("13", "00");
									}
									if (oxygen_sensor_volt2_channel1 == "null") {
										jsonObject.put("15", "00");
									}
									if (oxygen_sensor_volt3_channel1 == "null") {
										jsonObject.put("16", "00");
									}
									if (oxygen_sensor_volt4_channel1 == "null") {
										jsonObject.put("17", "00");
									}
									if (oxygen_sensor_volt1_channel2 == "null") {
										jsonObject.put("18", "00");
									}
									if (oxygen_sensor_volt2_channel2 == "null") {
										jsonObject.put("19", "00");
									}
									if (oxygen_sensor_volt3_channel2 == "null") {
										jsonObject.put("1A", "00");
									}
									if (oxygen_sensor_volt4_channel2 == "null") {
										jsonObject.put("1B", "00");
									}
									if (oil_pressure_manifold == "null") {
										jsonObject.put("22", "00");
									}
									if (oil_pressure_sprinkle == "null") {
										jsonObject.put("23", "00");
									}
									if (pressure == "null") {
										jsonObject.put("33", "00");
									}
									if (catalyzer_channel1_sensor1 == "null") {
										jsonObject.put("3C", "00");
									}
									if (catalyzer_channel2_sensor1 == "null") {
										jsonObject.put("3D", "00");
									}
									if (catalyzer_channel1_sensor2 == "null") {
										jsonObject.put("3E", "00");
									}
									if (catalyzer_channel2_sensor2 == "null") {
										jsonObject.put("3F", "00");
									}
									if (equivalent == "null") {
										jsonObject.put("44", "00");
									}
									if (relative_throttle == "null") {
										jsonObject.put("45", "00");
									}
									if (absolute_throttle_B == "null") {
										jsonObject.put("47", "00");
									}
									if (absolute_throttle_A == "null") {
										jsonObject.put("48", "00");
									}
									if (accelarate_D == "null") {
										jsonObject.put("49", "00");
									}
									if (accelarate_E == "null") {
										jsonObject.put("4A", "00");
									}
									if (accelarate_F == "null") {
										jsonObject.put("4B", "00");
									}
									if (throttle_control == "null") {
										jsonObject.put("4C", "00");
									}
									if (ethanol_percent == "null") {
										jsonObject.put("52", "00");
									}
									if (absolute_oil_pressure == "null") {
										jsonObject.put("59", "00");
									}
									if (relative_acc_position == "null") {
										jsonObject.put("5A", "00");
									}
									if (en_oil_temp == "null") {
										jsonObject.put("5C", "00");
									}
									if (require_torque == "null") {
										jsonObject.put("61", "00");
									}
									if (practical_torque == "null") {
										jsonObject.put("62", "00");
									}
									if (refer_torque == "null") {
										jsonObject.put("63", "00");
									}

									calculate_load = "null";
									oil_press = "null";
									oiltank_air_temp = "null";
									en_startruntime = "null";
									oil_lvl = "null";
									steam_press = "null";
									ctrl_module_voltage = "null";
									abs_load = "null";
									envi_oidtank_press = "null";
									oil_cos = "null";
									coolant = "null";
									tank_value = "null";
									rpm = "null";
									speed = "null";
									air_speed = "null";
									oxygen_sensor_volt1_channel1 = "null";

									// 2016/1/30/11:22
									oil_situation = "null";
									short_oil_update_1 = "null";
									long_oil_update_1 = "null";
									short_oil_update_2 = "null";
									long_oil_update_2 = "null";
									ignition = "null";
									throttle = "null";
									second_air = "null";
									oxygen_sensor = "null";
									oxygen_sensor_volt2_channel1 = "null";
									oxygen_sensor_volt3_channel1 = "null";
									oxygen_sensor_volt4_channel1 = "null";
									oxygen_sensor_volt1_channel2 = "null";
									oxygen_sensor_volt2_channel2 = "null";
									oxygen_sensor_volt3_channel2 = "null";
									oxygen_sensor_volt4_channel2 = "null";
									oil_pressure_manifold = "null";
									oil_pressure_sprinkle = "null";
									pressure = "null";
									catalyzer_channel1_sensor1 = "null";
									catalyzer_channel2_sensor1 = "null";
									catalyzer_channel1_sensor2 = "null";
									catalyzer_channel2_sensor2 = "null";
									equivalent = "null";
									relative_throttle = "null";
									absolute_throttle_B = "null";
									absolute_throttle_A = "null";
									accelarate_D = "null";
									accelarate_E = "null";
									accelarate_F = "null";
									throttle_control = "null";
									ethanol_percent = "null";
									absolute_oil_pressure = "null";
									relative_acc_position = "null";
									en_oil_temp = "null";
									require_torque = "null";
									practical_torque = "null";
									refer_torque = "null";

									Date date = new Date();
									SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									time = df.format(date);
									jsonObject.put("id", id);
									jsonObject.put("time", time);
									jsonObject.put("BTaddr", DataBean.getBTaddr());
									strJSON = String.valueOf(jsonObject);
									System.out.println(strJSON);
									handler.sendEmptyMessage(0x3000);
								}
							}
						}
						sleep(130);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}
}
