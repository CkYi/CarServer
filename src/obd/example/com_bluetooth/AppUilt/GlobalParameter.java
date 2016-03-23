package obd.example.com_bluetooth.AppUilt;

import obd.example.com_bluetooth.Database.DBManager;

/**
 * Created by yanzhiyi on 15/12/30.
 */
public class GlobalParameter {

    public GlobalParameter() {
        mileage = 0;
        oilConsumption = 0;
    }

    private static String Mac;
    public static String getMac() {
        return Mac;
    }
    public static void setMac(String mac) {
        Mac = mac;
    }

    private static String phonenumber;
    public static String getPhonenumber() {
        return phonenumber;
    }
    public static void setPhonenumber(String phonenumber) {
        GlobalParameter.phonenumber = phonenumber;
    }

    private static DBManager dbManager;
    public static DBManager getDbManager() {
        return dbManager;
    }
    public static void setDbManager(DBManager dbManager) {
        GlobalParameter.dbManager = dbManager;
    }


    private static double mileage;

    public static double getMileage() { return mileage;}
    public static void setMileage(double mileage) { GlobalParameter.mileage = mileage;}


    private static double oilConsumption;
    public static double getOilConsumption() { return oilConsumption;}
    public static void setOilConsumption(int oilConsumption) { GlobalParameter.oilConsumption = oilConsumption;}

}
