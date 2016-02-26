package com.hlcxdg.digou.bean;

import java.math.BigDecimal;

public class CPUsing {
	public String userNo = "";
	public String CouponNo;
	public String OwnerID;
	public String CouponType;
	public String cStoreNo = "";
	public String cSaleSheetno;
	public BigDecimal	FMoney;
	public String getUserNO() {
		return userNo;
	}

	public void setUserNO(String userNO) {
		this.userNo = userNO;
	}

	public String getCouponNo() {
		return CouponNo;
	}

	public void setCouponNo(String couponNo) {
		CouponNo = couponNo;
	}

	public String getOwnerID() {
		return OwnerID;
	}

	public void setOwnerID(String ownerID) {
		OwnerID = ownerID;
	}

	public String getCouponType() {
		return CouponType;
	}

	public void setCouponType(String couponType) {
		CouponType = couponType;
	}

	public String getcStoreNo() {
		return cStoreNo;
	}

	public void setcStoreNo(String cStoreNo) {
		this.cStoreNo = cStoreNo;
	}

	public String getcSaleSheetno() {
		return cSaleSheetno;
	}

	public void setcSaleSheetno(String cSaleSheetno) {
		this.cSaleSheetno = cSaleSheetno;
	}
}
