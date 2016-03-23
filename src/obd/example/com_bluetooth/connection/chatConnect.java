package obd.example.com_bluetooth.connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import android.bluetooth.BluetoothSocket;

/**
 * Created by Lenovo on 2015/10/23.
 */
public class chatConnect {

    private static Socket socketNET;
    private static BluetoothSocket socketBT = null;
    private	static BufferedWriter writerNET = null;
    private static BufferedReader readerNET = null;
    private	static BufferedWriter writerBT = null;
    private static BufferedReader readerBT = null;
    private static OutputStream outputNET = null;
    private static InputStream inputNET= null;
    public static BluetoothSocket getSocketBT(){
        return socketBT;
    }
    public static void setSocketBT(BluetoothSocket s){
        socketBT = s;
    }

    public static Socket getSocketNET(){
        return socketNET;
    }
    public static void setSocketNET(Socket s){
        socketNET = s;
    }

    public static BufferedWriter getWriterNET(){
        return writerNET;
    }
    public static void setWriterNET(BufferedWriter bw){
        writerNET = bw;
    }
    public static BufferedReader getReaderNET(){
        return readerNET;
    }
    public static void setReaderNET(BufferedReader br){
        readerNET = br;
    }

    public static BufferedWriter getWriterBT(){
        return writerBT;
    }
    public static void setWriterBT(BufferedWriter bw){
        writerBT = bw;
    }
    public static BufferedReader getReaderBT(){
        return readerBT;
    }
    public static void setReaderBT(BufferedReader br){
        readerBT = br;
    }

    public static InputStream getInputNET() {return inputNET;}
    public static void setInputNET(InputStream in){inputNET = in;}
    public static OutputStream getOutputNET(){return outputNET;}
    public static void setOutputNET(OutputStream ou){outputNET = ou;}
}
