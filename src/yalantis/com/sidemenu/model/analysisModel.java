package yalantis.com.sidemenu.model;

public class analysisModel {
	private int imageview;
	private String content;
	private String unit;
	public int getImageview() {
		return imageview;
	}
	public void setImageview(int imageview) {
		this.imageview = imageview;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public analysisModel(int imageview, String content, String unit) {
		super();
		this.imageview = imageview;
		this.content = content;
		this.unit = unit;
	}
	public analysisModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
