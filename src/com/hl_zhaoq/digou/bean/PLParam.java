package com.hl_zhaoq.digou.bean;

public class PLParam {
	private String Location="北京市";
	private double Longitude=116.3213060000;//
	private double Latitude=40.0411650000;//
	private int IndexNo=0;
	private int Num=10;

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public double getLongitude() {
		return Longitude;
	}

	public void setLongitude(double longitude) {
		Longitude = longitude;
	}

	public double getLatitude() {
		return Latitude;
	}

	public void setLatitude(double latitude) {
		Latitude = latitude;
	}

	public int getIndexNo() {
		return IndexNo;
	}

	public void setIndexNo(int indexNo) {
		IndexNo = indexNo;
	}

	public int getNum() {
		return Num;
	}

	public void setNum(int num) {
		Num = num;
	}

}
