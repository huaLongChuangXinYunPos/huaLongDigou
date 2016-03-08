package com.hl_zhaoq.digou.bean;

public class StoreListParam {
	private String Area="北京市";//ѡ�������0��ʾû�У���ı�ʾ���������
	private String cStoreTypeNo="1001";
	public String cStoreNo="";
	private double Longitude=116.3213060000;//����
	private double Latitude=40.0411650000;//Ψ��
	private int IndexNo=0;
	private int Count=9;
	public String SPActivity;
	public String getArea() {
		return Area;
	}
	public void setArea(String area) {
		Area = area;
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
	public int getCount() {
		return Count;
	}
	public void setCount(int count) {
		Count = count;
	}
	public String getcStoreTypeNo() {
		return cStoreTypeNo;
	}
	public void setcStoreTypeNo(String cStoreTypeNo) {
		this.cStoreTypeNo = cStoreTypeNo;
	}
}
