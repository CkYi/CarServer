package obd.example.com_bluetooth.Database;

import android.provider.BaseColumns;

/**
 * Created by Lenovo on 2015/11/8.
 */
public class FeedReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public FeedReaderContract() {}


    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Info";
        public static final String TABLE_NAME2 = "Data";

    }
}
