package com.hl_zhaoq.digou.bean;

public class StorePLParam {
	public String groupNo3 = "";
	public Integer IndexOrder = 0;
	public Integer Count = 9;
	public String StoreNo = "00";

	public String PriceFlag = "0";// asc,desc, //��0����ʾû��Ҫ��,�۸�����
	public String DistanceFlag = "0";// ��1������, ��0����ʾû��Ҫ�󣬾�������

	public String PinpaiNo = "0";// PinpaiNo,��0����ʾû��
	public String ActivityFlag = "0";// �ר�����룬��0����ʾû��

	
	
	
	
	public String getGroupNo3() {
		return groupNo3;
	}

	public void setGroupNo3(String groupNo3) {
		this.groupNo3 = groupNo3;
	}

	public Integer getIndexOrder() {
		return IndexOrder;
	}

	public void setIndexOrder(Integer indexOrder) {
		IndexOrder = indexOrder;
	}

	public Integer getCount() {
		return Count;
	}

	public void setCount(Integer count) {
		Count = count;
	}

	public String getStoreNo() {
		return StoreNo;
	}

	public void setStoreNo(String storeNo) {
		StoreNo = storeNo;
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

	public String getPinpaiNo() {
		return PinpaiNo;
	}

	public void setPinpaiNo(String pinpaiNo) {
		PinpaiNo = pinpaiNo;
	}

	public String getActivityFlag() {
		return ActivityFlag;
	}

	public void setActivityFlag(String activityFlag) {
		ActivityFlag = activityFlag;
	}

}
