package com.hlcxdg.digou.bean;

public class SPPC03Params {
	private String cStoreNo;
	private String SPActivity = "1";
	public String groupNo2 = "021";
	public Integer IndexNo = 0;
	public Integer Count = 12;
	public String DistanceFlag="1";
	public String PriceFlag="1";
	
	
	
	
	
	public String getGroupNo2() {
		return groupNo2;
	}

	public void setGroupNo2(String groupNo2) {
		this.groupNo2 = groupNo2;
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

	public String getDistanceFlag() {
		return DistanceFlag;
	}

	public void setDistanceFlag(String distanceFlag) {
		DistanceFlag = distanceFlag;
	}

	public String getPriceFlag() {
		return PriceFlag;
	}

	public void setPriceFlag(String priceFlag) {
		PriceFlag = priceFlag;
	}

	public String getcStoreNo() {
		return cStoreNo;
	}

	public void setcStoreNo(String cStoreNo) {
		this.cStoreNo = cStoreNo;
	}

	public String getSPActivity() {
		return SPActivity;
	}

	public void setSPActivity(String sPActivity) {
		SPActivity = sPActivity;
	}

}
