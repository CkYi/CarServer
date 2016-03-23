package obd.example.com_bluetooth.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import obd.example.com_bluetooth.model.DataBean;

/**
 * Created by Lenovo on 2015/10/22.
 */
public class SetBT implements Runnable {
	private boolean BTConnect = false;
	private BluetoothDevice device = null;
	private BluetoothSocket socketBT = null;
	private BufferedWriter writerBT = null;
	private BufferedReader readerBT = null;

	public void setBTConnect(boolean bTConnect) {
		BTConnect = bTConnect;
	}

	public SetBT(BluetoothDevice device) {
		this.device = device;
	}

	public boolean BTConnect() {
		return BTConnect;
	}

	public void run() {
		new Thread() {
			@Override
			public void run() {
				try {
					BluetoothSocket tmp;
					Method method;
					DataBean.setBTaddr(device.toString());
					method = device.getClass().getMethod("createRfcommSocket", new Class[] { int.class });
					tmp = (BluetoothSocket) method.invoke(device, 1);
					/*
					 * check timer TimerTask task = new TimerTask() {
					 * 
					 * @Override public void run() { int a = 1; } };
					 * 
					 * Timer timer = new Timer(true);
					 * timer.schedule(task,500,5000);
					 */
					socketBT = tmp;
					System.out.println("connecting");
					socketBT.connect();
					System.out.println("connected?");
					BTConnect = socketBT.isConnected();
					System.out.println(BTConnect);
					if (BTConnect) {
						chatConnect.setSocketBT(socketBT);
						writerBT = new BufferedWriter(new OutputStreamWriter(socketBT.getOutputStream()));
						readerBT = new BufferedReader(new InputStreamReader(socketBT.getInputStream()));
						chatConnect.setReaderBT(readerBT);
						chatConnect.setWriterBT(writerBT);
					} else {
						// BTConnect == false

					}
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
}
