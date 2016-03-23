package obd.example.com_bluetooth.model;

/**
 * Created by yanzhiyi on 15/12/15. Updata by zhengrenjie on 16/1/26
 */
public class DataBean {

	private static String air_speed = "null";
	private static String rm = "null";
	private static String spd = "null";
	private static String tv = "null";
	private static String cl = "null";
	private static String osv = "null";
	private static String os = "null";
	private static String BTaddr = "null";

	public static String getBTaddr() {
		return BTaddr;
	}

	public static void setBTaddr(String bTaddr) {
		BTaddr = bTaddr;
	}

	public static String getAir_speed() {
		return air_speed;
	}

	public static void setAir_speed(String air_speed) {
		DataBean.air_speed = air_speed;
	}

	public static String getRm() {
		return rm;
	}

	public static void setRm(String rm) {
		DataBean.rm = rm;
	}

	public static String getSpd() {
		return spd;
	}

	public static void setSpd(String spd) {
		DataBean.spd = spd;
	}

	public static String getTv() {
		return tv;
	}

	public static void setTv(String tv) {
		DataBean.tv = tv;
	}

	public static String getCl() {
		return cl;
	}

	public static void setCl(String cl) {
		DataBean.cl = cl;
	}

	public static String getOsv() {
		return osv;
	}

	public static void setOsv(String osv) {
		DataBean.osv = osv;
	}

	public static String getOs() {
		return os;
	}

	public static void setOs(String os) {
		DataBean.os = os;
	}

}
