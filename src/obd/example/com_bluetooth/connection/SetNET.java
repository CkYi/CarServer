//package com.example.com_bluetooth.connection;
//
//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//
///**
// * Created by Lenovo on 2015/11/2.
// */
//public class SetNET implements Runnable{
//    private OutputStream out = null;
//    private URL url = null;
//    private String strurl = "http://192.168.1.103:8080/Car_Experiment/carInsert_insert_info.action";
//    private boolean NETconnect = false;
//    public boolean NETconnect(){return NETconnect;}
//
//    public void run(){
//        new Thread(){
//            @Override
//            public void run() {
//                try {
//                    url = new URL(strurl);
//                    HttpURLConnection connection =(HttpURLConnection) url.openConnection();
//                    connection.setDoOutput(true);
//                    connection.setRequestMethod("POST");
//                    connection.setRequestProperty("ser-Agent", "Fiddler");
//                    connection.setRequestProperty("Content-Type", "application/json");
//                    out = connection.getOutputStream();
//                    chatConnect.setOutputNET(out);
//                    if(connection.getResponseCode() == 200)
//                    {
//                        NETconnect = true;
//                    }
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }
//}
