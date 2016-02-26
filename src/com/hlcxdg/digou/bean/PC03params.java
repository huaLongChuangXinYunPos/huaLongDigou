package com.hlcxdg.digou.bean;

public class PC03params {
	public String cStoreNo = "10001";
	public String groupNo2 = "021";
	private String SPActivity = "1";
	public Integer IndexOrder = 0;
	public Integer Count = 12;

	public String getStoreNo() {
		return cStoreNo;
	}

	public void setStoreNo(String cstoreNo) {
		cStoreNo = cstoreNo;
	}

	public String getGroupNo2() {
		return groupNo2;
	}

	public void setGroupNo2(String groupNo2) {
		this.groupNo2 = groupNo2;
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

}
