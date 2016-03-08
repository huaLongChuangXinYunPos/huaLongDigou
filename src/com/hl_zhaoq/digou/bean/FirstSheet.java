package com.hl_zhaoq.digou.bean;

public class FirstSheet {
	public String cStoreNo;

	public String userNo;

	public FirstSheet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FirstSheet(String userNo, String cStoreNo) {
		super();
		this.cStoreNo = cStoreNo;
		this.userNo = userNo;
	}

	public String getcStoreNo() {
		return cStoreNo;
	}

	public void setcStoreNo(String cStoreNo) {
		this.cStoreNo = cStoreNo;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

}
