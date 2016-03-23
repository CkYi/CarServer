package obd.example.com_bluetooth.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Lenovo on 2015/11/8.
 */
public class FeedReaderDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MYDB";
    private static final String TEXT_TYPE = " TEXT";
    private static final String TEXT_TYPE2 = " DOUBLE";
    private static final String COMMA_SEP = ",";
    private static final String FIELD_TEXT_1 = "PhoneNumber";
    private static final String FIELD_TEXT_2 = "MAC";
    private static final String FIELD_TEXT_3 = "TYPE";
    private static final String FIELD_TEXT_4 = "air_speed";
    private static final String FIELD_TEXT_5 = "rpm";
    private static final String FIELD_TEXT_6 = "speed";
    private static final String FIELD_TEXT_7 = "tank_value";
    private static final String FIELD_TEXT_8 = "coolant";
    private static final String FIELD_TEXT_9 = "oxygen_sensor_volt1_channel1";
    private static final String FIELD_TEXT_10 = "";

    public FeedReaderDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //用户信息表


        String SQL_CREATE_ENTRIES = "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " ("
                +  FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
                + FIELD_TEXT_1 + TEXT_TYPE + COMMA_SEP
                + FIELD_TEXT_2 + TEXT_TYPE + COMMA_SEP
                + FIELD_TEXT_3
                + " );";
        db.execSQL(SQL_CREATE_ENTRIES);


        db.execSQL("INSERT INTO Info(_id,PhoneNumber,MAC,TYPE) values(0,'null','null','myphone')");

        SQL_CREATE_ENTRIES = "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME2 + " ("
                        +  FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP
                        + FIELD_TEXT_4 + TEXT_TYPE2 + COMMA_SEP
                        + FIELD_TEXT_5 + TEXT_TYPE2 + COMMA_SEP
                        + FIELD_TEXT_6 + TEXT_TYPE2 + COMMA_SEP
                        + FIELD_TEXT_7 + TEXT_TYPE2 + COMMA_SEP
                        + FIELD_TEXT_8 + TEXT_TYPE2 + COMMA_SEP
                        + FIELD_TEXT_9 + TEXT_TYPE2
                        + " );";

        db.execSQL(SQL_CREATE_ENTRIES);
    }



    public void update(String text_1,String text_2){
        SQLiteDatabase db = this.getReadableDatabase();
        String where = FIELD_TEXT_3 + "=?";
        String[] whereValue = {"myphone"};
        ContentValues cv = new ContentValues();
        cv.put(FIELD_TEXT_1,text_1);
        cv.put(FIELD_TEXT_2,text_2);
        db.update(FeedReaderContract.FeedEntry.TABLE_NAME,cv,where,whereValue);
    }

    public Cursor select(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query
                (FeedReaderContract.FeedEntry.TABLE_NAME,
                        new String[] {FIELD_TEXT_1,FIELD_TEXT_2},
                        FIELD_TEXT_3 + " =?",
                        new String[] {"myphone"},
                        null,
                        null,
                        null);
        return cursor;
    }

    public void insert_data(double text_1,double text_2,double text_3,double text_4,double text_5,double text_6){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FIELD_TEXT_4,text_1);
        cv.put(FIELD_TEXT_5,text_2);
        cv.put(FIELD_TEXT_6,text_3);
        cv.put(FIELD_TEXT_7,text_4);
        cv.put(FIELD_TEXT_8,text_5);
        cv.put(FIELD_TEXT_9,text_6);
        long row = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME2,null,cv);
        System.out.println("insert:" + row);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }



}
