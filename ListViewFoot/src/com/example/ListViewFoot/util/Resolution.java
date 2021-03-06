package com.example.ListViewFoot.util;

/**
 * 处理有关分辨率的类
 * @author MichaelHuang
 *
 */
public class Resolution {
	private static Resolution instance;
	static {
		instance = new Resolution();
	}
	public static Resolution getInstance() {
		return instance;
	}

	private int screenWidth = -1;
	private int screenHeight = -1;
	private int statusHeight = -1;	//the height of status
 
	/**
	 * @return the statusHeight
	 */
	public int getStatusHeight() {
		return statusHeight;
	}

	/**
	 * @param statusHeight the statusHeight to set
	 */
	public void setStatusHeight(int statusHeight) {
		this.statusHeight = statusHeight;
	}

	/**
	 * @return the screenWidth
	 */
	public int getScreenWidth() {
		
		return screenWidth;
	}

	/**
	 * @param screenWidth the screenWidth to set
	 */
	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	/**
	 * @return the screenHeight
	 */
	public int getScreenHeight() {
		return screenHeight;
	}

	/**
	 * @param screenHeight the screenHeight to set
	 */
	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;

	}
	
	/**
	 * exchange the value of resolutions
	 */
	public void exchange() {
		this.screenWidth = this.screenWidth + this.screenHeight;
		this.screenHeight = this.screenWidth - this.screenHeight;
		this.screenWidth = this.screenWidth - this.screenHeight;
//		Logger.debug("screen width is :" + this.screenWidth + " and screen height is :" + this.screenHeight);
	}
	


}