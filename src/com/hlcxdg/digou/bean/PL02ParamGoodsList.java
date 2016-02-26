package com.hlcxdg.digou.bean;

public class PL02ParamGoodsList {
	public String cGoodsNo = "";// ��Ʒ���
	public Integer IndexOrder = 0;// ��ȡ���ݵĿ�ʼ����
	public Integer Count = 9;// ��Ҫ��ȡ����������
	public String PriceFlag = "0";// asc,desc,��0����ʾû��Ҫ��,�۸�����
	public String DistanceFlag = "0";// ��1������,��0����ʾû��Ҫ�󣬾�������
	public String PinpaiNo = "0"; // PinpaiNo,��0����ʾû��
	public String ActivityFlag = "0";// �ר������,��0����ʾû��
	public Float Longitude;
	public Float Latitude;

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

	public String getcGoodsNo() {
		return cGoodsNo;
	}

	public void setcGoodsNo(String cGoodsNo) {
		this.cGoodsNo = cGoodsNo;
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
