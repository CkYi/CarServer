//package com.example.com_bluetooth.connection;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import com.example.com_bluetooth.AppUilt.GlobalParameter;
//import com.example.com_bluetooth.model.DataBean;
//
//import android.os.Handler;
//
//public class MakeJSON {
//    private Handler handler;
//    private BufferedReader readerBT = null;
//    private int count = 0;
//    private int FIRST = 0;
//    private String id = "123456";
//    private String time=null;
//    private String oil_cos="null";
//    private String coolant=null;
//    private String tank_value=null;
//    private String rpm=null;
//    private String speed=null;
//    private String air_speed=null;
//    private String oxygen_sensor_volt1_channel1=null;
//    private JSONObject jsonObject = new JSONObject();
//
//    public JSONObject getJsonObject() {
//        return jsonObject;
//    }
//
//    public String getStrJSON() {
//        return strJSON;
//    }
//
//    private String strJSON = "null";
//
//    public MakeJSON(Handler handler){
//        this.handler = handler;
//    }
//    public void run() {
//        new Thread(){
//            @Override
//            public void run() {
//                try {
//                    String result;
//                    String head;
//                    while (true) {
//                        if(chatConnect.getReaderBT() == null && readerBT == null) {
//                            continue;
//                        }
//                        else if (readerBT == null){
//                            readerBT = chatConnect.getReaderBT();
//                        } else{
//                            if ((result = readerBT.readLine()) != null) {
//                                //result = readerBT.readLine();
//                                if(!result.contains("41 ")){
//                                    continue;
//                                }
//                                System.out.println(result);
//                                String[] array = result.split(" ");
//                                head = array[1];
//                                if(head.contains("14")){
//                                    oxygen_sensor_volt1_channel1 = array[2] + array[3];
//                                    jsonObject.put("14",oxygen_sensor_volt1_channel1);
//                                    DataBean.setOsv(Integer.parseInt(oxygen_sensor_volt1_channel1,16)/128);
//
//                                }
//                                else if(head.contains("0C")) {
//                                    FIRST++;
//                                    rpm = array[2] + array[3];
//                                    jsonObject.put("0C",rpm);
//                                    DataBean.setRm(Integer.parseInt(rpm, 16) / 4);
//
//                                }else if(head.contains("0D")){
//                                    speed = array[2];
//                                    jsonObject.put("0D",speed);
//                                    DataBean.setSpd(Integer.parseInt(speed, 16));
//
//                                }else if(head.contains("0B")){
//                                    tank_value = array[2];
//                                    jsonObject.put("0B",tank_value);
//                                    DataBean.setTv(Integer.parseInt(tank_value, 16));
//
//                                }else if(head.contains("05")){
//                                    coolant = array[2];
//                                    jsonObject.put("05",coolant);
//                                    DataBean.setCl(Integer.parseInt(coolant, 16));
//
//                                }else if(head.contains("10")){
//                                    air_speed = array[2] + array[3];
//                                    jsonObject.put("10",air_speed);
//                                    DataBean.setAir_speed(Integer.parseInt(air_speed, 16) / 100);
//
//                                }else  if(head.contains("5E")) {
//                                    oil_cos = array[2] + array[3];
//                                    jsonObject.put("5E", oil_cos);
//                                    System.out.println("/--------------//oil "+oil_cos);
//                                }
//                                //DBManager dbManager = new DBManager(get);
//
//
//                                count ++;
//                                if(count==7) {
//
//                                    if(oil_cos.equals("null")){
//                                        jsonObject.put("5E", "null");
//
//                                    }
//                                    if(speed == null){
//                                        jsonObject.put("0D", null);
//                                    }
//                                    if(rpm == null){
//                                        jsonObject.put("0C", null);
//                                    }
//                                    if(tank_value == null){
//                                        jsonObject.put("0B", null);
//                                    }
//                                    if(oxygen_sensor_volt1_channel1 == null){
//                                        jsonObject.put("14", null);
//                                    }
//                                    if(air_speed == null){
//                                        jsonObject.put("10", null);
//                                    }
//                                    if(coolant == null){
//                                        jsonObject.put("05", null);
//                                    }
//                                    count = 0;
//                                    Date date = new Date();
//                                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//                                    time = df.format(date);
//                                    jsonObject.put("id", id);
//                                    jsonObject.put("time",time);
//                                    jsonObject.put("PN", GlobalParameter.getDbManager().select_phnum());
//                                    strJSON = String.valueOf(jsonObject);
//                                    System.out.println(strJSON);
//                                    handler.sendEmptyMessage(1000);
//                                    System.out.println("run");
//                                }
//                            }
//                        }
//                        sleep(300);
//
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
//}
