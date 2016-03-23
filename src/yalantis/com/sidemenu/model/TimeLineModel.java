package yalantis.com.sidemenu.model;

import java.util.Date;


public class TimeLineModel {

	private int imageview;
	private String time;
	private String text;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getImageview() {
		return imageview;
	}

	public void setImageview(int imageview) {
		this.imageview = imageview;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public TimeLineModel(int imageview, String time, String text) {
		super();
		this.imageview = imageview;
		this.time = time;
		this.text = text;
	}

	public TimeLineModel() {
		super();
		// TODO Auto-generated constructor stub
	}

}
