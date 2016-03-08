package com.hl_zhaoq.digou.bean;

public class SPStorePLParam {
	public String groupNo3;// 三类编号
	public Integer IndexNo;// 获取数据的开始索引
	public Integer Count=9;// 需要获取的数据条数
	public String StoreNo;
	public String PriceFlag="asc";// asc,desc,”0”表示没有要求,价格排序
	public String DistanceFlag="1";// “1”排序,”0”表示没有要求，距离排序
	public String SPActivity="1";// bit,--true为当前有效的促销活动，0为将来有效的促销活动
	public String getGroupNo3() {
		return groupNo3;
	}
	public void setGroupNo3(String groupNo3) {
		this.groupNo3 = groupNo3;
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
	public String getSPActivity() {
		return SPActivity;
	}
	public void setSPActivity(String sPActivity) {
		SPActivity = sPActivity;
	}

	
	
	
	
}
