package com.hlcxdg.digou.bean;

public class StoreType {
	
	
	public StoreType(String cStoreTypeNo, String cStoreTypeName) {
		super();
		this.cStoreTypeNo = cStoreTypeNo;
		this.cStoreTypeName = cStoreTypeName;
	}
	public String cStoreTypeNo;
	public String getcStoreTypeNo() {
		return cStoreTypeNo;
	}
	public void setcStoreTypeNo(String cStoreTypeNo) {
		this.cStoreTypeNo = cStoreTypeNo;
	}
	public String getcStoreTypeName() {
		return cStoreTypeName;
	}
	public void setcStoreTypeName(String cStoreTypeName) {
		this.cStoreTypeName = cStoreTypeName;
	}
	public String cStoreTypeName;
}
