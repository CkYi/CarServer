package obd.example.com_bluetooth.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Lenovo on 2015/10/22.
 */
public class SendThread implements Runnable {
	// private String strurl =
	// "http://121.40.193.93:8080/Car_Experiment/carInsert_insert_info.action";
	// private String strurl =
	// "http://192.168.1.105:8080/Car_Experiment/carInsert_insert_info.action";
	private static final ArrayList<String> CmdArray = new ArrayList<String>() { // unsafe
		// in
		// the
		// mult-thread
		{
			add("AT SP 8h\r\n");
		}

		// 2016/1/30/20:04
		// {
		// add("0103\r\n");// 燃油系统状态
		// }
		//
		// {
		// add("0104\r\n"); // 引擎计算负载
		// }
		//
		// {
		// add("0105\r\n"); // 引擎冷却液
		// }

		{
			add("0106\r\n");// 短期燃油修正 通道1
		}

		{
			add("0107\r\n");// 长期燃油修正 通道1
		}

		// {
		// add("0108\r\n");// 短期燃油修正 通道2
		// }
		//
		// {
		// add("0109\r\n");// 长期燃油修正 通道2
		// }
		//
		// {
		// add("010A\r\n"); // 燃油压力
		// }

		{
			add("010B\r\n");// 油箱压力绝对值
		}

		{
			add("010C\r\n");//
		}

		{
			add("010D\r\n");
		}

		// {
		// add("010E\r\n");// 点火提前角值
		// }
		//
		// {
		// add("010F\r\n");// 油箱空气温度
		// }

		{
			add("0110\r\n");// MAF空气流量速率
		}

		{
			add("0111\r\n");// 节气门位置（检测发动机怠速或负荷）
		}

		// {
		// add("0112\r\n");// 二次进气状态
		// }
		//
		// {
		// add("0113\r\n");// 氧传感器情况（净化尾气）
		// }
		//
		// {
		// add("0114\r\n");//
		// }
		//
		// {
		// add("0115\r\n");// 通道1氧传感器电压2
		// }
		//
		// {
		// add("0116\r\n");// 通道1氧传感器电压3
		// }
		//
		// {
		// add("0117\r\n");// 通道1氧传感器电压4
		// }
		//
		// {
		// add("0118\r\n");// 通道2氧传感器电压1
		// }
		//
		// {
		// add("0119\r\n");// 通道2氧传感器电压2
		// }
		//
		// {
		// add("011A\r\n");// 通道2氧传感器电压3
		// }
		//
		// {
		// add("011B\r\n");// 通道2氧传感器电压4
		// }
		//
		// {
		// add("0122\r\n");// 油轨压力（歧管）
		// }
		//
		// {
		// add("011F\r\n");// 引擎启动后运行时间
		// }
		//
		// {
		// add("0123\r\n");// 油轨压力（直喷）
		// }

		{
			add("012F\r\n");// 油量液位情况
		}

		// {
		// add("0132\r\n"); // 系统蒸汽压力
		// }
		//
		// {
		// add("0133\r\n");// 大气压
		// }
		//
		// {
		// add("013C\r\n");// 催化剂温度 通道1 传感器1
		// }
		//
		// {
		// add("013D\r\n");// 催化剂温度 通道2 传感器1
		// }
		//
		// {
		// add("013E\r\n");// 催化剂温度 通道1 传感器2
		// }
		//
		// {
		// add("013F\r\n");// 催化剂温度 通道2 传感器2
		// }
		//
		// {
		// add("0142\r\n"); // 控制模块电压
		// }
		//
		// {
		// add("0143\r\n"); // 绝对载荷
		// }
		//
		// {
		// add("0144\r\n");// 等效比
		// }
		//
		// {
		// add("0145\r\n");// 相对节气门位置
		// }
		//
		// {
		// add("0146\r\n"); // 环境空气温度
		// }
		//
		// {
		// add("0147\r\n");// 绝对节气门位置B
		// }
		//
		// {
		// add("0148\r\n");// 绝对节气门位置C
		// }

		// {
		// add("0149\r\n");// 加速踏板位置D
		// }
		//
		// {
		// add("014A\r\n");// 加速踏板位置E
		// }

		// {
		// add("014B\r\n");// 加速踏板位置F
		// }
		//
		// {
		// add("014C\r\n");// 油门控制值
		// }
		//
		// {
		// add("0152\r\n");// 乙醇百分比
		// }
		//
		// {
		// add("0159\r\n");// 绝对油轨压力
		// }
		//
		// {
		// add("015A\r\n");// 相对加速踏板位置
		// }
		//
		// {
		// add("015C\r\n");// 引擎油温
		// }
		//
		// {
		// add("015E\r\n");// 引擎油量消耗速率
		// }
		//
		// {
		// add("0161\r\n");// 驾驶者要求引擎的扭矩百分比
		// }
		//
		// {
		// add("0162\r\n");// 实际引擎扭矩百分比
		// }
		//
		// {
		// add("0163\r\n");// 引擎参考扭矩
		// }

	};

	public static int pid_count = 0;
	private BufferedWriter writerBT = null;
	private OutputStream out = null;
	private String cmd = "null";
	private String strJSON = "null";

	public void setStrJSON(String str) {
		strJSON = str;
	}

	public void run() {
		new Thread() {
			@Override
			public void run() {

				try {
					while (true) {
						if ((writerBT = chatConnect.getWriterBT()) == null) {
							continue;
						} else {
							cmd = CmdArray.get(pid_count);
							pid_count++;
							if (pid_count == 9) {
								pid_count = 0;
							}
							if (!cmd.equals("null")) {
								System.out.println(cmd);
								writerBT.write(cmd);
								writerBT.flush();
								cmd = "null";
							}

							if (!strJSON.equals("null")) {
								System.out.println("strJSON ready to launch!" + strJSON);
								HttpURLConnection conn = null;
								String content = "";
								try {// 为了测试取消连接
										// Thread.sleep(5000);
										// http联网可以用httpClient或java.net.url
										// URL url = new URL(
										//
										//
										// "http://115.28.34.52:8080/Car_Experiment/carInsert_insert_info.action");
										// carInsert_insert_info.action"
									URL url = new URL(
											"http://120.27.126.68:8080/Car_Experiment/carInsert_insert_info.action");
									conn = (HttpURLConnection) url.openConnection();
									conn.setDoInput(true);
									conn.setDoOutput(true);
									conn.setConnectTimeout(1000 * 30);
									conn.setReadTimeout(1000 * 30);
									conn.setRequestMethod("GET");
									conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
									OutputStream output = conn.getOutputStream();
									// JSONObject json = new JSONObject();
									// json.put("10", "05");
									// json.put("0C", "05");
									// json.put("0D", "05");
									// json.put("0B", "05");
									// json.put("05", "05");
									// json.put("14", "05");
									// json.put("PN", "05");
									// System.out.println("json.toString()");
									// String str =
									//
									//
									// "{\"10\":\"lala\",\"0C\":\"lala\",\"0D\":\"lala\",\"0B\":\"lala\",\"05\":\"lala\",\"14\":\"lala\",\"PN\":\"lala\"}";
									// 向对象输出流写出数据，这些数据将存到内存缓冲区中

									output.write(strJSON.getBytes());

									// output.write(json.toString().getBytes());
									// System.out.println("json.toString()=" +
									// json.toString());
									// 刷新对象输出流，将任何字节都写入潜在的流中（些处为ObjectOutputStream）
									output.flush();
									output.close();
									// 关闭流对象。此时，不能再向对象输出流写入任何数据，先前写入的数据存在于内存缓冲区中,
									// 在调用下边的getInputStream()函数时才把准备好的http请求正式发送到服务器
									// objOutputStrm.close();
									InputStream in = conn.getInputStream();
									int responseCode = conn.getResponseCode();
									if (responseCode == HttpURLConnection.HTTP_OK) {
										System.out.println("responseCode=" + HttpURLConnection.HTTP_OK);
										// InputStreamReader reader = new
										// InputStreamReader(in,charSet);
										BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
										String line = null;
										while ((line = reader.readLine()) != null) {
											content += line;
										}
										System.out.println("result:" + content);
									}
									in.close();
								} catch (Exception e) {
									e.printStackTrace();
								} finally {
									conn.disconnect();
									conn = null;
								}
								strJSON = "null";
							}
						}
						sleep(150);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
