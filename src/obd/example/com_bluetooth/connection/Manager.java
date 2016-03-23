package obd.example.com_bluetooth.connection;

import android.bluetooth.BluetoothDevice;
import android.os.Handler;

/**
 * Created by Lenovo on 2015/11/2.
 */
public class Manager {
	private Handler handler = null;
	private SetBT setBT = null;
	private GetThread getThread = null;
	private SendThread sendThread = null;

	public SetBT getSetBT() {
		return setBT;
	}

	public SendThread getSendThread() {
		return sendThread;
	}

	public GetThread getGetThread() {
		return getThread;
	}

	public Manager(Handler handler) {
		this.handler = handler;
	}

	public void OnConnectBT(BluetoothDevice device) {
		setBT = new SetBT(device);

		sendThread = new SendThread();
		getThread = new GetThread(handler);
		setBT.run();
	}

	public void Extract() {
		sendThread.run();
		getThread.run();
	}
}
