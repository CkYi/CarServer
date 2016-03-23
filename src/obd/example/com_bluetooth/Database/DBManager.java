package obd.example.com_bluetooth.Database;

import android.content.Context;
import android.database.Cursor;

import java.util.Random;

/**
 * Created by Lenovo on 2015/11/10.
 */
public class DBManager {
    private FeedReaderDbHelper db;
    public DBManager(Context context){
        db = new FeedReaderDbHelper(context,FeedReaderDbHelper.DATABASE_NAME,null,FeedReaderDbHelper.DATABASE_VERSION);
    }

    public void update(String pn,String mac){
        db.update(pn, mac);
    }
    public String select_phnum(){
        Cursor cursor = db.select();
        cursor.moveToFirst();
        String result = cursor.getString(cursor.getColumnIndex("PhoneNumber"));
        return result;
    }
    public String select_Mac(){
        Cursor cursor = db.select();
        cursor.moveToFirst();
        String result = cursor.getString(cursor.getColumnIndex("MAC"));
        return result;
    }

    public void insertData(double t1,double t2,double t3,double t4,double t5,double t6){
        db.insert_data(t1,t2,t3,t4,t5,t6);
    }

}
