package com.hlcxdg.digou.bean;

public class SearchBean {
	public String StoreNo="00"; // ”00”,”10001”
	public String Name; // :"坚果"
	public Integer IndexNo; // 0，9，18
	public Integer Count=9; // 9,单次获取数据的个数
	public Float Longitude; // 经度
	public Float Latitude; // 纬度
	public String PriceFlag="asc";// ”asc”,”desc” 是否按照价格排序
	public String DistanceFlag="asc"; // ”asc”,”desc”， 按照距离排序

	public String getStoreNo() {
		return StoreNo;
	}

	public void setStoreNo(String storeNo) {
		StoreNo = storeNo;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Integer getIndexNo() {
		return IndexNo;
	}

	public void setIndexNo(Integer indexNo) {
		IndexNo = indexNo;
	}

	public Integer getCount() {
		return Count;
	}

	public void setCount(Integer count) {
		Count = count;
	}

	public Float getLongitude() {
		return Longitude;
	}

	public void setLongitude(Float longitude) {
		Longitude = longitude;
	}

	public Float getLatitude() {
		return Latitude;
	}

	public void setLatitude(Float latitude) {
		Latitude = latitude;
	}

	public String getPriceFlag() {
		return PriceFlag;
	}

	public void setPriceFlag(String priceFlag) {
		PriceFlag = priceFlag;
	}

	public String getDistanceFlag() {
		return DistanceFlag;
	}

	public void setDistanceFlag(String distanceFlag) {
		DistanceFlag = distanceFlag;
	}
}
